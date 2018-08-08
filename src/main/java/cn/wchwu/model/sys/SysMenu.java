package cn.wchwu.model.sys;

import java.io.Serializable;

public class SysMenu implements Serializable {

	private static final long serialVersionUID = -7946723030368126580L;

	private String id;
	private String pid;
	private String text;
	private String url;
	private String iconCls;
	private String status;
	private int rank;
	private int menuLevel;
	private String openModel;
	private String isleaf;
	private String idSeq;
	private String state;
	private boolean checked;
	
	
	private Object attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}


	public String getOpenModel() {
		return openModel;
	}

	public void setOpenModel(String openModel) {
		this.openModel = openModel;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	@Override
	public String toString() {
		return "SysMenu [id=" + id + ", pid=" + pid + ", text=" + text
				+ ", url=" + url + ", iconCls=" + iconCls + ", status="
				+ status + ", rank=" + rank + ", menuLevel=" + menuLevel
				+ ", openModel=" + openModel + ", isleaf=" + isleaf
				+ ", idSeq=" + idSeq + ", state=" + state + ", checked="
				+ checked + ", attributes=" + attributes + "]";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(String idSeq) {
		this.idSeq = idSeq;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
