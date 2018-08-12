
package cn.wchwu.service.busin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.busin.FamilyPoMapper;
import cn.wchwu.model.busin.FamilyPo;
import cn.wchwu.service.busin.FamilyService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月10日 下午7:54:29
 * @since JDK 1.6
 */
@Service
public class FamilyServiceImpl extends BaseService<FamilyPo> implements FamilyService {

    @Autowired
    private FamilyPoMapper familyPoMapper;

    @Override
    public List<FamilyPo> queryListById(Integer memberId) {
        return familyPoMapper.queryListById(memberId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Mapper<FamilyPo> getMapper() {
        return (Mapper<FamilyPo>) familyPoMapper;
    }

    @Override
    public void saveFamilyBatch(List<FamilyPo> deletedList, List<FamilyPo> insertedList, List<FamilyPo> updatedList) {
        insertFamilyBatch(insertedList);
        updateFamilyBatch(updatedList);
        deleteFamilyBatch(deletedList);
    }

    public void insertFamilyBatch(List<FamilyPo> list) {
        if (list != null) {
            Date d = new Date();
            for (FamilyPo po : list) {
                po.setCreateTime(d);
                po.setUpdateTime(d);
                familyPoMapper.insertSelective(po);
            }
        }
    }

    public void updateFamilyBatch(List<FamilyPo> list) {
        if (list != null) {
            Date d = new Date();
            for (FamilyPo po : list) {
                po.setUpdateTime(d);
                familyPoMapper.updateByPrimaryKeySelective(po);
            }
        }
    }

    public void deleteFamilyBatch(List<FamilyPo> list) {
        if (list != null) {
            for (FamilyPo po : list) {
                familyPoMapper.deleteByPrimaryKey(po);
            }
        }
    }

}
