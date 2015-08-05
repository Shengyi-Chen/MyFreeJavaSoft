package me.shengyi.albumpreview;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class ImagePreviewMatrix {
	private int matrixWidth;
	private int matrixHeight;
	//Picture width from 256px - 512px (3-6)
	private File[] inputImages;
	private File outputPreview;
	private String dbAlbum;
	private String dbAlbumConnectionStr;
	private String dbATable = ""
			+ "CREATE TABLE ALBUMN"
			+ "(PIC_ID INT PRIMARY KEY     NOT NULL,"
			+ "NAME	TEXT    NOT NULL, "
			+ "OFFSET	LONG    NOT NULL, "
			+ "SIZE	LONG NOT NULL,"
			+ "SOURCE VBINARY NOT NULL"
			+ ");";
	private ArrayList<Integer> rowInfo;
	private SettingsDatabase cfgDb; //0 for DB; 1 for FS; 2 for PO
	private String imageInfo;
	
	public ImagePreviewMatrix(File[] inputFiles, String pathAlbum){
		inputImages = inputFiles;
		cfgDb = new SettingsDatabase();
		matrixHeight = (int) cfgDb.getCfgData().get(OPT_NAME.G_H.ordinal()).getValue();
		matrixWidth = (int) cfgDb.getCfgData().get(OPT_NAME.G_W.ordinal()).getValue();
		
		outputPreview = new File(pathAlbum.replaceAll(".albump",".db"));
		if (!outputPreview.exists()){
			if (((String)cfgDb.getCfgData().get(OPT_NAME.DB_ST.ordinal()).getValue()).equals("DB")
					|| ((String)cfgDb.getCfgData().get(OPT_NAME.DB_ST.ordinal()).getValue()).equals("FS")){
				dbAlbumConnectionStr = "jdbc:sqlite:" + outputPreview.getAbsolutePath();
				initialAlbumnDB();
			}
			else {
				try {
					outputPreview.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}		
	}
	
	private void initialAlbumnDB(){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(dbAlbumConnectionStr);      
	      c.setAutoCommit(false);
	      
	      stmt = c.createStatement();	      
	      stmt.executeUpdate(dbATable);
	      c.commit();	     
	      
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );	      
	    }		
	}
	
	
}
