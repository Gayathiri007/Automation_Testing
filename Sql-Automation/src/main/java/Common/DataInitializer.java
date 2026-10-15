package Common;

import dataHandler.exportData;
import dataHandler.searchData;

public class DataInitializer {

    public static searchData searchDataHandler;
    public static exportData exportDataHandler;

    public static void dataInitializer(){

        searchDataHandler = new searchData();

        exportDataHandler = new exportData();
    }
}
