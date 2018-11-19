
package cn.wchwu.service.busin.impl;

import cn.wchwu.common.Constant;
import cn.wchwu.dao.busin.FileRecPoMapper;
import cn.wchwu.model.busin.FileRecPo;
import cn.wchwu.service.busin.FileRecService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<FileRecPo> queryListByMemberId(Integer memberId) {
        return fileRecPoMapper.queryListByMemberId(memberId);

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

    @Override
    public int getFileId() {
        return fileRecPoMapper.getFileId();
    }

    @Override
    public int insertFileRec(FileRecPo record) {
        return fileRecPoMapper.insertSelective(record);
    }

    @Override
    public int updateFileRec(FileRecPo record) {
        return fileRecPoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteFileRec(Integer id) {
        FileRecPo record = new FileRecPo();
        record.setId(id);
        record.setStatus(Constant.FILE_STATUS_DEL);

        return fileRecPoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public FileRecPo queryById(Integer id) {
        return fileRecPoMapper.queryById(id);
    }

    @Override
    public List<FileRecPo> queryListByRuleId(Integer ruleId) {
        return fileRecPoMapper.queryListByRuleId(ruleId);
    }

}
