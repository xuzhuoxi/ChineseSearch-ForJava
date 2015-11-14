package cs.cache.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.array.ArrayUtils;

/**
 * 指定了有效的字符集合<br>
 * 使用超出有效字符集合的键dimensionKey进行操作时会报错。<br>
 * 
 * @author xuzhuoxi
 */
public class FixedDimensionMapImpl extends DimensionMap implements IDimensionMap {
	private final int dimension;
	private final Character[] charList;

	FixedDimensionMapImpl(Character[] charSet, int dimension) {
		super();
		this.charList = ArrayUtils.cleanRepeat2List(charSet);
		if (charList.length < 1) {
			throw new Error("The different elements in keyCode should always be greater than 0.");
		}
		this.dimension = dimension;
		this.valueList = new ArrayList<Map<String, List<String>>>();
		List<String> dimensionKeylist = null;
		for (int i = 0; i < dimension; i++) {
			dimensionKeylist = addDimmension(dimensionKeylist);
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			this.valueList.add(map);
			for (String dk : dimensionKeylist) {
				map.put(dk, new ArrayList<String>());
			}
		}
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public void add(String dimensionKey, String dimensionValue) {
		if (dimensionKey.length() <= dimension) {
			List<String> list = getKeyListAtLenLimit(dimensionKey);
			if (!list.contains(dimensionValue)) {
				list.add(dimensionValue);
			}
		}
	}

	@Override
	public List<String> get(String dimensionKey) {
		return getKeyList(dimensionKey);
	}

	private List<String> addDimmension(List<String> dimesionKeylist) {
		List<String> dl = dimesionKeylist;
		if (null == dimesionKeylist) {
			dl = new ArrayList<String>();
		}
		List<String> rs = new ArrayList<String>();
		for (Character ch : charList) {
			if (dl.size() == 0) {
				rs.add(ch.toString());
			} else {
				for (String str : dl) {
					rs.add(str + ch);
				}
			}
		}
		return rs;
	}
}
