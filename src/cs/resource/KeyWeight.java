package cs.resource;

/**
 * 汉字(词)与权重信息
 * 
 * @author xuzhuoxi
 *
 */
public class KeyWeight {
	/**
	 * 汉字(词)
	 */
	private String key;
	/**
	 * 权重
	 */
	private double weight;

	public KeyWeight(String key, double weight) {
		super();
		this.key = key;
		this.weight = weight;
	}

	public final String getKey() {
		return key;
	}

	public final double getWeight() {
		return weight;
	}
}
