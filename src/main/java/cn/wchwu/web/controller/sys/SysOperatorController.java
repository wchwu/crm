package cn.wchwu.web.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.spring.mvc.bind.annotation.FormModel;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysOperator;
import cn.wchwu.service.sys.SysDictService;
import cn.wchwu.service.sys.SysOperatorService;
import cn.wchwu.web.filter.AuthorityFilter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 系统操作员控制器
 * 
 * @author orh
 *
 */
@Controller
@RequestMapping("/sys/operator")
public class SysOperatorController {

	@Autowired
	private SysOperatorService sysOperatorService;

	@Autowired
	private SysDictService sysDictService;

	// 实体与业务字典映射关系
	static FillDictEntityModel genderMapping = new FillDictEntityModel(
			"SYS_GENDER", "gender", "genderName");
	static FillDictEntityModel statusMapping = new FillDictEntityModel(
			"SC_OPERATOR_STATUS", "status", "statusName");
	static FillDictEntityModel authModeMapping = new FillDictEntityModel(
			"SYS_AUTHMODE", "authMode", "authModeName");

	/**
	 * 分页查询操作员
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            一页显示条数
	 * @param operator
	 *            查询参数载体(如果需要添加不在此实体内的参数，需要另外在方法中增加参数)
	 * @return
	 */
	@RequestMapping("pageView")
	@ResponseBody
	public JSONObject pageView(Integer page, Integer rows,
			@FormModel("operator") SysOperator operator, String roleCode,
			String notInRoleCode) {

		// 构建查询参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("likeLoginName", operator.getLoginName());
		map.put("realName", operator.getRealName());
		map.put("email", operator.getEmail());
		map.put("status", operator.getStatus());
		map.put("roleCode", roleCode);
		map.put("notInRoleCode", notInRoleCode);

		// 构建分页对象
		PageCond pageCond = new PageCond(page, rows);

		// 执行查询
		List<SysOperator> list = sysOperatorService.querySysOperator(pageCond,
				map);

		// 转换成jsonArr,再根据业务字典翻译字段 genderName
		JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));
		FillDictEntityModel[] mappings = { genderMapping, statusMapping,
				authModeMapping };

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("rows",
				sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
		rsJson.put("total", pageCond.getTotalRows());

		return rsJson;
	}

	/**
	 * 根据操作员ID获取操作员信息
	 * 
	 * @param id
	 *            操作员唯一ID
	 * @return
	 */
	@RequestMapping("getById")
	@ResponseBody
	public JSONObject getById(@RequestParam(required = true) long id) {
		// 按id查询
		SysOperator operator = sysOperatorService.getById(id);

		JSONObject rsJson = new JSONObject();
		if (operator != null) {
			rsJson = JSONObject.parseObject(JSONObject.toJSONString(operator));
			rsJson = sysDictService.fillJSONObjectWithDictValue(rsJson,
					authModeMapping);
		}
		// 构建返回结果
		return rsJson;
	}

	/**
	 * 新增操作员
	 * 
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(@FormModel("operator") SysOperator operator) {
		// 加密密码
		operator.setPassword(DigestUtils.md5Hex(operator.getPassword()));
		// 执行插入
		sysOperatorService.insertSysOperator(operator);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 删除操作员(更新状态为已删除)
	 * 
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	public JSONObject del(
			@RequestParam(value = "ids", required = true) List<Long> ids) {
		// 执行删除
		sysOperatorService.delSysOperatorBatch(ids);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 更新操作员
	 * 
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public JSONObject update(@FormModel("operator") SysOperator operator) {
		// 执行更新
		sysOperatorService.updateSysOperator(operator);

		// 构建返回结果
		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("modifyPassword")
	@ResponseBody
	public JSONObject modifyPassword(@RequestParam(required = true) String oldPassword,
			@RequestParam(required = true) String newPassword,
			HttpServletRequest request) {
		// 构建返回结果
		JSONObject rsJson = new JSONObject();

		HttpSession session = request.getSession();

		// 取session 用户信息，用于得到id
		SysOperator sessionOperator = (SysOperator) session
				.getAttribute(AuthorityFilter.SESSION_KEY_OPERATOR);

		// session信息不一定是最新的数据，从数据看再查询一遍
		SysOperator dbOperator = sysOperatorService.getById(sessionOperator
				.getId());

		if (DigestUtils.md5Hex(oldPassword).equals(dbOperator.getPassword())) {
			dbOperator.setPassword(newPassword); // 加密的动作在service 的update中已经做了
			sysOperatorService.updateSysOperator(dbOperator);
			session.setAttribute(AuthorityFilter.SESSION_KEY_OPERATOR, dbOperator);
			rsJson.put("success", "0");
		} else {
			rsJson.put("success", "1");
			rsJson.put("errorMsg", "原始密码不正确。");
		}
		return rsJson;
	}
}
