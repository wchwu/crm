
package cn.wchwu.service.busin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.FileRecPoMapper;
import cn.wchwu.model.busin.FileRecPo;
import cn.wchwu.service.busin.FileRecService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月10日 下午7:54:29
 * @since JDK 1.6
 */
@Service
public class FileRecServiceImpl extends BaseService<FileRecPo> implements FileRecService {

    @Autowired
    private FileRecPoMapper fileRecPoMapper;

    @Override
    public List<FileRecPo> queryListById(Integer memberId) {
        return fileRecPoMapper.queryListById(memberId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<FileRecPo> getMapper() {
        return (Mapper<FileRecPo>) fileRecPoMapper;
    }

    @Override
    public void saveFileBatch(List<FileRecPo> deletedList, List<FileRecPo> insertedList, List<FileRecPo> updatedList) {
        insertCertificateBatch(insertedList);
        updateCertificateBatch(updatedList);
        deleteCertificateBatch(deletedList);
    }

    public void insertCertificateBatch(List<FileRecPo> list) {
        if (list != null) {
            Date d = new Date();
            for (FileRecPo po : list) {
                po.setCreateTime(d);
                po.setUpdateTime(d);
                fileRecPoMapper.insertSelective(po);
            }
        }
    }

    public void updateCertificateBatch(List<FileRecPo> list) {
        if (list != null) {
            Date d = new Date();
            for (FileRecPo po : list) {
                po.setUpdateTime(d);
                fileRecPoMapper.updateByPrimaryKeySelective(po);
            }
        }
    }

    public void deleteCertificateBatch(List<FileRecPo> list) {
        if (list != null) {
            for (FileRecPo po : list) {
                fileRecPoMapper.deleteByPrimaryKey(po);
            }
        }
    }

}
