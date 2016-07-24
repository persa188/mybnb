package backend;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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
	private static final String[] cred = {USER, PASS, "mybnb?useSSL=false"};
	private SQLController ctrlr;
	public DBapi() {
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
		try {
			//query
			String q = "INSERT INTO `mybnb`.`accounts` (`user`, `pwd`, `fname`, `lname`, `dob`, `occ` ,`sin`)"
					 + " VALUES ('"+uname+"', '"
					 + pw + "', '"
					 + fname + "', '"
					 + lname + "', '"
					 + dob + "', '"
					 + occ + "', '"
					 + SIN + "');";
			ctrlr.connect(cred);
			ctrlr.insertOp(q);
			ctrlr.disconnect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public boolean addUserAddress(String uname, float lat, float lng, String addr,
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
			//close conn and stmt
			stmt.close();
			conn.close();
			return makeAddr(lat, lng, addr, city, ctry, pcode);
		} catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}
	

	public boolean makeRenter(String uname, int cardno, int verno, String name,
			String type, String exp){
		//Establish connection
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//make the user account
			String mkacc = "INSERT INTO `mybnb`.`renter` (`user`)"
						 + " VALUES ('"+uname+"');";
			stmt.execute(mkacc);
			
			//close conn and stmt
			stmt.close();
			conn.close();
			return addCCard(uname, cardno, verno, name, type, exp);

		} catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

	/**
	 * makes user with username uname a host
	 * @param uname
	 * @return
	 */
	public boolean makeHost(String uname){
			try {
				ctrlr.connect(cred);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String q = "INSERT INTO `mybnb`.`host` (`user`)"
						 + " VALUES ('"+uname+"');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
		return true;
	}
	
	/**
	 * adds credit card to user with username uname
	 * @param uname
	 * @param cardno
	 * @param verno
	 * @param name
	 * @param type
	 * @param exp
	 * @return
	 */
	public boolean addCCard(String uname, int cardno, int verno, String name,
			String type, String exp){
	
			try {
				ctrlr.connect(cred);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String q = "INSERT INTO `mybnb`.`has` (`user`, `cardno`)"
					+ "VALUES('"
					+ uname +"','"
					+ cardno + "');";
			ctrlr.insertOp(q);
			q = "INSERT INTO `mybnb`.`creditcard` (`cardno`, `verno`, `fullname`, `type`, `exp`)" 
					+ "VALUES ('"+ cardno + "', '"
					+ verno + "', '"
					+ name + "', '"
					+ type + "', '"
					+ exp + "');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
	
		return true;
	}
	
	/**
	 * creates a listing
	 * @param id
	 * @param type
	 * @param ch
	 * @param price
	 * @param lat
	 * @param lng
	 * @param addr
	 * @param city
	 * @param ctry
	 * @param pcode
	 * @return
	 */
	public boolean makeListing(int id, String type, String ch, int price, int lat, int lng,
			String addr, String city, String ctry, String pcode){
		try {
			ctrlr.connect(cred);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		//make listing
		String s = "INSERT INTO `mybnb`.`listing` (`id`, `type`, `characteristics`, `rentalPrice`)"
				+ "VALUES ('"+ id + "', '"
				+ type + "', '"
				+ ch + "', '"
				+ price + "');";
		ctrlr.insertOp(s);
		
		//add addr entry
		s = "INSERT INTO `mybnb`.`located` (`lid`, `long`, `lat`)"
				+ "VALUES ('"+ id + "', '"
				+ lng + "', '"
				+ lat + "');";
		ctrlr.insertOp(s);
		ctrlr.disconnect();
		return true;
	}
	
	/**
	 * adds entry to address table
	 * @param lat
	 * @param lng
	 * @param addr
	 * @param city
	 * @param ctry
	 * @param pcode
	 * @return
	 */
	private boolean makeAddr(float lat, float lng, String addr, String city, String ctry, String pcode){
		//Establish connection

		try {
			ctrlr.connect(cred);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//address
		String s = "INSERT INTO `mybnb`.`address` (`lat`, `long`, `addr`, `city`, `country`, `pcode`)"
					  + " VALUES ('"
					  + lat + "', '"
					  + lng + "', '"
					  + addr + "', '"
					  + city + "', '"
					  + ctry + "', '"
					  + pcode + "');";
		ctrlr.insertOp(s);
		ctrlr.disconnect();
		return true;
	}
	
	/**
	 * sets user with username uname to host of listing with listing_id.
	 * @param uname
	 * @param listing_id
	 * @return
	 */
	public boolean hostListing(String uname, int listing_id){
			
			try {
				ctrlr.connect(cred);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//hosts table, will fail if user not host
			String s = "INSERT INTO `mybnb`.`hosts` (`user`, `lid`)"
					  + " VALUES ('"
					  + uname + "', '"
					  + listing_id + "');";
			ctrlr.insertOp(s);
			ctrlr.disconnect();
		return true;
	}
	
	/**
	 * adds availability to a listing
	 * @param listing_id
	 * @param a
	 * @return
	 */
	public boolean addListingAvailability(int listing_id, String[] a){
		return false;
	}
	
	/**
	 * adds ammenity with id aid to listing with listing_id
	 * @param listing_id
	 * @param aid
	 * @return
	 */
	public boolean addListingAmmenities(int listing_id, int aid){
		return true;
	}
	
	/**
	 * creats an ammenity
	 * @param id
	 * @param name
	 * @param descr
	 * @return
	 */
	public boolean createAmmenity(int id, String name, String descr){
		return false;
	}
	
	/**
	 * leave feedback
	 * @param lid
	 * @param type
	 * @param rating
	 * @param comment
	 * @return
	 */
	public boolean leaveFeedBack(int lid, String type, int rating, int comment){
		/*type = renter2host, host2renter, renter2listing*/
		return false;
	}
	
	/**
	 * get all feedback for user
	 * @return
	 */
	public boolean getUserFeedback(String uname){
		/*
		 * x = SELECT lid from rents where rents.uname = user.uname
		 * y = SELECT lid from hosts where hosts.uname = user.uname
		 * take results from x and do somthing like
		 * SELECT * From Feedback where lid={somthing from x} and type = host2renter
		 * similarly for the host feedback except type=renter2host
		 * */
		return false;
	}
	
	/**
	 * get all feedback for listing
	 * @param lid
	 * @return
	 */
	public boolean getListingFeedback(int lid){
		return false;
	}
	
	/**
	 * returns listings within specifc distance from position at (lat, lng)
	 * kwargs[0] is the distance value in km, set kwargs[1] to 0 if you would
	 * like distance to be returned in ascending order, or 1 for descending order.
	 * @param lat
	 * @param lng
	 * @param kwargs
	 * @return
	 */
	public List<Integer> getListingByLoc(double lat, double lng, double [] kwargs){
		List<List<Double>> addrtable= getAddrMapping();
		List<Integer> res = new ArrayList<>();
		List<List<Double>> valid = new ArrayList<>();
		double dist = kwargs[0];
		int order = (int)kwargs[1];
		
		//get all the valid entries
		for (List<Double> listing: addrtable){
			double d = getDist(lat, listing.get(1), lng, listing.get(2));
			if (d <= dist && valid.isEmpty()){
				ArrayList<Double> temp = new ArrayList<>();
				temp.add(listing.get(0)); temp.add(d);
				valid.add(temp);
			} else if (d <= dist){
				ArrayList<Double> temp = new ArrayList<>();
				temp.add(listing.get(0)); temp.add(d);
				int index = valid.size();
				for (List <Double> l: valid){
					if (l.get(1) > d){
						index = valid.indexOf(l);
						break;
					}
				}
				if (index == valid.size()){
					valid.add(temp);
				} else {
					valid.add(index, temp);
				}
			}
		}
		
		//make the res list
		for (List<Double> l: valid) {
			if (order == 0){
				res.add(l.get(0).intValue());
			} else {
				res.add(0, l.get(0).intValue());
			}
		}
		
		return res;
	}
	
	/**
	 * returns dist b/n two points on globe
	 * @param d lat1
	 * @param e lat2
	 * @param f lng1
	 * @param g lng2
	 * @return
	 */
	public double getDist(double lat1, double lat2, double lng1, double lng2){
		double R = 6371e3;
		double dlat = lat2 - lat1;
		double dlong = lng2 - lng1;
		double a = Math.pow((Math.sin(dlat/2)), 2) + Math.cos(lat2) * Math.cos(lat1)
				* Math.pow(Math.sin(dlong/2),2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return c * 6371;
	}
	
	/**
	 * gets table "located" from db, that stores listing id, lat, lng
	 * remember to cast listing id to int if you're trying to use it as
	 * it is casted to double in order to store it
	 * @return List<List<double lid, double lat, double lng>>
	 */
	public List<List<Double>> getAddrMapping(){
		//Establish connection
		ArrayList<List<Double>> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM located";
			
			ResultSet rs = stmt.executeQuery(q);
			while (rs.next()) {
				ArrayList<Double> temp = new ArrayList<Double>();
				double lid = rs.getInt("lid");
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("long");
				temp.add(lid); temp.add(lat); temp.add(lng);
				res.add(temp);
			}
			
			//close conn and stmt
			stmt.close();
			conn.close();

		} catch (SQLException e){
			System.out.println(e);
			return res;
		}
		return res;
	}
}
