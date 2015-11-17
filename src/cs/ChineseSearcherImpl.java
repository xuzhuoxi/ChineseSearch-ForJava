package cs;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.string.StringMatching;
import cs.cache.IChineseCache;
import cs.cache.valuecoding.IValueCodingStrategy;
import cs.cache.valuecoding.ValueCoding;
import cs.cacheconfig.CachePool;
import cs.search.SearchConfig;
import cs.search.SearchInfo;
import cs.search.SearchKeyResult;
import cs.search.SearchResult;
import cs.search.SearchTypeInfo;
import cs.search.SearchTypes;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class ChineseSearcherImpl implements IChineseSearcher {

	ChineseSearcherImpl() {
		this.initCache();
	}

	/**
	 * 初始化全部的Cache
	 */
	private void initCache() {
		CachePool.initSingletonCaches();
	}

	@Override
	public SearchKeyResult[] searchWord(String input) {
		SearchTypes[] searchTypes = { SearchTypes.PINYIN_WORD, SearchTypes.WUBI_WORD };
		return this.trySearch(input, searchTypes, Integer.MAX_VALUE);
	}

	@Override
	public SearchKeyResult[] searchWord(String input, int max) {
		SearchTypes[] searchTypes = { SearchTypes.PINYIN_WORD, SearchTypes.WUBI_WORD };
		return this.trySearch(input, searchTypes, max);
	}

	@Override
	public SearchKeyResult[] searchWords(String input) {
		SearchTypes[] searchTypes = { SearchTypes.PINYIN_WORDS, SearchTypes.WUBI_WORDS };
		return this.trySearch(input, searchTypes, Integer.MAX_VALUE);
	}

	@Override
	public SearchKeyResult[] searchWords(String input, int max) {
		SearchTypes[] searchTypes = { SearchTypes.PINYIN_WORDS, SearchTypes.WUBI_WORDS };
		return this.trySearch(input, searchTypes, max);
	}

	@Override
	public SearchResult search(String input, SearchTypes[] searchTypes) {
		return this.tryAdvancedSearch(input, searchTypes, Integer.MAX_VALUE);
	}

	private SearchKeyResult[] trySearch(String input, SearchTypes[] searchTypes, int max) {
		if (null == input)
			return null;
		String newInput = input.trim().toLowerCase();
		if (!validityCheck(newInput, searchTypes, max))
			return null;
		return this.search(new SearchInfo(newInput, searchTypes, max));
	}

	private SearchResult tryAdvancedSearch(String input, SearchTypes[] searchTypes, int max) {
		if (null == input)
			return null;
		String newInput = input.trim().toLowerCase();
		if (!validityCheck(newInput, searchTypes, max))
			return null;
		return this.searchMutliType(new SearchInfo(newInput, searchTypes, max));
	}

	private boolean validityCheck(String input, SearchTypes[] searchTypes, int max) {
		return input.length() > 0 && null != searchTypes && searchTypes.length > 0 && max > 0;
	}

	private SearchKeyResult[] search(SearchInfo si) {
		SearchResult sr = searchMutliType(si);
		SearchKeyResult[] rs = sr.getSortedResults();
		if (rs.length < si.getMaxResultCount()) {
			return rs;
		} else {
			return Arrays.copyOf(rs, si.getMaxResultCount());
		}
	}

	/**
	 * 真正检索入口
	 * 
	 * @param searchInfo
	 *            已经通过验证的检索信息
	 * @return
	 */
	private SearchResult searchMutliType(SearchInfo searchInfo) {
		SearchTypes[] types = searchInfo.getSearchType();
		SearchResult sr = new SearchResult();
		if (searchInfo.isChineseInput()) {
			for (SearchTypes type : types) {
				SearchTypeInfo sti = SearchConfig.getSearchTypeInfo(type);
				IValueCodingStrategy vcs = ValueCoding.getValueCodingStrategy(sti.getValueType());
				String filteredInput = vcs.filter(searchInfo.getInputStr());
				if (0 == filteredInput.length())
					continue;
				String[] codedInputStrs = vcs.translate(filteredInput);
				SearchResult newSR = searchOneSearchType(type, codedInputStrs);
				sr.addResult(newSR);
			}
			if (searchInfo.isChineseInput()) {
				sr.chineseRegexpMatchingClear(searchInfo.getChineseWordsRegexp());
			}
		} else {
			for (SearchTypes type : types) {
				SearchTypeInfo sti = SearchConfig.getSearchTypeInfo(type);
				IValueCodingStrategy vcs = ValueCoding.getValueCodingStrategy(sti.getValueType());
				String filteredInput = vcs.filter(searchInfo.getInputStr());
				if (0 == filteredInput.length())
					continue;
				Collection<SearchKeyResult> values = searchOneCoded(type, filteredInput);
				for (SearchKeyResult value : values) {
					sr.addKeyResult(value);
				}
			}
		}
		return sr;
	}

	/**
	 * 针对一种检索类型里的全部编码输入进行检索
	 * 
	 * @param st
	 *            检索类型
	 * @param codedInputStrs
	 *            经过编码的输入信息数组
	 * @return
	 */
	private SearchResult searchOneSearchType(SearchTypes st, String[] codedInputStrs) {
		SearchResult sr = new SearchResult();
		for (String codedInputStr : codedInputStrs) {
			if (0 == codedInputStr.length()) {
				continue;
			}
			Collection<SearchKeyResult> values = searchOneCoded(st, codedInputStr);
			for (SearchKeyResult value : values) {
				sr.addKeyResult(value);
			}
		}
		return sr;
	}

	/**
	 * 针对一种检索类型里的单个编码输入进行检索
	 * 
	 * @param st
	 *            检索类型
	 * @param codedInputStr
	 *            经过编码的输入信息
	 * @return
	 */
	private Collection<SearchKeyResult> searchOneCoded(SearchTypes st, String codedInputStr) {
		SearchTypeInfo sti = SearchConfig.getSearchTypeInfo(st);
		String cacheName = sti.getCacheName();
		IChineseCache cc = (IChineseCache) CachePool.getCache(cacheName);
		IValueCodingStrategy strategy = ValueCoding.getValueCodingStrategy(sti.getValueType());
		String str = strategy.getSimplifyValue(strategy.filter(codedInputStr));

		String[] dimensionKeys = this.handleSimplifyValue(str);// 低精度检索，快速
		// String[] dimensionKeys = this.advancedHandleSimplifyValue(strategy,
		// str);// 高精度检索
		Map<String, SearchKeyResult> rsMap = new HashMap<String, SearchKeyResult>();
		SearchKeyResult skr = null;
		for (String dimensionKey : dimensionKeys) {
			List<String> keyList = cc.getKeys(dimensionKey);
			if (null != keyList) {
				for (String chineseKey : keyList) {
					String[] values = cc.getValues(chineseKey);
					for (String value : values) {
						double v = StringMatching.computeMatchintResult(StringMatching.matching(value, codedInputStr));
						if (v > 0 && v <= 2) {
							if (rsMap.containsKey(chineseKey)) {
								skr = rsMap.get(chineseKey);
							} else {
								skr = new SearchKeyResult(chineseKey);
								rsMap.put(chineseKey, skr);
							}
							skr.updateBiggerValue(st, v);
						}
					}
				}
			}
		}
		return rsMap.values();
	}

	/**
	 * 取最多前两字符作为简化编码<br>
	 * 用于检索时范围更小，精确度变低，相对的耗时变短<br>
	 * 
	 * @param simplifyValue
	 * @return
	 */
	private String[] handleSimplifyValue(String simplifyValue) {
		if (simplifyValue.length() > 2) // 截取前两位作为检索，这里可以
			return new String[] { simplifyValue.substring(0, 2) };
		return new String[] { simplifyValue };
	}

	/**
	 * 取得对应可能的简化编码<br>
	 * 用于检索时范围更大更精确，相对的耗时变长<br>
	 * 
	 * @param strategy
	 * @param simplifyValue
	 * @return
	 */
	@SuppressWarnings("unused")
	private String[] advancedHandleSimplifyValue(IValueCodingStrategy strategy, String simplifyValue) {
		return strategy.getDimensionKeys(simplifyValue);
	}
}
