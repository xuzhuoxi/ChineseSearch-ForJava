package cs.cache.valuecoding;

/**
 * 值处理接口<br>
 * 使用过程：<br>
 * 情况一：缓存过程。汉字或词的值编码－〉简化－〉计算缓存Key－〉建立缓存Key与汉字或词的影射<br>
 * 情况一: 查看过程。输入字符串－〉过滤－〉翻译－〉简化－〉用于处理(如何处理由外部决定)
 * 
 * @author xuzhuoxi
 *
 */
public interface IValueCodingStrategy {
	/**
	 * 简化:对值编码或输入编码进行进一步简化
	 * 
	 * @param value
	 * @return
	 */
	String getSimplifyValue(String value);

	/**
	 * 获取缓存key
	 * 
	 * @param simplifyValue
	 * @return
	 */
	String[] getDimensionKeys(String simplifyValue);

	/**
	 * 过滤输入的字符串
	 * 
	 * @param input
	 * @return
	 */
	String filter(String input);

	/**
	 * 翻译
	 * 
	 * @param filteredInput
	 * @return
	 */
	String[] translate(String filteredInput);
}
