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
public class WubiWordStrategyImpl extends AbstractWubiStrategy implements IValueCodingStrategy {

	WubiWordStrategyImpl() {
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
	 * 如果输入中包含已编码的中文，返回对应第一个中文的编码<br>
	 * 否则截取前4位拼音字符作为返回
	 */
	@Override
	public String[] translate(String filteredInput) {
		if (ChineseUtils.hasChinese(filteredInput)) {
			IChineseCache wordWubiMap = (IChineseCache) CachePool.getCache(CacheNames.WUBI_WORD);
			char c;
			String key;
			for (int i = 0; i < filteredInput.length(); i++) {
				c = filteredInput.charAt(i);
				if (ChineseUtils.isChinese(c)) {
					key = c + "";
					if (wordWubiMap.isKey(key)) {// 是字典中的汉字，返回编码
						return wordWubiMap.getValues(key);
					}
				}
			}
		}
		return new String[] { filteredInput.substring(0, 4) };
	}

	/**
	 * 简化编码的计算过程：<br>
	 * 分别截取从前[1-length]位作为dimensionKeys<br>
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
