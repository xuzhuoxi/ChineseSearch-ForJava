package cs.cache;

import org.junit.Test;

import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class WeightCacheTest {

	@Test
	public void test() {
		Resource resource;
		IWeightCache wc;
		try {
			resource = Resource.getResource("words", ResourceTypes.TYPE_WEIGHT);
			wc = WeightCache.createWeightCache("wordsWeight", resource);
			System.out.println("WeightCacheTest.test()：" + wc.getKeysSize());
			double weight = wc.getValues("一个巴掌拍不响");
			System.out.println(weight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
