
package cn.wchwu.service.busin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.CertificatePoMapper;
import cn.wchwu.model.busin.CertificatePo;
import cn.wchwu.service.busin.CertificateService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月10日 下午7:54:29
 * @since JDK 1.6
 */
@Service
public class CertificateServiceImpl extends BaseService<CertificatePo> implements CertificateService {

    @Autowired
    private CertificatePoMapper certificatePoMapper;

    @Override
    public List<CertificatePo> queryListById(Integer memberId) {
        return certificatePoMapper.queryListById(memberId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<CertificatePo> getMapper() {
        return (Mapper<CertificatePo>) certificatePoMapper;
    }

    @Override
    public void saveCertificateBatch(List<CertificatePo> deletedList, List<CertificatePo> insertedList, List<CertificatePo> updatedList) {
        insertCertificateBatch(insertedList);
        updateCertificateBatch(updatedList);
        deleteCertificateBatch(deletedList);
    }

    public void insertCertificateBatch(List<CertificatePo> list) {
        if (list != null) {
            Date d = new Date();
            for (CertificatePo po : list) {
                po.setCreateTime(d);
                po.setUpdateTime(d);
                certificatePoMapper.insertSelective(po);
            }
        }
    }

    public void updateCertificateBatch(List<CertificatePo> list) {
        if (list != null) {
            Date d = new Date();
            for (CertificatePo po : list) {
                po.setUpdateTime(d);
                certificatePoMapper.updateByPrimaryKeySelective(po);
            }
        }
    }

    public void deleteCertificateBatch(List<CertificatePo> list) {
        if (list != null) {
            for (CertificatePo po : list) {
                certificatePoMapper.deleteByPrimaryKey(po);
            }
        }
    }

}
