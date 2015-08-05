package me.shengyi.albumpreview;

public enum OPT_BYTE {
	/*
	 * GW 10 00 00 00 00
	 * GH 11 00 00 00 00
	 * HAS_TITLE 11 10 00 00
	 * HAS_DATE  11 10 00 01
	 * HAS_NAME  11 10 00 11
	 * DB_ST	 11 11 00 00
	 * FS_ST     11 11 00 01
	 * PO_ST     11 11 00 11
	 * */
	G_W(128),
	G_H(192),
	HAS_TITLE("HAS_TITLE"),
	HAS_DATE("HAS_DATE"),
	HAS_NAME("HAS_NAME"),
	DB_ST("DB_ST")
	;
	
	private int optName;
	
	
	private OPT_BYTE(int optName) {
		this.optName = optName;
	}	
}
