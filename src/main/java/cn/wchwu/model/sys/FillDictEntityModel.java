package cn.wchwu.model.sys;

/**
 * 根据业务字典翻译的中间传递对象
 * @author orh
 *
 */
public class FillDictEntityModel {
	
	/**
	 * 
	 * @param dictTypeId	业务字典类型
	 * @param field	需要翻译的字段
	 * @param valueField	翻译出的值放置在什么字段上
	 */
	public FillDictEntityModel(String dictTypeId, String field,
			String valueField) {
		this.dictTypeId = dictTypeId;
		this.field = field;
		this.valueField = valueField;
	}
	
	/**
	 * 业务字典类型
	 */
	private String dictTypeId;
	
	/**
	 * 需要翻译的字段
	 */
	private String field;
	
	/**
	 * 翻译出的值放置在什么字段上
	 */
	private String valueField;

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
}
