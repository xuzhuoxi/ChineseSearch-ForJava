package cs.search;

import java.util.HashMap;
import java.util.Map;

import cs.cache.valuecoding.ValueCodingTypes;
import cs.cacheconfig.CacheNames;

/**
 * 检索类别信息配置
 * 
 * @author xuzhuoxi
 *
 */
public class SearchConfig {
	private static final Map<SearchTypes, SearchTypeInfo> config = new HashMap<SearchTypes, SearchTypeInfo>();

	static {
		addConfig(SearchTypes.PINYIN_WORD, CacheNames.PINYIN_WORD, ValueCodingTypes.PINYIN_WORD);
		addConfig(SearchTypes.PINYIN_WORDS, CacheNames.PINYIN_WORDS, ValueCodingTypes.PINYIN_WORDS);
		addConfig(SearchTypes.WUBI_WORD, CacheNames.WUBI_WORD, ValueCodingTypes.WUBI_WORDS);
		addConfig(SearchTypes.WUBI_WORDS, CacheNames.WUBI_WORDS, ValueCodingTypes.WUBI_WORDS);
	}

	/**
	 * 
	 * @param searchType
	 *            检索类型
	 * @param cacheName
	 *            检索用到的Cache名称
	 * @param valueType
	 *            输入处理类型
	 * @param maxResultCount
	 *            最大返回量
	 */
	private static final void addConfig(SearchTypes searchType, String cacheName, ValueCodingTypes valueType) {
		config.put(searchType, new SearchTypeInfo(searchType, cacheName, valueType));
	}

	public static final SearchTypeInfo getSearchTypeInfo(SearchTypes searchType) {
		return config.get(searchType);
	}
}
