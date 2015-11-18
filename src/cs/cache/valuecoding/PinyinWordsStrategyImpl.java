package cs.cache.valuecoding;

import java.util.Arrays;

import code.array.ArrayUtils;
import code.chinese.ChineseUtils;
import code.math.MathUtils;
import code.string.StringCombination;
import cs.cache.IChineseCache;
import cs.cacheconfig.CacheNames;
import cs.cacheconfig.CachePool;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class PinyinWordsStrategyImpl extends ValueCoding implements IValueCodingStrategy {
	/**
	 * 单声母
	 */
	private final char[] danshengmu = new char[] { 'b', 'p', 'm', 'f', 'd', 't', 'n', 'l', 'g', 'k', 'h', 'j', 'q', 'x',
			'r', 'z', 'c', 's', 'w', 'y' };
	/**
	 * 空格
	 */
	private final char space = ' ';

	PinyinWordsStrategyImpl() {
		Arrays.sort(danshengmu);// 对声母进行排序，便于查找
	}

	/**
	 * 从头开始遍历<br>
	 * 遇到以下情况则加入到返回字符串中：<br>
	 * 1.第一个字符<br>
	 * 2.空格后的第一个字符<br>
	 * 3.声母字符<br>
	 * 把以上得到的字符按遍历顺序组成字符串返回<br>
	 */
	@Override
	public String getSimplifyValue(String value) {
		sb.setLength(0);
		char c;
		boolean add = false;
		for (int i = 0; i < value.length(); i++) {
			c = value.charAt(i);
			if (c != space) {
				if (add || Arrays.binarySearch(danshengmu, c) >= 0 || i == 0) {
					sb.append(c);
					add = false;
				}
			} else {
				add = true;
			}
		}
		return sb.toString();
	}

	@Override
	public String[] getDimensionKeys(String simplifyValue) {
		return abstractGetDimensionKeys(simplifyValue);
	}

	/**
	 * 过滤输入字符串，保留中文字符、拼音字符及空格<br>
	 */
	@Override
	public String filter(String input) {
		sb.setLength(0);
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (ChineseUtils.isChinese(c) || ChineseUtils.isPinyinChar(c) || space == c) {
				sb.append(c);
			}
		}
		// System.out.println("PinyinWordsStrategyImpl.filter():" + input + "|"
		// + sb.toString());
		return sb.toString();
	}

	/**
	 * 翻译过程：<br>
	 * 1.对字符串的中文进行翻译，前后以空格相隔，对于多音字，则进行自由组合<br>
	 * 2.对上面的每一个字符串进行简化{@link #getSimplifyValue(String)}<br>
	 * 3.返回上一步字符串组成的非重复数组<br>
	 * 
	 * @see #getSimplifyValue(String)
	 */
	@Override
	public String[] translate(String filteredInput) {
		String[] rs;
		if (ChineseUtils.hasChinese(filteredInput)) {
			IChineseCache wordPinyinMap = (IChineseCache) CachePool.getCache(CacheNames.PINYIN_WORD);
			int[] indexs = ChineseUtils.getChineseIndexs(filteredInput);
			String[][] values = new String[indexs.length][];
			int[] system = new int[indexs.length];
			int maxValue = 1;
			for (int i = 0; i < indexs.length; i++) {
				String key = filteredInput.charAt(indexs[i]) + "";
				if (wordPinyinMap.isKey(key)) {
					values[i] = wordPinyinMap.getValues(key);
				} else {
					values[i] = new String[] { "" };
				}
				system[i] = values[i].length;
				maxValue *= system[i];
			}
			rs = new String[maxValue];
			int[] temp;
			for (int i = 0; i < maxValue; i++) {
				sb.setLength(0);
				sb.append(filteredInput);
				temp = MathUtils.tenToCustomSystem(i, system);
				for (int j = indexs.length - 1; j >= 0; j--) {
					int sourceIndex = indexs[j];
					int valueIndex = temp[j];
					sb.replace(sourceIndex, sourceIndex + 1, " " + values[j][valueIndex] + " ");
				}
				rs[i] = sb.toString().trim();
			}
		} else {
			rs = new String[] { filteredInput };
		}
		for (int i = 0; i < rs.length; i++) {
			rs[i] = getSimplifyValue(rs[i]);
		}
		return ArrayUtils.cleanRepeat2List(rs);
	}

	/**
	 * 简化编码的计算过程：<br>
	 * 1.截取第二位开始的字符中进行无重复基于顺序的自由组合，详细请看{@link StringCombination}<br>
	 * 2.第一位分别拼接上面得的到字符串数组<br>
	 * 3.返回第一位字符以及上面的字符串数组组成的数组<br>
	 * 
	 * @see StringCombination
	 */
	@Override
	protected String[] computeDimensionKeys(String simplifyValue) {
		final int maxDimension = 2;
		String[] source = simplifyValue.split("");
		if (source.length > 1) {
			String[] subRs = StringCombination.getDimensionCombinationArray(ArrayUtils.subArray(source, 1),
					maxDimension - 1, false);
			String[] rs = new String[subRs.length + 1];
			rs[0] = source[0];
			for (int i = 1; i < rs.length; i++) {
				rs[i] = rs[0] + subRs[i - 1];
			}
			return rs;
		} else {
			return source;
		}
	}
}
