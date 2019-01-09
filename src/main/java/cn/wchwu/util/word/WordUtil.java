/**
 *Copyright (c) 2019, ShangHai HOWBUY INVESTMENT MANAGEMENT Co., Ltd.
 *All right reserved.
 *
 *THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF HOWBUY INVESTMENT
 *MANAGEMENT CO., LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *WITHOUT THE PRIOR WRITTEN PERMISSION OF HOWBUY INVESTMENT MANAGEMENT
 * CO., LTD.
*/

package cn.wchwu.util.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @description:word文件处理工具类
 * @reason:
 * @author Chaowu.Wang
 * @date 2019年1月4日 下午3:29:51
 * @since JDK 1.6
 */
public class WordUtil {

    private static final Logger logger = LogManager.getLogger(WordUtil.class);

    public static void createWord(String tempFile, Map<String, Object> parametersMap, String exportFile) {

        try {
            Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
            // Map<String, Object> parametersMap = new HashMap<String,
            // Object>();// 存储报表中不循环的数据

            wordDataMap.put("parametersMap", parametersMap);

            File file = new File(tempFile);

            // 读取word模板
            FileInputStream fileInputStream = new FileInputStream(file);
            WordTemplate template = new WordTemplate(fileInputStream);

            // 替换数据
            template.replaceDocument(wordDataMap);

            // 生成文件
            File outputFile = new File(exportFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            template.getDocument().write(fos);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("word文件生成异常", e);

        }
    }

}
