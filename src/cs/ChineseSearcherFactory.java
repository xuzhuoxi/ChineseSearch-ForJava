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
	 * @return
	 */
	public final static IChineseSearcher getChineseSearcher() {
		return instance;
	}

	/**
	 * 取得一个IChineseSearcher实例<br>
	 * 
	 * @return
	 */
	public final static IChineseSearcher getChineseSearcher(boolean newInstance) {
		return new ChineseSearcherImpl();
	}
}
