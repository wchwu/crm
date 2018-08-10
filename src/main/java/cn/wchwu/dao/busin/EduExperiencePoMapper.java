package cn.wchwu.dao.busin;

import java.util.List;

import cn.wchwu.model.busin.EduExperiencePo;

public interface EduExperiencePoMapper {
    int deleteByPrimaryKey(EduExperiencePo key);

    int insert(EduExperiencePo record);

    int insertSelective(EduExperiencePo record);

    EduExperiencePo selectByPrimaryKey(EduExperiencePo key);

    int updateByPrimaryKeySelective(EduExperiencePo record);

    int updateByPrimaryKey(EduExperiencePo record);

    List<EduExperiencePo> queryListById(Integer id);
}