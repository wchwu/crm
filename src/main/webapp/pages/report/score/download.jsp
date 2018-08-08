<%@page pageEncoding="UTF-8"%>
<%@page import="javax.servlet.ServletOutputStream"%>
<%@page import="java.io.*"%>
<%
	//获取标签中使用的国际化资源信息
	String fileNotExist ="文件不存在";
	String localFile = (String) request.getAttribute("downloadFile");
	String fileName = (String)  request.getAttribute("fileName");
	
	System.out.println("fileName:" + fileName);
	System.out.println("localFile:" + localFile);
	
	fileName = new String(fileName.getBytes("GB2312"), "ISO_8859_1");
	
	byte[] buffer = new byte[512];
	int size = 0;
	response.reset();
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition", "attachment;filename=" + fileName);
	ServletOutputStream os = null;
	FileInputStream in = null;
	try {
		os = response.getOutputStream();
		File downloadFile = new File(localFile);
		if (downloadFile != null && downloadFile.exists()) {
			in = new FileInputStream(new File(localFile));
			while ((size = in.read(buffer)) != -1) {
				os.write(buffer, 0, size);
			}
			out.clear();
			out = pageContext.pushBody();
		} else {
			System.out.println("download file:" + localFile + "  " + fileNotExist);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (in != null)
				in.close();
			if (os != null)
				os.close();
			File file = new File(localFile);
			if (file != null && file.isFile() && file.exists()) {
				file.delete();
				System.out.println("导出成功，删除临时文件：" + localFile );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
%>
