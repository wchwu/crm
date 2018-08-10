package cn.wchwu.model.busin;

import java.io.Serializable;
import java.util.Date;

public class CertificatePo implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 6350810963981529069L;

    private String gainDate;

    private String prize;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    public String getGainDate() {
        return gainDate;
    }

    private Integer id;

    private Integer memberId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public void setGainDate(String gainDate) {
        this.gainDate = gainDate == null ? null : gainDate.trim();
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize == null ? null : prize.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}