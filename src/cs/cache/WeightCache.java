package cs.cache;

import java.util.HashMap;

import cs.cache.valuecoding.ValueCodingTypes;
import cs.resource.KeyWeight;
import cs.resource.Resource;

/**
 * 
 * @author xuzhuoxi
 *
 */
public abstract class WeightCache implements IWeightCache, ICacheInit {
	protected String cacheName;
	protected HashMap<String, KeyWeight> key2weight;

	@Override
	public void initCache(String cacheName, ValueCodingTypes valueCodingType, int initialCapacity) {
		this.cacheName = cacheName;
		this.key2weight = new HashMap<String, KeyWeight>(initialCapacity);
	}

	@Override
	public void supplyResource(Resource resource) {
		if (null != resource) {
			int size = resource.size();
			for (int i = 0; i < size; i++) {
				this.tryCacheResourceInfo(resource.getKey(i), resource.getValue(i));
			}
		}
	}

	/**
	 * 尝试缓存一对键值对<br>
	 * 先检查有效性，不通过则忽略<br>
	 * 
	 * @param resourceKey
	 * @param resourceValue
	 * @return
	 */
	abstract protected boolean tryCacheResourceInfo(String resourceKey, String resourceValue);

	@Override
	public String getCacheName() {
		return cacheName;
	}

	@Override
	public boolean isKey(String key) {
		return key2weight.containsKey(key);
	}

	@Override
	public int getKeysSize() {
		return key2weight.size();
	}

	/**
	 * 对于没有缓存的，统一返回默认值
	 */
	@Override
	public double getValues(String key) {
		if (isKey(key)) {
			return key2weight.get(key).getWeight();
		}
		return DEFAULT_VALUE;
	}

	public static final IWeightCache createWeightCache(String cacheName, Resource resource) {
		WeightCacheImpl rs = new WeightCacheImpl();
		rs.initCache(cacheName, null, resource.size());
		rs.supplyResource(resource);
		return rs;
	}
}
