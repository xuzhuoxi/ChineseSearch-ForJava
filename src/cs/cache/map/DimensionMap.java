package cs.cache.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接口IDimensionMap对应的抽象类，存放共用或相似的行为方法<br>
 * 每个IDimensionMap的实现类建议继承这个类<br>
 * 
 * @author xuzhuoxi
 *
 */
public abstract class DimensionMap {
	/**
	 * 列表，内部存放的是Map的实例<br>
	 * 规律：<br>
	 * 1.每个Map实例中，全部Key的字符长度相同。<br>
	 * 2.List中第一个Map实例中Key的长度为1，第二个Map实例中Key的长度为2，如此类推<br>
	 */
	protected List<Map<String, List<String>>> valueList = new ArrayList<Map<String, List<String>>>();

	/**
	 * 通过键dimensionKey取得对应的值列表<br>
	 * 
	 * @param dimensionKey
	 *            不验证dimensionKey的长度及存在性
	 * @return 值列表
	 */
	protected final List<String> getKeyListAtLenLimit(String dimensionKey) {
		return valueList.get(dimensionKey.length() - 1).get(dimensionKey);
	}

	/**
	 * 通过键dimensionKey取得对应的值列表<br>
	 * 
	 * @param dimensionKey
	 *            如果长度大于valueList的size，取前部分允许长度的字符串作为键值进行获取<br>
	 * @return 值列表
	 */
	protected final List<String> getKeyList(String dimensionKey) {
		if (dimensionKey.length() <= valueList.size())
			return getKeyListAtLenLimit(dimensionKey);
		return valueList.get(valueList.size() - 1).get(dimensionKey.substring(0, valueList.size()));
	}

	@Override
	public String toString() {
		String className = this.getClass().toString();
		StringBuilder sb = new StringBuilder();
		sb.append(className + ":Dimension=\t" + valueList.size() + "\n");
		for (int i = 0; i < valueList.size(); i++) {
			Map<String, List<String>> map = valueList.get(i);
			sb.append(className + ":Dimension(" + (i + 1) + "):Begin[len=" + map.size() + "]\n");
			Set<String> keySet = map.keySet();
			List<String> values;
			for (String key : keySet) {
				values = map.get(key);
				sb.append(className + ":Key(" + key + " len=" + values.size() + ")=[");
				for (String value : values) {
					sb.append(value + " ");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("]\n");
			}
			sb.append(className + ":Dimension(" + (i + 1) + "):End\n");
		}
		return sb.toString();
	}

	public static final IDimensionMap createDimensionMap(Character[] charSet, int dimension) {
		return new FixedDimensionMapImpl(charSet, dimension);
	}

	public static final IDimensionMap createDimensionMap() {
		return new UnfixedDimensionMapImpl();
	}
}
