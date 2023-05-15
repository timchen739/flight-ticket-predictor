package com.flight.ticket.predictor.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.DateHelper;

import java.time.LocalDate;

@SpringBootTest
public class DateHelperTest {
    @Test
    public void getFutureDateShouldWork() {
        String result = DateHelper.getDateFromAnotherDate(3, LocalDate.of(2023, 05, 15));
        Assertions.assertEquals("05/18/2023", result);
    }

    @Test
    public void getPastDateShouldWork() {
        String result = DateHelper.getDateFromAnotherDate(-5, LocalDate.of(2023, 05, 15));
        Assertions.assertEquals("05/10/2023", result);
    }
}
