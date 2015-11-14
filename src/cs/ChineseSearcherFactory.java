package cs;

/**
 * 
 * @author xuzhuoxi
 *
 */
public abstract class ChineseSearcherFactory {
	private static IChineseSearcher instance = new ChineseSearcherImpl();

	/**
	 * 取得一个IChineseSearcher单例实例<br>
	 * 
	 * @return 单例实例
	 */
	public final static IChineseSearcher getChineseSearcher() {
		return instance;
	}

	/**
	 * 取得一个IChineseSearcher实例<br>
	 * 
	 * @param newInstance
	 *            是否创建新实例
	 * @return 当newInstance为true时，创建新实例<br>
	 *         当newInstance为false时，返回单例<br>
	 */
	public final static IChineseSearcher getChineseSearcher(boolean newInstance) {
		if (newInstance)
			return new ChineseSearcherImpl();
		return instance;
	}
}
