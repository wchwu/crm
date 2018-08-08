package cn.wchwu.dao.busin;

import cn.wchwu.model.busin.WorkExperiencePo;

public interface WorkExperiencePoMapper {
    int deleteByPrimaryKey(WorkExperiencePo key);

    int insert(WorkExperiencePo record);

    int insertSelective(WorkExperiencePo record);

    WorkExperiencePo selectByPrimaryKey(WorkExperiencePo key);

    int updateByPrimaryKeySelective(WorkExperiencePo record);

    int updateByPrimaryKey(WorkExperiencePo record);
}