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

package cn.wchwu.service.busin.impl;

import cn.wchwu.dao.busin.RuleRegPoMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.RuleRegPo;
import cn.wchwu.service.busin.RuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:规章制度service
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年11月19日 上午12:15:34
 * @since JDK 1.6
 */
@Service
public class RuleServiceImpl extends BaseService<RuleRegPo> implements RuleService {

    @Autowired
    private RuleRegPoMapper ruleRegPoMapper;

    @Override
    public List<RuleRegPo> queryList(PageCond pageCond, RuleRegPo record) {
        return ruleRegPoMapper.queryList(pageCond, record);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<RuleRegPo> getMapper() {
        return (Mapper<RuleRegPo>) ruleRegPoMapper;
    }
}
