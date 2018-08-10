/**
 *Copyright (c) 2018, ShangHai HOWBUY INVESTMENT MANAGEMENT Co., Ltd.
 *All right reserved.
 *
 *THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF HOWBUY INVESTMENT
 *MANAGEMENT CO., LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *WITHOUT THE PRIOR WRITTEN PERMISSION OF HOWBUY INVESTMENT MANAGEMENT
 * CO., LTD.
*/

package cn.wchwu.service.busin;

import java.util.List;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.busin.vo.MemberVo;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:35:50
 * @since JDK 1.6
 */
public interface MemberService extends IService<MemberPo> {

    /**
     * queryList:查询成员列表
     * 
     * @param pageCond
     * @param map
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月5日 下午10:22:16
     */
    public List<MemberPo> queryList(PageCond pageCond, MemberPo record);

    /**
     * getMemberById:查询成员
     * 
     * @param id
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月9日 下午6:52:51
     */
    MemberVo getMemberById(Integer id);

    /**
     * update:更新
     * 
     * @param record
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月9日 下午6:45:01
     */
    public int update(MemberPo record);

    /**
     * delBatch:批量删除
     * 
     * @param ids
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月9日 下午3:40:40
     */
    public int delBatch(List<Integer> ids);
}
