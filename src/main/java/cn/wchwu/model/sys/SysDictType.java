package cn.wchwu.model.sys;

import java.io.Serializable;

public class SysDictType implements Serializable {

	private static final long serialVersionUID = 2521035022094454743L;

	private String dictTypeId;
	private String dictTypeName;
	private int rank;
	private String parentId;
	private String seqNo;

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
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

	@Override
	public String toString() {
		return "SysDictType [dictTypeId=" + dictTypeId + ", dictTypeName="
				+ dictTypeName + ", rank=" + rank + ", parentId=" + parentId
				+ ", seqNo=" + seqNo + "]";
	}

}
