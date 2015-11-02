package cs.cache;

/**
 * 实例必须保留空的构造方法<br>
 * 
 * @author xuzhuoxi
 *
 */
public interface ICache {

	/**
	 * 取得当前实例在实例化期间被赋予的名字
	 * 
	 * @return
	 */
	String getCacheName();

	/**
	 * 检测key是否被缓存起来
	 * 
	 * @param key
	 * @return
	 */
	boolean isKey(String key);

	/**
	 * 缓存Key数
	 * 
	 * @return
	 */
	int getKeysSize();
}
