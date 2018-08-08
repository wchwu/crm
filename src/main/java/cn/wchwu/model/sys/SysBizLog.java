package cn.wchwu.model.sys;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 系统业务日志
 * @author wcw.sh
 * @version 创建时间：2015年5月6日  下午3:26:53
 */
public class SysBizLog implements Serializable {

	private static final long serialVersionUID = -3176348156997187622L;

	private long id;
	private String optLoginName;
	private String optRealName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	private String menuName;
	private String funcName;
	private String reqPath;
	private String reqParams;
	private String logMsg;
	private String logType;
	private String memo;
	private String clientIp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOptLoginName() {
		return optLoginName;
	}

	public void setOptLoginName(String optLoginName) {
		this.optLoginName = optLoginName;
	}

	public String getOptRealName() {
		return optRealName;
	}

	public void setOptRealName(String optRealName) {
		this.optRealName = optRealName;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getReqPath() {
		return reqPath;
	}

	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}

	public String getReqParams() {
		return reqParams;
	}

	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "SysBizLog [id=" + id + ", optLoginName=" + optLoginName
				+ ", optRealName=" + optRealName + ", insertDate=" + insertDate
				+ ", menuName=" + menuName + ", funcName=" + funcName
				+ ", reqPath=" + reqPath + ", reqParams=" + reqParams
				+ ", logMsg=" + logMsg + ", logType=" + logType + ", memo="
				+ memo + ", clientIp=" + clientIp + "]";
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
