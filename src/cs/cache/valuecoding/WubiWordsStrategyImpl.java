package cs.cache.valuecoding;

import code.chinese.ChineseUtils;
import cs.cache.IChineseCache;
import cs.cacheconfig.CacheNames;
import cs.cacheconfig.CachePool;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class WubiWordsStrategyImpl extends AbstractWubiStrategy implements IValueCodingStrategy {

	WubiWordsStrategyImpl() {
	}

	@Override
	public String getSimplifyValue(String value) {
		return value;
	}

	@Override
	public String[] getDimensionKeys(String simplifyValue) {
		return abstractGetDimensionKeys(simplifyValue);
	}

	@Override
	public String filter(String input) {
		return wubiFilter(input);
	}

	/**
	 * 翻译<br>
	 * <br>
	 * 从开始遍历input，如果是以下情况：<br>
	 * 是中文字符，取最长编码的第一个字符<br>
	 * 是五笔字符，直接取出<br>
	 * 如果长度到达4或input遍历结束，返回以上字符的顺序字符串<br>
	 * 
	 * @param filteredInput
	 *            过滤后的字符串
	 * @return 把要翻译的字符进行翻译后得到的字符串数组。
	 */
	@Override
	public String[] translate(String filteredInput) {
		sb.setLength(0);
		IChineseCache wordWubiMap = (IChineseCache) CachePool.getCache(CacheNames.WUBI_WORD);
		char c;
		String key;
		int len = 0;
		for (int i = 0; i < filteredInput.length() && len < 4; i++) {
			c = filteredInput.charAt(i);
			if (ChineseUtils.isChinese(c)) {
				key = c + "";
				if (wordWubiMap.isKey(key)) {// 是字典中的汉字，返回最长编码的第一位编码
					sb.append(getWubiMaxlenValue(wordWubiMap, key).charAt(0));
					len++;
				}
			} else {
				sb.append(c);
				len++;
			}
		}
		return new String[] { sb.toString() };
	}

	/**
	 * 计算简化编码<br>
	 * <br>
	 * 简化编码的计算过程：<br>
	 * 分别截取从前[1-length]位作为dimensionKeys<br>
	 * 
	 * @param simplifyValue
	 *            简化输入
	 * @return 计算得到的dimensionKey列表
	 */
	@Override
	protected String[] computeDimensionKeys(String simplifyValue) {
		String[] rs = new String[simplifyValue.length()];
		for (int i = 0; i < simplifyValue.length(); i++) {
			rs[i] = simplifyValue.substring(0, i + 1);
		}
		return rs;
	}
}
