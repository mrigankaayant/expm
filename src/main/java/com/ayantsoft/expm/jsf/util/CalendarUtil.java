package com.ayantsoft.expm.jsf.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil {

	private static Calendar getCalendarForNow() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}
	
	
	public static Date getStartDateOfCurrentMonth(){
		Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
	}
	
	public static Date getEndDateOfCurrentMonth(){
		Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(calendar);
        return calendar.getTime();
	}
	
	
	public static Date getStartDateOfPreviousMonth(){
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.MONTH, -1);
		aCalendar.set(Calendar.DATE, 1);
		return aCalendar.getTime();
	}
	
	
	public static Date getStartDateOfPreviousTwoMonth(){
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.MONTH, -2);
		aCalendar.set(Calendar.DATE, 1);
		return aCalendar.getTime();
	}
	
	
	
}
