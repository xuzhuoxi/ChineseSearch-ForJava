package cs.cache;

import java.util.List;

/**
 * 
 * @author xuzhuoxi
 *
 */
public interface IChineseCache extends ICache {

	/**
	 * 获取键对应全部值
	 * 
	 * @param key
	 * @return
	 */
	String[] getValues(String key);

	/**
	 * 通过简化编码取得一部分key列表
	 * 
	 * @param valuePrex
	 *            简化编码
	 * @return
	 */
	List<String> getKeys(String valuePrex);
}
