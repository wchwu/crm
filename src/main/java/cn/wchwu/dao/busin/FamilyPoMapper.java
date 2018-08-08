package cn.wchwu.dao.busin;

import cn.wchwu.model.busin.FamilyPo;

public interface FamilyPoMapper {
    int deleteByPrimaryKey(FamilyPo key);

    int insert(FamilyPo record);

    int insertSelective(FamilyPo record);

    FamilyPo selectByPrimaryKey(FamilyPo key);

    int updateByPrimaryKeySelective(FamilyPo record);

    int updateByPrimaryKey(FamilyPo record);
}