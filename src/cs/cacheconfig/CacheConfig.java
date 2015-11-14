package cs.cacheconfig;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cs.cache.ChineseCacheImpl;
import cs.cache.ICache;
import cs.cache.WeightCacheImpl;
import cs.cache.valuecoding.ValueCodingTypes;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class CacheConfig {
	private static final Map<String, CacheInfo> map = new LinkedHashMap<String, CacheInfo>();

	/**
	 * 缓存配置<br>
	 * resourcePath路径在实际使用时前置补充了运行时根目录，如要取消这种做法，请修改CachePool类<br>
	 * initialCapacity的配置应该根据实际情况进行配置<br>
	 * 计算方法：缓存数的下一个2的n次幂<br>
	 */
	static {
		addConfig(CacheNames.PINYIN_WORD, ChineseCacheImpl.class, "./resource/word_pinyin.properites", "UTF-8",
				ValueCodingTypes.PINYIN_WORD, true, 32768);
		addConfig(CacheNames.WUBI_WORD, ChineseCacheImpl.class, "./resource/word_wubi.properites", "UTF-8",
				ValueCodingTypes.WUBI_WORDS, true, 8192);
		addConfig(CacheNames.PINYIN_WORDS, ChineseCacheImpl.class, "./resource/words_pinyin.properites", "UTF-8",
				ValueCodingTypes.PINYIN_WORDS, true, 131072);
		addConfig(CacheNames.WUBI_WORDS, ChineseCacheImpl.class, "./resource/words_wubi.properites", "UTF-8",
				ValueCodingTypes.WUBI_WORDS, true, 131072);
		addConfig(CacheNames.WEIGHT, WeightCacheImpl.class, "./resource/words_weight.properites", "UTF-8", null, true,
				16);
	}

	private static final void addConfig(String cacheName, Class<? extends ICache> cacheClass, String resourcePath,
			String charsetName, ValueCodingTypes valueType, boolean singleton, int initialCapacity) {
		map.put(cacheName,
				new CacheInfo(cacheName, cacheClass, resourcePath, charsetName, valueType, singleton, initialCapacity));
	}

	public static final CacheInfo getCacheInfo(String cacheName) {
		return map.get(cacheName);
	}

	public static final Collection<CacheInfo> getCacheInfos() {
		return map.values();
	}
}
