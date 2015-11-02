package cs.search;

import cs.cache.valuecoding.ValueCodingTypes;

/**
 * 检索类别信息
 * 
 * @author xuzhuoxi
 *
 */
public class SearchTypeInfo {
	/**
	 * 检索类型
	 */
	private SearchTypes searchType;
	/**
	 * 检索使用的Cache名称
	 */
	private String cacheName;
	/**
	 * 输入处理类型
	 */
	private ValueCodingTypes valueType;

	public SearchTypeInfo(SearchTypes searchType, String cacheName, ValueCodingTypes valueType) {
		super();
		this.searchType = searchType;
		this.cacheName = cacheName;
		this.valueType = valueType;
	}

	/**
	 * 检索类型
	 */
	public final SearchTypes getSearchType() {
		return searchType;
	}

	/**
	 * 检索使用的Cache名称
	 */
	public final String getCacheName() {
		return cacheName;
	}

	/**
	 * 输入处理类型
	 */
	public final ValueCodingTypes getValueType() {
		return valueType;
	}
}
