package ilink.service;

import ilink.dao.ProposalDao;
import ilink.domain.EsignBillingAccount;
import ilink.domain.ProposalDataType;
import ilink.domain.ProposalDataTypeROP;
import ilink.utils.iLinkUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ProposalManagerImpl implements ProposalManager 
{
    private ProposalDao proposalDao;

    protected final Log logger = LogFactory.getLog(getClass());


    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    public void setProposalDao(ProposalDao proposalDao) 
    {
        this.proposalDao = proposalDao;
    }

    public List< ProposalDataType > getProposalByCustomerId(String type, long custId, int proposalId) 
    {
        logger.debug("################ Start getProposalDWR ----> " + (new java.util.Date()).toString());
        List< ProposalDataType > resultList = null;
        List< ProposalDataType > resultListFromSession = null;
        try 
        {
            String repId = iLinkUtils.getEmployeeIdFromDWR();
            resultListFromSession = iLinkUtils.getProposalByCustomerIdFromSession(type, custId, proposalId, null);

            if (resultListFromSession != null)
            {
                resultList = resultListFromSession;
            }
            else
            {
                if (proposalId == iLinkUtils.NEW_ENVELOPE)
                {
                    return null;
                }
                else
                {
                    resultList = proposalDao.getProposalByCustomerId(custId, proposalId, repId);
                    iLinkUtils.setProposalByCustomerIdToSession(type, custId, proposalId, resultList, null);
                }
            }
        }
        catch (Exception ie) 
        {
            logger.error("################ Error getProposalDWR ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getProposalDWR ----> " + (new java.util.Date()).toString());
        return resultList;
    }

    public List<ProposalDataTypeROP> getROPProposalByCustomerId(String type, long custId, int proposalId)
    {
        logger.debug("################ Start getROPProposalByCustomerId ----> " + (new java.util.Date()).toString());
        List<ProposalDataTypeROP> resultList = null;
        List<ProposalDataTypeROP> resultListFromSession = null;
        try
        {
            String repId = iLinkUtils.getEmployeeIdFromDWR();
            resultListFromSession = iLinkUtils.getROPProposalByCustomerIdFromSession(type, custId, proposalId, null);

            if (resultListFromSession != null)
            {
                resultList = resultListFromSession;
            }
            else
            {
                if (proposalId == iLinkUtils.NEW_ENVELOPE)
                {
                    return null;
                }
                else
                {
                    resultList = proposalDao.getROPProposalByCustomerId(custId, proposalId, repId);
                    iLinkUtils.setROPProposalByCustomerIdToSession(type, custId, proposalId, resultList, null);
                }
            }
        }
        catch (Exception ie)
        {
            logger.error("################ Error getROPProposalByCustomerId ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getROPProposalByCustomerId ----> " + (new java.util.Date()).toString());
        return resultList;
    }

    public EsignBillingAccount getNewEnvelopeBillingInfoFromSessionDWR(String type, long custId)
    {
        logger.debug("################ Start getNewEnvelopeBillingInfoFromSessionDWR ----> " + (new java.util.Date()).toString());
        EsignBillingAccount billingAccount = null;
        EsignBillingAccount billingAccountFromSession = null;

        try
        {
            billingAccountFromSession = iLinkUtils.getNewEnvelopeBillingInfoFromSession(type, custId);
            if (billingAccountFromSession != null)
            {
                billingAccount = billingAccountFromSession;
            }
        }
        catch (Exception ie)
        {
            logger.error("################ Error getNewEnvelopeBillingInfoFromSessionDWR ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getNewEnvelopeBillingInfoFromSessionDWR ----> " + (new java.util.Date()).toString());
        return billingAccount;
    }

    public List< ProposalDataType > getPendingPublishByCustomerId(String type, long custId)
    {
        logger.debug("################ Start getPendingPublishByCustomerId ----> " + (new java.util.Date()).toString());
        List< ProposalDataType > resultList = null;
        List< ProposalDataType > resultListFromSession = null;
        try
        {
            String repId = iLinkUtils.getEmployeeIdFromDWR();
            resultListFromSession = iLinkUtils.getProposalByCustomerIdFromSession(type, custId, 5, null);

            if (resultListFromSession != null)
            {
                resultList = resultListFromSession;
            }
            else
            {
        	    resultList = proposalDao.getPendingPublishByCustomerId(custId, repId);
                iLinkUtils.setProposalByCustomerIdToSession(type, custId, 5, resultList, null);
            }
        } 
        catch (Exception ie) 
        {
            logger.error("################ Error getPendingPublishByCustomerId ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getPendingPublishByCustomerId ----> " + (new java.util.Date()).toString());
        return resultList;
    }

    public boolean saveProposalProductsListToSessionDWR(String type, long custId, int proposalId, List< ProposalDataType > productsList)
    {
        Boolean result = false;
        try
        {
            if (productsList != null)
            {
                iLinkUtils.setProposalByCustomerIdToSession(type, custId, proposalId, productsList, null);
            }
            result = true;
        }
        catch (Exception e)
        {
            logger.error("################ Error saveProposalProductsListToSessionDWR ----> " + e.getMessage());
        }
        return result;
    }

    public boolean saveNewEnvelopeNoProductsToSessionDWR(String type, long custId, EsignBillingAccount billingAccount)
    {
        Boolean result = false;
        try
        {
            if (billingAccount != null)
            {
                iLinkUtils.setNewEnvelopeBillingInfoToSession(type, custId, billingAccount);
            }
            result = true;
        }
        catch (Exception e)
        {
            logger.error("################ Error saveNewEnvelopeNoProductsToSessionDWR ----> " + e.getMessage());
        }
        return result;
    }

}
