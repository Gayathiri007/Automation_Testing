package dataHandler;

import Config.AppConfig;
import utilities.ExcelUtils;

import java.util.Map;

public class searchData {

    Map<String, String> searchDataMap;


    public searchData(){

        searchDataMap = ExcelUtils.getTestDataFromRow(AppConfig.workBookPath,"Sheet1","Read");

        username = searchDataMap.get("Username");

        password = searchDataMap.get("Password");

        filterCriteria = searchDataMap.get("FilterCriteria");
    }



    private String username;

    private String password;

    private String filterCriteria;

    public String getUsername(){
        return  username;
    }

    public String getPassword(){
        return password;
    }

    public String getFilterCriteria() { return filterCriteria; }


}
