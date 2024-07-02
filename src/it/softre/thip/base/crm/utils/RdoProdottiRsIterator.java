package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.RdoProdotto;
import it.thera.thip.cs.ResultSetIterator;

public class RdoProdottiRsIterator extends ResultSetIterator {

	public RdoProdottiRsIterator(ResultSet rs) {
		super(rs);
	}

	@Override
	protected Object createObject() throws SQLException {
		RdoProdotto prodotto = (RdoProdotto) Factory.createObject(RdoProdotto.class);
		prodotto.setIdRdoProdotti(cursor.getInt("ID_Rdo_Prodotti"));
		prodotto.setIdRdo(cursor.getInt("ID_Rdo"));
		prodotto.setNumeroRdo(cursor.getString("Numero_Rdo"));
		prodotto.setCategoriaListino(cursor.getString("Categoria_Listino"));
		prodotto.setIdCategoriaListino(cursor.getInt("ID_Categoria_Listino"));
		prodotto.setIdMacro(cursor.getInt("ID_Macro"));
		prodotto.setIdMicro(cursor.getInt("ID_Micro"));
		prodotto.setIdModello(cursor.getInt("ID_Modello"));
		prodotto.setIdProdotto(cursor.getInt("ID_Prodotto"));
		prodotto.setArticoloPh(cursor.getString("Articolo_PH"));
		prodotto.setNomeProdotto(cursor.getString("Nome_Prodotto"));
		prodotto.setNomeListino(cursor.getString("Nome_Listino"));
		prodotto.setDescrizioneRdo(cursor.getString("Descrizione_Rdo"));
		prodotto.setQuantita(cursor.getInt("Quantita"));
		prodotto.setSconto(cursor.getInt("Sconto"));
		prodotto.setImportoSconto(cursor.getInt("Importo_Sconto"));
		prodotto.setPrezzoBase(cursor.getDouble("Prezzo_Base"));
		prodotto.setPrezzoScontato(cursor.getDouble("Prezzo_Scontato"));
		prodotto.setPrezzoLibero(cursor.getDouble("Prezzo_Libero"));
		prodotto.setPrezzoDefinitivo(cursor.getDouble("Prezzo_Definitivo"));
		prodotto.setDepliant(cursor.getInt("Depliant"));
		prodotto.setNumeroRdod(cursor.getString("Numero_Rdod"));
		prodotto.setDescrizioneRdod(cursor.getString("Descrizione_Rdod"));
		prodotto.setPrezzoIniziale(cursor.getDouble("Prezzo_Iniziale"));
		prodotto.setPrezzoFinale(cursor.getDouble("Prezzo_Finale"));
		prodotto.setScontoRdod(cursor.getDouble("Sconto_RDOD"));
		prodotto.setCreatedAt(cursor.getTimestamp("created_at"));
		prodotto.setUpdatedAt(cursor.getTimestamp("updated_at"));
		return prodotto;
	}



}
