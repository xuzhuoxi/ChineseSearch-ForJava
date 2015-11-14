package cs.wordscoding;

import cs.cache.IChineseCache;

/**
 * 
 * @author xuzhuoxi
 *
 */
abstract public class ChineseWordsCoding {
	public static final IChineseWordsCoding generateCodingImpl(ChineseWordsCodingTypes wordsCodingType) {
		switch (wordsCodingType) {
		case PINYIN:
			return new PinyinCodingImpl();
		case WUBI:
			return new WubiCodingImpl();
		default:
			return null;
		}
	}

	/**
	 * 检查输入是否可能进行编码，要求：<br>
	 * 1.输入中字符必须为中文字符范围。<br>
	 * 2.输入中字符必须在wordCache中有编码信息。<br>
	 * 
	 * @param wordCache
	 *            中文缓存实例{@link IChineseCache}
	 * @param words
	 *            词
	 * @return 输入中有一个字符不符合则返回false
	 */
	protected final boolean canCoding(IChineseCache wordCache, String words) {
		final int keyLen = words.length();
		char word;
		for (int i = 0; i < keyLen; i++) {
			word = words.charAt(i);
			if (word < 0x4e00 || word > 0x9fa5 || !wordCache.isKey(word + "")) {
				return false;
			}
		}
		return true;
	}
}
