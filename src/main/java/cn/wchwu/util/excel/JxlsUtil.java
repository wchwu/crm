/**
 * Copyright (c) 2018, ShangHai HOWBUY INVESTMENT MANAGEMENT Co., Ltd.
 * All right reserved.
 * <p>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF HOWBUY INVESTMENT
 * MANAGEMENT CO., LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 * TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 * WITHOUT THE PRIOR WRITTEN PERMISSION OF HOWBUY INVESTMENT MANAGEMENT
 * CO., LTD.
 */
package cn.wchwu.util.excel;

import cn.wchwu.util.StringUtil;
import com.alibaba.fastjson.JSON;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;

/**
 * @author bin.li
 * @description: 调用JXLS引擎生成excel工具类
 * @reason: JXLS生成excel
 * @date 2018/2/1 9:29
 * @since JDK 1.7
 */
public class JxlsUtil {

    private static final Logger logger = LogManager.getLogger(JxlsUtil.class);

    /**
     * generateExcel:生成excel. <br/>
     * 支持单个sheet模式.<br/>
     *
     * @param templateFile
     *            模板路径文件
     * @param beanParams
     *            数据参数
     * @param destFilePath
     *            生成excel的路径文件
     * @return void
     * @author bin.li
     * @date 2018/2/1 16:12
     */
    public static void generateExcel(String templateFile, Map<String, Object> beanParams, String destFilePath) {
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(templateFile, beanParams, destFilePath);
        } catch (Exception e) {
            logger.error("jxls generate excel error:{}", e);
        }
    }

    /**
     * generateMultipleSheetsExcel:生成多sheet模式excel. <br/>
     * 暂时采用支持多sheet的模式原因是支持sheet名称自定义.<br/>
     *
     * @param templateFile
     *            模板路径文件
     * @param beanParams
     *            数据参数
     * @param destFilePath
     *            生成excel的路径文件
     * @param sheetName
     *            excel sheet名称
     * @return void
     * @author bin.li
     * @date 2018/2/1 17:13
     */
    @SuppressWarnings("rawtypes")
    public static void generateMultipleSheetsExcel(String templateFile, Map<String, Object> beanParams, String destFilePath, String sheetName) {
        try {
            FileInputStream is = new FileInputStream(templateFile);

            List<Map<String, Object>> list = new ArrayList<>();
            list.add(beanParams);

            ArrayList<List> objects = new ArrayList<>();
            objects.add(list);

            // sheet的名称
            List<String> listSheetNames = new ArrayList<>();
            listSheetNames.add(sheetName);

            // 调用JXLS引擎生成excel
            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformMultipleSheetsList(is, objects, listSheetNames, "list", new HashMap(0), 0);
            workbook.write(new FileOutputStream(destFilePath));
        } catch (Exception e) {
            logger.error("jxls generate multiple sheets excel error:{}", e);
        }
    }

    /**
     * exportExcel:导出excel. <br/>
     * 支持自定义函数.<br/>
     *
     * @param templateFilePath
     *            模板路径
     * @param destFilePath
     *            导出excel路径
     * @param model
     *            数据模型
     * @param sheetName
     *            sheet名称
     * @return void
     * @author bin.li
     * @date 2018/2/5 13:02
     */
    public static void exportExcel(String templateFilePath, String destFilePath, Map<String, Object> model, String sheetName) {
        // 因为excel sheet名称最多只能输入31位长度，超过会自动截取掉，并且报错，所有超过31截取sheet名
        if (StringUtil.isNotEmpty(sheetName) && sheetName.length() > 31) {
            sheetName = sheetName.substring(0, 31);
        }
        try {
            InputStream is = new FileInputStream(templateFilePath);
            OutputStream os = new FileOutputStream(destFilePath);
            exportExcel(is, os, model, sheetName);
            // 删除多出来的sheet
            deleteSheet(destFilePath, "Sheet1");
        } catch (Exception e) {
            logger.error("通过jxls引擎导出excel，异常 e:{}", e);
        }
    }

    /**
     * exportExcel:导出excel. <br/>
     * 支持自定义函数.<br/>
     *
     * @param templateStream
     *            模板路径流
     * @param os
     *            导出excel文件路径流
     * @param model
     *            数据模型
     * @param sheetName
     *            sheet名称
     * @return void
     * @author bin.li
     * @date 2018/2/5 11:39
     */
    private static void exportExcel(InputStream templateStream, OutputStream os, Map<String, Object> model, String sheetName) throws IOException {
        Context context = new Context(model);
        Transformer transformer = TransformerFactory.createTransformer(templateStream, os);

        // 添加自定义函数供excel使用
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<>();
        funcs.put("utils", new JxlsUtil());
        evaluator.getJexlEngine().setFunctions(funcs);
        // 直接构建区域信息，需要excel中已设置好批注信息，注意A1区域一定要写批注（开始点）
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        Area xlsArea = xlsAreaList.get(0);
        xlsArea.applyAt(new CellRef(sheetName + "!A1"), context);
        transformer.write();
    }

    /**
     * dateFormat:excel日期格式化. <br/>
     * excel中使用标签例子：.<br/>
     * ${utils:dateFormat(r.date,"yyyy-MM-dd")}
     *
     * @param date
     *            日期
     * @param format
     *            格式
     * @return java.lang.String
     * @author bin.li
     * @date 2018/2/5 13:15
     */
    public String dateFormat(Date date, String format) {
        if (null == date) {
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.error("通过jxls引擎导出excel，dateFormat error:{}", JSON.toJSONString(date));
        }
        return "";
    }

    /**
     * dateFormat:返回yyyy-mm-dd格式的日期
     *
     * @param dateyyyymmdd
     * @return
     * @author cheng.tao
     * @date 2018年2月12日 下午2:32:27
     */
    public String dateFormat(String date) {
        if (StringUtil.isNotBlank(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        }
        return "";
    }

    /**
     * 
     * dateFormat:返回yyyy-mm-dd格式的日期
     * 
     * @param dateyyyymmdd
     *            format 分割符
     * @return
     * @author cheng.tao
     * @date 2018年2月12日 下午2:32:27
     */
    public String dateFormat(String date, String format) {
        if (StringUtil.isBlank(date)) {
            return "";
        }
        if (StringUtil.isBlank(format)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        } else {
            return date.substring(0, 4) + format + date.substring(4, 6) + format + date.substring(6, 8);
        }
    }

    /**
     * decimalFormat:excel金额格式化. <br/>
     * excel中使用标签例子.<br/>
     * ${utils:decimalFormat(r.amount,"###,###.00","元")}
     *
     * @param value
     *            金额值
     * @param format
     *            格式化
     * @param unit
     *            单位
     * @return java.lang.String
     * @author bin.li
     * @date 2018/2/5 13:17
     */
    public String decimalFormat(double value, String format, String unit) {
        if (value > 0) {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(value) + unit;
        }
        return "0.00" + unit;
    }

    /**
     * 
     * numFormat:金额格式化
     * 
     * @param value
     * @param format
     * @param unit
     * @return
     * @author cheng.tao
     * @date 2018年2月12日 上午10:03:36
     */
    public String numFormat(BigDecimal value, String format, String unit) {
        if (null != value && 0 < value.compareTo(new BigDecimal("0"))) {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(value) + unit;
        }
        return "0.00" + unit;
    }

    /**
     * 金额类型字符串转换 numFormat:
     * 
     * @param value
     * @param format
     * @param unit
     * @return
     * @author jinkai.mao
     * @date 2018年10月25日 下午1:39:30
     */
    public String stringNumFormat(String value, String format, String unit) {
        if (null != value && value != "" && 0 < new BigDecimal(value).compareTo(new BigDecimal("0"))) {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(new BigDecimal(value)) + unit;
        }
        return "-";
    }

    /**
     * getEnumValue:excel获取枚举值. <br/>
     * excel中使用标签例子： ${utils:getEnumValue(r.defDividendMethod,
     * "[\"0\":\"红利再投\",\"1\":\"现金分红\"]")}
     *
     * @param key
     *            枚举键
     * @param valueMap
     *            枚举值
     * @return java.lang.String
     * @author bin.li
     * @date 2018/2/5 13:05
     */
    @SuppressWarnings("unchecked")
    public String getEnumValue(String key, String valueMap) {
        if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(valueMap)) {
            String replace = valueMap.replace("[", "{").replace("]", "}");
            Map<String, String> values = (Map<String, String>) (JSON.parse(replace));

            if (values.containsKey(key)) {
                return values.get(key);
            }
        }
        return "";
    }

    /**
     * 
     * getEnumValue:获取枚举值
     * 
     * @param key
     * @param valueMap
     * @param defaultValue默认值
     * @return
     * @author cheng.tao
     * @date 2018年2月12日 下午1:56:44
     */
    @SuppressWarnings("unchecked")
    public String getEnumValue(String key, String valueMap, String defaultValue) {
        if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(valueMap)) {
            String replace = valueMap.replace("[", "{").replace("]", "}");
            Map<String, String> values = (Map<String, String>) (JSON.parse(replace));

            if (values.containsKey(key)) {
                return values.get(key);
            } else {
                return defaultValue;
            }
        }
        return "";
    }

    /**
     * deleteSheet:删除excel sheet. <br/>
     *
     * @param outExcelFile
     *            导出的excel文件
     * @param sheetName
     *            sheet名称
     * @return void
     * @author bin.li
     * @date 2018/2/5 11:31
     */
    private static void deleteSheet(String outExcelFile, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(outExcelFile);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            fileWrite(outExcelFile, wb);
            // 删除Sheet
            wb.removeSheetAt(wb.getSheetIndex(sheetName));
            fileWrite(outExcelFile, wb);
            fis.close();
        } catch (Exception e) {
            logger.error("删除excel sheet异常 e:{}", e);
        }
    }

    /**
     * fileWrite:写excel文件. <br/>
     *
     * @param outExcelFile
     *            导出的excel文件
     * @param wb
     *            excel工作空间
     * @return void
     * @author bin.li
     * @date 2018/2/5 11:33
     */
    private static void fileWrite(String outExcelFile, HSSFWorkbook wb) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(outExcelFile);
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}
