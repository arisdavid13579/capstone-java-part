package resources;

/**
 * A list of valid months of the year. Used for month validation.
 * 
 * @author Aris Fernandez
 *
 */
public enum Month {
	_1(1), _JANUARY(1), _JAN(1), _2(2), _FEBRUARY(2), _FEB(2), _3(3), _MARCH(3), _MAR(3), _4(4), _APRIL(4), _APR(4),
	_5(5), _MAY(5), _6(6), _JUNE(6), _JUN(6), _7(7), _JULY(7), _JUL(7), _8(8), _AUGUST(8), _AUG(8), _9(9),
	_SEPTEMBER(9), _SEP(9), SEPT(9), _10(10), _OCTOBER(10), _OCT(10), _11(11), _NOVEMBER(11), _NOV(11), _12(12),
	_DECEMBER(12), _DEC(12);


	private int monthNo;

	private Month(int monthNo) {
		this.monthNo = monthNo;
	}

	public int getMonthNo() {
		return this.monthNo;
	}

}
