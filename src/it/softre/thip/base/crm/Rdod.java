package it.softre.thip.base.crm;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ClienteVendita;

public class Rdod {

	protected String idAzienda;
	protected int idRdod;
	protected Integer idOpportunity;
	protected Integer idAnagrafica;
	protected String codCliPh;
	protected String numeroRdo;
	protected String revInzN;
	protected String numeroRdod;
	protected String ragioneSociale;
	protected String indirizzo;
	protected String localita;
	protected Integer cap;
	protected String prov;
	protected String indirizzoSedeDestinazione;
	protected String localitaSedeDestinazione;
	protected Integer capSedeDestinazione;
	protected String provSedeDestinazione;
	protected Integer corrispondenzaSedi;
	protected String telefono;
	protected String email;
	protected String riferimento;
	protected String emailRiferimento;
	protected String riferimentoSecondo;
	protected String emailRiferimentoSecondo;
	protected String riferimentoNuovo;
	protected String emailNuovoRiferimento;
	protected String rifAmm;
	protected String emailRifAmm;
	protected String rifAcquisti;
	protected String emailRifAcquisti;
	protected String rifStabilimento;
	protected String emailRifStabilimento;
	protected String rifProduzione;
	protected String emailRifProduzione;
	protected String Codice_Nazionalita;
	protected String partitaIva;
	protected String Codice_Fiscale;
	protected String codiceSdi;
	protected String emailPec;
	protected String modalitaPagamento;
	protected String istitutoBancarioCliente;
	protected String iban;
	protected Date dataConsegna;
	protected String modalitaConsegna;
	protected String causaliTrasporto;
	protected String agentePrincipale;
	protected String subagente;
	protected String note;
	protected String allegatoRdc;
	protected String allegatoSchedaB;
	protected String allegatoOffertaFirmata;
	protected String tabella;
	protected Character statoInterscambio;
	protected String keyPth;
	protected Date createdAt;
	protected Date updatedAt;

	protected List<RdoProdotto> prodotti = null;
	protected List<RdoInstallazione> installazioni = null;
	protected List<RdoAccessorio> accessori = null;
	protected RdoTotale totale = null;
	
	protected ClienteVendita cliente = null;

	public ClienteVendita getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVendita cliente) {
		this.cliente = cliente;
	}

	public String getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
	}

	public Rdod() {
		prodotti = new ArrayList<RdoProdotto>();
		installazioni = new ArrayList<RdoInstallazione>();
		accessori = new ArrayList<RdoAccessorio>();
		totale = new RdoTotale();
		idAzienda = Azienda.getAziendaCorrente();
	}
	
	public List<RdoProdotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<RdoProdotto> prodotti) {
		this.prodotti = prodotti;
	}

	public List<RdoInstallazione> getInstallazioni() {
		return installazioni;
	}

	public void setInstallazioni(List<RdoInstallazione> installazioni) {
		this.installazioni = installazioni;
	}

	public List<RdoAccessorio> getAccessori() {
		return accessori;
	}

	public void setAccessori(List<RdoAccessorio> accessori) {
		this.accessori = accessori;
	}

	public RdoTotale getTotale() {
		return totale;
	}

	public void setTotale(RdoTotale totale) {
		this.totale = totale;
	}

	public int getIdRdod() {
		return idRdod;
	}
	public void setIdRdod(int idRdod) {
		this.idRdod = idRdod;
	}
	public Integer getIdOpportunity() {
		return idOpportunity;
	}
	public void setIdOpportunity(Integer idOpportunity) {
		this.idOpportunity = idOpportunity;
	}
	public Integer getIdAnagrafica() {
		return idAnagrafica;
	}
	public void setIdAnagrafica(Integer idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}
	public String getCodCliPh() {
		return codCliPh;
	}
	public void setCodCliPh(String codCliPh) {
		this.codCliPh = codCliPh;
	}
	public String getNumeroRdo() {
		return numeroRdo;
	}
	public void setNumeroRdo(String numeroRdo) {
		this.numeroRdo = numeroRdo;
	}
	public String getRevInzN() {
		return revInzN;
	}
	public void setRevInzN(String revInzN) {
		this.revInzN = revInzN;
	}
	public String getNumeroRdod() {
		return numeroRdod;
	}
	public void setNumeroRdod(String numeroRdod) {
		this.numeroRdod = numeroRdod;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public Integer getCap() {
		return cap;
	}
	public void setCap(Integer cap) {
		this.cap = cap;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getIndirizzoSedeDestinazione() {
		return indirizzoSedeDestinazione;
	}
	public void setIndirizzoSedeDestinazione(String indirizzoSedeDestinazione) {
		this.indirizzoSedeDestinazione = indirizzoSedeDestinazione;
	}
	public String getLocalitaSedeDestinazione() {
		return localitaSedeDestinazione;
	}
	public void setLocalitaSedeDestinazione(String localitaSedeDestinazione) {
		this.localitaSedeDestinazione = localitaSedeDestinazione;
	}
	public Integer getCapSedeDestinazione() {
		return capSedeDestinazione;
	}
	public void setCapSedeDestinazione(Integer capSedeDestinazione) {
		this.capSedeDestinazione = capSedeDestinazione;
	}
	public String getProvSedeDestinazione() {
		return provSedeDestinazione;
	}
	public void setProvSedeDestinazione(String provSedeDestinazione) {
		this.provSedeDestinazione = provSedeDestinazione;
	}
	public Integer getCorrispondenzaSedi() {
		return corrispondenzaSedi;
	}
	public void setCorrispondenzaSedi(Integer corrispondenzaSedi) {
		this.corrispondenzaSedi = corrispondenzaSedi;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	public String getEmailRiferimento() {
		return emailRiferimento;
	}
	public void setEmailRiferimento(String emailRiferimento) {
		this.emailRiferimento = emailRiferimento;
	}
	public String getRiferimentoSecondo() {
		return riferimentoSecondo;
	}
	public void setRiferimentoSecondo(String riferimentoSecondo) {
		this.riferimentoSecondo = riferimentoSecondo;
	}
	public String getEmailRiferimentoSecondo() {
		return emailRiferimentoSecondo;
	}
	public void setEmailRiferimentoSecondo(String emailRiferimentoSecondo) {
		this.emailRiferimentoSecondo = emailRiferimentoSecondo;
	}
	public String getRiferimentoNuovo() {
		return riferimentoNuovo;
	}
	public void setRiferimentoNuovo(String riferimentoNuovo) {
		this.riferimentoNuovo = riferimentoNuovo;
	}
	public String getEmailNuovoRiferimento() {
		return emailNuovoRiferimento;
	}
	public void setEmailNuovoRiferimento(String emailNuovoRiferimento) {
		this.emailNuovoRiferimento = emailNuovoRiferimento;
	}
	public String getRifAmm() {
		return rifAmm;
	}
	public void setRifAmm(String rifAmm) {
		this.rifAmm = rifAmm;
	}
	public String getEmailRifAmm() {
		return emailRifAmm;
	}
	public void setEmailRifAmm(String emailRifAmm) {
		this.emailRifAmm = emailRifAmm;
	}
	public String getRifAcquisti() {
		return rifAcquisti;
	}
	public void setRifAcquisti(String rifAcquisti) {
		this.rifAcquisti = rifAcquisti;
	}
	public String getEmailRifAcquisti() {
		return emailRifAcquisti;
	}
	public void setEmailRifAcquisti(String emailRifAcquisti) {
		this.emailRifAcquisti = emailRifAcquisti;
	}
	public String getRifStabilimento() {
		return rifStabilimento;
	}
	public void setRifStabilimento(String rifStabilimento) {
		this.rifStabilimento = rifStabilimento;
	}
	public String getEmailRifStabilimento() {
		return emailRifStabilimento;
	}
	public void setEmailRifStabilimento(String emailRifStabilimento) {
		this.emailRifStabilimento = emailRifStabilimento;
	}
	public String getRifProduzione() {
		return rifProduzione;
	}
	public void setRifProduzione(String rifProduzione) {
		this.rifProduzione = rifProduzione;
	}
	public String getEmailRifProduzione() {
		return emailRifProduzione;
	}
	public void setEmailRifProduzione(String emailRifProduzione) {
		this.emailRifProduzione = emailRifProduzione;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getCodiceSdi() {
		return codiceSdi;
	}
	public void setCodiceSdi(String codiceSdi) {
		this.codiceSdi = codiceSdi;
	}
	public String getEmailPec() {
		return emailPec;
	}
	public void setEmailPec(String emailPec) {
		this.emailPec = emailPec;
	}
	public String getModalitaPagamento() {
		return modalitaPagamento;
	}
	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}
	public String getIstitutoBancarioCliente() {
		return istitutoBancarioCliente;
	}
	public void setIstitutoBancarioCliente(String istitutoBancarioCliente) {
		this.istitutoBancarioCliente = istitutoBancarioCliente;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public Date getDataConsegna() {
		return dataConsegna;
	}
	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
	public String getModalitaConsegna() {
		return modalitaConsegna;
	}
	public void setModalitaConsegna(String modalitaConsegna) {
		this.modalitaConsegna = modalitaConsegna;
	}
	public String getCausaliTrasporto() {
		return causaliTrasporto;
	}
	public void setCausaliTrasporto(String causaliTrasporto) {
		this.causaliTrasporto = causaliTrasporto;
	}
	public String getAgentePrincipale() {
		return agentePrincipale;
	}
	public void setAgentePrincipale(String agentePrincipale) {
		this.agentePrincipale = agentePrincipale;
	}
	public String getSubagente() {
		return subagente;
	}
	public void setSubagente(String subagente) {
		this.subagente = subagente;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAllegatoRdc() {
		return allegatoRdc;
	}
	public void setAllegatoRdc(String allegatoRdc) {
		this.allegatoRdc = allegatoRdc;
	}
	public String getAllegatoSchedaB() {
		return allegatoSchedaB;
	}
	public void setAllegatoSchedaB(String allegatoSchedaB) {
		this.allegatoSchedaB = allegatoSchedaB;
	}
	public String getAllegatoOffertaFirmata() {
		return allegatoOffertaFirmata;
	}
	public void setAllegatoOffertaFirmata(String allegatoOffertaFirmata) {
		this.allegatoOffertaFirmata = allegatoOffertaFirmata;
	}
	public String getTabella() {
		return tabella;
	}
	public void setTabella(String tabella) {
		this.tabella = tabella;
	}
	public Character getStatoInterscambio() {
		return statoInterscambio;
	}
	public void setStatoInterscambio(Character statoInterscambio) {
		this.statoInterscambio = statoInterscambio;
	}
	public String getKeyPth() {
		return keyPth;
	}
	public void setKeyPth(String keyPth) {
		this.keyPth = keyPth;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getCodice_Nazionalita() {
		return Codice_Nazionalita;
	}

	public void setCodice_Nazionalita(String codice_Nazionalita) {
		Codice_Nazionalita = codice_Nazionalita;
	}

	public String getCodice_Fiscale() {
		return Codice_Fiscale;
	}

	public void setCodice_Fiscale(String codice_Fiscale) {
		Codice_Fiscale = codice_Fiscale;
	}

	public boolean isClienteCodificato() {
		if(getCodCliPh() == null || getCodCliPh().isEmpty()) {
			return false;
		}else {
			if(getCliente() == null) {
				try {
					setCliente((ClienteVendita) ClienteVendita.elementWithKey(ClienteVendita.class, KeyHelper.buildObjectKey(new String[] {
							getIdAzienda(),getCodCliPh().trim()
					}), PersistentObject.NO_LOCK));
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
			return true;
		}
	}
	
}
