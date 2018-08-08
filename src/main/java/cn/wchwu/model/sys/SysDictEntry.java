package cn.wchwu.model.sys;

import java.io.Serializable;

public class SysDictEntry implements Serializable {
	
	private static final long serialVersionUID = 5419165472913905338L;
	
	private String dictTypeId;
	private String dictId;
	private String dictName;
	private int status;
	private int sortNo;
	private int rank;
	private String parentId;
	private String seqNo;
	private String filter1;
	private String filter2;

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getFilter1() {
		return filter1;
	}

	public void setFilter1(String filter1) {
		this.filter1 = filter1;
	}

	public String getFilter2() {
		return filter2;
	}

	public void setFilter2(String filter2) {
		this.filter2 = filter2;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	@Override
	public String toString() {
		return "SysDictEntry [dictTypeId=" + dictTypeId + ", dictId=" + dictId
				+ ", dictName=" + dictName + ", status=" + status + ", sortNo="
				+ sortNo + ", rank=" + rank + ", parentId=" + parentId
				+ ", seqNo=" + seqNo + ", filter1=" + filter1 + ", filter2="
				+ filter2 + "]";
	}

}
