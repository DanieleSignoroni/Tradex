package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.RdoInstallazione;
import it.thera.thip.cs.ResultSetIterator;

public class RdoInstallazioniRsIterator extends ResultSetIterator {

	public RdoInstallazioniRsIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		RdoInstallazione installazione = (RdoInstallazione) Factory.createObject(RdoInstallazione.class);
		installazione.setIdRdoInstallazione(cursor.getInt("ID_Rdo_Installazione"));
		installazione.setIdRdo(cursor.getInt("ID_Rdo"));
		installazione.setNumeroRdo(cursor.getString("Numero_Rdo"));
		installazione.setIdInstallazione(cursor.getInt("ID_Installazione"));
		installazione.setArticoloPh(cursor.getString("Articolo_PH"));
		installazione.setNomeInstallazione(cursor.getString("Nome_Installazione"));
		installazione.setNomeListino(cursor.getString("Nome_Listino"));
		installazione.setDescrizioneRdo(cursor.getString("Descrizione_Rdo"));
		installazione.setQuantita(cursor.getInt("Quantita"));
		installazione.setPrezzoBase(cursor.getDouble("Prezzo_Base"));
		installazione.setNumeroRdod(cursor.getString("Numero_Rdod"));
		installazione.setDescrizioneRdod(cursor.getString("Descrizione_Rdod"));
		installazione.setPrezzoIniziale(cursor.getDouble("Prezzo_Iniziale"));
		installazione.setPrezzoFinale(cursor.getDouble("Prezzo_Finale"));
		installazione.setScontoRdod(cursor.getDouble("Sconto_RDOD"));
		installazione.setCreatedAt(cursor.getDate("created_at"));
		installazione.setUpdatedAt(cursor.getDate("updated_at"));
		return installazione;
	}


}
