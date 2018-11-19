package cn.wchwu.dao.busin;

import cn.wchwu.model.busin.FileRecPo;
import java.util.List;

public interface FileRecPoMapper {
    int deleteByPrimaryKey(FileRecPo key);

    int insert(FileRecPo record);

    int insertSelective(FileRecPo record);

    int getFileId();

    FileRecPo selectByPrimaryKey(FileRecPo key);

    int updateByPrimaryKeySelective(FileRecPo record);

    int updateByPrimaryKey(FileRecPo record);

    List<FileRecPo> queryListByMemberId(Integer memberId);

    List<FileRecPo> queryListByRuleId(Integer ruleId);

    int deleteFileRec(Integer id);

    FileRecPo queryById(Integer id);
}