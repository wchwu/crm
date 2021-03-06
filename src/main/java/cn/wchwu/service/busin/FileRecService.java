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

import cn.wchwu.model.busin.FileRecPo;
import java.util.List;

/**
 * @description:文件记录
 * @reason:
 * @author Chaowu.Wang
 * @date 2018年10月21日 下午11:50:34
 * @since JDK 1.6
 */
public interface FileRecService extends IService<FileRecPo> {

    /**
     * queryListById:查询文件列表
     * 
     * @param memberId
     * @return
     * @author Chaowu.Wang
     * @date 2018年10月21日 下午11:51:09
     */
    public List<FileRecPo> queryListByMemberId(Integer memberId);

    /**
     * queryListByRuleId:查询制度文件
     * 
     * @param ruleId
     * @return
     * @author Chaowu.Wang
     * @date 2018年11月19日 下午2:39:21
     */
    public List<FileRecPo> queryListByRuleId(Integer ruleId);

    /**
     * saveFileBatch:保存文件记录
     * 
     * @param deletedList
     * @param insertedList
     * @param updatedList
     * @author Chaowu.Wang
     * @date 2018年10月21日 下午11:52:02
     */
    public void saveFileBatch(List<FileRecPo> deletedList, List<FileRecPo> insertedList, List<FileRecPo> updatedList);

    public int getFileId();

    public int insertFileRec(FileRecPo record);

    public int updateFileRec(FileRecPo record);

    public int deleteFileRec(Integer id);

    public FileRecPo queryById(Integer id);
}
