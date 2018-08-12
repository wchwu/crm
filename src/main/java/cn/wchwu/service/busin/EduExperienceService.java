
package cn.wchwu.service.busin;

import java.util.List;

import cn.wchwu.model.busin.EduExperiencePo;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:35:50
 * @since JDK 1.6
 */
public interface EduExperienceService extends IService<EduExperiencePo> {

    /**
     * queryListById:查询教育经历列表
     * 
     * @param memberId
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月10日 下午7:51:59
     */
    public List<EduExperiencePo> queryListById(Integer memberId);

    /**
     * saveWorkExperienceBatch:保存教育经历
     * 
     * @param deletedList
     * @param insertedList
     * @param updatedList
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午6:12:27
     */
    public void saveEduExperienceBatch(List<EduExperiencePo> deletedList, List<EduExperiencePo> insertedList, List<EduExperiencePo> updatedList);
}
