
package cn.wchwu.web.controller.busin;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;

import cn.wchwu.model.busin.vo.FileEntity;
import cn.wchwu.util.ConfigUtil;

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

    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Map<String, Object> paramsObj =
        // HttpClientUtils.getParamsObj(request);
        // log.info("参数：" + paramsObj);
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        System.out.println("=====================start=====================");
        FileEntity fe = new FileEntity();
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
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 重命名上传后的文件名
                        String fileName = file.getOriginalFilename();
                        String contentType = file.getContentType();
                        long fileSize = (long) ((file.getSize()) / 1024);
                        // 定义上传路径
                        String uploadRootPath = ConfigUtil.getPropertyValue("upload.root.path"); // web端根目录
                        String uploadPath = ConfigUtil.getPropertyValue("upload.path"); // web相对路径目录
                        String path = uploadRootPath + uploadPath;
                        // path = FileUtil.getFileDirectory(path);
                        String newFileName = fileName;
                        String lastFilePath = path + newFileName;
                        log.info("上传后的文件： " + lastFilePath); // 上传后的原始文件
                        // attach.setAttachId(attachId);
                        fe.setFileName(fileName);
                        fe.setFileSize(fileSize);

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
        // return JSONObject.fromObject(fe);
        return null;
    }

}
