package cs.cacheconfig;

import cs.cache.ICache;
import cs.cache.valuecoding.ValueCodingTypes;

/**
 * 缓存信息
 * 
 * @author xuzhuoxi
 *
 */
public class CacheInfo {
	/**
	 * cache名称
	 */
	private String cacheName;
	/**
	 * 缓存初始化时使用资源的路径 多个资源可使用";"相隔
	 */
	private String resourcePath;
	/**
	 * 字符文件编码类型，如UTF-8等
	 */
	private String charsetName;
	/**
	 * 资源的值处理类型
	 */
	private ValueCodingTypes valueType;
	/**
	 * 是否为单例<br>
	 * 是：实例化后加入到缓存池中。{@link CachePool}<br>
	 * 否：实例化后不加入到缓存池中。<br>
	 */
	private boolean singleton;

	/**
	 * 缓存实例的Class对象
	 */
	private Class<? extends ICache> cacheClass;

	/**
	 * 哈希表初始容量
	 */
	private int initialCapacity = 16;

	public CacheInfo(String cacheName, Class<? extends ICache> myClass, String resourcePath, ValueCodingTypes valueType,
			boolean singleton) {
		super();
		this.cacheName = cacheName;
		this.cacheClass = myClass;
		this.resourcePath = resourcePath;
		this.charsetName = "UTF-8";
		this.valueType = valueType;
		this.singleton = singleton;
	}

	public CacheInfo(String cacheName, Class<? extends ICache> myClass, String resourcePath, String charsetName,
			ValueCodingTypes valueType, boolean singleton, int initialCapacity) {
		super();
		this.cacheName = cacheName;
		this.cacheClass = myClass;
		this.resourcePath = resourcePath;
		this.charsetName = charsetName;
		this.valueType = valueType;
		this.singleton = singleton;
		this.initialCapacity = initialCapacity;
	}

	/**
	 * @return cache名称
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @return 缓存初始化时使用资源的路径<br>
	 *         多个资源可使用";"相隔
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * @return 字符文件编码类型，如UTF-8等<br>
	 *         默认为UTF-8<br>
	 */
	public String getCharsetName() {
		return charsetName;
	}

	/**
	 * @return 资源的值处理类型
	 */
	public ValueCodingTypes getValueType() {
		return valueType;
	}

	/**
	 * @return 是否为单例<br>
	 *         是：实例化后加入到缓存池中。{@link CachePool}<br>
	 *         否：实例化后不加入到缓存池中。<br>
	 */
	public boolean isSingleton() {
		return singleton;
	}

	/**
	 * @return 缓存实例的Class对象
	 */
	public Class<? extends ICache> getCacheClass() {
		return cacheClass;
	}

	/**
	 * @return 哈希表初始容量
	 */
	public int getInitialCapacity() {
		return initialCapacity;
	}

	/**
	 * @return 是否需要资源作为初始化，当resourcePath{@link #resourcePath}无效时返回false。
	 */
	public boolean isNeedResource() {
		return null != resourcePath && resourcePath.length() > 0;
	}
}
