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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.common.Constant;
import cn.wchwu.dao.busin.MemberPoMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.MemberPo;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:35:50
 * @since JDK 1.6
 */
@Service
public class MemberService {

    @Autowired
    private MemberPoMapper memberPoMapper;

    /**
     * queryList:查询成员列表
     * 
     * @param pageCond
     * @param map
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月5日 下午10:22:16
     */
    public List<MemberPo> queryList(PageCond pageCond, MemberPo record) {
        return memberPoMapper.queryList(Constant.DB_NAME_DEDAULT, pageCond, record);
    }
}
