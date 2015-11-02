package cs.cache;

import cs.resource.KeyValues;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class ChineseCacheImpl extends ChineseCache {

	ChineseCacheImpl() {
	}

	/**
	 * 1.先检查有效性，不通过则忽略。<br>
	 * 2.检查键是否存，进行补充或新增。<br>
	 * 3.缓存每个值时，同进行简化反向影射。<br>
	 */
	@Override
	protected final boolean tryCacheResourceInfo(String resourceKey, String resourceValue) {
		if (null == resourceKey || resourceKey.length() == 0 || null == resourceValue || resourceValue.length() == 0) {
			return false;
		}
		KeyValues kv = null;
		if (key2values.containsKey(resourceKey)) {// 补充
			kv = key2values.get(resourceKey);
		}
		if (null == kv) {// 新增
			kv = new KeyValues(resourceKey);
			key2values.put(resourceKey, kv);
		}
		String[] values = resourceValue.split("#");
		for (String value : values) {
			kv.addValue(value);
			this.cache2DimensionMap(value, resourceKey);// 简化反向影射
		}
		return true;
	}
}
