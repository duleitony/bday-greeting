package com.tony.bday.greeting.unitTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.tony.bday.greeting.processor.GetTargetRecords;

public class IsBirthdayTodayTest {
    GetTargetRecords getTargetRecords = new GetTargetRecords();
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    Date today = new Date();
    String todayStr = df.format(today);

    @Test
    public void isTodayTest() throws ParseException {
        assert(getTargetRecords.isBirthdayToday(todayStr));
    }

    @Test
    public void isTodayBirthdayFromStrTest() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        System.out.println("1975/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH));
        //Plus 1 is because the first month of the year in the Gregorian and Julian calendars is JANUARY which is 0
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        assert(getTargetRecords.isBirthdayToday("1975/" + currentMonth + "/" + currentDayOfMonth));
    }

    @Test
    public void isNotTodayFromStrTest() throws ParseException {
        assert(!getTargetRecords.isBirthdayToday("1975/11/6"));
    }

    @Test(expected = ParseException.class)
    public void wrongDateStringFormatTest() throws ParseException {
        assert(!getTargetRecords.isBirthdayToday("1975116"));
    }

    @Test(expected = ParseException.class)
    public void emptyDateStringFormatTest() throws ParseException {
        assert(!getTargetRecords.isBirthdayToday(""));
    }
}
