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
	 * 1.不可编码部分作为编码保留，可编码则求取编码。
	 * 2.编码数组作自由组合，中间补充空格。<br>
	 */
	@Override
	public final String[] coding(IChineseCache wordCache, String words) {
		final int keyLen = words.length();
		if (keyLen > 0) {
			String[] parts = participles(wordCache, words);
			String[][] values = new String[parts.length][];
			int i;
			int size = 1;
			for (i = 0; i < values.length; i++) {
				if (parts[i].length() > 1 || !wordCache.isKey(parts[i])) {
					values[i] = new String[] { parts[i] };
				} else {
					values[i] = wordCache.getValues(parts[i]);
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

	private String[] participles(IChineseCache wordCache, String words) {
		List<String> temp = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.length(); i++) {
			String word = words.charAt(i) + "";
			if (wordCache.isKey(word)) {
				if (sb.length() > 0) {
					temp.add(sb.toString());
					sb.setLength(0);
				}
				temp.add(word);
			} else {
				sb.append(word);
			}
		}
		if (sb.length() > 0) {
			temp.add(sb.toString());
		}
		String[] rs = new String[temp.size()];
		temp.toArray(rs);
		return rs;
	}
}
