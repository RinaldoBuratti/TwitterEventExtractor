package it.uniroma3.sii.classifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataReader {
	public static ArrayList<String> getData(String table_name){
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tweets",
					"postgres", "password");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT tweet_text FROM " + table_name;
			rs = stmt.executeQuery(sql);

			while(rs.next()){
				tweets.add(rs.getString(1));
			}
			stmt.close();
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		return tweets;
	}
}
