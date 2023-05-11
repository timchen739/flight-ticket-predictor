package util;

import java.io.FileWriter;
import java.io.IOException;

public class CsvFileHelper {
    public static void createFile(String fileName, String[][] dataSource) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        String[] headers = {"Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price"};
        String[] row1 = {"John Doe", "25", "New York"};

        for(int i=0; i < headers.length; i++) {
            String header = headers[i];
            writer.append(header);
            if(i < headers.length-1) {
                writer.append(",");
            }
        }
        writer.append("\n");

        // Write the rows
        for(String[] row: dataSource) {
            writer.append(String.join(",", row));
            writer.append("\n");
        }

        writer.flush();
        writer.close();
    }
}
