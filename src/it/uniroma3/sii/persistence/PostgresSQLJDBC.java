package it.uniroma3.sii.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class PostgresSQLJDBC {
   public static void main( String args[] )
     {
       Connection c = null;
       Statement stmt = null;
       try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/tweets",
            "postgres", "password");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "CREATE TABLE TWEET_AND_EVENT " +
                      "(ID INT PRIMARY KEY     		NOT NULL," +
                      " TWITTER_USER   VARCHAR(50)    	NOT NULL, " +
                      " TWEET_TEXT     VARCHAR(250)	NOT NULL, " + 
                      " CLASSIFICATION   VARCHAR(250) ,"+ 
                      "SOGGETTO     VARCHAR(50)," +
                      "LUOGO      VARCHAR(50),"+ 
                      "DATA    VARCHAR(100)  )" ;
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
       } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
       }
       System.out.println("Table created successfully");
     }
}