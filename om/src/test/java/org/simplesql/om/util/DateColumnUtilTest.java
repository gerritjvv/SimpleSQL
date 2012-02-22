package org.simplesql.om.util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.simplesql.om.util.DateColumnUtil;

/**
 * 
 * Test the column name date conversion
 * 
 */
public class DateColumnUtilTest {

	@Test
	public void testConvertDates() {

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		for (int i = 0; i < 500; i++) {
			for (int a = 0; a < 365; a++) {

				byte[] bytes = DateColumnUtil.toDayDateColumn(cal
						.getTimeInMillis());
				assertEquals(4, bytes.length);
				long ts = DateColumnUtil.readDate(bytes);
				cal2.setTimeInMillis(ts);
				assertEquals(cal.get(Calendar.DAY_OF_YEAR),
						cal2.get(Calendar.DAY_OF_YEAR));
				assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
				assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));

				cal.add(Calendar.DAY_OF_YEAR, 1);

			}
		}
	}

	@Test
	public void testConvertDateHours() {

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		for (int i = 0; i < 100; i++) {
			for (int a = 0; a < 365; a++) {

				for (int b = 0; b < 24; b++) {
					byte[] bytes = DateColumnUtil.toDayDateHourColumn(cal
							.getTimeInMillis());
					assertEquals(4, bytes.length);
					long ts = DateColumnUtil.readDateHours(bytes);

					cal2.setTimeInMillis(ts);
					assertEquals(cal.get(Calendar.HOUR),
							cal2.get(Calendar.HOUR));

					assertEquals(cal.get(Calendar.DAY_OF_YEAR),
							cal2.get(Calendar.DAY_OF_YEAR));
					assertEquals(cal.get(Calendar.MONTH),
							cal2.get(Calendar.MONTH));
					assertEquals(cal.get(Calendar.YEAR),
							cal2.get(Calendar.YEAR));

					cal.add(Calendar.HOUR, 1);
				}

			}
		}

	}

}
