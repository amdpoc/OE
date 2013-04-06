package ilink.dao;

import ilink.domain.ProposalDataType;
import ilink.domain.ProposalDataTypeROP;

import java.util.List;

public interface ProposalDao 
{
    public List< ProposalDataType >  getProposalByCustomerId(long custId, int proposalId, String repId);
    public List<ProposalDataTypeROP> getROPProposalByCustomerId(long custId, int proposalId, String repId);
    public List< ProposalDataType >  getPendingPublishByCustomerId(long custId, String repId);
}
