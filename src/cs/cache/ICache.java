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
	 * @return 取得当前实例在实例化期间被赋予的名字
	 */
	String getCacheName();

	/**
	 * 检测key是否被缓存起来
	 * 
	 * @param key
	 *            键
	 * @return 有true无false
	 */
	boolean isKey(String key);

	/**
	 * 缓存Key数
	 * 
	 * @return 缓存Key数
	 */
	int getKeysSize();
}
