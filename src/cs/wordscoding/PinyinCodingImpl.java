package cs.wordscoding;

import java.util.ArrayList;
import java.util.List;

import cs.cache.IChineseCache;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class PinyinCodingImpl extends ChineseWordsCoding implements IChineseWordsCoding {

	PinyinCodingImpl() {
	}

	/**
	 * 编码过程：<br>
	 * 1.验证可编码性。<br>
	 * 2.取每个汉字的拼音编码数组分别作自由组合，中间补充空间。<br>
	 */
	@Override
	public final String[] coding(IChineseCache wordCache, String words) {
		final int keyLen = words.length();
		if (keyLen > 0 && canCoding(wordCache, words)) {
			String[][] values = new String[keyLen][];
			int i;
			int size = 1;
			for (i = 0; i < keyLen; i++) {
				String word = words.charAt(i) + "";
				if (wordCache.isKey(word)) {
					values[i] = wordCache.getValues(words.charAt(i) + "");
				} else {
					values[i] = new String[] { word };
				}
				size *= values[i].length;
			}
			List<StringBuilder> sbList = new ArrayList<StringBuilder>(size);
			for (i = 0; i < size; i++) {
				sbList.add(new StringBuilder());
			}
			for (i = 0; i < size; i++) {
				StringBuilder sb = sbList.get(i);
				for (String[] strAry : values) {
					int index = i % strAry.length;
					sb.append(strAry[index] + " ");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			String[] rs = new String[size];
			for (i = 0; i < rs.length; i++) {
				rs[i] = sbList.get(i).toString();
			}
			return rs;
		} else {
			return new String[] { words };
		}
	}

}
