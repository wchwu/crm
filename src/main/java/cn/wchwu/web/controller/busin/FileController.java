
package cn.wchwu.web.controller.busin;

import cn.wchwu.model.busin.FileRecPo;
import cn.wchwu.service.busin.FileRecService;
import cn.wchwu.util.ConfigUtil;
import cn.wchwu.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 类的描述：
 * 
 * @ClassName: FileUploadController
 * @author ChaoWu.Wang
 * @date 2016年1月20日 下午6:37:37
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/file")
public class FileController {
    Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileRecService fileRecService;

    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject rsJson = new JSONObject();
        // Map<String, Object> paramsObj =
        // HttpClientUtils.getParamsObj(request);
        // log.info("参数：" + paramsObj);

        String memberId = request.getParameter("memberId");
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        System.out.println("=====================start=====================");
        FileRecPo fe = new FileRecPo();
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            int count = 0;
            int starttime = (int) System.currentTimeMillis();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {

                    // 取得当前上传文件的文件名称
                    String fileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (fileName.trim() != "") {
                        int fileId = fileRecService.getFileId();
                        // 重命名上传后的文件名
                        String contentType = file.getContentType();
                        long fileSize = (long) ((file.getSize()) / 1024);
                        // 定义上传路径
                        String uploadRootPath = ConfigUtil.getPropertyValue("upload.root.path"); // 上传文件根目录
                        String filePath = uploadRootPath + File.separator + memberId;
                        File f = new File(filePath);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        String lastFilePath = filePath + File.separator + fileName;

                        if (new File(lastFilePath).exists()) {
                            int index = fileName.lastIndexOf(".");
                            String toPrefix = "";
                            String toSuffix = "";
                            if (index == -1) {
                                toPrefix = fileName;
                            } else {
                                toPrefix = fileName.substring(0, index);
                                toSuffix = fileName.substring(index, fileName.length());
                            }

                            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                            String formatDate = format.format(new Date());
                            toPrefix = fileName + formatDate;
                            fileName = toPrefix + toSuffix;
                            lastFilePath = filePath + File.separator + fileName;
                        }
                        log.info("上传后的文件： " + lastFilePath); // 上传后的原始文件
                        fe.setFileName(fileName);
                        fe.setFileSize(new BigDecimal(fileSize));
                        fe.setId(fileId);
                        fe.setMemberId(Integer.valueOf(memberId));
                        Date t = new Date();
                        fe.setCreateTime(t);
                        fe.setUpdateTime(t);
                        fileRecService.insertFileRec(fe);

                        File localFile = new File(lastFilePath);
                        file.transferTo(localFile); // 上传操作

                        count++;
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                log.info("上传文件：" + file.getOriginalFilename() + ",花费：" + (finaltime - pre) + "毫秒");
            }
            int endtime = (int) System.currentTimeMillis();
            log.info("上传文件：" + count + "个,花费：" + (endtime - starttime) + "毫秒");
        }

        rsJson.put("code", "0000");
        rsJson.put("msg", "");
        rsJson.put("data", fe);

        return rsJson;
    }

    /**
     * delFile:删除文件
     * 
     * @param id
     * @return
     * @author Chaowu.Wang
     * @date 2018年11月18日 下午7:38:27
     */
    @RequestMapping("delFile")
    @ResponseBody
    public JSONObject delFile(@RequestParam(value = "id", required = true) Integer id) {
        JSONObject rsJson = new JSONObject();
        int ret = fileRecService.deleteFileRec(id);
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) Integer id) throws Exception {
        FileRecPo f = fileRecService.queryById(id);
        if (null != f) {
            String uploadRootPath = ConfigUtil.getPropertyValue("upload.root.path"); // 上传文件根目录
            String path = uploadRootPath + File.separator + f.getMemberId() + File.separator + f.getFileName();
            this.downloadFile(path, request, response);
        }
    }

    public void downloadFile(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        String fileName = file.getName();
        FileInputStream inputStream = new FileInputStream(file);
        int i = inputStream.available();
        // byte数组用于存放图片字节数据
        byte[] buff = new byte[i];
        inputStream.read(buff);
        // 记得关闭输入流
        inputStream.close();

        response.setCharacterEncoding("utf-8");
        // 设置发送到客户端的响应内容类型
        response.setContentType(this.getContentType(file.getCanonicalPath()));

        fileName = this.getFileName(fileName, request);

        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        // cookie添加，通知前端下载完成
        Cookie fileDownload = new Cookie("fileDownload", "true");
        fileDownload.setPath("/");
        response.addCookie(fileDownload);

        OutputStream out = response.getOutputStream();
        out.write(buff);
        // 关闭响应输出流
        out.close();
    }

    // 获取文件类型
    public String getContentType(String filename) {
        String type = null;
        Path path = Paths.get(filename);
        try {
            type = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    public String getFileName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        if (StringUtil.isEmpty(fileName)) {
            return fileName;
        }
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            // firefox浏览器
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0 || request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT") > 0
                || request.getHeader("User-Agent").toUpperCase().indexOf("EDGE") > 0) {
            // IE浏览器
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // IE下载文件名空格变+号问题
            fileName = fileName.replace("+", "%20");
        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
            // 谷歌
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return fileName;
    }
}
