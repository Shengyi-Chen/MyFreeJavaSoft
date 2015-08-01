package me.shengyi.albumpreview;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;



public class SettingsDatabase {
	private File dbFile = new File("abpv.cfg");
	private String dbConnectionString = "jdbc:sqlite:" + dbFile.getName();
	private static String createDataTableSQL =  
			"CREATE TABLE ABPRECFG " +
            "(OPT_ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL, " + 
            " VALUE          CHAR(4)     NOT NULL)";
	private ArrayList<SimpleEntry<String, Object>> cfgData = new ArrayList<SimpleEntry<String, Object>>();
	
	/*
	 * OPT_ID	NAME	VALUE
	 * 1		G_W		from 0-3	Offset: 3
	 * 2		G_H		from 0-7	Offset: 3
	 * 3		HAS_TITLE	Y or N
	 * 4		HAS_DATE	Y or N
	 * 5		HAS_NAME	Y or N
	 * 6		DB_ST	DB, FS or PO	; DB = database, FS = filesystem, PO = preview only 
	 * */
	public SettingsDatabase(){
		if (dbFile.exists()){
			//TODO: load settings
			loadDatabase();
		}
		else
		{
			initialDataBase();
			loadDatabase();
		}
	}
	
	private void initialDataBase(){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(dbConnectionString);      
	      c.setAutoCommit(false);
	      
	      stmt = c.createStatement();	      
	      stmt.executeUpdate(createDataTableSQL);
	      c.commit();
	      /*
  		 * OPT_ID	NAME	VALUE
  		 * 1		G_W		from 0-3	Offset: 3 Default:0
  		 * 2		G_H		from 0-7	Offset: 3 Default:0
  		 * 3		HAS_TITLE	Y or N Default:N
  		 * 4		HAS_DATE	Y or N Default:N
  		 * 5		HAS_NAME	Y or N Default:N
  		 * 6		DB_ST	DB, FS or PO	; DB = database (Default), FS = filesystem, PO = preview only 
  		 * */
	      String sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
	                   "VALUES ("+ OPT_NAME.G_W.ordinal() + ", '" + OPT_NAME.G_W +"', '0');"; 
	      
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
                  "VALUES ("+ OPT_NAME.G_H.ordinal() + ", '" + OPT_NAME.G_H +"', '0');";
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
                  "VALUES ("+ OPT_NAME.HAS_TITLE.ordinal() + ", '" + OPT_NAME.HAS_TITLE +"', 'N');";
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
                  "VALUES ("+ OPT_NAME.HAS_DATE.ordinal() + ", '" + OPT_NAME.HAS_DATE +"', 'N');";
	      stmt.executeUpdate(sql);
	      
	      sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
                  "VALUES ("+ OPT_NAME.HAS_NAME.ordinal() + ", '" + OPT_NAME.HAS_NAME +"', 'N');";
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO ABPRECFG (OPT_ID,NAME,VALUE) " +
                  "VALUES ("+ OPT_NAME.DB_ST.ordinal() + ", '" + OPT_NAME.DB_ST +"', 'DB');";
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );	      
	    }
	}
	
	private void loadDatabase(){
		Connection c = null;
	    Statement stmt = null;
	    //SimpleEntry<String, Object> keyPair = new SimpleEntry<String, Object>(null);
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(dbConnectionString);
	      c.setAutoCommit(false);
	      
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM ABPRECFG;" );
	      while ( rs.next() ) {
	    	  /*
	    		 * OPT_ID	NAME	VALUE
	    		 * 1		G_W		from 0-3	Offset: 3 Default:0
	    		 * 2		G_H		from 0-7	Offset: 3 Default:0
	    		 * 3		HAS_TITLE	Y or N Default:N
	    		 * 4		HAS_DATE	Y or N Default:N
	    		 * 5		HAS_NAME	Y or N Default:N
	    		 * 6		DB_ST	DB, FS or PO	; DB = database (Default), FS = filesystem, PO = preview only 
	    		 * */
	    	  String name = rs.getString("NAME");
	    	  String value = rs.getString("VALUE");
	    	  
	    	  switch (value){
	    	  case "Y":
	    		  cfgData.add(new SimpleEntry<String, Object>(name, true));
	    		  break;
	    	  case "N":
	    		  cfgData.add(new SimpleEntry<String, Object>(name, false));
	    		  break;
	    	  case "0":
	    	  case "1":
	    	  case "2":
	    	  case "3":
	    	  case "4":
	    	  case "5":
	    	  case "6":
	    	  case "7":
	    		  cfgData.add(new SimpleEntry<String, Object>(name, Integer.parseInt(value) + 3));
	    		  break;
	    		  default:
	    			  cfgData.add(new SimpleEntry<String, Object>(name, value));
	    			  break;
	    	  }    	  
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }	    
	}
	
	public boolean saveData(OPT_NAME name, Object value){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(dbConnectionString);
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "SELECT * FROM ABPRECFG;";
	      switch(name){
	      case G_W:	    	  
	      case G_H:
	    	  sql = "UPDATE ABPRECFG set VALUE = '" + String.valueOf((int)value - 3) + "' where OPT_ID=" + name.ordinal() +";";
	    	  break;
	      case HAS_TITLE:	    	  
	      case HAS_DATE:	    	 
	      case HAS_NAME:
	    	  if ((boolean)value){
	    		  sql = "UPDATE ABPRECFG set VALUE = 'Y' where OPT_ID=" + name.ordinal() +";";	    		  
	    		  }
	    	  else{
	    		  sql = "UPDATE ABPRECFG set VALUE = 'N' where OPT_ID=" + name.ordinal() +";";
	    	  }
	    	  break;
	      case DB_ST:	    	 
	    		  sql = "UPDATE ABPRECFG set VALUE = '"+ value +"' where OPT_ID=" + name.ordinal() +";";	    	 
	    	  break;
	    	  default:
	      }
	      
	      stmt.executeUpdate(sql);
	      c.commit();
	      
	      stmt.close();
	      c.close();
	      return true;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
		return false;
	}

	public ArrayList<SimpleEntry<String, Object>> getCfgData() {
		return cfgData;
	}
	
}
