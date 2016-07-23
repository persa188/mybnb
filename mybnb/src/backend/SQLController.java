package backend;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.ArrayList;

/*
 * This class acts as the medium between our CommandLine interface
 * and the SQL Backend. It is a controller class.
 */
public class SQLController {
	
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/";
    //Object that establishes and keeps the state of our application's
    //connection with the MySQL backend.
	private Connection conn = null;
    //Object which communicates with the SQL backend delivering to it the
    //desired query from our application and returning the results of this
    //execution the same way that are received from the SQL backend.
	private Statement st = null;
	
    // Initialize current instance of this class.
	public boolean connect(String[] cred) throws ClassNotFoundException {
		Class.forName(dbClassName);
		boolean success = true;
		String user = cred[0];
		String pass = cred[1];
		String connection = CONNECTION + cred[2];
		try {
			conn = DriverManager.getConnection(connection, user, pass);
			st = conn.createStatement();
		} catch (SQLException e) {
			success = false;
			System.err.println("Connection could not be established!");
			e.printStackTrace();
		}
		return success;
	}

    // Destroy the private objects/fields of current instance of this class.
    // Acts like a destructor.
	public void disconnect() {
		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Exception occured while disconnecting!");
			e.printStackTrace();
		} finally {
			st = null;
			conn = null;
		}
	}
	
    // Controls the execution of functionality: "3. Print schema."
	public ArrayList<String> getSchema() {
		ArrayList<String> output = new ArrayList<String>();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet schemas = meta.getTables(null,null,"%",null);
			//ResultSet catalogs = meta.getCatalogs();
			while (schemas.next()) {
				output.add(schemas.getString("TABLE_NAME"));
			}
			schemas.close();
		} catch (SQLException e) {
			System.err.println("Retrieval of Schema Info failed!");
			e.printStackTrace();
			output.clear();
		}
		return output;
	}
	
    // Controls the execution of functionality: "4. Print table schema."
	public ArrayList<String> colSchema(String tableName) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getColumns(null, null, tableName, null);
			while(rs.next()) {
				result.add(rs.getString(4));
				result.add(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Retrieval of Table Info failed!");
			e.printStackTrace();
			result.clear();
		}
		return result;
	}
	
    //Controls the execution of a select query.
    //Functionality: "2. Select a record."
	public void selectOp(String query) {
		try {
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during Select execution!");
			e.printStackTrace();
		}
		System.out.println();
	}
	
    //Controls the execution of an insert query.
    //Functionality: "1. Insert a record."
	public int insertOp(String query) {
		int rows = 0; 
		try {
			rows = st.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println("Exception triggered during Insert execution!");
			e.printStackTrace();
		}
		return rows;
	}

}