package backend;

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
		
		
}
