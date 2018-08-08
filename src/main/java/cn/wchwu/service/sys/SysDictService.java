package cn.wchwu.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.dao.sys.SysDictMapper;
import cn.wchwu.framework.mybatis.bean.PageCond;
import cn.wchwu.framework.utils.SysCacheUtil;
import cn.wchwu.model.sys.FillDictEntityModel;
import cn.wchwu.model.sys.SysDictEntry;
import cn.wchwu.model.sys.SysDictType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 业务字典服务类.包括业务字典类型、业务字典项的维护
 * 
 * @author orh
 *
 */
@Service("sysDictService")
public class SysDictService {

	@Autowired
	private DBUtil<SysDictEntry> dbUtil;

	@Autowired
	private SysDictMapper sysDictMapper;

	/**
	 * 根据业务字典类型ID 得到 业务字典项的map
	 * 
	 * @param dictTypeId
	 * @return
	 */
	public Map<String, String> getDictEntryMapByDictTypeId(String dictTypeId) {
		Map<String, Map<String, String>> dictMap = SysCacheUtil.getDictMap();
		return dictMap.get(dictTypeId);
	}

	/**
	 * 填充集合中的业务字典字段
	 * 
	 * @param entities
	 *            需要填充的集合
	 * @param fillModels
	 *            对象中包括：dictTypeId：业务字典类型、field：需要翻译的字段、valueField：翻译出的值放置在什么字段上
	 * @return
	 */
	public JSONArray fillJSONArrayWithDictValues(JSONArray entities,
			FillDictEntityModel[] fillModels) {
		// 获取业务字典Map
		Map<String, Map<String, String>> dictMap = SysCacheUtil.getDictMap();

		int len = entities.size();
		JSONObject j = null;

		// 遍历集合、需要翻译的字段，分别赋值
		if (dictMap != null) {
			for (int i = 0; i < len; i++) {
				j = entities.getJSONObject(i);
				for (FillDictEntityModel f : fillModels) {
					if (dictMap.containsKey(f.getDictTypeId())) {
						j.put(f.getValueField(), dictMap.get(f.getDictTypeId())
								.get(j.getString(f.getField())));
					} else {
						// 没有值的情况下，获取原来的业务字典id值显示
						j.put(f.getValueField(), f.getField());
					}
				}
			}
		}

		return entities;
	}

	/**
	 * 填充JsonObject中的业务字典字段
	 * 
	 * @param entities
	 *            需要填充的集合
	 * @param filleModel
	 *            对象中包括：dictTypeId：业务字典类型、field：需要翻译的字段、valueField：翻译出的值放置在什么字段上
	 * @return
	 */
	public JSONObject fillJSONObjectWithDictValue(JSONObject json,
			FillDictEntityModel filleModel) {
		// 获取业务字典Map
		json.put(filleModel.getValueField(),
				getDictName(filleModel.getDictTypeId(), filleModel.getField()));
		return json;
	}

	/**
	 * 根据业务字典类型、业务字典ID获取翻译值
	 * 
	 * @param dictTypeId
	 * @param dictId
	 * @return
	 */
	public String getDictName(String dictTypeId, String dictId) {
		Map<String, Map<String, String>> dictMap = SysCacheUtil.getDictMap();
		if (dictMap.containsKey(dictTypeId)) {
			return dictMap.get(dictTypeId).get(dictId);
		} else {
			// 没有值的情况下，获取原来的业务字典id值显示
			return dictId;
		}
	}

	/**
	 * 分页查询业务字典类型
	 * 
	 * @param page
	 *            分页对象
	 * @param parameter
	 *            查询条件
	 * @return
	 */
	public List<SysDictType> querySysDictType(PageCond page, Object parameter) {
		return sysDictMapper.querySysDictType("default", page, parameter);
	}

	/**
	 * 分页查询业务字典项
	 * 
	 * @param page
	 *            分页对象
	 * @param parameter
	 *            查询条件
	 * @return
	 */
	public List<SysDictEntry> querySysDictEntry(PageCond page, Object parameter) {
		return sysDictMapper.querySysDictEntry("default", page, parameter);
	}

	/**
	 * 单个新增
	 * 
	 * @param parameter
	 */
	public void insertDictType(Object parameter) {
		dbUtil.insert("default",
				"cn.wchwu.dao.sys.SysDictMapper.insertDictType", parameter);
	}

	/**
	 * 批量新增
	 * 
	 * @param list
	 */
	public void insertDictBatch(List<SysDictType> list) {
		if (list != null) {
			for (SysDictType d : list) {
				insertDictType(d);
			}
		}
	}

	/**
	 * 单个更新
	 * 
	 * @param parameter
	 */
	public void updateDictType(Object parameter) {
		dbUtil.update("default",
				"cn.wchwu.dao.sys.SysDictMapper.updateDictType", parameter);
	}

	/**
	 * 批量更新
	 * 
	 * @param list
	 */
	public void updateDictTypeBatch(List<SysDictType> list) {
		if (list != null) {
			for (SysDictType d : list) {
				updateDictType(d);
			}
		}
	}

	/**
	 * 单个删除
	 * 
	 * @param parameter
	 */
	public void deleteDictType(Object parameter) {
		dbUtil.delete("default",
				"cn.wchwu.dao.sys.SysDictMapper.deleteDictType", parameter);
	}

	/**
	 * 单个删除
	 * 
	 * @param list
	 */
	public void deleteDictTypeBatch(List<SysDictType> list) {
		if (list != null) {
			for (SysDictType d : list) {
				deleteDictType(d);
			}
		}
	}

	/**
	 * 批量修改业务字典类型
	 * 
	 * @param deletedList
	 *            要删除 的集合
	 * @param insertedList
	 *            要插入的集合
	 * @param updatedList
	 *            要更新的集合
	 */
	public void saveDictTypeBatch(List<SysDictType> deletedList,
			List<SysDictType> insertedList, List<SysDictType> updatedList) {
		insertDictBatch(insertedList);
		updateDictTypeBatch(updatedList);
		deleteDictTypeBatch(deletedList);
	}

	/**
	 * 批量修改业务字典项
	 * 
	 * @param deletedList
	 *            要删除 的集合
	 * @param insertedList
	 *            要插入的集合
	 * @param updatedList
	 *            要更新的集合
	 */
	public void saveDictEntryBatch(List<SysDictEntry> deletedList,
			List<SysDictEntry> insertedList, List<SysDictEntry> updatedList) {
		insertDictEntryBatch(insertedList);
		updateDictEntryTypeBatch(updatedList);
		deleteDictEntryTypeBatch(deletedList);
	}

	/**
	 * 删除单个业务字典项
	 * 
	 * @param deletedList
	 */
	public int deleteDictEntry(SysDictEntry d) {
		return dbUtil.delete("default",
				"cn.wchwu.dao.sys.SysDictMapper.deleteDictEntry", d);

	}

	/**
	 * 删除多个业务字典项
	 * 
	 * @param deletedList
	 */
	public void deleteDictEntryTypeBatch(List<SysDictEntry> list) {
		if (list != null) {
			for (SysDictEntry d : list) {
				deleteDictEntry(d);
			}
		}
	}

	/**
	 * 更新单个业务字典项
	 * 
	 * @param deletedList
	 */
	public int updateDictEntry(SysDictEntry d) {
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysDictMapper.updateDictEntry", d);

	}

	/**
	 * 更新多个业务字典项
	 * 
	 * @param list
	 */
	public void updateDictEntryTypeBatch(List<SysDictEntry> list) {
		if (list != null) {
			for (SysDictEntry d : list) {
				updateDictEntry(d);
			}
		}
	}

	/**
	 * 插入多个业务字典项
	 * 
	 * @param list
	 */
	public void insertDictEntryBatch(List<SysDictEntry> list) {
		if (list != null) {
			for (SysDictEntry d : list) {
				insertDictEntry(d);
			}
		}
	}

	/**
	 * 删除单个业务字典项
	 */
	public int insertDictEntry(SysDictEntry d) {
		return dbUtil.insert("default",
				"cn.wchwu.dao.sys.SysDictMapper.insertDictEntry", d);

	}

}
