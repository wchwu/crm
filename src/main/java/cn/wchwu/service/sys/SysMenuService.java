package cn.wchwu.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wchwu.dao.common.DBUtil;
import cn.wchwu.model.sys.SysFunc;
import cn.wchwu.model.sys.SysMenu;


/**
 * 菜单操作服务层
 * 
 * @author orh
 *
 */
@Service("sysMenuService")
public class SysMenuService {

	@Autowired
	private DBUtil<SysMenu> dbUtil;
	
	@Autowired
	private SysFuncService sysFuncService;

	/**
	 * 列表查询
	 * 
	 * @param parameter
	 * @return
	 */
	public List<SysMenu> querySysMenu(Object parameter) {
		return dbUtil.selectList("default",
				"cn.wchwu.dao.sys.SysMenuMapper.querySysMenu", parameter);
	}

	/**
	 * 单个查询
	 * 
	 * @param id
	 * @return
	 */
	public SysMenu getMenuById(String id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("qtype", "all"); // 查询类型 如all： 查询全部、空值：则查询全部启用的

		return dbUtil.selectOne("default",
				"cn.wchwu.dao.sys.SysMenuMapper.querySysMenu", paramMap);
	}

	/**
	 * 新增菜单
	 * 
	 * @param sysOperator
	 * @return
	 */
	public int insertSysMenu(SysMenu menu) {
		SysMenu parentMenu = null;

		if (StringUtils.isNotBlank(menu.getPid())) {
			// 有pid，则根据pid查询父菜单信息
			parentMenu = getMenuById(menu.getPid());
			menu.setIdSeq(parentMenu.getIdSeq());
			menu.setMenuLevel(parentMenu.getMenuLevel() + 1);
		} else {
			// 无pid，则表示添加的是顶级菜单
			menu.setIdSeq("."); // 添加顶级菜单时，没有父菜单，在idSeq前面加上一个"."号，因为此时还没有插入
								// 得不到本身菜单的id，这个会在sql中处理
			menu.setMenuLevel(1);
		}

		if (StringUtils.isBlank(menu.getOpenModel())) { // 默认页面打开方式为普通
			menu.setOpenModel("normal");
		}
		if (StringUtils.isBlank(menu.getStatus())) { // 菜单默认状态为启用
			menu.setStatus("0");
		}

		// 执行插入
		int rs = dbUtil.insert("default",
				"cn.wchwu.dao.sys.SysMenuMapper.insertSysMenu", menu);

		// 如果插入的菜单为启用状态、并且存在父菜单、并且父菜单状态为禁用状态：则存在一情况，父菜单是禁用的 此时更新父菜单为启用
		if ("0".equals(menu.getStatus())
				&& StringUtils.isNotBlank(menu.getPid())
				&& (!"0".equals(parentMenu.getStatus()))) {
			updateAncestorStatus(menu.getIdSeq(), menu.getStatus());
		}
		
		// 如果插入的为叶子菜单，则功能表自动生成一条数据
		if ("y".equals(menu.getIsleaf())) {
			sysFuncService.insertAutoFuncForMenu(menu);
		}
		return rs;
	}

	/**
	 * 修改菜单基金信息
	 * 
	 * @param sysOperator
	 * @return
	 */
	public int updateSysMenu(SysMenu menu) {
		if (StringUtils.isBlank(menu.getStatus())) {
			menu.setStatus("0"); // 默认是启用菜单的
		}

		// 启用菜单节点：则自动启用级联的祖先节点
		if ("0".equals(menu.getStatus())) {
			// 一级菜单不存在祖先节点
			if (menu.getMenuLevel() != 1) {
				updateAncestorStatus(menu.getIdSeq(), menu.getStatus());
			}
		} else if ("n".equals(menu.getIsleaf())) {
			// 禁用当前菜单时：则禁用后代节点菜单 非叶子节点才有子菜单否则 则表示
			updateDescendantStatus(menu.getId(), menu.getStatus());
		}
		// 如果更新的为叶子菜单，则自动更新"自动生成的"功能
		if ("y".equals(menu.getIsleaf())) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("menuId", menu.getId());
			paramMap.put("sysFlag", "0");
			
			SysFunc func = sysFuncService.selectOne(paramMap);
			if (func != null) {
				//设置更新内容
				func.setFuncUrlPath(menu.getUrl());
				sysFuncService.updateFunc(func);
			} else {
				sysFuncService.insertAutoFuncForMenu(menu);
			}
			
		}
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysMenuMapper.updateSysMenu", menu);
	}

	/**
	 * 更新后代节点菜单(包括本身节点)
	 * 
	 * @return
	 */
	public boolean updateDescendantStatus(String menuId, String status) {
		HashMap<String, Object> paramterMap = new HashMap<String, Object>();
		paramterMap.put("id", menuId);
		paramterMap.put("status", status);
		dbUtil.update("default",
				"cn.wchwu.dao.sys.SysMenuMapper.updateDescendantStatus",
				paramterMap);
		return true;
	}

	/**
	 * 更新祖先节点菜单状态(包括本身节点)
	 * 
	 * @return
	 */
	public boolean updateAncestorStatus(String idSeq, String status) {
		// 找 祖先节点的方法：根据本节点的 idSeq ".1.11.111." 查找

		if (StringUtils.isNotBlank(idSeq)) {
			// idSeq = idSeq.replace("." + menu.getId() + ".", ""); // 去除本身的id

			if (StringUtils.isNotBlank(idSeq)) {
				idSeq = idSeq.substring(1, idSeq.length()); // 去除首部"."号
				String[] idArrays = org.springframework.util.StringUtils
						.tokenizeToStringArray(idSeq, "."); // "."号分割数组

				if (idArrays.length > 0) {
					HashMap<String, Object> paramterMap = new HashMap<String, Object>();
					paramterMap.put("idArrays", idArrays);
					paramterMap.put("status", status);

					dbUtil.update(
							"default",
							"cn.wchwu.dao.sys.SysMenuMapper.updateAncestorStatus",
							paramterMap);
				}
			}
		}
		return true;
	}

	/**
	 * 启用菜单
	 * 
	 * @param id
	 *            菜单本身id
	 * @param idSeq
	 *            菜单的seq
	 * @param status
	 *            要更新的状态
	 * @return
	 */
	public boolean updateStatus(String id, String idSeq, String status) {
		if ("0".equals(status)) {
			updateAncestorStatus(idSeq, status); // 启用祖先菜单
			updateDescendantStatus(id, status); // 启用子孙菜单和其本身
		} else {
			// 禁用菜单
			updateDescendantStatus(id, status); // 禁用子孙菜单和其本身
		}
		return true;
	}

	/**
	 * 菜单拖拽的功能
	 * 
	 * @return
	 */
	public int moveSysMenu(Map<String,Object> map) {
		return dbUtil.update("default",
				"cn.wchwu.dao.sys.SysMenuMapper.moveSysMenu", map);
	}

	/**
	 * 删除菜单(级联删除子菜单、已经菜单对应的功能[TODO 对应'功能'还未做！])
	 * 
	 * @return
	 */
	public int deleteMenu(String id) {
		return dbUtil.delete("default",
				"cn.wchwu.dao.sys.SysMenuMapper.deleteMenu", id);
	}
}
