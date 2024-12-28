package main.java.io;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final String COMMA_DELIMITER = ",";
    
    public List<String[]> readCSV(Path filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            // Skip header
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                // Split on comma, but handle possible commas within quoted fields
                String[] values = line.split(COMMA_DELIMITER);
                // Trim whitespace and quotes from values
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replace("\"", "").trim();
                }
                records.add(values);
            }
        }
        
        return records;
    }
}
