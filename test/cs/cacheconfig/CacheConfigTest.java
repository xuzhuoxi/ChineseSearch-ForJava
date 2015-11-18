package cs.cacheconfig;

import org.junit.Test;

public class CacheConfigTest {
	static final int MAXIMUM_CAPACITY = 1 << 30;

	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	@Test
	public void computeInitialCapacity() {
		System.out.println("CacheConfigTest.computeInitialCapacity()");
		
		System.out.println(tableSizeFor(20808));// 拼音字 结果：32768
		System.out.println(tableSizeFor(6791));// 五笔字 结果：8192
		
		System.out.println(tableSizeFor(90679));// 拼音词 结果：131072
		System.out.println(tableSizeFor(90674));// 五笔词 结果：131072
		
		System.out.println(tableSizeFor(90680));// 权值 结果：131072
		
		System.out.println(tableSizeFor(50000));// 拼音词简化 结果：65536
	}

}
