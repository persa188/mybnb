package backend;

import java.util.ArrayList;
import java.util.List;

public class Test {
	//decalare a new api instance, static for testing purposes only
	private static DBapi api = new DBapi();
	private static Reports r = new Reports();
	public static void main(String[] args){
		//test add the user daniel
//		System.out.println(api.makeUser("testuser233", "testpwd", "testname", "t", "12/21/1995", "student", 123456789));
		//test date of birth checker
//		System.out.println(api.checkDOB("01/21/1998"));
		//test add address to existing user
//		System.out.println(api.addUserAddress("testuser2343", 140, 110, "4001 steeles", "TORONTO", "CAN", "M3N2T8"));
//		System.out.println(api.makeRenter("testuserq34", 122314123, 2323, "BOB DYLAN", "MC", "12/21"));
//		System.out.println(api.makeHost("testuser34"));
//		System.out.println(api.getDist(44.968046, -94.420307, 44.33328, -89.132008));
//		System.out.println(api.getAddrMapping());
//		double[] t = {50, 0.0};
//		System.out.println(api.getDist(43.7741600, 43.775107, -79.5241100, -79.525159));
//
//		System.out.println(api.getListingByLoc(43.7741600, -79.5241100, t));
//		System.out.println(api.Login("test", "test2"));
//		System.out.println(api.rentListing("test2", 1, "12-12-1994", "12-21-1995"));
//		System.out.println(api.getListing("1", "1", "1", "1"));
//		System.out.println(api.makeListing(222, "apt", "nice", 14564.3, 123 , 121.222, "FM<L", "TEST","TSET", "123"));
//		System.out.println(api.getListingByPostalCode("M3N2T8"));
//		System.out.println(api.filterListingsByDateRange("2012-12-12", "2013-12-12"));
//		List<Integer> l = new ArrayList<>();l.add(1); l.add(2); l.add(3);
//		System.out.println(api.filterListingsByDateRange(l, "2012-12-12" , "2013-12-12"));
//		System.out.println(api.getListingByPrice(0));
//		System.out.println(api.getListingByPrice(1));
//		System.out.println(api.addListingAmmenities(1, 1));
//		System.out.println(api.makeFeedBack(111, "user2host", 5, "CSADFD"));
//		System.out.println(api.createAmmenity(100, "TEST", "TSET"));
//		System.out.println(api.getUserFeedback("tetuser"));
//		r.getNumBookingsByCity("1900-12-21", "2040-12-21");
//		r.getNumBookingsByPCode("1900-12-21", "2040-12-21");
////		r.getNumListingsPerCountry();
//		r.getNumListingsPerCountryAndCity();
//		r.getNumListingsPerCountryAndCityAndPostal();
	}
}
