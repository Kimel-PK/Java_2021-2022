// Autor: .𝕋𝕨𝕖𝕝𝕧𝕖𝕎𝕖𝕥

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tester {
	
	final static String url = "jdbc:mysql://localhost:3306/java_12";
	final static String user = "root";
	final static String password ="";
	
	public static Connection getConnection() throws SQLException{
		
		try{
			Class.forName ("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
			System.out.println("Connected!");
			return con;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	   
		return null;
	}

	public static void main(String[] args) throws SQLException {
		
		PrzechowywaczObiektow p = new PrzechowywaczObiektow();
		p.setConnection (getConnection ());
		
		for(int i = 0; i < 6; i++){
			
			Cos cos = new Cos(i);
			p.save(i % 2 + 1, cos);
		}
		
		for(int i = 1; i < 7; i++){
			Cos cos1 = (Cos) p.read(i).get();
			System.out.println(cos1.val);
		}
		
		try {
			Cos cos1 = (Cos) p.read(30).get();
		} catch (Exception e) {
			System.out.println("Obiekt nie istnieje");
		}
		
	}
}

class Cos implements Serializable {
	int val;
	
	public Cos(int val){
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
}