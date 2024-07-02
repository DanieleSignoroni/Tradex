package it.softre.thip.base.crm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.RdoAccessorio;
import it.softre.thip.base.crm.RdoInstallazione;
import it.softre.thip.base.crm.RdoProdotto;
import it.softre.thip.base.crm.RdoTotale;
import it.softre.thip.base.crm.Rdod;
import it.thera.thip.vendite.ordineVE.OrdineVendita;

/**
 * Classe di utility per la comunicazione con il database crm_easytradex.<br>
 * Utilizzarla tramite metodo {@link #getInstance()}.<br></br>
 * Offre alcune utilities per il collegamento, come ottenere una connessione, leggere tabelle o effettuare scritture.<br></br>
 * 
 * Il database usa il driver <b>com.mysql.cj.jdbc.Driver</b> per la connessione, quindi e' richiesto averlo nel class path.<br>
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 02/07/2024
 * <br><br>
 * <b>71XXX	DSSOF3	02/07/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class ImportOrdiniCrmUtils {

	private static final String URL = "jdbc:mysql://192.168.10.132:3306/crm_easytradex?serverTimezone=UTC";
	private static final String USER = "easytradex_db";
	private static final String PASSWORD = "Fpd_U2xWG$";
	//private static final String DB_NAME = "crm_easytradex";
	//private static final String PORT = "3306";

	private static ImportOrdiniCrmUtils instance = null;

	private static final String STMT_RDOD = "SELECT * FROM rdod WHERE Stato_Interscambio IS NULL AND Key_Pth IS NULL ";	
	private static final String STMT_RDO_PRODOTTI = "SELECT * FROM rdo_prodotti WHERE ID_Rdo = ? ";
	private static final String STMT_RDO_ACCESSORI = "SELECT * FROM rdo_accessori WHERE ID_Rdo = ? ";
	private static final String STMT_RDO_INSTALLAZIONI = "SELECT * FROM rdo_installazioni WHERE ID_Rdo = ? ";
	private static final String STMT_RDO_TOTALE = "SELECT * FROM rdo_totale WHERE ID_Rdo = ? ";

	private static final String STMT_UPT_RDOD = "UPDATE rdod SET Stato_Interscambio = ? , Key_Pth = ? WHERE ID_Rdo = ? ";

	public static ImportOrdiniCrmUtils getInstance() {
		if(instance == null) {
			instance = (ImportOrdiniCrmUtils) Factory.createObject(ImportOrdiniCrmUtils.class);
		}
		return instance;
	}

	public Connection getCrmEasyTradexConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}

	public int flaggaRdodProcessata(Connection connessione, Integer idRdod, OrdineVendita ordine) {
		int risUpdt = 0;
		PreparedStatement ps = null;
		try {
			ps = connessione.prepareStatement(STMT_UPT_RDOD);
			ps.setString(1, String.valueOf(idRdod));
			ps.setString(2, ordine.getKey());
			ps.setInt(3,idRdod);
			risUpdt = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return risUpdt;
	}

	public List<Rdod> leggiRdodsCrmEasytradex(Connection connessione){
		List<Rdod> rdods = new ArrayList<Rdod>();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		RdodRsIterator rdodIterator = null;
		try {
			ps = connessione.prepareStatement(STMT_RDOD);
			rs = ps.executeQuery();
			rdodIterator = new RdodRsIterator(rs);
			while(rdodIterator.hasNext()) {
				Rdod rdod = (Rdod) rdodIterator.next();

				rdod.setProdotti(leggiProdotti(connessione, rdod.getIdRdod()));
				rdod.setAccessori(leggiAccessori(connessione, rdod.getIdRdod()));
				rdod.setInstallazioni(leggiInstallazioni(connessione, rdod.getIdRdod()));
				rdod.setTotale(leggiTotale(connessione, rdod.getIdRdod()));

				rdods.add(rdod);
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rdodIterator != null) {
				try {
					rdodIterator.closeCursor();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return rdods;
	}

	protected List<RdoProdotto> leggiProdotti(Connection connessione, int idRdod){
		List<RdoProdotto> prodotti = new ArrayList<RdoProdotto>();
		ResultSet rsChild = null;
		RdoProdottiRsIterator prodottiIterator = null;
		try {
			PreparedStatement ps = connessione.prepareStatement(STMT_RDO_PRODOTTI);
			ps.setInt(1,idRdod);
			rsChild = ps.executeQuery();
			prodottiIterator = new RdoProdottiRsIterator(rsChild);
			while(prodottiIterator.hasNext()) {
				RdoProdotto prodotto = (RdoProdotto) prodottiIterator.next();
				prodotti.add(prodotto);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(prodottiIterator != null) {
					prodottiIterator.closeCursor();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return prodotti;
	}

	protected List<RdoAccessorio> leggiAccessori(Connection connessione, int idRdod){
		List<RdoAccessorio> prodotti = new ArrayList<RdoAccessorio>();
		ResultSet rsChild = null;
		RdoAccessoriRsIterator prodottiIterator = null;
		try {
			PreparedStatement ps = connessione.prepareStatement(STMT_RDO_ACCESSORI);
			ps.setInt(1,idRdod);
			rsChild = ps.executeQuery();
			prodottiIterator = new RdoAccessoriRsIterator(rsChild);
			while(prodottiIterator.hasNext()) {
				RdoAccessorio prodotto = (RdoAccessorio) prodottiIterator.next();
				prodotti.add(prodotto);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(prodottiIterator != null) {
					prodottiIterator.closeCursor();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return prodotti;
	}

	protected List<RdoInstallazione> leggiInstallazioni(Connection connessione, int idRdod){
		List<RdoInstallazione> prodotti = new ArrayList<RdoInstallazione>();
		ResultSet rsChild = null;
		RdoInstallazioniRsIterator prodottiIterator = null;
		try {
			PreparedStatement ps = connessione.prepareStatement(STMT_RDO_INSTALLAZIONI);
			ps.setInt(1,idRdod);
			rsChild = ps.executeQuery();
			prodottiIterator = new RdoInstallazioniRsIterator(rsChild);
			while(prodottiIterator.hasNext()) {
				RdoInstallazione prodotto = (RdoInstallazione) prodottiIterator.next();
				prodotti.add(prodotto);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(prodottiIterator != null) {
					prodottiIterator.closeCursor();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return prodotti;
	}

	protected RdoTotale leggiTotale(Connection connessione, int idRdod) {
		RdoTotale totale = null;
		ResultSet rsChild = null;
		RdoTotaleRsIterator prodottiIterator = null;
		try {
			PreparedStatement ps = connessione.prepareStatement(STMT_RDO_TOTALE);
			ps.setInt(1,idRdod);
			rsChild = ps.executeQuery();
			prodottiIterator = new RdoTotaleRsIterator(rsChild);
			while(prodottiIterator.hasNext()) {
				RdoTotale prodotto = (RdoTotale) prodottiIterator.next();
				totale = prodotto;
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(prodottiIterator != null) {
					prodottiIterator.closeCursor();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return totale;
	}

}
