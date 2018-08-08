package cn.wchwu.model.sys;

import java.io.Serializable;

public class SysRole implements Serializable {

	private static final long serialVersionUID = 8249939135421142723L;

	private String roleCode;
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public SysRole() {
	}

	public SysRole(String roleCode, String roleName) {
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "SysRole [roleCode=" + roleCode + ", roleName=" + roleName + "]";
	}
}
