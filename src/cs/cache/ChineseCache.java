package cs.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs.cache.map.DimensionMap;
import cs.cache.map.IDimensionMap;
import cs.cache.valuecoding.IValueCodingStrategy;
import cs.cache.valuecoding.ValueCoding;
import cs.cache.valuecoding.ValueCodingTypes;
import cs.resource.KeyValues;
import cs.resource.Resource;

/**
 * 
 * @author xuzhuoxi
 *
 */
public abstract class ChineseCache implements IChineseCache, ICacheInit {
	protected String cacheName;
	protected Map<String, KeyValues> key2values;
	protected IDimensionMap chineseMap;
	protected IValueCodingStrategy strategy;

	@Override
	public void initCache(String cacheName, ValueCodingTypes valueCodingType, int initialCapacity) {
		this.cacheName = cacheName;
		this.strategy = ValueCoding.getValueCodingStrategy(valueCodingType);
		this.key2values = new HashMap<String, KeyValues>(initialCapacity);
		this.chineseMap = DimensionMap.createDimensionMap();
	}

	@Override
	public void supplyResource(Resource resource) {
		int size = resource.size();
		for (int i = 0; i < size; i++) {
			this.tryCacheKeyValue(resource.getKey(i), resource.getValue(i));
		}
	}

	@Override
	public void supplyData(String data) {
		try {
			supplyResource(Resource.getResourceByData(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void supplyData(String resourceKey, String resourceValue) {
		this.tryCacheKeyValue(resourceKey, resourceValue);
	}

	/**
	 * 尝试缓存一对键值对<br>
	 * 先检查有效性，不通过则忽略<br>
	 * 
	 * @param resourceKey
	 *            键
	 * @param resourceValue
	 *            值
	 * @return 缓存成功true，否则false.
	 */
	abstract protected boolean tryCacheKeyValue(String resourceKey, String resourceValue);

	/**
	 * 简化反向影射:<br>
	 * 1.简化输入。{@link IValueCodingStrategy#getSimplifyValue}<br>
	 * 2.计算多维缓存Key。{@link IValueCodingStrategy#getDimensionKeys} <br>
	 * 3.建立缓存key与汉字的影射。<br>
	 * 
	 * @param singleValue
	 *            值的简化键
	 * @param key
	 *            键
	 */
	protected final void cache2DimensionMap(String singleValue, String key) {
		String[] dimensionKeys = strategy.getDimensionKeys(strategy.getSimplifyValue(singleValue));//35%性能占用
		for (String dimensionKey : dimensionKeys) {//65%性能占用
			chineseMap.add(dimensionKey, key);
		}
	}

	@Override
	public String getCacheName() {
		return cacheName;
	}

	@Override
	public boolean isKey(String key) {
		return key2values.containsKey(key);
	}

	@Override
	public String[] getValues(String key) {
		return key2values.get(key).getValues();
	}

	@Override
	public List<String> getKeys(String valuePrex) {
		return chineseMap.get(valuePrex);
	}

	@Override
	public int getKeysSize() {
		return key2values.size();
	}

	@Override
	public String toString() {
		return chineseMap.toString();
	}

	public static final IChineseCache createChineseCache(String cacheName, Resource resource,
			ValueCodingTypes valueType) {
		ChineseCacheImpl rs = new ChineseCacheImpl();
		rs.initCache(null, valueType, resource.size());
		rs.supplyResource(resource);
		return rs;
	}
}
