package ilink.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import ilink.domain.*;

import java.util.List;

public class IbatisCustNotesDao extends SqlMapClientDaoSupport implements CustNotesDao {

    public List<CustNotesListItem> getCustNotesByCustId(long custId) {
        List<CustNotesListItem> list = null;
        try {
            list = (List<CustNotesListItem>) getSqlMapClientTemplate().queryForList("getCustNotesByCustId", custId);
        }
        catch (Exception ex) {
            System.out.println("IbatisCustNotesDao.getCustNotesByCustId : exception ->" + ex.getMessage());
        }
        return list;
    }

     public List<CustNotesTxListItem> getCustNotesTxByCustId(long custId) {
        List<CustNotesTxListItem> list = null;
        try {
            list = (List<CustNotesTxListItem>) getSqlMapClientTemplate().queryForList("getCustNotesTx", custId);
        }
        catch (Exception ex) {
            System.out.println("IbatisCustNotesDao.getCustNotesTxByCustId : exception ->" + ex.getMessage());
        }
        return list;
    }
}
