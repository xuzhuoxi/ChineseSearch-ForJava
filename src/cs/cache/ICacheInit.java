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
	 * 初始化实例数据<br>
	 * 
	 * @param resource
	 *            数据资源，详细请看Resource类{@link Resource}
	 */
	void supplyResource(Resource resource);

	// /**
	// * 初始化实例数据<br>
	// *
	// * @param resource
	// * 数据资源，详细请看Resource类{@link Resource}
	// * @param valueType
	// * 初始化时用于处理值行为的类型
	// * @param charSet
	// * 指定字符集
	// * @param cacheDimension
	// * 缓存维度
	// */
	// void initCache(Resource resource, ValueCodingTypes valueType, Character[]
	// charSet, int cacheDimension);
}
