package ilink.service;

import ilink.domain.EsignBillingAccount;
import ilink.domain.ProposalDataType;
import ilink.domain.ProposalDataTypeROP;

import java.util.List;

public interface ProposalManager 
{
    public List<ProposalDataType> getProposalByCustomerId(String type, long custId, int proposalId);
    public List<ProposalDataTypeROP> getROPProposalByCustomerId(String type, long custId, int proposalId);
    public List<ProposalDataType> getPendingPublishByCustomerId(String type, long custId);
    public boolean saveProposalProductsListToSessionDWR(String type, long custId, int proposalId, List<ProposalDataType> productsList);
    public boolean saveNewEnvelopeNoProductsToSessionDWR(String type, long custId, EsignBillingAccount billingAccount);
    public EsignBillingAccount getNewEnvelopeBillingInfoFromSessionDWR(String type, long custId);
}
