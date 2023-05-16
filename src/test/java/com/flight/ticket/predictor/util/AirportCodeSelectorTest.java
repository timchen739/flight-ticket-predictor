package com.flight.ticket.predictor.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.AirportCodeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class AirportCodeSelectorTest {
    private AirportCodeSelector airportCodeSelector;

    @BeforeEach
    public void init() {
        List<String> airportCodes = new ArrayList<>();
        airportCodes.add("ATL");
        airportCodes.add("SEA");
        airportCodeSelector = new AirportCodeSelector(airportCodes);
    }
    @Test
    public void testSelectAirportCode() {
        String code = airportCodeSelector.selectAirportCodeExcept(Collections.emptyList());
        Assertions.assertTrue(airportCodeSelector.hasCode(code));
    }

    @Test
    public void from_code_and_to_code_should_be_different() {
        String fromCode = airportCodeSelector.selectAirportCodeExcept(Collections.emptyList());
        String toCode = airportCodeSelector.selectAirportCodeExcept(List.of(fromCode));

        Assertions.assertFalse(fromCode.equals(toCode), "From Code and To Code are the same" );
    }
}
