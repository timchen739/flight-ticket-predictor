package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AirportCodeSelector {
    private List<String> airportCodes;
    private Random random;

    public AirportCodeSelector() {
        airportCodes = new ArrayList<>();
        airportCodes.add("ATL");
        airportCodes.add("PEK");
        airportCodes.add("ORD");
        airportCodes.add("LHR");
        airportCodes.add("HND");
        airportCodes.add("LAX");
        airportCodes.add("CDG");
        airportCodes.add("DFW");
        airportCodes.add("FRA");
        airportCodes.add("DEN");
        airportCodes.add("HKG");
        airportCodes.add("MAD");
        airportCodes.add("DXB");
        airportCodes.add("JFK");
        airportCodes.add("AMS");
        airportCodes.add("CGK");
        airportCodes.add("BKK");
        airportCodes.add("SIN");
        airportCodes.add("CAN");
        airportCodes.add("PVG");

        random = new Random();
    }

    public AirportCodeSelector(List<String> airportCodes) {
        this.airportCodes = airportCodes;
        random = new Random();
    }

    public String selectAirportCodeExcept(List<String> codes) {
        int index = random.nextInt(airportCodes.size());

        String code = airportCodes.get(index);

        while(codes.contains(code)) {
            index = random.nextInt(airportCodes.size());
            code = airportCodes.get(index);
        }
        return code;
    }

    public boolean hasCode(String code) {
        return this.airportCodes.contains(code);
    }
}
