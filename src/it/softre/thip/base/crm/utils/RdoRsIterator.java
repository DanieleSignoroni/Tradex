package it.softre.thip.base.crm.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.persist.Factory;

import it.softre.thip.base.crm.Rdo;
import it.thera.thip.cs.ResultSetIterator;

public class RdoRsIterator extends ResultSetIterator {

    public RdoRsIterator(ResultSet rs) {
        super(rs);
    }

    @Override
    protected Object createObject() throws SQLException {
        Rdo rdo = (Rdo) Factory.createObject(Rdo.class);
        
        rdo.setIdRdo(cursor.getInt("ID_Rdo"));
        rdo.setDataIns(cursor.getDate("Data_Ins"));
        rdo.setDataAgg(cursor.getDate("Data_Agg"));
        rdo.setNumero(cursor.getString("Numero"));
        rdo.setOrigineNumero(cursor.getString("Origine_Numero"));
        rdo.setNumeroRdod(cursor.getString("Numero_Rdod"));
        rdo.setIdRdod(cursor.getInt("ID_Rdod"));
        rdo.setIdAgente(cursor.getString("ID_Agente"));
        rdo.setIdAdmin(cursor.getInt("ID_Admin"));
        rdo.setDescrizione(cursor.getString("Descrizione"));
        rdo.setAzienda(cursor.getString("Azienda"));
        rdo.setFileDoc(cursor.getString("file_doc"));
        rdo.setFilePdf(cursor.getString("file_pdf"));
        rdo.setIdOpportunity(cursor.getInt("ID_Opportunity"));
        rdo.setIdAnagrafica(cursor.getInt("ID_Anagrafica"));
        rdo.setTabella(cursor.getString("Tabella"));
        rdo.setStato(cursor.getInt("Stato"));
        rdo.setNote(cursor.getString("Note"));
        rdo.setChiInviaMail(cursor.getString("Chi_Invia_Mail"));
        rdo.setNewRdo(cursor.getInt("New_Rdo"));
        rdo.setCreatedAt(cursor.getTimestamp("created_at"));
        rdo.setUpdatedAt(cursor.getTimestamp("updated_at"));
        
        return rdo;
    }
}
