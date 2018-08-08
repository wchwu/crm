package cn.wchwu.web.controller.sys;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysDictEntry;
import cn.wchwu.model.sys.SysDictType;
import cn.wchwu.service.sys.SysDictService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/sys/dict")
// 1. 处理器通用前缀
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;

	private static FillDictEntityModel statusMapping = new FillDictEntityModel(
			"SYS_YESORNO", "status", "statusName");

	/**
	 * 分页查询业务字典类型
	 * @param page 页码
	 * @param rows 一页显示行数
	 * @param dictTypeName 查询条件-业务字典名称/代码
	 * @param dictName 字典项名称
	 * @return
	 */
	@RequestMapping(value = "dictTypePageView")
	@ResponseBody
	public JSONObject querySysdictType(Integer page, Integer rows,
			String dictTypeName, String dictName) {
		PageCond pageCond = new PageCond(page, rows);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dictTypeName", dictTypeName);
		map.put("dictName", dictName);

		List<SysDictType> list = sysDictService.querySysDictType(pageCond, map);
		JSONObject rsJson = new JSONObject();
		rsJson.put("rows", list);
		rsJson.put("total", pageCond.getTotalRows());

		return rsJson;
	}

	/**
	 * 批量保存 业务字典类型
	 * @param deleted 要删除的对象
	 * @param inserted 要新增的集合
	 * @param updated 要更新的集合
	 * @return
	 */
	@RequestMapping(value = "saveDictType")
	@ResponseBody
	public JSONObject saveDictType(String deleted, String inserted,
			String updated) {
		List<SysDictType> deletedList = null;
		List<SysDictType> insertedList = null;
		List<SysDictType> updatedList = null;
		if (deleted != null) {
			deletedList = JSON.parseArray(deleted, SysDictType.class);
		}
		if (inserted != null) {
			insertedList = JSON.parseArray(inserted, SysDictType.class);
		}
		if (updated != null) {
			updatedList = JSON.parseArray(updated, SysDictType.class);
		}

		sysDictService
				.saveDictTypeBatch(deletedList, insertedList, updatedList);

		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 批量保存 业务字典项
	 * @param deleted 要删除的对象
	 * @param inserted 要新增的集合
	 * @param updated 要更新的集合
	 * @return
	 */
	@RequestMapping(value = "saveDictEntry")
	@ResponseBody
	public JSONObject saveDictEntry(String deleted, String inserted,
			String updated) {
		List<SysDictEntry> deletedList = null;
		List<SysDictEntry> insertedList = null;
		List<SysDictEntry> updatedList = null;
		if (deleted != null) {
			deletedList = JSON.parseArray(deleted, SysDictEntry.class);
		}
		if (inserted != null) {
			insertedList = JSON.parseArray(inserted, SysDictEntry.class);
		}
		if (updated != null) {
			updatedList = JSON.parseArray(updated, SysDictEntry.class);
		}

		sysDictService.saveDictEntryBatch(deletedList, insertedList,
				updatedList);

		JSONObject rsJson = new JSONObject();
		rsJson.put("success", "0");
		return rsJson;
	}

	/**
	 * 根据业务字典类型ID列出显示该类型下的所有字典项(不分页、不展示已经禁用的项)
	 * @param dictTypeId 业务字典类型ID
	 * @return
	 */
	@RequestMapping(value = "entryView")
	@ResponseBody
	public JSONArray querySysdictEntry(
			@RequestParam(required = true) String dictTypeId) {
		// 封装请求参数
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dictTypeId", dictTypeId);

		List<SysDictEntry> list = sysDictService.querySysDictEntry(null, map);
		return JSONArray.parseArray(JSONObject.toJSONString(list));
	}

	/**
	 * 分页按业务字典查询
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            一页的条数
	 * @param dictTypeId
	 *            业务字典类型，必填
	 * @return
	 */
	@RequestMapping(value = "entryPageView")
	@ResponseBody
	public JSONObject querySysdictEntry(Integer page, Integer rows,
			@RequestParam(required = true) String dictTypeId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dictTypeId", dictTypeId);
		map.put("qtype", "all");

		PageCond pageCond = new PageCond(page, rows);
		List<SysDictEntry> list = sysDictService.querySysDictEntry(pageCond,
				map);

		// 转换成jsonArr,再根据业务字典翻译字段 状态
		JSONArray jsonArr = JSONArray.parseArray(JSONObject.toJSONString(list));

		FillDictEntityModel[] mappings = { statusMapping };

		JSONObject rsJson = new JSONObject();
		rsJson.put("rows",
				sysDictService.fillJSONArrayWithDictValues(jsonArr, mappings));
		rsJson.put("total", pageCond.getTotalRows());

		return rsJson;
	}
}
