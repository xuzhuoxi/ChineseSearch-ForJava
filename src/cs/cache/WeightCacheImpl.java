package cs.cache;

import cs.resource.KeyWeight;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class WeightCacheImpl extends WeightCache {
	/**
	 * ^[1-9]d*.d*$ //大于等于1浮点数
	 */
	private static final String REGEX_FLOAT_1MORE = "^+?([1-9]\\d*.\\d*|[1-9]\\d*)$";

	WeightCacheImpl() {
	}

	/**
	 * 已有缓存、格式不对、小于等于默认权值的忽略，不加入缓存
	 */
	@Override
	protected boolean tryCacheKeyValue(String resourceKey, String resourceValue) {
		if (!key2weight.containsKey(resourceKey)) {
			if (resourceValue.matches(REGEX_FLOAT_1MORE)) {
				double value = Double.parseDouble(resourceValue);
				if (Double.compare(value, DEFAULT_VALUE) > 0) {
					key2weight.put(resourceKey, new KeyWeight(resourceKey, value));
					return true;
				}
			}
		}
		return false;
	}
}
