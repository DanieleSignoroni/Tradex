package it.softre.thip.base.crm;

import java.util.Date;

public class Rdo {

    private int idRdo;
    private Date dataIns;
    private Date dataAgg;
    private String numero;
    private String origineNumero;
    private String numeroRdod;
    private int idRdod;
    private String idAgente;
    private int idAdmin;
    private String descrizione;
    private String azienda;
    private String fileDoc;
    private String filePdf;
    private int idOpportunity;
    private int idAnagrafica;
    private String tabella;
    private Integer stato;
    private String note;
    private String chiInviaMail;
    private Integer newRdo;
    private Date createdAt;
    private Date updatedAt;
    
    protected Rdod offerta = null;
    
    public Rdod getOfferta() {
		return offerta;
	}

	public void setOfferta(Rdod offerta) {
		this.offerta = offerta;
	}

	// Getters and Setters
    public int getIdRdo() {
        return idRdo;
    }

    public void setIdRdo(int idRdo) {
        this.idRdo = idRdo;
    }

    public Date getDataIns() {
        return dataIns;
    }

    public void setDataIns(Date dataIns) {
        this.dataIns = dataIns;
    }

    public Date getDataAgg() {
        return dataAgg;
    }

    public void setDataAgg(Date dataAgg) {
        this.dataAgg = dataAgg;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrigineNumero() {
        return origineNumero;
    }

    public void setOrigineNumero(String origineNumero) {
        this.origineNumero = origineNumero;
    }

    public String getNumeroRdod() {
        return numeroRdod;
    }

    public void setNumeroRdod(String numeroRdod) {
        this.numeroRdod = numeroRdod;
    }

    public int getIdRdod() {
        return idRdod;
    }

    public void setIdRdod(int idRdod) {
        this.idRdod = idRdod;
    }

    public String getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(String idAgente) {
        this.idAgente = idAgente;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getFileDoc() {
        return fileDoc;
    }

    public void setFileDoc(String fileDoc) {
        this.fileDoc = fileDoc;
    }

    public String getFilePdf() {
        return filePdf;
    }

    public void setFilePdf(String filePdf) {
        this.filePdf = filePdf;
    }

    public int getIdOpportunity() {
        return idOpportunity;
    }

    public void setIdOpportunity(int idOpportunity) {
        this.idOpportunity = idOpportunity;
    }

    public int getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(int idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getTabella() {
        return tabella;
    }

    public void setTabella(String tabella) {
        this.tabella = tabella;
    }

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getChiInviaMail() {
        return chiInviaMail;
    }

    public void setChiInviaMail(String chiInviaMail) {
        this.chiInviaMail = chiInviaMail;
    }

    public Integer getNewRdo() {
        return newRdo;
    }

    public void setNewRdo(Integer newRdo) {
        this.newRdo = newRdo;
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
}
