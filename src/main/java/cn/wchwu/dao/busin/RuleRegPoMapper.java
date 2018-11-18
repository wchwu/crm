package cn.wchwu.dao.busin;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.RuleRegPo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleRegPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RuleRegPo record);

    int insertSelective(RuleRegPo record);

    RuleRegPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RuleRegPo record);

    int updateByPrimaryKeyWithBLOBs(RuleRegPo record);

    int updateByPrimaryKey(RuleRegPo record);

    List<RuleRegPo> queryList(@Param("PAGE") PageCond pageCond, @Param("record") RuleRegPo record);
}