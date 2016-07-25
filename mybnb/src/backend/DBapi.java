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
			String mkentry = "INSERT INTO `mybnb`.`lives` (`user`, `lat`, `lng`)"
						 + " VALUES ('"+uname+"', '"+ lat + "', '" + lng + "');";
			
			try {
				stmt.execute(mkentry);
			} catch (SQLException e) {
				/*entry exists, user can only live at one address though
				so we will overwrite the prev address value*/
				mkentry = "UPDATE `mybnb`.`lives` SET `lat`=" + lat + "," 
						+ "`lng`=" + lng
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
	public boolean makeListing(int id, String type, String ch, double price, double lat, double lng,
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
		s = "INSERT INTO `mybnb`.`located` (`lid`, `lng`, `lat`)"
				+ "VALUES ('"+ id + "', '"
				+ lng + "', '"
				+ lat + "');";
		ctrlr.insertOp(s);
		ctrlr.disconnect();
		
		makeAddr(lat, lng, addr, city, ctry, pcode);
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
	public boolean makeAddr(double lat, double lng, String addr, String city, String ctry, String pcode){
		//Establish connection

		try {
			ctrlr.connect(cred);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//address
		String s = "INSERT INTO `mybnb`.`address` (`lat`, `lng`, `addr`, `city`, `country`, `pcode`)"
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
	 * @param start_date start date of listing availability in form MM/dd/yyyy
	 * @param end_date the end date of listing availability in form MM/dd/yyyy
	 * @return
	 */
	public boolean addListingAvailability(int listing_id, String start_date, String end_date){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//convert to sql supported date form
			Date d = df.parse(start_date);
			Date c = df.parse(end_date);
			String s = sf.format(d); //start date
			String e = sf.format(c); //end date
			
			//insert to sql db
			ctrlr.connect(cred);
			String q = "INSERT INTO `mybnb`.`availability` (`lid`, `sdate`, `edate`)"
					  + " VALUES ('"
					  + listing_id + "', '"
					  + s + "', '"
					  + e + "');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
		}catch (Exception e){
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * adds ammenity with id aid to listing with listing_id
	 * @param listing_id
	 * @param aid
	 * @return
	 */
	public boolean addListingAmmenities(int listing_id, int aid){
		try {
			ctrlr.connect(cred);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try{
			String q = "INSERT INTO offers(id, aid) VALUES('"
				+listing_id+"', '"+aid+"');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
			return true;
		} catch (Exception e){System.out.println("insertion fail");return false;}
	}
	
	/**
	 * creats an ammenity
	 * @param id
	 * @param name
	 * @param descr
	 * @return
	 */
	public boolean createAmmenity(int id, String name, String descr){
		try{
			ctrlr.connect(cred);
			String q = "INSERT INTO ammenities(id, name, description) VALUES('"
					+ id +"', '"
					+ name +"', '"
					+ descr +"');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
			return true;
		}catch (Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * leave feedback
	 * @param lid listing id
	 * @param type renter2host, host2renter, renter2listing
	 * @param rating *mandatory
	 * @param comment *mandatory
	 * @return
	 */
	public boolean makeFeedBack(int lid, String type, int rating, String comment){
		/*type = renter2host, host2renter, renter2listing*/
		//insert op and set type
		try {
			ctrlr.connect(cred);
			String q = "INSERT INTO feedback(type, lid, rating, comment) VALUES('"
					+ type +"', '"
					+ lid +"', '"
					+ rating + "', '"
					+ comment +"');";
			ctrlr.insertOp(q);
			ctrlr.disconnect();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return false;
		}
	}
	
	public boolean leaveUserFeedback(String user1, String user2, int fid){
		try {
			ctrlr.connect(cred);
			String q = "INSERT INTO gives(user, fid) VALUES('"+user2+"', '"+fid+"');";
			ctrlr.insertOp(q);
			q = "INSERT INTO receives(user, fid) VALUES('"+user1+"', '"+fid+"');";
			ctrlr.disconnect();
			return true;
		} catch (Exception e){
			System.out.println(e);
			return false;
		}
	}

	/**
	 * get all feedback for user
	 * @return list of feedback id's for use in feedback table
	 */
	public List<Integer> getUserFeedback(String uname){
		ArrayList<Integer> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM receives WHERE ruser='"+uname+"';";
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				res.add(rs.getInt("fid"));
			}
			return res;
		} catch (Exception e){
			System.out.println(e);
			return new ArrayList<>();
		}
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
		
		//get all the valid entries, could be more efficient but runs in polynomial time so meh
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
	 * A function to calculate distance between two points on earth.
	 * @param lat1 latitude of first point
	 * @param lat2 latitude of second point
	 * @param lng1 longitude of second point
	 * @param lng2 longitude of second point
	 * @return distance in KM between (lat1, lng1) and (lat2, lng2)
	 */
	public double getDist(double lat1, double lat2, double lng1, double lng2){
		/*The Following code is basically the haversine formula converted to java code*/
		double dlat = lat2 - lat1;
		double dlong = lng2 - lng1;
		double a = Math.pow((Math.sin(dlat/2)), 2) + Math.cos(lat2) * Math.cos(lat1)
				* Math.pow(Math.sin(dlong/2),2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return c * 6373; /*6371 is earths radius in KM, use 6373 to account for margin of err.
		                   that occurs due to he fact that earth isnt a perfect sphere during rotation*/
	}
	
	/**
	 * gets table "located" from db, that stores listing id, lat, lng
	 * remember to cast listing id to int if you're trying to use it as
	 * it is casted to double in order to store it
	 * @return List<List<double lid, double lat, double lng>> `mybnb`.`located` table
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
				double lng = rs.getDouble("lng");
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
	
	
	/**
	 * function that checks database for login credentials.
	 * @param uname username
	 * @param pwd password
	 * @return true iff uname:pwd are valid login credentials, false o/w
	 */
	public boolean Login(String uname, String pwd){
		
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM accounts;";
			ResultSet r  = stmt.executeQuery(q);
			
			//not the most efficient way, however COUNT() was buggy so..
			while(r.next()){

				if (r.getString("user").equals(uname) && r.getString("pwd").equals(pwd)){
					//close conn and stmt & return
					stmt.close();
					conn.close();
					return true;
				} 
			}
			//close conn and stmt
			stmt.close();
			conn.close();
			return false;
		} catch (SQLException e){
			System.out.println(e); //kept for debugging purposes
			return false;
		}
		
	}
	
	private Date SQLStrtoDate(String d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dte;
		try {
			dte = sdf.parse(d);
			return dte;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * attempts to rent the listing given username, listing id, start date sdate, and end date edate
	 * @param uname
	 * @param listing_id
	 * @param sdate
	 * @param edate
	 * @return
	 */
	public boolean rentListing(String uname, int listing_id, String sdate, String edate){
		//check that sdate > sdate from listing and edate < edate from listing
		//then check each person renting doesnt conflict with you
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM availability";
			ResultSet rs  = stmt.executeQuery(q);
			
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); // for converting to date
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd"); //for inserting to table
			Date s2;
			Date e2;
			try {
				s2 = df.parse(sdate);
				e2 = df.parse(edate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			//not check if availabilty for listing is OK!
			boolean avail = false;
			while (rs.next()){
				Date s1 = SQLStrtoDate(rs.getString("sdate"));
				Date e1 = SQLStrtoDate(rs.getString("edate"));
				System.out.println(s1 + ":"+ s2);
				if ((s1.before(s2)
						|| s1.equals(s2))
						&& (e1.after(e2)
								|| e2.equals(e1))
						&& rs.getInt("lid") == listing_id){
					avail = true;
				}
			}
						
			//check against other renters
			q = "SELECT * FROM rents";
			rs = stmt.executeQuery(q);
			while(rs.next()){
				Date s1 = SQLStrtoDate(rs.getString("sdate"));
				Date e1 = SQLStrtoDate(rs.getString("edate"));
				if ( (rs.getInt("lid") == listing_id)
						&& !(
								(e1.before(s2) && e1.before(s2))) || //their booking is before theirs
								(e2.before(e1) && e2.before(s1)) //your booking is before yours
								){
					avail = false;
					break;
				}
			}

			//close conn and stmt
			stmt.close();
			conn.close();
			//if available add to rents
			if (avail){
				sdate = df2.format(s2);
				edate = df2.format(e2);
				q = "INSERT INTO `mybnb`.rents (`ruser`,`lid`, `sdate`, `edate`)"
						  + " VALUES ('"
						  + uname + "', '"
						  + listing_id + "', '"
						  + sdate + "', '"
						  + edate + "');";
				ctrlr.connect(cred);
				ctrlr.insertOp(q);
				ctrlr.disconnect();
				return true;
			}else {
				return false;
			}

		} catch (SQLException e){
			System.out.println(e); //kept for debugging purposes
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @param opt 0 iff ascending, 1 for descending
	 * @return list of listing id's
	 */
	public List<Integer> getListingByPrice(int opt){
		List<Integer> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT id FROM listing ORDER BY rentalPrice";
			if (opt==1){
				q += " DESC;";
			}else { q += ";";}

			ResultSet rs = stmt.executeQuery(q);
			
			while (rs.next()){
				res.add(rs.getInt("id"));
			}
			rs.close();
			conn.close();
			return res;
		} catch (Exception e){
			System.out.println(e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * 
	 * @param postal_code
	 * @return returns a list of listing id's with similar postal codes
	 */
	public List<Integer> getListingByPostalCode(String postal_code){
		List<Integer> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM mybnb.located NATURAL JOIN mybnb.address WHERE address.pcode LIKE '"+postal_code.substring(0, 3)+"%%%'";

			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				res.add(rs.getInt("lid"));
			}
			rs.close();
			conn.close();
			return res;
		} catch (Exception e){
			System.out.println(e);
			return res;
		}
	}
	
	/**
	 * 
	 * @param s start date in form yyyy-MM-dd
	 * @param e end date in form yyyy-MM-dd
	 * @return
	 */
	public List<Integer> filterListingsByDateRange(String s, String e){
		List<Integer> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			String q = "SELECT * FROM mybnb.availability WHERE sdate <= '"+s
					+"' and edate >= '"+e+"';";
			
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()){
				res.add(rs.getInt("lid"));
			}
			return res;
		} catch (Exception ex) {
			System.out.println(ex);
			return res;
		}
	}
	
	/**
	 * applies a date range filter to a list of listing id's
	 * @param l_ids
	 * @param s start date in form yyyy-MM-dd
	 * @param e start date in form yyyy-MM-dd
	 * @return list of listing id's with that satisfy filter
	 */
	public List<Integer> filterListingsByDateRange(List<Integer> l_ids, String s, String e){
		List<Integer> res = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			String q;
			for(Integer lid: l_ids){
				q = "SELECT lid FROM mybnb.availability WHERE sdate <= '"+s
					+"' and edate >= '"+e+"' and lid='"+ lid+"';";
			
				rs = stmt.executeQuery(q);
				if (rs.next()){
					res.add(lid);
				}
			}
			rs.close();
			conn.close();
			return res;
		} catch (Exception ex){
			System.out.println(ex);
			ResultSet rs;
			return new ArrayList<>();
		}
	}
	
	/**
	 * gets a listing by address if listing exists
	 * @param addr address to search for (street addr)
	 * @param city the city
	 * @param ctry the country
	 * @param pcode the postal code
	 * @return id of listing or null if no such listing
	 */
	public Integer getListing(String addr, String city, String ctry, String pcode){
		try {
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			
			//query
			String q = "SELECT * FROM address WHERE city='"
					+city+"' and country='" + ctry + "' and pcode='"
					+ pcode + "' and addr='" + addr +"';";
			ResultSet rs  = stmt.executeQuery(q);
			double lat = Double.NaN;
			double lng = Double.NaN;
			while (rs.next()){
				lat = rs.getDouble("lat");
				lng = rs.getDouble("lng");
			}
			if (lat == Double.NaN || lng == Double.NaN) {
				return null;
			}
			
			//get lid
			q = "SELECT lid FROM located JOIN listing WHERE lng=" +lat +" and lat="+lng+";";
			rs = stmt.executeQuery(q);
			while(rs.next()){
				return rs.getInt("lid");
			} return null;
			
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
