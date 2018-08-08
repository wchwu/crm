package cn.wchwu.model.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统操作员实体类
 * 
 * @author orh
 *
 */
public class SysOperator implements Serializable {

	private static final long serialVersionUID = -8411043638208615553L;

	private long id; // 主键ID
	private String loginName; // 登录名称
	private String authMode; // 认证模式
	private String password; // 登录密码
	private String realName; // 真实姓名
	private String gender; // 性别
	private String tel; // 联系电话
	private String address; // 住址
	private String email; // 邮箱
	private String status; // 操作员状态
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "SysOperator [id=" + id + ", loginName=" + loginName
				+ ", authMode=" + authMode + ", password=" + password
				+ ", realName=" + realName + ", gender=" + gender + ", tel="
				+ tel + ", address=" + address + ", email=" + email
				+ ", status=" + status + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", attributes=" + attributes
				+ "]";
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public boolean getLocked() {
		
		return "1".equals(this.status);
	}

}
