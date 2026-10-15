package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVUtils {
	public static Connection con;
	public static Statement stmt;
	public static ResultSet rSet;
	
	public static void initConnection() {
		
		try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Connection con = DriverManager.getConnection("jdbc:relique:csv:" + "testData");
			stmt = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static ResultSet getResultSetFromSQL(String sql) {
		
		ResultSet rSet = null;
		
		try {
			
			stmt.executeQuery(sql);
			rSet = stmt.getResultSet();
		}catch (Exception e) {
			
		}
		return rSet;
	}
	
}
