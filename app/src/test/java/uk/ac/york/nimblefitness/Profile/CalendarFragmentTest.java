package uk.ac.york.nimblefitness.Profile;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;


public class CalendarFragmentTest {

    @Test
    void checkCorrectDate(){

        SimpleDateFormat month = new SimpleDateFormat("M");
        SimpleDateFormat day = new SimpleDateFormat("d");
        Date currentTime = Calendar.getInstance().getTime();
        String monthString = month.format(currentTime);
        String dayString = day.format(currentTime);

        String dayNumber = monthText(Integer.parseInt(monthString)) + " " + dayString + datePrefix(Integer.parseInt(dayString));

        //Change to current Month & Date - "March 18th"
        assertEquals("March 18th", dayNumber);
    }



    public String monthText(int month){
        switch (month){
            case 1: return("January");
            case 2: return("February");
            case 3: return("March");
            case 4: return("April");
            case 5: return("May");
            case 6: return("June");
            case 7: return("July");
            case 8: return("August");
            case 9: return("September");
            case 10: return("October");
            case 11: return("November");
            case 12: return("December");
            default: return("error");
        }
    }

    public String datePrefix(int dayOfMonth){
        switch (dayOfMonth){
            case 1:
            case 21:
            case 31:
                return ("st");
            case 2:
            case 22:
                return ("nd");
            case 3:
            case 23:
                return ("rd");
            default: return ("th");
        }
    }


}
