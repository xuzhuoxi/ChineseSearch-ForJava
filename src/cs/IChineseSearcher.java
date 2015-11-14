package cs;

import cs.search.SearchKeyResult;
import cs.search.SearchResult;
import cs.search.SearchTypes;

/**
 * 不考虑多线程<br>
 * 
 * @author xuzhuoxi
 *
 */
public interface IChineseSearcher {

	/**
	 * 检索字，支持范围：<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 *            检索输入字符串
	 * @return 检索结果数组
	 */
	SearchKeyResult[] searchWord(String input);

	/**
	 * 检索字，支持范围：<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 *            检索输入字符串
	 * @param max
	 *            返回最大检索结果量
	 * @return 检索结果数组
	 */
	SearchKeyResult[] searchWord(String input, int max);

	/**
	 * 检索词，支持范围:<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 *            检索输入字符串
	 * @return 检索结果数组
	 */
	SearchKeyResult[] searchWords(String input);

	/**
	 * 检索词，支持范围:<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 *            检索输入字符串
	 * @param max
	 *            返回最大检索结果量
	 * @return 检索结果数组
	 */
	SearchKeyResult[] searchWords(String input, int max);

	/**
	 * 检索
	 * 
	 * @param input
	 *            检索输入字符串
	 * @param searchType
	 *            自定义检索类型
	 * @return SearchResult实例{@link SearchResult}
	 */
	SearchResult search(String input, SearchTypes[] searchType);
}
