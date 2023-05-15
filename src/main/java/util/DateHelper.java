package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static String getDateFromAnotherDate(int days, LocalDate today) {
        LocalDate expectedDate = today.plusDays(days);
        return expectedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
}
