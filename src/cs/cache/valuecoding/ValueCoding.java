package cs.cache.valuecoding;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author xuzhuoxi
 *
 */
public abstract class ValueCoding {
	private static final Map<ValueCodingTypes, IValueCodingStrategy> map = new HashMap<ValueCodingTypes, IValueCodingStrategy>();

	static {
		for (ValueCodingTypes type : ValueCodingTypes.values()) {
			map.put(type, createValueCodingStrategy(type));
		}
	}

	public static final IValueCodingStrategy getValueCodingStrategy(ValueCodingTypes type) {
		return map.get(type);
	}

	public static final IValueCodingStrategy createValueCodingStrategy(ValueCodingTypes type) {
		switch (type) {
		case PINYIN_WORD:
			return new PinyinWordStrategyImpl();
		case PINYIN_WORDS:
			return new PinyinWordsStrategyImpl();
		case WUBI_WORD:
			return new WubiWordStrategyImpl();
		case WUBI_WORDS:
			return new WubiWordsStrategyImpl();
		default:
			return null;
		}
	}

	protected final StringBuilder sb = new StringBuilder();
	protected final Map<String, String[]> simplifyValueKeysMap = new HashMap<String, String[]>();

	/**
	 * 如果缓存中没有，则调用子类的{@link #computeDimensionKeys(String)}方法进行计算
	 * 
	 * @param simplifyValue
	 *            简化输入
	 * @return 计算得到的dimensionKey列表
	 */
	protected final String[] abstractGetDimensionKeys(String simplifyValue) {
		if (simplifyValueKeysMap.containsKey(simplifyValue)) {
			return simplifyValueKeysMap.get(simplifyValue);
		} else {
			String[] rs = computeDimensionKeys(simplifyValue);
			simplifyValueKeysMap.put(simplifyValue, rs);
			return rs;
		}
	}

	/**
	 * 计算简化编码
	 * 
	 * @param simplifyValue
	 *            简化输入
	 * @return 计算得到的dimensionKey列表
	 */
	abstract protected String[] computeDimensionKeys(String simplifyValue);
}
