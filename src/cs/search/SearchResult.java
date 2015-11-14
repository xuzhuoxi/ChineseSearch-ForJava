package cs.search;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import code.array.ArrayUtils;

/**
 * 检索总结果
 * 
 * @author xuzhuoxi
 *
 */
public class SearchResult {
	private final Comparator<SearchKeyResult> c = new Comparator<SearchKeyResult>() {
		@Override
		public int compare(SearchKeyResult o1, SearchKeyResult o2) {
			return o1.compareTo(o2);
		}
	};
	private Map<String, SearchKeyResult> keyResultMap = new HashMap<String, SearchKeyResult>();

	/**
	 * 与别一个检索结果相加<br>
	 * 相加方式:遍历全部SearchKeyResult进行合并<br>
	 * 1.有key相同的：{@link SearchKeyResult#addSearchKeyResult(SearchKeyResult)}<br>
	 * 2.没有key相同的:直接存起来<br>
	 * 
	 * @param searchResult
	 *            另一个检索结果
	 */
	public void addResult(SearchResult searchResult) {
		if (searchResult == this)
			return;
		SearchKeyResult[] adds = searchResult.getResults();
		for (SearchKeyResult add : adds) {
			tryAddKeyResutl(add);
		}
	}

	/**
	 * 与SearchKeyResult实例相加<br>
	 * 1.有key相同的：{@link SearchKeyResult#addSearchKeyResult(SearchKeyResult)}<br>
	 * 2.没有key相同的:直接存起来<br>
	 * 
	 * @param keyResult
	 *            键结果
	 */
	public void addKeyResult(SearchKeyResult keyResult) {
		tryAddKeyResutl(keyResult);
	}

	/**
	 * 进行正则过滤，当输入包含中文时才能使用<br>
	 * 与正则表达式不匹配的键结果会被清除<br>
	 * {@link String#matches(String)}{@link SearchInfo#getChineseWordsRegexp()}
	 * 
	 * @param regexp
	 *            正则表达式
	 */
	public void chineseRegexpMatchingClear(String regexp) {
		Set<String> keySet = keyResultMap.keySet();
		String[] keys = new String[keySet.size()];
		keySet.toArray(keys);
		for (String key : keys) {
			if (!key.matches(regexp)) {
				keyResultMap.remove(key);
			}
		}
	}

	/**
	 * 取得当前键结果数量<br>
	 * 
	 * @return 结果数量
	 */
	public int getResultsSize() {
		return keyResultMap.size();
	}

	/**
	 * 取得当前全部的键结果数组<br>
	 * 未经排序<br>
	 * 
	 * @return SearchKeyResult数组{@link SearchKeyResult}
	 */
	public SearchKeyResult[] getResults() {
		Collection<SearchKeyResult> values = keyResultMap.values();
		SearchKeyResult[] rs = new SearchKeyResult[values.size()];
		values.toArray(rs);
		return rs;
	}

	/**
	 * 取得当前全部键结果数组<br>
	 * 已排序，匹配度大在前{@link SearchKeyResult#compareTo(SearchKeyResult)}
	 * 
	 * @return 按匹配度从大到小的数组
	 */
	public SearchKeyResult[] getSortedResults() {
		Collection<SearchKeyResult> values = keyResultMap.values();
		SearchKeyResult[] rs = new SearchKeyResult[values.size()];
		values.toArray(rs);
		Arrays.sort(rs, c);
		ArrayUtils.reverse(rs);
		return rs;
	}

	/**
	 * 把一个键结果合并进来
	 * 
	 * @param keyResult
	 *            键结果{@link SearchKeyResult}
	 */
	private void tryAddKeyResutl(SearchKeyResult keyResult) {
		if (null != keyResult) {
			if (keyResultMap.containsKey(keyResult.getKey())) {
				keyResultMap.get(keyResult.getKey()).addSearchKeyResult(keyResult);
			} else {
				keyResultMap.put(keyResult.getKey(), keyResult);
			}
		}
	}
}
