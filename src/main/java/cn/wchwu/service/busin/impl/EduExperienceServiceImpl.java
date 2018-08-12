
package cn.wchwu.service.busin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.EduExperiencePoMapper;
import cn.wchwu.model.busin.EduExperiencePo;
import cn.wchwu.service.busin.EduExperienceService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月10日 下午7:54:29
 * @since JDK 1.6
 */
@Service
public class EduExperienceServiceImpl extends BaseService<EduExperiencePo> implements EduExperienceService {

    @Autowired
    private EduExperiencePoMapper wduExperiencePoMapper;

    @Override
    public List<EduExperiencePo> queryListById(Integer memberId) {
        return wduExperiencePoMapper.queryListById(memberId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<EduExperiencePo> getMapper() {
        return (Mapper<EduExperiencePo>) wduExperiencePoMapper;
    }

    @Override
    public void saveEduExperienceBatch(List<EduExperiencePo> deletedList, List<EduExperiencePo> insertedList, List<EduExperiencePo> updatedList) {
        insertEduExperienceBatch(insertedList);
        updateEduExperienceBatch(updatedList);
        deleteEduExperienceBatch(deletedList);
    }

    public void insertEduExperienceBatch(List<EduExperiencePo> list) {
        if (list != null) {
            Date d = new Date();
            for (EduExperiencePo po : list) {
                po.setCreateTime(d);
                po.setUpdateTime(d);
                wduExperiencePoMapper.insertSelective(po);
            }
        }
    }

    public void updateEduExperienceBatch(List<EduExperiencePo> list) {
        if (list != null) {
            Date d = new Date();
            for (EduExperiencePo po : list) {
                po.setUpdateTime(d);
                wduExperiencePoMapper.updateByPrimaryKeySelective(po);
            }
        }
    }

    public void deleteEduExperienceBatch(List<EduExperiencePo> list) {
        if (list != null) {
            for (EduExperiencePo po : list) {
                wduExperiencePoMapper.deleteByPrimaryKey(po);
            }
        }
    }

}
