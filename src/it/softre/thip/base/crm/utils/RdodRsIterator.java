package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.Rdod;
import it.thera.thip.cs.ResultSetIterator;

public class RdodRsIterator extends ResultSetIterator {

	public RdodRsIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		Rdod rdod = (Rdod) Factory.createObject(Rdod.class);
		rdod.setIdRdod(cursor.getInt("ID_Rdod"));
		rdod.setIdOpportunity(cursor.getInt("ID_Opportunity"));
		rdod.setIdAnagrafica(cursor.getInt("ID_Anagrafica"));
		rdod.setCodCliPh(cursor.getString("Cod_Cli_Ph"));
		rdod.setNumeroRdo(cursor.getString("Numero_Rdo"));
		rdod.setRevInzN(cursor.getString("Rev_Inz_N"));
		rdod.setNumeroRdod(cursor.getString("Numero_Rdod"));
		rdod.setRagioneSociale(cursor.getString("Ragione_Sociale"));
		rdod.setIndirizzo(cursor.getString("Indirizzo"));
		rdod.setLocalita(cursor.getString("Localita"));
		rdod.setCap(cursor.getInt("Cap"));
		rdod.setProv(cursor.getString("Prov"));
		rdod.setIndirizzoSedeDestinazione(cursor.getString("Indirizzo_Sede_Destinazione"));
		rdod.setLocalitaSedeDestinazione(cursor.getString("Localita_Sede_Destinazione"));
		rdod.setCapSedeDestinazione(cursor.getInt("Cap_Sede_Destinazione"));
		rdod.setProvSedeDestinazione(cursor.getString("Prov_Sede_Destinazione"));
		rdod.setCorrispondenzaSedi(cursor.getInt("Corrispondenza_Sedi"));
		rdod.setTelefono(cursor.getString("Telefono"));
		rdod.setEmail(cursor.getString("Email"));
		rdod.setRiferimento(cursor.getString("Riferimento"));
		rdod.setEmailRiferimento(cursor.getString("Email_Riferimento"));
		rdod.setRiferimentoSecondo(cursor.getString("Riferimento_Secondo"));
		rdod.setEmailRiferimentoSecondo(cursor.getString("Email_Riferimento_Secondo"));
		rdod.setRiferimentoNuovo(cursor.getString("Riferimento_Nuovo"));
		rdod.setEmailNuovoRiferimento(cursor.getString("Email_Nuovo_Riferimento"));
		rdod.setRifAmm(cursor.getString("Rif_Amm"));
		rdod.setEmailRifAmm(cursor.getString("Email_Rif_Amm"));
		rdod.setRifAcquisti(cursor.getString("Rif_Acquisti"));
		rdod.setEmailRifAcquisti(cursor.getString("Email_Rif_Acquisti"));
		rdod.setRifStabilimento(cursor.getString("Rif_Stabilimento"));
		rdod.setEmailRifStabilimento(cursor.getString("Email_Rif_Stabilimento"));
		rdod.setRifProduzione(cursor.getString("Rif_Produzione"));
		rdod.setEmailRifProduzione(cursor.getString("Email_Rif_Produzione"));
		rdod.setPartitaIva(cursor.getString("Partita_IVA"));
		rdod.setCodiceSdi(cursor.getString("Codice_SDI"));
		rdod.setEmailPec(cursor.getString("Email_PEC"));
		rdod.setModalitaPagamento(cursor.getString("Modalita_Pagamento"));
		rdod.setIstitutoBancarioCliente(cursor.getString("Istituto_Bancario_Cliente"));
		rdod.setIban(cursor.getString("IBAN"));
		rdod.setDataConsegna(cursor.getDate("Data_Consegna"));
		rdod.setModalitaConsegna(cursor.getString("Modalita_Consegna"));
		rdod.setCausaliTrasporto(cursor.getString("Causali_Trasporto"));
		rdod.setAgentePrincipale(cursor.getString("Agente_Principale"));
		rdod.setSubagente(cursor.getString("Subagente"));
		rdod.setNote(cursor.getString("Note"));
		rdod.setAllegatoRdc(cursor.getString("Allegato_RDC"));
		rdod.setAllegatoSchedaB(cursor.getString("Allegato_Scheda_B"));
		rdod.setAllegatoOffertaFirmata(cursor.getString("Allegato_Offerta_Firmata"));
		rdod.setTabella(cursor.getString("Tabella"));
		rdod.setStatoInterscambio(cursor.getString("Stato_Interscambio").charAt(0));
		rdod.setKeyPth(cursor.getString("Key_Pth"));
		rdod.setCreatedAt(cursor.getDate("created_at"));
		rdod.setUpdatedAt(cursor.getDate("updated_at"));
		
		//nuovi fields
		rdod.setCodice_Fiscale(cursor.getString("Codice_Fiscale"));
		rdod.setCodice_Nazionalita(cursor.getString("Codice_Nazionalita"));
		return rdod;
	}

}
