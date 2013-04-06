package ilink.dao;

import ilink.domain.AdocsPdfEntity;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class IbatisAdocsPdfGeneratorDao extends SqlMapClientDaoSupport implements PdfGeneratorDao {
    public AdocsPdfEntity getGeneratedPdfPath(long requestId) {
        return (AdocsPdfEntity) getSqlMapClientTemplate().queryForObject("getAdocsPdfPath", requestId);
    }
}
