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
public class PinyinWordStrategyImpl extends ValueCoding implements IValueCodingStrategy {

	PinyinWordStrategyImpl() {
	}

	@Override
	public String getSimplifyValue(String value) {
		return value;
	}

	@Override
	public String[] getDimensionKeys(String simplifyValue) {
		return abstractGetDimensionKeys(simplifyValue);
	}

	/**
	 * 过滤输入字符串，保留中文字符和拼音字符<br>
	 */
	@Override
	public String filter(String input) {
		sb.setLength(0);
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (ChineseUtils.isChinese(c) || ChineseUtils.isPinyinChar(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 如果输入中包含已编码的中文，返回对应第一个中文的全部编码<br>
	 * 否则返回源字符串
	 */
	@Override
	public String[] translate(String filteredInput) {
		if (ChineseUtils.hasChinese(filteredInput)) {
			IChineseCache wordPinyinMap = (IChineseCache) CachePool.getCache(CacheNames.PINYIN_WORD);
			char c;
			String key;
			for (int i = 0; i < filteredInput.length(); i++) {
				c = filteredInput.charAt(i);
				if (ChineseUtils.isChinese(c)) {
					key = c + "";
					if (wordPinyinMap.isKey(key)) {
						return wordPinyinMap.getValues(key);
					}
				}
			}
		}
		return new String[] { filteredInput };
	}

	/**
	 * 简化编码的计算过程：<br>
	 * 分别截取从前[1-length]位作为dimensionKeys
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
