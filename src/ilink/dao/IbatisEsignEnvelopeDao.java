package ilink.dao;

import ilink.domain.EsignEnvelope;
import ilink.domain.EsignBillingAccount;
import ilink.domain.EsignProduct;
import ilink.utils.iLinkUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class IbatisEsignEnvelopeDao extends SqlMapClientDaoSupport implements EsignEnvelopeDao {

    public void updateEsignEnvelopeStatus(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status) throws Exception {

        try {
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("status", status.toString());
            getSqlMapClientTemplate().update("updateEnvelopeStatus", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEsignEnvelopeStatus :" +
                    " envelopeId =" + envelopeId + " update with status =" + status.toString() +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }
    public void updateEnvelopeDocusignInfo(double envelopeId, String docusignEnvelopeId, EsignEnvelope.DocusignEnvelopeStatus docusignStatus) throws Exception {

        try {
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("docusignEnvelopeId", docusignEnvelopeId);
            entityModel.put("status", docusignStatus);
            getSqlMapClientTemplate().update("updateEnvelopeDocusignInfo", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEnvelopeDocusignInfo :" +
                    " envelopeId =" + envelopeId + ";  update with docusignEnvelopeId =" + docusignEnvelopeId +  ";  docusignStatus=" + docusignStatus  +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }
    public void updateEsignEnvelopeContactInfo(double envelopeId, String contactName, String contactEmail) throws Exception {

        try {
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("contactName", contactName);
            entityModel.put("contactEmail", contactEmail);
            getSqlMapClientTemplate().update("updateEnvelopeContactInfo", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEsignEnvelopeContactInfo :" +
                    " envelopeId =" + envelopeId + ";  update with contactName =" + contactName +  ";  contactEmail=" + contactEmail  +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }


    public void updateEsignEnvelopeWithError(double envelopeId, String error) throws Exception {

        try {
            error = error.length() > 300? error.substring(0,299):error;
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("error", error);
            getSqlMapClientTemplate().update("updateEnvelopeWithError", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEsignEnvelopeWithError :" +
                    " envelopeId =" + envelopeId + " update with error =" + error +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }
    public void updateEsignEnvelopeStatusWithError(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status, String error) throws Exception {

        try {
            error = error.length() > 300? error.substring(0,299):error;
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("status", status.toString());
            entityModel.put("error", error);
            getSqlMapClientTemplate().update("updateEnvelopeStatusWithError", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEsignEnvelopeStatusWithError :" +
                    " envelopeId =" + envelopeId + " update with error =" + error +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }
     public void updateEsignEnvelopeDocusignStatusWithError(double envelopeId, EsignEnvelope.DocusignEnvelopeStatus status, String error) throws Exception {

        try {
            error = error.length() > 300? error.substring(0,299):error;
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("status", status.toString());
            entityModel.put("error", error);
            getSqlMapClientTemplate().update("updateEnvelopeDocusignStatusWithError", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEsignEnvelopeDocusignStatusWithError :" +
                    " envelopeId =" + envelopeId + " update with error =" + error +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }
    public void updateEnvelopeStatusAndPdfPath(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status,
                                               String pdfPath) throws Exception {

        try {
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("envelopeId", envelopeId);
            entityModel.put("status", status.toString());
            entityModel.put("pdfPath", pdfPath);
            getSqlMapClientTemplate().update("updateEnvelopeStatusAndPdfPath", entityModel);
        }
        catch (Exception ex) {
            System.out.println("######################## exception IbatisEsignEnvelopeDao.updateEnvelopeStatusAndPdfPath :" +
                    " envelopeId =" + envelopeId + " update with status =" + status.toString() +
                    " pdfPath = " + pdfPath +
                    " exception ->" + ex.getMessage());
            throw ex;
        }
    }

    public EsignEnvelope createEsignEnvelope(EsignEnvelope envelope) throws Exception {

        try {
            Double envelopeId = (Double) getSqlMapClientTemplate().insert("insertEnvelope", envelope);
            envelope.setEsignEnvelopeId(envelopeId);
            List<EsignBillingAccount> baccounts = envelope.getBillingAccounts();
            for (EsignBillingAccount it : baccounts) {
                it.setEsignEnvelopeId(envelopeId);
                double accountId = insertEsingBillingAccount(it);
                List<EsignProduct> baProducts = it.getBaProductList();
                for (EsignProduct ip : baProducts) {
                    ip.setEsignBAccountId(accountId);
                    ip.setEsignEnvelopeId(envelopeId);
                    insertEsingBAProduct(ip);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("############# exception  IbatisEsignEnvelopeDao.createEsignEnvelope : exception ->" + ex.getMessage());
            throw ex;
        }
        return envelope;
    }

    public double insertEsingBillingAccount(EsignBillingAccount baccount) {

        Double baccountId = (Double) getSqlMapClientTemplate().insert("insertBAccount", baccount);
        baccount.setEsignBAccountId(baccountId);
        return baccountId;
    }

    public void insertEsingBAProduct(EsignProduct product) {

        if(product != null && product.getIssueRelatedInd().equals("N")){
          product.setIssueDate(null); 
        }
        getSqlMapClientTemplate().insert("insertBAProduct", product);
    }

    public EsignEnvelope getEsignEnvelope(String envelopeId) {
        EsignEnvelope envelope = null;

        //todo implement
        return envelope;

    }
}
