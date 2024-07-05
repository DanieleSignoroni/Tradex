package it.softre.thip.base.crm.imp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.gui.cnr.OpenType;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.softre.thip.base.crm.RdoAccessorio;
import it.softre.thip.base.crm.RdoInstallazione;
import it.softre.thip.base.crm.RdoProdotto;
import it.softre.thip.base.crm.RdoTotale;
import it.softre.thip.base.crm.Rdod;
import it.softre.thip.base.crm.utils.ImportOrdiniCrmUtils;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ClienteLight;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.cliente.web.CodiceNumericoClienteFornitore;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.vendite.ordineVE.OrdineVendita;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;

/**
 * Lavoro per l'importazione degli 'Ordini di Vendita' dal CRM 'Easy Tradex'.<br>
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 02/07/2024
 * <br><br>
 * <b>71XXX	DSSOF3	02/07/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class ImportOrdiniCrm extends BatchRunnable implements Authorizable{

	@SuppressWarnings("unchecked")
	@Override
	protected boolean run() {
		output.println(" ** INIZIO IMPORTAZIONE ORDINI VENDITA DA CRM EASYTRADEX ** ");
		boolean isOk = true;
		List<Rdod> records = leggiRdodDaImportare();
		if(!records.isEmpty()) {
			Map<String,OrdineVendita> offerteImportate = new HashMap<String, OrdineVendita>();
			for(Rdod offerta : records) {
				offerta.setCodCliPh("103737");
				output.println("--> Inizio il processo dell'offerta {"+offerta.getIdRdod()+"}");
				try {
					if(!offerta.isClienteCodificato()) {
						//codifico il cliente
						BODataCollector boDC = createDataCollector("ClienteLight");
						if (boDC.initSecurityServices(OpenType.NEW, false, false, true) == BODataCollector.ERROR) {
							output.println(" ** --> Utente non abilitato ad eseguire NEW su ClienteLight! ");
							continue;
						}else {
							CodiceNumericoClienteFornitore codice = new CodiceNumericoClienteFornitore();
							boDC.set("RagioneSocialeRidotta", offerta.getRagioneSociale());
							boDC.set("IdCliente", codice.getCodiceFormattato('C'));
							boDC.set("Indirizzo", offerta.getIndirizzo());
							boDC.set("Localita", offerta.getLocalita().trim().toUpperCase());
							boDC.set("CAP", offerta.getCap());
							boDC.set("IdNazione", offerta.getCodice_Nazionalita().trim().toUpperCase()); 
							boDC.set("IdProvincia", offerta.getProv().trim().toUpperCase());
							boDC.set("NumeroTelefono", offerta.getTelefono() != null ? offerta.getTelefono() : "");
							boDC.set("IndirizzoEmail", offerta.getEmail() != null ? offerta.getEmail() : "");
							boDC.set("PartitaIVA",offerta.getPartitaIva());
							boDC.set("CodiceFiscale",offerta.getCodice_Fiscale().trim()); 
							if(offerta.getCodice_Nazionalita().trim().toUpperCase().contains("IT")) {
								boDC.set("IdCategContabileCliente", "001"); 
							}else {
								boDC.set("IdCategContabileCliente", "002"); 
							}
							boDC.setAutoCommit(false);
							if (offerta.getPartitaIva() == null) {
								boDC.setCodErrorsForced("THIP_TN340PartitaIVA");
							}
							if (offerta.getCodice_Fiscale() == null) {
								boDC.setCodErrorsForced(boDC.getCodErrorsForced() + "THIP_TN341CodiceFiscale");
							}
							boDC.setCodErrorsForced(boDC.getCodErrorsForced()+"THIP300132IdAnagrafico");
							int rc = boDC.save();
							if (rc >= BODataCollector.OK) {
								ClienteLight clienteLight = (ClienteLight) boDC.getBo();
								offerta.setCliente(clienteLight.getClienteVendita());

								ClienteVendita cliente = clienteLight.getClienteVendita();
								cliente.setStato(DatiComuniEstesi.SOSPESO);
								rc = cliente.save();
								//passiamo alla commit o rollback
							}else {
								//errori
								continue;
							}
						}
					}
					BODataCollector bodcTes = createDataCollector("OrdineVendita");
					if (bodcTes.initSecurityServices(OpenType.NEW, false, false, true) == BODataCollector.ERROR) {
						output.println(" ** --> Utente non abilitato ad eseguire NEW su OrdineVendita! ");
						continue;
					}else {
						OrdineVendita ordine = (OrdineVendita) bodcTes.getBo();
						assegnaDatiOrdineVendita(ordine, offerta);
						ordine.setSalvaRighe(false);
						ordine.setAbilitaCheckBloccoImmissione(false);
						if(offerta.isClienteCodificato()) {
							//se il cliente e' codificato ma ho due modalita' di pagamento diverse tra crm e pth allora prendo quella del crm per stavolta
							if((offerta.getModalitaPagamento() != null 
									&& !offerta.getModalitaPagamento().isEmpty() 
									&& offerta.getCliente().getIdModalitaPagamento() != null
									)
									&& !(offerta.getModalitaPagamento().equals(offerta.getCliente().getIdModalitaPagamento()))){
								ordine.setIdModPagamento(offerta.getModalitaPagamento());
							}
						}
						bodcTes.setBo(ordine);
						int rc = bodcTes.save();
						if(rc == BODataCollector.OK) {
							for(RdoProdotto prodotto : offerta.getProdotti()) {
								Articolo articolo = getArticoloPth(ordine.getIdAzienda(), prodotto.getArticoloPh());
								if(articolo != null) {
									BigDecimal quantita = new BigDecimal(prodotto.getQuantita());
									int count = quantita.intValue();
									//Il cliente ha deciso che per i prodotti va creata 1 riga per ogni quantita'
									for(int i = 0; i < count; i ++) { 
										BODataCollector boDCRig = createDataCollector("OrdineVenditaRigaPrm");
										OrdineVenditaRigaPrm riga = (OrdineVenditaRigaPrm) boDCRig.getBo();
										BigDecimal prezzo = new BigDecimal(prodotto.getPrezzoIniziale());
										BigDecimal pezzoNetto = new BigDecimal(prodotto.getPrezzoFinale());
										BigDecimal sconto = new BigDecimal(prodotto.getScontoRdod());
										assegnaDatiOrdineVenditaRigaPrm(ordine, riga, articolo, prezzo, pezzoNetto, sconto,quantita);
										ordine.getRighe().add(riga);
									}
								}else {
									throw new Exception(" ** Non esiste nessun Articolo in Panthera con codice : "+prodotto.getArticoloPh().trim());
								}
							}
							for(RdoInstallazione installazione : offerta.getInstallazioni()) {
								Articolo articolo = getArticoloPth(ordine.getIdAzienda(), installazione.getArticoloPh());
								if(articolo != null) {
									BODataCollector boDCRig = createDataCollector("OrdineVenditaRigaPrm");
									OrdineVenditaRigaPrm riga = (OrdineVenditaRigaPrm) boDCRig.getBo();
									BigDecimal quantita = new BigDecimal(installazione.getQuantita());
									BigDecimal prezzo = new BigDecimal(installazione.getPrezzoIniziale());
									BigDecimal pezzoNetto = new BigDecimal(installazione.getPrezzoFinale());
									BigDecimal sconto = new BigDecimal(installazione.getScontoRdod());
									assegnaDatiOrdineVenditaRigaPrm(ordine, riga, articolo, prezzo, pezzoNetto, sconto,quantita);
									ordine.getRighe().add(riga);
								}else {
									throw new Exception(" ** Non esiste nessun Articolo in Panthera con codice : "+installazione.getArticoloPh().trim());
								}
							}
							for(RdoAccessorio accessorio : offerta.getAccessori()) {
								Articolo articolo = getArticoloPth(ordine.getIdAzienda(), accessorio.getArticoloPh().toUpperCase());
								if(articolo != null) {
									BODataCollector boDCRig = createDataCollector("OrdineVenditaRigaPrm");
									OrdineVenditaRigaPrm riga = (OrdineVenditaRigaPrm) boDCRig.getBo();
									BigDecimal quantita = new BigDecimal(accessorio.getQuantita());
									BigDecimal prezzo = new BigDecimal(accessorio.getPrezzoIniziale());
									BigDecimal pezzoNetto = new BigDecimal(accessorio.getPrezzoFinale());
									BigDecimal sconto = new BigDecimal(accessorio.getScontoRdod());
									assegnaDatiOrdineVenditaRigaPrm(ordine, riga, articolo, prezzo, pezzoNetto, sconto,quantita);
									ordine.getRighe().add(riga);
								}else {
									throw new Exception(" ** Non esiste nessun Articolo in Panthera con codice : "+accessorio.getArticoloPh().trim());
								}
							}
							ordine.setSalvaRighe(true);
							ordine.setAbilitaCheckBloccoImmissione(false);
							ordine.setEseguiControlloFido(false);	
							ordine.setStatoAvanzamento(StatoAvanzamento.DEFINITIVO);
							bodcTes.setBo(ordine);
							bodcTes.setAutoCommit(false);
							rc = bodcTes.save();
							if(rc == BODataCollector.OK) {
								offerteImportate.put(offerta.getNumeroRdod(), (OrdineVendita) bodcTes.getBo());
								ConnectionManager.commit();
								output.println("--> Creato correttamente l'ordine di vendita {"+bodcTes.getBo().getKey()+"}");
							}else {
								ConnectionManager.rollback();
								output.println(" ** Impossibile salvare l' ordine finale : "+bodcTes.messages().toString());
							}
						}else {
							output.println(" ** Impossibile salvare la testata ordine : "+bodcTes.messages().toString());
						}
					}
				}catch (Exception e) {
					output.println(" ** --> Errore opportunita : "+offerta.getIdRdod()+"..."+e.getMessage());
					e.printStackTrace();
				}
				output.println("--> Termino il processo dell'offerta {"+offerta.getIdRdod()+"}");
			}

			offerteImportate.clear(); //per debug

			//ora vanno flaggate come importate le offerte 
			if(offerteImportate.size() > 0) {
				output.println(" -- INIZIO FLAG OFFERTE COME PROCESSATE -- ");
				Connection crmEasyTradex = null;
				try {
					crmEasyTradex = ImportOrdiniCrmUtils.getInstance().getCrmEasyTradexConnection();
					for (Map.Entry<String, OrdineVendita> entry : offerteImportate.entrySet()) {
						String numeroRdod = entry.getKey();
						OrdineVendita ordine = entry.getValue();
						int risUpt = ImportOrdiniCrmUtils.getInstance().flaggaRdodProcessata(crmEasyTradex, numeroRdod, ordine);
						if(risUpt < 0) {
							output.println(" ** Impossibile flaggare l'offerta {"+numeroRdod+"} come importata, ordine : "+ordine.getKey());
							crmEasyTradex.rollback();
						}else {
							crmEasyTradex.commit();
						}
						output.println("--> Offerta {"+numeroRdod+"} flaggata "+(risUpt > 0 ? "correttamente" : "con errori"));
					}
				}catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				} catch (ClassNotFoundException e) {
					e.printStackTrace(Trace.excStream);
				}finally {
					try {
						if(crmEasyTradex != null) {
							crmEasyTradex.close();
						}
					}catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
				output.println(" -- TERMINE FLAG OFFERTE COME PROCESSATE -- ");
			}

		}else {
			output.println(" --> Nessun record da importare! ");
		}
		output.println(" ** TERMINE IMPORTAZIONE ORDINI VENDITA ** ");
		return isOk;
	}

	protected void assegnaDatiOrdineVendita(OrdineVendita ordine,Rdod offerta) {
		ordine.setIdAzienda(Azienda.getAziendaCorrente());
		ordine.getNumeratoreHandler().setIdNumeratore("ORD_VEN");
		ordine.getNumeratoreHandler().setIdSerie("IT");
		ordine.setCliente(offerta.getCliente());
		if(!offerta.isClienteCodificato())
			ordine.setIdCau(recuperaCausaleTestata(offerta));
		else
			ordine.setIdCau("VE");
		ordine.setIdMagazzino("001");
		ordine.completaBO();

		//ora i dati 
		ordine.setNumeroOrdineIntestatario(offerta.getNumeroRdod());
		ordine.setDataDocumento((Date) offerta.getCreatedAt());
		if(offerta.getTotale().getScontoRdod() > 0) {
			ordine.setPrcScontoIntestatario(new BigDecimal(offerta.getTotale().getScontoRdod()));
		}
	}

	protected String recuperaCausaleTestata(Rdod offerta) {
		String idCau = null;
		if(offerta.getModalitaPagamento() != null && !offerta.getModalitaPagamento().isEmpty()) {
			String modP = offerta.getModalitaPagamento();
			if(modP.equals("CGN") || modP.equals("B40") || modP.equals("B95") || modP.equals("CZ1") || modP.equals("C56") || modP.equals("C72")
					|| modP.equals("C29") || modP.equals("C15") || modP.equals("P10") || modP.equals("C42") || modP.equals("CZ3") || modP.equals("CZ4")
					|| modP.equals("C09") || modP.equals("C32") || modP.equals("CZ5") || modP.equals("R03") || modP.equals("C36")) {
				idCau = "VE";
				return idCau;
			}else if(modP.equals("A40")) {
				RdoTotale rdoTotale = offerta.getTotale();
				if(rdoTotale != null){
					Integer tipologia = rdoTotale.getTipologia();
					Integer durataNoleggio = Integer.valueOf(rdoTotale.getDurataNoleggio().trim());
					if(tipologia == 2) {
						
					}
				}
			}
		}
		return idCau;
	}

	protected void assegnaDatiOrdineVenditaRigaPrm(OrdineVendita ordine, OrdineVenditaRigaPrm riga, Articolo articolo,
			BigDecimal prezzo, BigDecimal prezzoNetto, BigDecimal scontoArt1, BigDecimal quantita) {
		riga.setIdAzienda(ordine.getIdAzienda());
		riga.setTestata(ordine);
		riga.setArticolo(articolo);
		riga.setIdCauRig(getIdCausaleRiga(ordine.getIdCau(), articolo));
		riga.completaBO();
		riga.setPrezzo(prezzo);
		riga.setPrezzoNetto(prezzoNetto);
		riga.setScontoArticolo1(scontoArt1);
		riga.setUMRif(articolo.getUMDefaultVendita());
		riga.getQuantitaOrdinata().setQuantitaInUMPrm(quantita);
		riga.getQuantitaOrdinata().setQuantitaInUMRif(quantita);
		riga.cambiaArticolo(articolo, null, false);
		if(articolo.getIdAssoggettamentoIVA() != null)
			riga.setIdAssogIVA(articolo.getIdAssoggettamentoIVA());
		else
			riga.setIdAssogIVA(ordine.getIdAssogIva());
		riga.setServizioCalcDatiVendita(false); //disattivo il recupero dei prezzi automatico 
	}

	protected Articolo getArticoloPth(String idAzienda,String idArticolo) {
		try {
			return (Articolo) Articolo.elementWithKey(Articolo.class, 
					KeyHelper.buildObjectKey(new String[] {
							idAzienda,
							idArticolo.trim()
					}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}


	/**
	 * 
	 * @author Daniele Signoroni 01/07/2024
	 * <p>
	 * Prima stesura.<br></br>
	 * <table border="1" cellspacing="0" cellpadding="5" style="border-collapse: collapse; width: 100%; font-family: Arial, sans-serif;">
	 *    <thead>
	 *        <tr>
	 *            <th>Codice testata</th>
	 *             <th>Descrizione testata</th>
	 *            <th>Causale riga di default</th>
	 *           <th>Descrizione Causale riga di default</th>
	 *          <th>Utilizzo</th>
	 *     </tr>
	 *   </thead>
	 *   <tbody>
	 *       <tr>
	 *           <td rowspan="3">AFB</td>
	 *           <td rowspan="3">Locazione Breve</td>
	 *           <td>AFB</td>
	 *           <td>Uscita Locazione breve</td>
	 *           <td>Tutti gli articoli tranne quelli sotto indicati</td>
	 *       </tr>
	 *       <tr>
	 *           <td>02</td>
	 *           <td>Quantità con scarico</td>
	 *           <td>SE LA MACROFAMIGLIA DELL' ANAGRAFICA ARTICOLO è 08, 18, 34, 01, 06, 22, 30, 63, 115, 74 e 108 ALLORA RIGA CAUSALE è 02</td>
	 *       </tr>
	 *       <tr>
	 *           <td>16</td>
	 *           <td>Qta/val no provv no mag</td>
	 *           <td>Articoli: INSTALLAZIONE S, INSTALLAZIONE M, INSTALLAZIONE F, REMOTO S, REMOTO F</td>
	 *       </tr>
	 *       <tr>
	 *           <td rowspan="3">AFL</td>
	 *           <td rowspan="3">Locazione Lunga</td>
	 *           <td>AFL</td>
	 *           <td>Uscita Locazione Lunga</td>
	 *           <td>Tutti gli articoli tranne quelli sotto indicati</td>
	 *       </tr>
	 *       <tr>
	 *           <td>02</td>
	 *           <td>Quantità con scarico</td>
	 *          <td>SE LA MACROFAMIGLIA DELL' ANAGRAFICA ARTICOLO è 08, 18, 34, 01, 06, 22, 30, 63, 115, 74 e 108 ALLORA RIGA CAUSALE è 02</td>
	 *      </tr>
	 *      <tr>
	 *          <td>16</td>
	 *          <td>Qta/val no provv no mag</td>
	 *          <td>Articoli: INSTALLAZIONE S, INSTALLAZIONE M, INSTALLAZIONE F, REMOTO S, REMOTO F</td>
	 *      </tr>
	 *      <tr>
	 *          <td rowspan="3">VE</td>
	 *          <td rowspan="3">Ordine di vendita</td>
	 *          <td>VE</td>
	 *         <td>Vendita</td>
	 *         <td>Tutti gli articoli tranne quelli sotto indicati</td>
	 *     </tr>
	 *     <tr>
	 *         <td>14</td>
	 *           <td>Qta/val si provv no mag</td>
	 *           <td>Articolo: VARIE IMPIANTI</td>
	 *       </tr>
	 *       <tr>
	 *           <td>16</td>
	 *           <td>Qta/val no provv no mag</td>
	 *           <td>Articoli: INSTALLAZIONE S, INSTALLAZIONE M, INSTALLAZIONE F, REMOTO S, REMOTO F</td>
	 *       </tr>
	 *       <tr>
	 *           <td rowspan="3">VSP</td>
	 *          <td rowspan="3">Ordine di vendita Split</td>
	 *           <td>VE</td>
	 *            <td>Vendita</td>
	 *           <td>Tutti gli articoli tranne quelli sotto indicati</td>
	 *       </tr>
	 *       <tr>
	 *           <td>14</td>
	 *           <td>Qta/val si provv no mag</td>
	 *           <td>Articolo: VARIE IMPIANTI</td>
	 *       </tr>
	 *       <tr>
	 *           <td>16</td>
	 *           <td>Qta/val no provv no mag</td>
	 *           <td>Articoli: INSTALLAZIONE S, INSTALLAZIONE M, INSTALLAZIONE F, REMOTO S, REMOTO F</td>
	 *       </tr>
	 *   </tbody>
	 * </table>
	 * </p>
	 * @param idCausaleTestata
	 * @param articolo
	 * @return
	 */
	protected String getIdCausaleRiga(String idCausaleTestata, Articolo articolo) {
		if(idCausaleTestata == null || articolo == null)
			return null;
		String idMacrofamiglia = articolo.getIdMacroFamiglia() != null ? articolo.getIdMacroFamiglia() : "";
		String idCausaleRiga = null;
		if(idCausaleTestata.equals("AFB") || idCausaleTestata.equals("AFL") || idCausaleTestata.equals("VE")) {
			if(idMacrofamiglia.equals("08") || idMacrofamiglia.equals("18") || idMacrofamiglia.equals("34") ||
					idMacrofamiglia.equals("01") || idMacrofamiglia.equals("06") || idMacrofamiglia.equals("22") ||
					idMacrofamiglia.equals("30") || idMacrofamiglia.equals("63") || idMacrofamiglia.equals("115") ||
					idMacrofamiglia.equals("74") || idMacrofamiglia.equals("108")) {
				idCausaleRiga = "02";
			}
			if(articolo.getIdArticolo().equals("INSTALLAZIONE S") || articolo.getIdArticolo().equals("INSTALLAZIONE M")
					|| articolo.getIdArticolo().equals("INSTALLAZIONE F") || articolo.getIdArticolo().equals("REMOTO S") || articolo.getIdArticolo().equals("REMOTO F") ) {
				idCausaleRiga = "16";
			}
		}else if(idCausaleTestata.equals("VSP")) {
			if(articolo.getIdArticolo().equals("INSTALLAZIONE S") || articolo.getIdArticolo().equals("INSTALLAZIONE M")
					|| articolo.getIdArticolo().equals("INSTALLAZIONE F") || articolo.getIdArticolo().equals("REMOTO S") || articolo.getIdArticolo().equals("REMOTO F") ) {
				idCausaleRiga = "16";
			}else {
				idCausaleRiga = "VE";
			}
		}
		if(idCausaleRiga == null)
			idCausaleRiga = "VE";
		return idCausaleRiga;
	}

	protected List<Rdod> leggiRdodDaImportare() {
		Connection connection = null;
		try {
			connection = ImportOrdiniCrmUtils.getInstance().getCrmEasyTradexConnection();
			return ImportOrdiniCrmUtils.getInstance().leggiRdodsCrmEasytradex(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(Trace.excStream);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return null;
	}

	protected BODataCollector createDataCollector(String classname) {
		try {
			ClassADCollection hdr = ClassADCollectionManager.collectionWithName(classname);
			return createDataCollector(hdr);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BODataCollector createDataCollector(ClassADCollection classDescriptor) {
		BODataCollector dataCollector = null;
		String collectorName = classDescriptor.getBODataCollector();
		if (collectorName != null) {
			dataCollector = (BODataCollector) Factory.createObject(collectorName);
		} else {
			dataCollector = new BODataCollector();
		}
		dataCollector.initialize(classDescriptor.getClassName(), true);
		return dataCollector;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YImportOrdiniCRM";
	}

}
