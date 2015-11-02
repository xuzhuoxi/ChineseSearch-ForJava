package cs.search;

import code.chinese.ChineseUtils;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class SearchInfo {
	private String inputStr;
	private SearchTypes[] searchType;
	private int maxResultCount;
	private boolean chineseInput;
	private String chineseWordsRegexp;

	/**
	 * 
	 * @param inputStr
	 *            输入信息，掉前后空格并把大写字母转为小写后存于inputStr{@link #inputStr}<br>
	 * @param searchType
	 *            需要执行的检索类别<br>
	 * @param maxResultCount
	 *            最大返回量<br>
	 */
	public SearchInfo(String inputStr, SearchTypes[] searchType, int maxResultCount) {
		super();
		this.inputStr = inputStr.trim().toLowerCase();
		this.searchType = searchType;
		this.maxResultCount = maxResultCount;

		this.chineseInput = ChineseUtils.hasChinese(this.inputStr);
		if (chineseInput) {
			chineseWordsRegexp = ChineseUtils.toChineseWordsRegexp(this.inputStr);
		}
	}

	/**
	 * 输入信息，保证已经去掉前后空格并把大写字母转为小写<br>
	 */
	public String getInputStr() {
		return inputStr;
	}

	/**
	 * 需要执行的检索类别<br>
	 */
	public SearchTypes[] getSearchType() {
		return searchType;
	}

	/**
	 * 最大返回量
	 */
	public int getMaxResultCount() {
		return maxResultCount;
	}

	/**
	 * 输入中是否带中文字
	 */
	public boolean isChineseInput() {
		return chineseInput;
	}

	/**
	 * 当chineseInput为true时有效<br>
	 * 一个针对中文字生成的正则表达式{@link ChineseUtils#toChineseWordsRegexp(String)}<br>
	 */
	public String getChineseWordsRegexp() {
		return chineseWordsRegexp;
	}
}
