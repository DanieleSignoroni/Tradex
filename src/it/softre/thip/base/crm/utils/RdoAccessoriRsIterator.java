package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.RdoAccessorio;
import it.thera.thip.cs.ResultSetIterator;

public class RdoAccessoriRsIterator extends ResultSetIterator{

	public RdoAccessoriRsIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		RdoAccessorio accessorio = (RdoAccessorio) Factory.createObject(RdoAccessorio.class);
        accessorio.setIdRdoAccessori(cursor.getInt("ID_Rdo_Accessori"));
        accessorio.setIdRdo(cursor.getInt("ID_Rdo"));
        accessorio.setNumeroRdo(cursor.getString("Numero_Rdo"));
        accessorio.setIdAccessorio(cursor.getInt("ID_Accessorio"));
        accessorio.setArticoloPh(cursor.getString("Articolo_PH"));
        accessorio.setNomeAccessorio(cursor.getString("Nome_Accessorio"));
        accessorio.setNomeListino(cursor.getString("Nome_Listino"));
        accessorio.setDescrizioneRdo(cursor.getString("Descrizione_Rdo"));
        accessorio.setQuantita(cursor.getInt("Quantita"));
        accessorio.setSconto(cursor.getInt("Sconto"));
        accessorio.setImportoSconto(cursor.getInt("Importo_Sconto"));
        accessorio.setPrezzoBase(cursor.getDouble("Prezzo_Base"));
        accessorio.setPrezzoScontato(cursor.getDouble("Prezzo_Scontato"));
        accessorio.setPrezzoLibero(cursor.getDouble("Prezzo_Libero"));
        accessorio.setPrezzoDefinitivo(cursor.getDouble("Prezzo_Definitivo"));
        accessorio.setNumeroRdod(cursor.getString("Numero_Rdod"));
        accessorio.setDescrizioneRdod(cursor.getString("Descrizione_Rdod"));
        accessorio.setPrezzoIniziale(cursor.getDouble("Prezzo_Iniziale"));
        accessorio.setPrezzoFinale(cursor.getDouble("Prezzo_Finale"));
        accessorio.setScontoRdod(cursor.getDouble("Sconto_RDOD"));
        accessorio.setAccessoriProdotto(cursor.getInt("Accessori_Prodotto"));
        accessorio.setCreatedAt(cursor.getDate("created_at"));
        accessorio.setUpdatedAt(cursor.getDate("updated_at"));
        return accessorio;
	}

}
