package dataHandler;

import Config.AppConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class exportData {

    Map<String, String> exportDataMap;

    public int exportDataCount() {
        String filePath = AppConfig.exportPath; // CSV file path from configuration
        int rowCount = 0; // Initialize row count

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Read the first line (header)

            if (headerLine != null) {
                String[] columns = headerLine.split(","); // Split columns by comma

                // Find the index of "Offerings(Service Name)" column
                int columnIndex = -1;
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].trim().equalsIgnoreCase("Offerings(Service Name)")) {
                        columnIndex = i;
                        break;
                    }
                }

                if (columnIndex == -1) {
                    System.out.println("\"Offerings(Service Name)\" column NOT found in the file.");
                    return rowCount; // Return zero if column not found
                }

                String line;
                while ((line = br.readLine()) != null) { // Read remaining lines
                    String[] row = line.split(","); // Split row by comma
                    if (row.length > columnIndex && !row[columnIndex].trim().isEmpty()) {
                        rowCount++; // Increment count for non-empty cells
                    }
                }
            } else {
                System.out.println("File is empty or could not be read.");
            }
        } catch (IOException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }

        return rowCount; // Return the total row count
    }
}


