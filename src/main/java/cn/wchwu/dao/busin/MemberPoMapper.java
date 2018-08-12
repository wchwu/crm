package cn.wchwu.dao.busin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.MemberPo;

public interface MemberPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberPo record);

    int insertSelective(MemberPo record);

    MemberPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberPo record);

    int updateByPrimaryKey(MemberPo record);

    int getMemberId();

    /**
     * queryList:查询成员列表
     * 
     * @param pageCond
     * @param map
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月5日 下午10:00:58
     */
    List<MemberPo> queryList(@Param("PAGE") PageCond pageCond, @Param("record") MemberPo record);

    /**
     * deleteBatch:批量删除
     * 
     * @param ids
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月9日 下午3:39:47
     */
    int deleteBatch(@Param("ids") List<Integer> ids);

    int save(MemberPo memberPo);
}