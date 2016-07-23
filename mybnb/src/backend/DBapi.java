package backend;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * backend API
 * @author Daniel
 *
 */
public class DBapi {
	
	//stuff for connecting to database
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/mybnb?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "1234";
	
	public DBapi() {
		//Register JDBC driver
		try {
			Class.forName(dbClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * makes a user
	 * @param uname
	 * @param pw
	 * @param fname
	 * @param lname
	 * @param dob date of birth in form MM/dd/yyyy
	 * @param occ
	 * @param SIN
	 * @return
	 */
	public boolean makeUser(String uname, String pw, String fname, String lname, String dob, String occ,
			int SIN){
		if (!checkDOB(dob)){ //check if age > 18
			return false;
		}
		//Establish connection
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//make the user account
			String mkacc = "INSERT INTO `mybnb`.`accounts` (`user`, `pwd`, `fname`, `lname`, `dob`, `occ` ,`sin`)"
						 + " VALUES ('"+uname+"', '"
						 + pw + "', '"
						 + fname + "', '"
						 + lname + "', '"
						 + dob + "', '"
						 + occ + "', '"
						 + SIN + "');";
			stmt.execute(mkacc);
			
			//close conn and stmt
			stmt.close();
			conn.close();
		} catch (SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * checks if age >= 18, given date of birth
	 * @param dob String Date of Birth in form MM/dd/yyyy
	 * @return
	 */
	public boolean checkDOB(String dob) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date d = df.parse(dob);
			Date c = new Date();
			long diff = Math.abs(c.getTime() - d.getTime());
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diffDays < 18*365){
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * adds/updates the address of a user
	 * @param uname
	 * @param lat
	 * @param lng
	 * @param addr
	 * @param city
	 * @param ctry
	 * @param pcode
	 * @return
	 */
	public boolean addAddress(String uname, double lat, double lng, String addr,
			String city, String ctry, String pcode){
		//Establish connection
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//add the address entry in lives
			String mkentry = "INSERT INTO `mybnb`.`lives` (`user`, `lat`, `long`)"
						 + " VALUES ('"+uname+"', '"+ lat + "', '" + lng + "');";
			
			try {
				stmt.execute(mkentry);
			} catch (SQLException e) {
				/*entry exists, user can only live at one address though
				so we will overwrite the prev address value*/
				mkentry = "UPDATE `mybnb`.`lives` SET `lat`=" + lat + "," 
						+ "`long`=" + lng
						+ "WHERE `user`='" + uname +"';";
				stmt.execute(mkentry);
			}
			//add the actual address
			String mkaddr = "INSERT INTO `mybnb`.`address` (`lat`, `long`, `addr`, `city`, `country`, `pcode`)"
						  + " VALUES ('"
						  + lat + "', '"
						  + lng + "', '"
						  + addr + "', '"
						  + city + "', '"
						  + ctry + "', '"
						  + pcode + "');";
			try {
				stmt.execute(mkaddr);
			} catch (SQLException e){
//				System.out.println(e); //the address already exists, we dont care though..
			}
			//close conn and stmt
			stmt.close();
			conn.close();
		} catch (SQLException e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	
	
}