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

import cn.wchwu.model.busin.CertificatePo;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:35:50
 * @since JDK 1.6
 */
public interface CertificateService extends IService<CertificatePo> {

    /**
     * queryListById:查询证书列表
     * 
     * @param memberId
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月10日 下午7:51:59
     */
    public List<CertificatePo> queryListById(Integer memberId);

    /**
     * saveFamilyBatch:保存证书情况
     * 
     * @param deletedList
     * @param insertedList
     * @param updatedList
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午6:12:27
     */
    public void saveCertificateBatch(List<CertificatePo> deletedList, List<CertificatePo> insertedList, List<CertificatePo> updatedList);
}
