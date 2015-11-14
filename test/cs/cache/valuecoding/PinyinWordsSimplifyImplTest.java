package cs.cache.valuecoding;

import org.junit.Test;

import cs.cache.valuecoding.IValueCodingStrategy;
import cs.cache.valuecoding.PinyinWordsStrategyImpl;

public class PinyinWordsSimplifyImplTest {

	@Test
	public void test() {
		//幸免于难=nie mian xu nan#xing mian xu nan#nie wan xu nan#xing wan xu nan#nie wen xu nan#xing wen xu nan#nie mian yu nan#xing mian yu nan#nie wan yu nan#xing wan yu nan#nie wen yu nan#xing wen yu nan#nie mian xu nuo#xing mian xu nuo#nie wan xu nuo#xing wan xu nuo#nie wen xu nuo#xing wen xu nuo#nie mian yu nuo#xing mian yu nuo#nie wan yu nuo#xing wan yu nuo#nie wen yu nuo#xing wen yu nuo
		IValueCodingStrategy strategy = new PinyinWordsStrategyImpl();
		String value = "nie mian xu nan#xing mian xu nan#nie wan xu nan#xing wan xu nan#nie wen xu nan#xing wen xu nan#nie mian yu nan#xing mian yu nan#nie wan yu nan#xing wan yu nan#nie wen yu nan#xing wen yu nan#nie mian xu nuo#xing mian xu nuo#nie wan xu nuo#xing wan xu nuo#nie wen xu nuo#xing wen xu nuo#nie mian yu nuo#xing mian yu nuo#nie wan yu nuo#xing wan yu nuo#nie wen yu nuo#xing wen yu nuo";
		String[] values = value.split("#");
		System.out.println("PinyinWordsSimplifyImplTest.test(): " + values.length);
		for (String singleValue : values) {
			String sValue = strategy.getSimplifyValue(singleValue);
			System.out.println("PinyinWordsSimplifyImplTest.test(): [" + singleValue + "][" + sValue + "]");
			String[] sValues = strategy.getDimensionKeys(sValue);
			for (String str : sValues) {
				System.out.print(" " + str);
			}
			System.out.println();
		}

	}

}
