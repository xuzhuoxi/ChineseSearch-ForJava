package cs.search;

/**
 * 单个字(词)的单个检索类型结果
 * 
 * @author xuzhuoxi
 *
 */
public class SearchTypeResult implements Comparable<SearchTypeResult> {
	private static final double fullMatchingValue = 2.0;

	private SearchTypes searchType;
	private double value = 0;

	public SearchTypeResult(SearchTypes searchType) {
		this.searchType = searchType;
	}

	/**
	 * 检索类型
	 * 
	 * @return 检索类型
	 */
	public SearchTypes getSearchType() {
		return searchType;
	}

	/**
	 * 检索结果
	 * 
	 * @return 检索结果(匹配度)
	 */
	public double getValue() {
		return value;
	}

	/**
	 * 更新检索结果
	 * 
	 * @param value
	 *            检索结果(匹配度)
	 * 
	 * @see #fullMatchingValue
	 */
	public void updateBiggerValue(double value) {
		if (value <= fullMatchingValue && Double.compare(this.value, value) < 0) {
			this.value = value;
		}
	}

	/**
	 * 是否为全匹配
	 * 
	 * @return 是true否false
	 * @see #fullMatchingValue
	 */
	public boolean isFullMatching() {
		return value >= fullMatchingValue;
	}

	/**
	 * 大小比较
	 * 
	 * @param o
	 *            待比较对象
	 * @return [-1,0,1]
	 */
	@Override
	public int compareTo(SearchTypeResult o) {
		if (searchType != o.getSearchType()) {
			return 0;
		} else {
			return Double.compare(value, o.getValue());
		}
	}
}
