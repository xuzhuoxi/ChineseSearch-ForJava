package cs.search;

import java.util.ArrayList;
import java.util.List;

import cs.cache.IWeightCache;
import cs.cacheconfig.CacheNames;
import cs.cacheconfig.CachePool;

/**
 * 单个汉字(词)的检索结果 别名：键结果
 * 
 * @author xuzhuoxi
 *
 */
public class SearchKeyResult implements Comparable<SearchKeyResult> {
	private String key;
	private double weight;
	private List<SearchTypes> typeList = new ArrayList<SearchTypes>();
	private List<SearchTypeResult> list = new ArrayList<SearchTypeResult>();

	public SearchKeyResult(String key) {
		super();
		this.key = key;
		IWeightCache iwc = (IWeightCache) CachePool.getCache(CacheNames.WEIGHT);
		this.weight = iwc.getValues(key);
	}

	public final String getKey() {
		return key;
	}

	public double getWeight() {
		return weight;
	}

	/**
	 * 计算是否有完整匹配情况<br>
	 * 
	 * @return
	 */
	public final boolean hasFullMatching() {
		for (SearchTypeResult str : list) {
			if (str.isFullMatching())
				return true;
		}
		return false;
	}

	/**
	 * 计算匹配度<br>
	 * 把各个检索类别的结果相加<br>
	 * 
	 * @return
	 */
	public final double getTotalValue() {
		double value = 0;
		for (SearchTypeResult str : list) {
			value += str.getValue();
		}
		return value * weight;

	}

	/**
	 * 更新单个检索类别的匹配值，存储更大的<br>
	 * 
	 * @param searchType
	 * @param value
	 */
	public final void updateBiggerValue(SearchTypes searchType, double value) {
		SearchTypeResult str = null;
		if (typeList.contains(searchType)) {
			str = list.get(typeList.indexOf(searchType));
		} else {
			typeList.add(searchType);
			str = new SearchTypeResult(searchType);
			list.add(str);
		}
		str.updateBiggerValue(value);
	}

	/**
	 * 更新单个字(词)的单个检索类型结果，存储更大的<br>
	 * 
	 * @param str
	 */
	public final void updateBiggerValue(SearchTypeResult str) {
		if (null == str)
			return;
		SearchTypes st = str.getSearchType();
		if (typeList.contains(str.getSearchType())) {
			list.get(typeList.indexOf(st)).updateBiggerValue(str.getValue());
		} else {
			typeList.add(st);
			list.add(str);
		}
	}

	/**
	 * 取得指定检索类别的匹配度<br>
	 * 
	 * @param searchType
	 * @return
	 */
	public final double getValue(SearchTypes searchType) {
		if (typeList.contains(searchType)) {
			return list.get(typeList.indexOf(searchType)).getValue();
		} else {
			return 0;
		}
	}

	/**
	 * 取得全部的检索类别<br>
	 * 
	 * @return
	 */
	public final SearchTypes[] getSearchTypes() {
		SearchTypes[] rs = new SearchTypes[typeList.size()];
		return typeList.toArray(rs);
	}

	/**
	 * 当相加的SearchKeyResult实例的key{@link #key}与当前相同时，<br>
	 * 遍历全部的检索类别和值，进行更新{@link #updateBiggerValue(SearchTypes, double)}<br>
	 * 
	 * @param skr
	 * @return
	 */
	public final boolean addSearchKeyResult(SearchKeyResult skr) {
		if (null != skr && skr != this && this.key.equals(skr.getKey())) {
			SearchTypes[] types = skr.getSearchTypes();
			for (SearchTypes searchType : types) {
				updateBiggerValue(searchType, skr.getValue(searchType));
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 与另一个SearchKeyResult实例比较大小<br>
	 * 1.有完整匹配的大于没有完整匹配的{@link #hasFullMatching()}<br>
	 * 2.完整匹配信息一致的，比较匹配度的大小{@link #getTotalValue()}<br>
	 */
	@Override
	public int compareTo(SearchKeyResult o) {
		final boolean myFull = hasFullMatching();
		if (myFull != o.hasFullMatching()) {
			return myFull ? 1 : -1;
		} else {
			return Double.compare(getTotalValue(), o.getTotalValue());
		}
	}
}
