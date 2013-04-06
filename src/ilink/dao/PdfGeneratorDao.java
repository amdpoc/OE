package ilink.dao;

import ilink.domain.AdocsPdfEntity;


public interface PdfGeneratorDao {
    public AdocsPdfEntity getGeneratedPdfPath(long requestId);
}
