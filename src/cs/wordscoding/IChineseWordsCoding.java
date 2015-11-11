package cs.wordscoding;

import cs.cache.IChineseCache;

/**
 * 
 * @author xuzhuoxi
 *
 */
public interface IChineseWordsCoding {
	/**
	 * 对字符串进行编码<br>
	 * 要求实现对象先验证有效性。<br>
	 * 
	 * @param wordCache
	 *            中文缓存实例{@link IChineseCache}
	 * @param words
	 *            词
	 * @return 编码数组
	 */
	String[] coding(IChineseCache wordCache, String words);
}
