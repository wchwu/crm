package cn.wchwu.dao.busin;

import java.util.List;

import cn.wchwu.model.busin.FileRecPo;

public interface FileRecPoMapper {
    int deleteByPrimaryKey(FileRecPo key);

    int insert(FileRecPo record);

    int insertSelective(FileRecPo record);

    FileRecPo selectByPrimaryKey(FileRecPo key);

    int updateByPrimaryKeySelective(FileRecPo record);

    int updateByPrimaryKey(FileRecPo record);

    List<FileRecPo> queryListById(Integer id);
}