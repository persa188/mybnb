package backend;

import java.util.List;

public class HostToolkit {
	//stuff for connecting to database
		private static final String dbClassName = "com.mysql.jdbc.Driver";
		private static final String CONNECTION = "jdbc:mysql://127.0.0.1/mybnb?useSSL=false";
		private static final String USER = "root";
		private static final String PASS = "";
		private static final String[] cred = {USER, PASS, "mybnb?useSSL=false"};
		private SQLController ctrlr;

		public HostToolkit() {
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
		 * given a list of amenities suggest addons to incr price
		 * @param a
		 */
		public void suggestAmmenities(List<String> a){
			try {
				ctrlr.connect(cred);
				String q = "SELECT name, vmod FROM ammenities WHERE ";
				for (String l: a){
					q += "name != '"+l+"' and ";
				}
				q = q.substring(0, q.length() - 4) + " ORDER BY vmod DESC;";
				System.out.println("Here are a list of ammenities that you can try to add to your listing");
				System.out.println("They are in form ammenity name : additional price per hr");
				ctrlr.selectOp(q);
				ctrlr.disconnect();
				System.out.println("END OF REPORT");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
		
		
}
