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
	 * @param words
	 * @return
	 */
	String[] coding(IChineseCache wordCache, String words);
}
