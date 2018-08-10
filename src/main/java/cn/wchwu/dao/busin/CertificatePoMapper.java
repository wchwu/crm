package cn.wchwu.dao.busin;

import java.util.List;

import cn.wchwu.model.busin.CertificatePo;

public interface CertificatePoMapper {
    int deleteByPrimaryKey(CertificatePo key);

    int insert(CertificatePo record);

    int insertSelective(CertificatePo record);

    CertificatePo selectByPrimaryKey(CertificatePo key);

    int updateByPrimaryKeySelective(CertificatePo record);

    int updateByPrimaryKey(CertificatePo record);

    List<CertificatePo> queryListById(Integer id);
}