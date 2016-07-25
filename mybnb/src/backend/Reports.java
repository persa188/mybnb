package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Reports {
	//stuff for connecting to database
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/mybnb?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "1234";
	private static final String[] cred = {USER, PASS, "mybnb?useSSL=false"};
	private SQLController ctrlr;

	public Reports() {
		//Register JDBC driver
		try {
			Class.forName(dbClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctrlr = new SQLController();
	}
	
	/**
	 * gets all cities
	 * @return
	 */
	public List<String> getAllCities(String d1, String d2){
		List<String> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			String q = "SELECT city FROM address NATURAL JOIN located NATURAL JOIN rents WHERE "
					+ "sdate >= '" + d1 + "' and edate <= '"+d2 + "';";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				res.add(rs.getString("city"));
			}
			return res;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * prints report to console
	 */
	public void getNumBookingsByCity(String date1, String date2){
		List<String> cities = getAllCities(date1, date2);
		System.out.println("CITY : # of Bookings");
		for (String  city: cities){
			System.out.println(city + " : " + getNumBookingsInCity(city));
		}
		System.out.println("END OF REPORT");
	}
	
	/**
	 * gets num bookings in this city
	 * @param city
	 * @return
	 */
	private int getNumBookingsInCity(String city){
		int res = 0;
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
//			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
//			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT COUNT(*) as total FROM rents NATURAL JOIN located NATURAL JOIN address WHERE city='"+city+"';";
			ResultSet rs = stmt.executeQuery(q);
			while (rs.next()){
				res = rs.getInt("total");
			}
			return res;
		} catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}
	
	/**
	 * gets all postal codes
	 * @param date1 in form yyyy-mm-dd
	 * @return
	 */
	public List<String> getAllPCodes(String date1, String date2){
		List<String> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			String q = "SELECT pcode FROM address NATURAL JOIN located NATURAL JOIN rents WHERE "
					+ "rents.sdate >= '" +date1+ "' and rents.edate <= '"+date2 +"';";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				res.add(rs.getString("pcode"));
			}
			return res;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * prints report to console
	 * @param date1 yyyy-MM-dd
	 * @param date2 yyyy-MM-dd
	 */
	public void getNumBookingsByPCode(String date1, String date2){
		List<String> pcodes = getAllPCodes(date1, date2);
		System.out.println(pcodes);
		System.out.println("Postal Code : # of Bookings");
		for (String  pcode: pcodes){
			System.out.println(pcode + " : " + getNumBookingsPCode(pcode));
		}
		System.out.println("END OF REPORT");
	}
	
	/**
	 * gets num bookings in this city
	 * @param city
	 * @return
	 */
	private int getNumBookingsPCode(String pcode){
		int res = 0;
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
//			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
//			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT COUNT(*) as total FROM rents NATURAL JOIN located NATURAL JOIN address WHERE pcode='"+pcode+"';";
			ResultSet rs = stmt.executeQuery(q);
			while (rs.next()){
				res = rs.getInt("total");
			}
			return res;
		} catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}
	
	public void getNumListingsPerCountry(){
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
//			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
//			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			System.out.println("COUNTRY:TOTAL");
			String q = "SELECT country, COUNT(country) as total FROM located NATURAL JOIN address GROUP BY country;";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				System.out.println(rs.getString("country")+ " : " + rs.getInt("total"));
			}
			System.out.println("END OF REPORT");
		}catch (Exception e){
			return;
		}
	}

	public void getNumListingsPerCountryAndCity() {
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
//			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
//			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			System.out.println("COUNTRY:CITY:TOTAL");
			String q = "SELECT country, city, COUNT(city) as total FROM located NATURAL JOIN address GROUP BY city;";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				System.out.println(rs.getString("country")+ " : " + rs.getString("city")+ " : " + rs.getInt("total"));
			}
			System.out.println("END OF REPORT");
		}catch (Exception e){
			return;
		}
	}
	
	public void getNumListingsPerCountryAndCityAndPostal() {
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
//			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
//			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			System.out.println("COUNTRY:CITY:POSTALCODE:TOTAL");
			String q = "SELECT country, city, pcode, COUNT(pcode) as total FROM located NATURAL JOIN address GROUP BY pcode;";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				System.out.println(rs.getString("country")+ " : " + rs.getString("city") + " : " + rs.getString("pcode")+ " : "+ rs.getInt("total"));
			}
			System.out.println("END OF REPORT");
		}catch (Exception e){
			return;
		}
	}
}
