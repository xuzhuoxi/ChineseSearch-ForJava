package cs.cache;

/**
 * 
 * @author xuzhuoxi
 *
 */
public interface IWeightCache extends ICache {
	/**
	 * 默认权重
	 */
	public static final double DEFAULT_VALUE = 1.0;

	/**
	 * 取权重，若key没有记录，则返回默认权重<br>
	 * 
	 * @param key
	 *            键
	 * @return 权重值
	 */
	double getValues(String key);
}
