package backend;

public class Test {
	//decalare a new api instance, static for testing purposes only
	private static DBapi api = new DBapi();
	public static void main(String[] args){
		//test add the user daniel
//		System.out.println(api.makeUser("testuser233", "testpwd", "testname", "t", "12/21/1995", "student", 123456789));
		//test date of birth checker
//		System.out.println(api.checkDOB("01/21/1998"));
		//test add address to existing user
//		System.out.println(api.addUserAddress("testuser234", 10, 110, "4001 steeles", "TORONTO", "CAN", "M3N2T8"));
//		System.out.println(api.makeRenter("testuserq34", 122314123, 2323, "BOB DYLAN", "MC", "12/21"));
//		System.out.println(api.makeHost("testuser34"));
//		System.out.println(api.getDist(44.968046, -94.420307, 44.33328, -89.132008));
//		System.out.println(api.getAddrMapping());
//		double[] t = {50, 0.0};
//		System.out.println(api.getDist(43.7741600, 43.775107, -79.5241100, -79.525159));
//
//		System.out.println(api.getListingByLoc(43.7741600, -79.5241100, t));
		System.out.println(api.Login("test", "test2"));
	}
}
