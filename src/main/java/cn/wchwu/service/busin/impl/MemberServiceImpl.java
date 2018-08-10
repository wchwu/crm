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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.CertificatePoMapper;
import cn.wchwu.dao.busin.EduExperiencePoMapper;
import cn.wchwu.dao.busin.FamilyPoMapper;
import cn.wchwu.dao.busin.MemberPoMapper;
import cn.wchwu.dao.busin.WorkExperiencePoMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.busin.vo.MemberVo;
import cn.wchwu.service.busin.MemberService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:35:50
 * @since JDK 1.6
 */
@Service
public class MemberServiceImpl extends BaseService<MemberPo> implements MemberService {

    @Autowired
    private MemberPoMapper memberPoMapper;
    @Autowired
    private EduExperiencePoMapper eduExperiencePoMapper;
    @Autowired
    private FamilyPoMapper familyPoMapper;
    @Autowired
    private WorkExperiencePoMapper workExperiencePoMapper;
    @Autowired
    private CertificatePoMapper certificatePoMapper;

    /**
     * queryList:查询成员列表
     * 
     * @param pageCond
     * @param map
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月5日 下午10:22:16
     */
    @Override
    public List<MemberPo> queryList(PageCond pageCond, MemberPo record) {
        return memberPoMapper.queryList(pageCond, record);
    }

    public int update(MemberPo record) {
        return memberPoMapper.updateByPrimaryKeySelective(record);
    }

    public int save(MemberPo memberPo) {
        int ret = 0;
        if (null == memberPo.getId()) {
            // 新增
            ret = memberPoMapper.insertSelective(memberPo);
        } else {
            // 更新
            ret = memberPoMapper.updateByPrimaryKeySelective(memberPo);
        }
        return ret;
    }

    public int save(MemberVo record) {
        MemberPo memberPo = record.getMemberPo();
        if (null == record || null == memberPo) {
            return 0;
        }
        int ret = 0;
        ret = save(memberPo);

        return ret;
    }

    /**
     * delBatch:批量删除
     * 
     * @param ids
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月9日 下午3:40:40
     */
    @Override
    public int delBatch(List<Integer> ids) {
        return memberPoMapper.deleteBatch(ids);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<MemberPo> getMapper() {
        return (Mapper<MemberPo>) memberPoMapper;
    }

    @Override
    public MemberVo getMemberById(Integer id) {
        MemberVo vo = new MemberVo();
        MemberPo memberPo = memberPoMapper.selectByPrimaryKey(id);
        if (null == memberPo) {
            return null;
        }
        vo.setMemberPo(memberPo);
        vo.setEduExperienceList(eduExperiencePoMapper.queryListById(id));
        vo.setFamilyList(familyPoMapper.queryListById(id));
        vo.setWorkExperienceList(workExperiencePoMapper.queryListById(id));
        vo.setCertificateList(certificatePoMapper.queryListById(id));
        return vo;

    }
}
