package ilink.service;

import ilink.domain.*;

import java.util.List;


public interface CustNotesManager 
{
        public List<CustNotesListItem> getCustNotesByCustId(long custId);
        public List<CustNotesTxListItem> getCustNotesTxByCustId(long custId);
        public GeneralResponse createNewCustNote(long custId, String noteText);
}
