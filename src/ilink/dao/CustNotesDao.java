package ilink.dao;

import ilink.domain.*;

import java.util.List;

public interface CustNotesDao {
    public List<CustNotesListItem> getCustNotesByCustId(long custId);
    public List<CustNotesTxListItem> getCustNotesTxByCustId(long custId);
}
