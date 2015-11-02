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
	 * @return
	 */
	SearchKeyResult[] searchWord(String input);

	/**
	 * 检索字，支持范围：<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 * @param max
	 *            返回最大检索结果量
	 * @return
	 */
	SearchKeyResult[] searchWord(String input, int max);

	/**
	 * 检索词，支持范围:<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 * @return
	 */
	SearchKeyResult[] searchWords(String input);

	/**
	 * 检索词，支持范围:<br>
	 * 拼音、五笔
	 * 
	 * @param input
	 * @param max
	 *            返回最大检索结果量
	 * @return
	 */
	SearchKeyResult[] searchWords(String input, int max);

	/**
	 * 检索
	 * 
	 * @param input
	 * @param searchType
	 *            自定义检索类型
	 * @return
	 */
	SearchResult search(String input, SearchTypes[] searchType);

	/**
	 * 检索
	 * 
	 * @param input
	 * @param searchType
	 *            自定义检索类型
	 * @param max
	 *            返回最大检索结果量
	 * @return
	 */
	SearchResult search(String input, SearchTypes[] searchType, int max);
}
