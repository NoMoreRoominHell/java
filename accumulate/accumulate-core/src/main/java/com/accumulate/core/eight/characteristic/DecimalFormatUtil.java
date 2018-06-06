package com.accumulate.core.eight.characteristic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.Function;

public class DecimalFormatUtil {

	private static final DecimalFormat DF = new DecimalFormat("0.00");

	/**
	 * BigDecimal保留两位小数
	 * 
	 * @param param
	 * @return
	 */
	public static BigDecimal format(BigDecimal param) {
		if (param == null)
			return BigDecimal.ZERO;
		return new BigDecimal(DF.format(param));
	}

	/**
	 * 
	 * @param param
	 * @param func
	 * @return
	 */
	public static BigDecimal custom(BigDecimal param, Function<BigDecimal, BigDecimal> func) {
		if (param == null)
			return BigDecimal.ZERO;
		return func.apply(param);
	}

	public static void main(String[] args) {
	}
}
