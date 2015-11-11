
package cs.cache.map;

/**
 * 字符表定义，用于FixedDimensionMapImpl类
 * 
 * @author xuzhuoxi
 *
 */
public class CharacterSet {
	private CharacterSet() {
	}

	/**
	 * 拼音用到的字符
	 */
	private static final Character[] letterCharset = new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	/**
	 * 五笔用到的字符
	 */
	private static final Character[] wubiLetterCharset = new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y' };
	/**
	 * 数字用到的字符
	 */
	private static final Character[] numberCharset = new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	/**
	 * 获取 拼音用到的字符
	 * 
	 * @return 拼音用到的字符
	 */
	public static final Character[] LETTER_CHARSET() {
		return letterCharset.clone();
	}

	/**
	 * 获取 五笔用到的字符
	 * 
	 * @return 五笔用到的字符
	 */
	public static final Character[] WUBI_LETTER_CHARSET() {
		return wubiLetterCharset.clone();
	}

	/**
	 * 获取 数字用到的字符
	 * 
	 * @return 数字用到的字符
	 */
	public static final Character[] NUMBER_CHARSET() {
		return numberCharset.clone();
	}
}
