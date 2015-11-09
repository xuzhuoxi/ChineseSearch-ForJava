package cs.cache;

import cs.cache.valuecoding.ValueCodingTypes;
import cs.cacheconfig.CacheInfo;
import cs.resource.Resource;

/**
 * 
 * @author xuzhuoxi
 *
 */
public interface ICacheInit {
	/**
	 * 初始化Cache信息
	 * 
	 * @param cacheName
	 *            Cache名称
	 * @param valueCodingType
	 *            资源的值处理类型{@link CacheInfo#getValueType()}
	 * @param initialCapacity
	 *            初始化容器{@link CacheInfo#getInitialCapacity()}
	 */
	void initCache(String cacheName, ValueCodingTypes valueCodingType, int initialCapacity);

	/**
	 * 缓存数据<br>
	 * 
	 * @param resource
	 *            数据资源，详细请看Resource类{@link Resource}
	 */
	void supplyResource(Resource resource);

	/**
	 * 缓存数据<br>
	 * 
	 * @param data
	 *            数据字符串，以换行符区分单个数据
	 */
	void supplyData(String data);

	/**
	 * 缓存数据<br>
	 * 
	 * @param key
	 *            汉字(词)
	 * @param value
	 *            值
	 */
	void supplyData(String resourceKey, String resourceValue);
}
