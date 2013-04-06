package ilink.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import ilink.domain.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class IbatisProposalDao extends SqlMapClientDaoSupport implements ProposalDao 
{
    public List< ProposalDataType > getProposalByCustomerId(long custId, int proposalId, String repId) 
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("proposalId", proposalId);
        params.put("repId", repId);
        List< ProposalDataType> proposalDataList = null;
        try 
        {
        	proposalDataList = (List< ProposalDataType>) getSqlMapClientTemplate().queryForList("getProposalByCustomerId", params);
        } 
        catch (Exception ex) 
        {
            System.out.println("###############IbatisProposalDao.getProposalByCustomerId : exception ->" + ex.getMessage());
        }
        return proposalDataList;
    }

    public List< ProposalDataTypeROP > getROPProposalByCustomerId(long custId, int proposalId, String repId)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("proposalId", proposalId);
        params.put("repId", repId);
        List<ProposalDataTypeROP> proposalDataList = null;
        try
        {
        	proposalDataList = (List<ProposalDataTypeROP>) getSqlMapClientTemplate().queryForList("getROPProposalByCustomerId", params);
        }
        catch (Exception ex)
        {
            System.out.println("###############IbatisProposalDao.getROPProposalByCustomerId : exception ->" + ex.getMessage());
        }
        return proposalDataList;
    }

    public List< ProposalDataType > getPendingPublishByCustomerId(long custId, String repId) 
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("repId", repId);
        List< ProposalDataType> proposalDataList = null;
        try 
        {
        	proposalDataList = (List< ProposalDataType>) getSqlMapClientTemplate().queryForList("getPendingPublishByCustomerId", params);
        } 
        catch (Exception ex) 
        {
            System.out.println("###############IbatisProposalDao.getPendingPublishByCustomerId : exception ->" + ex.getMessage());
        }
        return proposalDataList;
    }    
}

