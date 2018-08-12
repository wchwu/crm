
package cn.wchwu.service.busin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.WorkExperiencePoMapper;
import cn.wchwu.model.busin.WorkExperiencePo;
import cn.wchwu.service.busin.WorkExperienceService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月10日 下午7:54:29
 * @since JDK 1.6
 */
@Service
public class WorkExperienceServiceImpl extends BaseService<WorkExperiencePo> implements WorkExperienceService {

    @Autowired
    private WorkExperiencePoMapper workExperiencePoMapper;

    @Override
    public List<WorkExperiencePo> queryListById(Integer memberId) {
        return workExperiencePoMapper.queryListById(memberId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<WorkExperiencePo> getMapper() {
        return (Mapper<WorkExperiencePo>) workExperiencePoMapper;
    }

    @Override
    public void saveWorkExperienceBatch(List<WorkExperiencePo> deletedList, List<WorkExperiencePo> insertedList, List<WorkExperiencePo> updatedList) {
        insertWorkExperienceBatch(insertedList);
        updateWorkExperienceBatch(updatedList);
        deleteWorkExperienceBatch(deletedList);
    }

    public void insertWorkExperienceBatch(List<WorkExperiencePo> list) {
        if (list != null) {
            Date d = new Date();
            for (WorkExperiencePo po : list) {
                po.setCreateTime(d);
                po.setUpdateTime(d);
                workExperiencePoMapper.insertSelective(po);
            }
        }
    }

    public void updateWorkExperienceBatch(List<WorkExperiencePo> list) {
        if (list != null) {
            Date d = new Date();
            for (WorkExperiencePo po : list) {
                po.setUpdateTime(d);
                workExperiencePoMapper.updateByPrimaryKeySelective(po);
            }
        }
    }

    public void deleteWorkExperienceBatch(List<WorkExperiencePo> list) {
        if (list != null) {
            for (WorkExperiencePo po : list) {
                workExperiencePoMapper.deleteByPrimaryKey(po);
            }
        }
    }

}
