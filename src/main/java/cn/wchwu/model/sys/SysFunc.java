package cn.wchwu.model.sys;

import java.io.Serializable;

/**
 * 系统功能实体类
 * 
 * @author orh
 *
 */
public class SysFunc implements Serializable {

	private static final long serialVersionUID = 1578199955038972429L;

	private String id;
	private String funcName;
	private String menuId;
	private String menuName;
	private String funcUrlPath;
	private String status;
	private String memo;
	private String logExpress;
	private String logType;
	private String logStatus;
	private String sysFlag; // 自动生成的功能

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getFuncUrlPath() {
		return funcUrlPath;
	}

	public void setFuncUrlPath(String funcUrlPath) {
		this.funcUrlPath = funcUrlPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLogExpress() {
		return logExpress;
	}

	public void setLogExpress(String logExpress) {
		this.logExpress = logExpress;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Override
	public String toString() {
		return "SysFunc [id=" + id + ", funcName=" + funcName + ", menuId="
				+ menuId + ", menuName=" + menuName + ", funcUrlPath="
				+ funcUrlPath + ", status=" + status + ", memo=" + memo
				+ ", logExpress=" + logExpress + ", logType=" + logType
				+ ", logStatus=" + logStatus + ", sysFlag=" + sysFlag + "]";
	}

}
