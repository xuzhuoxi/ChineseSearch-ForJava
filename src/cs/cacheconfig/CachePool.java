package cs.cacheconfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import code.path.BasePathUtils;
import cs.cache.ICache;
import cs.cache.ICacheInit;
import cs.resource.Resource;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class CachePool {
	private static final Map<String, ICache> cachePool = new HashMap<String, ICache>();

	/**
	 * 初始化全部单例Cache
	 */
	public static final void initSingletonCaches() {
		Collection<CacheInfo> c = CacheConfig.getCacheInfos();
		for (CacheInfo ci : c) {
			if (ci.isSingleton()) {
				getCache(ci.getCacheName());
			}
		}
	}

	/**
	 * 通过cacheName取得一个实例
	 * 
	 * @param cacheName
	 *            Cache名称{@link CacheNames}
	 * @return ICache实例
	 */
	public static final ICache getCache(String cacheName) {
		CacheInfo ci = CacheConfig.getCacheInfo(cacheName);
		if (null == ci) {
			return null;
		}
		ICache rs;
		if (ci.isSingleton()) {
			if (cachePool.containsKey(ci.getCacheName())) {
				rs = cachePool.get(ci.getCacheName());
			} else {
				rs = createInstance(ci);
				cachePool.put(ci.getCacheName(), rs);
			}
		} else {
			rs = createInstance(ci);
		}
		return rs;
	}

	private static final ICache createInstance(CacheInfo ci) {
		try {
			Constructor<? extends ICache> c = ci.getCacheClass().getDeclaredConstructor();
			c.setAccessible(true);
			ICache rs = (ICache) c.newInstance();
			if (ci.isNeedResource() && rs instanceof ICacheInit) {
				ICacheInit init = (ICacheInit) rs;
				init.initCache(ci.getCacheName(), ci.getValueType(), ci.getInitialCapacity());
				String[] paths;
				if (ci.getResourcePath().indexOf(";") > 0) {
					paths = ci.getResourcePath().split(";");
				} else {
					paths = new String[] { ci.getResourcePath() };
				}
				for (int i = 0; i < paths.length; i++) {
					// Resource resource =
					// Resource.getResourceByPath(paths[i]);//使用这里，取消路径补充这个行为。
					Resource resource = Resource
							.getResourceByPath(BasePathUtils.getAppPath(CachePool.class) + paths[i]);
					init.supplyResource(resource);
				}
			}
			return rs;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
