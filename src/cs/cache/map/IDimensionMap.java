package cs.cache.map;

import java.util.List;

/**
 * 键值表，用于反向查找汉字或词语<br>
 * 通过针对汉字或词语对应的各种编码进行进一步的简化编码，得到的简化编码作为键<br>
 * 记录简化编码与汉字或词语对应的影射关系<br>
 * 
 * @author xuzhuoxi
 *
 */
public interface IDimensionMap {
	/**
	 * 获得当前维度，即存放中最长的Key的长度
	 * 
	 * @return
	 */
	int getDimension();

	/**
	 * 存储一对键值对，如果键已存在，就尝试在对应值列表中追加值，已存在相同的值则忽略。<br>
	 * 
	 * @param dimensionKey键
	 * @param dimensionValue值
	 */
	void add(String dimensionKey, String dimensionValue);

	/**
	 * 通过键，取得值列表<br>
	 * 
	 * @param dimensionKey
	 * @return 返回的是原始列表
	 */
	List<String> get(String dimensionKey);
}
