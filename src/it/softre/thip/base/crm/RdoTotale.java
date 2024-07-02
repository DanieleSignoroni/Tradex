package it.softre.thip.base.crm;

import java.sql.Date;

public class RdoTotale {
	
    protected int idRdoTotale;
    protected Integer idRdo;
    protected String numeroRdo;
    protected Double totaleCalcolato;
    protected Double totaleRiservato;
    protected String numeroRdod;
    protected Double prezzoIniziale;
    protected Double prezzoFinale;
    protected Double scontoRdod;
    protected Integer tipologia;
    protected String durataNoleggio;
    protected Date createdAt;
    protected Date updatedAt;
	public int getIdRdoTotale() {
		return idRdoTotale;
	}
	public void setIdRdoTotale(int idRdoTotale) {
		this.idRdoTotale = idRdoTotale;
	}
	public Integer getIdRdo() {
		return idRdo;
	}
	public void setIdRdo(Integer idRdo) {
		this.idRdo = idRdo;
	}
	public String getNumeroRdo() {
		return numeroRdo;
	}
	public void setNumeroRdo(String numeroRdo) {
		this.numeroRdo = numeroRdo;
	}
	public Double getTotaleCalcolato() {
		return totaleCalcolato;
	}
	public void setTotaleCalcolato(Double totaleCalcolato) {
		this.totaleCalcolato = totaleCalcolato;
	}
	public Double getTotaleRiservato() {
		return totaleRiservato;
	}
	public void setTotaleRiservato(Double totaleRiservato) {
		this.totaleRiservato = totaleRiservato;
	}
	public String getNumeroRdod() {
		return numeroRdod;
	}
	public void setNumeroRdod(String numeroRdod) {
		this.numeroRdod = numeroRdod;
	}
	public Double getPrezzoIniziale() {
		return prezzoIniziale;
	}
	public void setPrezzoIniziale(Double prezzoIniziale) {
		this.prezzoIniziale = prezzoIniziale;
	}
	public Double getPrezzoFinale() {
		return prezzoFinale;
	}
	public void setPrezzoFinale(Double prezzoFinale) {
		this.prezzoFinale = prezzoFinale;
	}
	public Double getScontoRdod() {
		return scontoRdod;
	}
	public void setScontoRdod(Double scontoRdod) {
		this.scontoRdod = scontoRdod;
	}
	public Integer getTipologia() {
		return tipologia;
	}
	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}
	public String getDurataNoleggio() {
		return durataNoleggio;
	}
	public void setDurataNoleggio(String durataNoleggio) {
		this.durataNoleggio = durataNoleggio;
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
