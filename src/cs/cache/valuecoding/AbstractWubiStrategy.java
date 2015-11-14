package cs.cache.valuecoding;

import code.chinese.ChineseUtils;
import cs.cache.IChineseCache;

/**
 * 
 * @author xuzhuoxi
 *
 */
public abstract class AbstractWubiStrategy extends ValueCoding {
	/**
	 * 过滤输入，保留中文字符和五笔编码用到的字符<br>
	 * 
	 * @param input
	 *            输入字符串
	 * @return 过滤处理后的字符串
	 */
	protected final String wubiFilter(String input) {
		sb.setLength(0);
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (ChineseUtils.isChinese(c) || ChineseUtils.isWubiChar(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 通过word在wordMap中查找全部值，返回编码长度最长的一个
	 * 
	 * @param wordMap
	 *            字(词)库，IChineseCache实例{@link IChineseCache}
	 * @param word
	 *            字(词)
	 * @return 最长的编码
	 */
	protected final String getWubiMaxlenValue(IChineseCache wordMap, String word) {
		String[] values = wordMap.getValues(word);
		if (values.length > 1) {
			return values[0];
		} else {
			int maxLenIndex = 0;
			int maxLen = 0;
			for (int i = 0; i < values.length; i++) {
				if (values[i].length() > maxLen) {
					maxLen = values[i].length();
					maxLenIndex = i;
				}
			}
			return values[maxLenIndex];
		}
	}
}
