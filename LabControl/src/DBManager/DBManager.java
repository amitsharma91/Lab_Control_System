package DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	public static Connection con = null;
	public static ResultSet rs = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/labcontrol", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static ResultSet getResultSet(String query) {
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Connection c  = null;
		try{
			c = getConnection();
			System.out.println("Sucessfully Connected...");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
}