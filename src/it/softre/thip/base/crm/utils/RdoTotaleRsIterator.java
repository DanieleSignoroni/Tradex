package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.RdoTotale;
import it.thera.thip.cs.ResultSetIterator;

public class RdoTotaleRsIterator extends ResultSetIterator {

	public RdoTotaleRsIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		RdoTotale totale = (RdoTotale) Factory.createObject(RdoTotale.class);
		totale.setIdRdoTotale(cursor.getInt("ID_Rdo_Totale"));
		totale.setIdRdo(cursor.getInt("ID_Rdo"));
		totale.setNumeroRdo(cursor.getString("Numero_Rdo"));
		totale.setTotaleCalcolato(cursor.getDouble("Totale_Calcolato"));
		totale.setTotaleRiservato(cursor.getDouble("Totale_Riservato"));
		totale.setNumeroRdod(cursor.getString("Numero_Rdod"));
		totale.setPrezzoIniziale(cursor.getDouble("Prezzo_Iniziale"));
		totale.setPrezzoFinale(cursor.getDouble("Prezzo_Finale"));
		totale.setScontoRdod(cursor.getDouble("Sconto_RDOD"));
		totale.setTipologia(cursor.getInt("Tipologia"));
		totale.setDurataNoleggio(cursor.getString("Durata_Noleggio"));
		totale.setCreatedAt(cursor.getDate("created_at"));
		totale.setUpdatedAt(cursor.getDate("updated_at"));
		return totale;
	}

}
