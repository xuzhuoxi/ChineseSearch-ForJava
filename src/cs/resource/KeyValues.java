package cs.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 汉字(词)与编码的信息
 * 
 * @author xuzhuoxi
 *
 */
public class KeyValues {
	/**
	 * 汉字(词)
	 */
	private String key;
	/**
	 * 编码
	 */
	private List<String> values = new ArrayList<String>();

	public KeyValues(String key) {
		super();
		this.key = key;
	}

	public final String getKey() {
		return key;
	}

	/**
	 * 验证编码是否重复，否则存起来
	 * 
	 * @param value
	 * @return
	 */
	public final boolean addValue(String value) {
		if (values.contains(value)) {
			return false;
		} else {
			return values.add(value);
		}
	}

	/**
	 * 取得全部编码信息
	 * 
	 * @return
	 */
	public final String[] getValues() {
		String[] rs = new String[values.size()];
		return values.toArray(rs);
	}
}
