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

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.RuleRegPo;
import java.util.List;

/**
 * @description:规章制度service
 * @reason:
 * @author Chaowu.Wang
 * @date 2018年11月19日 上午12:12:54
 * @since JDK 1.6
 */
public interface RuleService extends IService<RuleRegPo> {

    /**
     * queryList:查询列表
     * 
     * @param po
     * @return
     * @author Chaowu.Wang
     * @date 2018年11月19日 上午12:13:59
     */
    public List<RuleRegPo> queryList(PageCond pageCond, RuleRegPo record);

}
