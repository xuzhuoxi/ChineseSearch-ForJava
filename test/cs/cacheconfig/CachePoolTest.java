package cs.cacheconfig;

import java.util.List;

import org.junit.Test;

import cs.cache.IChineseCache;
import cs.cache.IWeightCache;

public class CachePoolTest {

	// @Test
	public void testWordWubi() {
		IChineseCache cc = (IChineseCache) CachePool.getCache(CacheNames.WUBI_WORD);
		List<String> keyList = cc.getKeys("sg");
		if (null != keyList) {
			System.out.println("ChineseCacheTest.testWordWubi(): " + keyList.size());
			for (String key : keyList) {
				System.out.print(" " + key);
			}
			System.out.println();
		} else {
			System.out.println("ChineseCacheTest.testWordWubi(): 0");
		}
		System.out.println(cc.toString());
	}

	// @Test
	public void testWordsWubi() {
		IChineseCache cc = (IChineseCache) CachePool.getCache(CacheNames.WUBI_WORDS);
		List<String> keyList = cc.getKeys("sg");
		if (null != keyList) {
			System.out.println("ChineseCacheTest.testWordsWubi(): " + keyList.size());
			for (String key : keyList) {
				System.out.print(" " + key);
			}
			System.out.println();
		} else {
			System.out.println("ChineseCacheTest.testWordsWubi(): 0");
		}
	}

	// @Test
	public void testWordPinyin() {
		IChineseCache cc = (IChineseCache) CachePool.getCache(CacheNames.PINYIN_WORD);
		List<String> keyList = cc.getKeys("sg");
		if (null != keyList) {
			System.out.println("ChineseCacheTest.testWordPinyin(): " + keyList.size());
			for (String key : keyList) {
				System.out.print(" " + key);
			}
			System.out.println();
		} else {
			System.out.println("ChineseCacheTest.testWordPinyin(): 0");
		}
		System.out.println(cc.toString());
	}

	@Test
	public void testWordsPinyin() {
		IChineseCache cc = (IChineseCache) CachePool.getCache(CacheNames.PINYIN_WORDS);
		List<String> keyList = cc.getKeys("sg");
		if (null != keyList) {
			System.out.println("ChineseCacheTest.testWordsWubi(): " + keyList.size());
			for (String key : keyList) {
				System.out.print(" " + key);
			}
			System.out.println();
		} else {
			System.out.println("ChineseCacheTest.testWordsWubi(): 0");
		}
		System.out.println(cc.toString());
	}

	// @Test
	public void testWordsWeight() {
		// 一一=1
		// 一丁点=0.1
		// 一丁点儿=1.06
		// 一万=5
		// 一丈=9
		// 一下=1.0
		String[] test = { "一一", "一丁点", "一丁点儿", "一万", "一丈", "一下" };
		IWeightCache wc = (IWeightCache) CachePool.getCache(CacheNames.WEIGHT);
		for (String key : test) {
			System.out.println("CachePoolTest.testWordsWeight(): " + wc.getValues(key));
		}
	}

	// @Test
	public void testIntegrity() {
		System.out.println("测试汉字完成性，共" + (0x9fa5 - 0x4e00 + 1) + "个汉字！");
		IChineseCache cc0 = (IChineseCache) CachePool.getCache(CacheNames.WUBI_WORD);
		IChineseCache cc1 = (IChineseCache) CachePool.getCache(CacheNames.PINYIN_WORD);
		IChineseCache[] ccArr = { cc0, cc1 };
		for (IChineseCache cc : ccArr) {
			int i = 0;
			StringBuilder sb = new StringBuilder();
			for (char c = 0x4e00; c <= 0x9fa5; c++) {
				String key = c + "";
				if (!cc.isKey(key)) {
					sb.append(Integer.toHexString(c) + " " + c + "\n");
					i++;
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(cc.getCacheName() + "中无编码总数" + i + "个：");
			System.out.println(sb.toString());
			System.out.println("————————————end");
		}
	}
}
