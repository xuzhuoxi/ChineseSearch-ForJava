package cs.cache.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无字符集合限制<br>
 * 可使用任何键dimensionKey进行操作<br>
 * 
 * @author xuzhuoxi
 */
public class UnfixedDimensionMapImpl extends DimensionMap implements IDimensionMap {
	UnfixedDimensionMapImpl() {
	}

	@Override
	public int getDimension() {
		return valueList.size();
	}

	/**
	 * 如果键dimensionKey的长度大于维度(valueList.size)，则填充空的Map至使维度(valueList.size)
	 * 等于dimensionKey的长度<br>
	 */
	@Override
	public void add(String dimensionKey, String dimensionValue) {
		if (null == dimensionKey || dimensionKey.length() == 0) {
			return;
		}
		int dValue = dimensionKey.length() - valueList.size();
		if (dValue > 0) {
			for (int i = 0; i < dValue; i++) {
				valueList.add(new HashMap<String, List<String>>(8192));
			}
		}
		Map<String, List<String>> map = valueList.get(dimensionKey.length() - 1);
		List<String> list = null;
		if (!map.containsKey(dimensionKey)) {
			list = new ArrayList<String>();
			map.put(dimensionKey, list);
		} else {
			list = map.get(dimensionKey);
		}
		if (!list.contains(dimensionValue)) {
			list.add(dimensionValue);
		}
	}

	@Override
	public List<String> get(String dimensionKey) {
		return getKeyList(dimensionKey);
	}
}
