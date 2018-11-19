package cn.wchwu.web.controller.busin;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.busin.CertificatePo;
import cn.wchwu.model.busin.EduExperiencePo;
import cn.wchwu.model.busin.FamilyPo;
import cn.wchwu.model.busin.MemberPo;
import cn.wchwu.model.busin.WorkExperiencePo;
import cn.wchwu.model.busin.vo.MemberVo;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.service.busin.CertificateService;
import cn.wchwu.service.busin.EduExperienceService;
import cn.wchwu.service.busin.FamilyService;
import cn.wchwu.service.busin.MemberService;
import cn.wchwu.service.busin.WorkExperienceService;
import cn.wchwu.service.sys.SysDictService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @description:成员controller
 * @reason:TODO ADD REASON(可选)
 * @author Chaowu.Wang
 * @date 2018年8月5日 下午9:36:48
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private FamilyService familyService;
    @Autowired
    private WorkExperienceService workExperienceService;
    @Autowired
    private EduExperienceService eduExperienceService;
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private SysDictService sysDictService;

    // 业务字典翻译
    static FillDictEntityModel genderMapping = new FillDictEntityModel("SYS_GENDER", "gender", "genderName");
    static FillDictEntityModel nationMapping = new FillDictEntityModel("NATION", "nation", "nationName");
    static FillDictEntityModel marriageMapping = new FillDictEntityModel("MARRIAGE", "marriage", "marriageName");
    static FillDictEntityModel polityMapping = new FillDictEntityModel("POLITY", "polity", "polityName");
    static FillDictEntityModel statusMapping = new FillDictEntityModel("STATUS", "status", "statusName");
    static FillDictEntityModel deptMapping = new FillDictEntityModel("DEPT", "dept", "deptName");

    @RequestMapping("memberList")
    @ResponseBody
    public JSONObject memberList(Integer page, Integer rows, @FormModel("member") MemberPo member) {
        // 构建分页对象
        PageCond pageCond = new PageCond(page, rows);
        System.out.println("查询参数：" + JSONObject.toJSONString(member));
        // 执行查询
        List<MemberPo> list = memberService.queryList(pageCond, member);

        // 转换成jsonArr,再根据业务字典翻译字段 genderName
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
        FillDictEntityModel[] mappings = { genderMapping, nationMapping, marriageMapping, polityMapping, statusMapping, deptMapping };

        // 构建返回结果
        JSONObject rsJson = new JSONObject();
        System.out.println(JSON.toJSONString(sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings)));
        rsJson.put("rows", sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
        rsJson.put("total", pageCond.getTotalRows());
        return rsJson;
    }

    /**
     * memberDelete:删除成员信息
     * 
     * @param ids
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午6:08:13
     */
    @RequestMapping("del")
    @ResponseBody
    public JSONObject memberDelete(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(ids));
        System.out.println(jsonArr.toJSONString());
        JSONObject rsJson = new JSONObject();
        int ret = memberService.delBatch(ids);

        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("updateMember")
    @ResponseBody
    public JSONObject updateMember(@ModelAttribute("member") MemberPo member) {
        System.out.println("提交参数：" + JSONObject.toJSONString(member));
        JSONObject rsJson = new JSONObject();
        int ret = memberService.update(member);
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("updateMemberBatch")
    @ResponseBody
    public JSONObject updateMemberBatch(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rsJson = new JSONObject();
        String ids = request.getParameter("ids");
        String type = request.getParameter("type");
        String[] idsArr = ids.split(",");
        int ret = 0;
        if (null != idsArr && idsArr.length > 0) {
            for (int i = 0; i < idsArr.length; i++) {
                MemberPo member = new MemberPo();
                if (StringUtil.isEmpty(idsArr[i])) {
                    continue;
                }
                member.setId(Integer.valueOf(idsArr[i]));
                member.setType(type);
                ret += memberService.update(member);
            }
        }

        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    @RequestMapping("addMember")
    @ResponseBody
    public JSONObject addMember(@ModelAttribute("member") MemberPo member) {
        System.out.println("提交参数：" + JSONObject.toJSONString(member));
        JSONObject rsJson = new JSONObject();
        int ret = memberService.save(member);
        if (ret > 0) {
            rsJson.put("success", "0");
        } else {
            rsJson.put("success", "9");
            rsJson.put("errorMsg", "操作失败");
        }
        return rsJson;
    }

    /**
     * getMemberId:生成MemberId
     * 
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午8:02:01
     */
    @RequestMapping("getMemberId")
    @ResponseBody
    public JSONObject getMemberId() {
        int memberId = memberService.getMemberId();
        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        rsJson.put("memberId", memberId);
        return rsJson;
    }

    @RequestMapping("getMemberById")
    @ResponseBody
    public String getMemberById(@RequestParam(value = "id", required = true) Integer id) {
        MemberVo memberVo = memberService.getMemberById(id);
        return JSONObject.toJSONString(memberVo, SerializerFeature.WriteMapNullValue);
    }

    /**
     * getFamilyById:查询家庭情况
     * 
     * @param memberId
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午5:59:41
     */
    @RequestMapping("getFamilyById")
    @ResponseBody
    public JSONObject getFamilyById(@RequestParam(value = "memberId", required = false) Integer memberId) {
        JSONObject rsJson = new JSONObject();
        List<FamilyPo> familyList = new ArrayList<>();
        if (null != memberId && memberId != 0) {
            familyList = familyService.queryListById(memberId);
        }
        rsJson.put("rows", familyList);
        rsJson.put("total", familyList.size());
        return rsJson;
    }

    /**
     * saveFamily:保存家庭情况
     * 
     * @param deleted
     * @param inserted
     * @param updated
     * @return
     * @author Chaowu.Wang
     * @date 2018年8月12日 下午6:20:08
     */
    @RequestMapping(value = "saveFamily")
    @ResponseBody
    public JSONObject saveFamily(String deleted, String inserted, String updated) {
        List<FamilyPo> deletedList = null;
        List<FamilyPo> insertedList = null;
        List<FamilyPo> updatedList = null;
        if (deleted != null) {
            deletedList = JSON.parseArray(deleted, FamilyPo.class);
        }
        if (inserted != null) {
            insertedList = JSON.parseArray(inserted, FamilyPo.class);
        }
        if (updated != null) {
            updatedList = JSON.parseArray(updated, FamilyPo.class);
        }

        familyService.saveFamilyBatch(deletedList, insertedList, updatedList);

        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        return rsJson;
    }

    @RequestMapping("getWorkExperienceById")
    @ResponseBody
    public JSONObject getWorkExperienceById(@RequestParam(value = "memberId", required = false) Integer memberId) {
        JSONObject rsJson = new JSONObject();
        List<WorkExperiencePo> list = new ArrayList<>();
        if (null != memberId && memberId != 0) {
            list = workExperienceService.queryListById(memberId);
        }
        rsJson.put("rows", list);
        rsJson.put("total", list.size());
        return rsJson;
    }

    @RequestMapping(value = "saveWorkExperience")
    @ResponseBody
    public JSONObject saveWorkExperience(String deleted, String inserted, String updated) {
        List<WorkExperiencePo> deletedList = null;
        List<WorkExperiencePo> insertedList = null;
        List<WorkExperiencePo> updatedList = null;
        if (deleted != null) {
            deletedList = JSON.parseArray(deleted, WorkExperiencePo.class);
        }
        if (inserted != null) {
            insertedList = JSON.parseArray(inserted, WorkExperiencePo.class);
        }
        if (updated != null) {
            updatedList = JSON.parseArray(updated, WorkExperiencePo.class);
        }

        workExperienceService.saveWorkExperienceBatch(deletedList, insertedList, updatedList);

        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        return rsJson;
    }

    @RequestMapping("getEduExperienceById")
    @ResponseBody
    public JSONObject getEduExperienceById(@RequestParam(value = "memberId", required = false) Integer memberId) {
        JSONObject rsJson = new JSONObject();
        List<EduExperiencePo> list = new ArrayList<>();
        if (null != memberId && memberId != 0) {
            list = eduExperienceService.queryListById(memberId);
        }
        rsJson.put("rows", list);
        rsJson.put("total", list.size());
        return rsJson;
    }

    @RequestMapping(value = "saveEduExperience")
    @ResponseBody
    public JSONObject saveEduExperience(String deleted, String inserted, String updated) {
        List<EduExperiencePo> deletedList = null;
        List<EduExperiencePo> insertedList = null;
        List<EduExperiencePo> updatedList = null;
        if (deleted != null) {
            deletedList = JSON.parseArray(deleted, EduExperiencePo.class);
        }
        if (inserted != null) {
            insertedList = JSON.parseArray(inserted, EduExperiencePo.class);
        }
        if (updated != null) {
            updatedList = JSON.parseArray(updated, EduExperiencePo.class);
        }

        eduExperienceService.saveEduExperienceBatch(deletedList, insertedList, updatedList);

        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        return rsJson;
    }

    @RequestMapping("getCertificateById")
    @ResponseBody
    public JSONObject getCertificateById(@RequestParam(value = "memberId", required = false) Integer memberId) {
        JSONObject rsJson = new JSONObject();
        List<CertificatePo> list = new ArrayList<>();
        if (null != memberId && memberId != 0) {
            list = certificateService.queryListById(memberId);
        }
        rsJson.put("rows", list);
        rsJson.put("total", list.size());
        return rsJson;
    }

    @RequestMapping(value = "saveCertificate")
    @ResponseBody
    public JSONObject saveCertificate(String deleted, String inserted, String updated) {
        List<CertificatePo> deletedList = null;
        List<CertificatePo> insertedList = null;
        List<CertificatePo> updatedList = null;
        if (deleted != null) {
            deletedList = JSON.parseArray(deleted, CertificatePo.class);
        }
        if (inserted != null) {
            insertedList = JSON.parseArray(inserted, CertificatePo.class);
        }
        if (updated != null) {
            updatedList = JSON.parseArray(updated, CertificatePo.class);
        }

        certificateService.saveCertificateBatch(deletedList, insertedList, updatedList);

        JSONObject rsJson = new JSONObject();
        rsJson.put("success", "0");
        return rsJson;
    }

}
