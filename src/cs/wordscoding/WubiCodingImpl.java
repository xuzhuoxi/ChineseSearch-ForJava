package cs.wordscoding;

import java.util.Arrays;
import java.util.Comparator;

import cs.cache.IChineseCache;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class WubiCodingImpl extends ChineseWordsCoding implements IChineseWordsCoding {
	/**
	 * 五笔编码中没有长度致的两个不同编码
	 */
	private static final Comparator<String> comparator = new Comparator<String>() {
		@Override
		public final int compare(String o1, String o2) {
			return o2.length() - o1.length();
		}
	};

	WubiCodingImpl() {
	}

	/**
	 * 编码过程：<br>
	 * 1.一个字的，返回对应字的编码。<br>
	 * 2.两个字的，返回前两个字的最长编码前两个字符的合并字符串。<br>
	 * 3.三个字的，返回前三个字的最长编码第一个字符加上第三个字的最长编码第二个字符的合并字符串。<br>
	 * 4.四个字或以上的，返回前三个字的的最长编码的第一个字符加最后一个字的最长编码的第一个字符。<br>
	 */
	@Override
	public final String[] coding(IChineseCache wordCache, String words) {
		final int keyLen = words.length();
		if (keyLen > 0 && canCoding(wordCache, words)) {
			String[] codingResult = new String[1];
			switch (keyLen) {
			case 1:
				codingResult[0] = getMaxValue(wordCache, words);
				break;
			case 2:
				String value2_0 = getMaxValue(wordCache, words.charAt(0) + "");
				String value2_1 = getMaxValue(wordCache, words.charAt(1) + "");
				codingResult[0] = value2_0.substring(0, 2) + value2_1.substring(0, 2);
				break;
			case 3:
				String value3_0 = getMaxValue(wordCache, words.charAt(0) + "");
				String value3_1 = getMaxValue(wordCache, words.charAt(1) + "");
				String value3_2 = getMaxValue(wordCache, words.charAt(2) + "");
				codingResult[0] = "" + value3_0.charAt(0) + value3_1.charAt(0) + value3_2.substring(0, 2);
				break;
			default:
				String value4_0 = getMaxValue(wordCache, words.charAt(0) + "");
				String value4_1 = getMaxValue(wordCache, words.charAt(1) + "");
				String value4_2 = getMaxValue(wordCache, words.charAt(2) + "");
				String value4_3 = getMaxValue(wordCache, words.charAt(words.length() - 1) + "");
				codingResult[0] = "" + value4_0.charAt(0) + value4_1.charAt(0) + value4_2.charAt(0)
						+ value4_3.charAt(0);
				break;
			}
			return codingResult;
		} else {
			return words.split("");
		}
	}

	/**
	 * 取汉字的最长五笔编码<br>
	 * 
	 * @param wordCache
	 * @param word
	 * @return 如果没有编码信息，返回源字符
	 */
	private String getMaxValue(IChineseCache wordCache, String word) {
		if (wordCache.isKey(word)) {
			String[] values = wordCache.getValues(word);
			if (values.length > 1) {
				Arrays.sort(values, comparator);
			}
			return values[0];
		} else {
			return word;
		}
	}
}
