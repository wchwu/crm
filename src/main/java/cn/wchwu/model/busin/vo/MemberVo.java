package cn.wchwu.model.busin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.wchwu.model.busin.CertificatePo;
import cn.wchwu.model.busin.EduExperiencePo;
import cn.wchwu.model.busin.FamilyPo;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.busin.WorkExperiencePo;

public class MemberVo implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 862185593412676735L;

    private MemberPo memberPo = new MemberPo();
    private List<WorkExperiencePo> workExperienceList = new ArrayList<>();
    private List<FamilyPo> familyList = new ArrayList<>();
    private List<EduExperiencePo> eduExperienceList = new ArrayList<>();
    private List<CertificatePo> CertificateList = new ArrayList<>();

    public MemberPo getMemberPo() {
        return memberPo;
    }

    public void setMemberPo(MemberPo memberPo) {
        this.memberPo = memberPo;
    }

    public List<WorkExperiencePo> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperiencePo> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    public List<FamilyPo> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(List<FamilyPo> familyList) {
        this.familyList = familyList;
    }

    public List<EduExperiencePo> getEduExperienceList() {
        return eduExperienceList;
    }

    public void setEduExperienceList(List<EduExperiencePo> eduExperienceList) {
        this.eduExperienceList = eduExperienceList;
    }

    public List<CertificatePo> getCertificateList() {
        return CertificateList;
    }

    public void setCertificateList(List<CertificatePo> certificateList) {
        CertificateList = certificateList;
    }

}