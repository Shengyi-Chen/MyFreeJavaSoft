package me.shengyi.albumpreview;

/*
 * OPT_ID	NAME	VALUE
 * 1		G_W		from 0-3	Offset: 3
 * 2		G_H		from 0-7	Offset: 3
 * 3		HAS_TITLE	Y or N
 * 4		HAS_DATE	Y or N
 * 5		HAS_NAME	Y or N
 * 6		DB_ST	DB, FS or PO	; DB = database, FS = filesystem, PO = preview only 
 * */

public enum OPT_NAME {	
		G_W("G_W"),
		G_H("G_H"),
		HAS_TITLE("HAS_TITLE"),
		HAS_DATE("HAS_DATE"),
		HAS_NAME("HAS_NAME"),
		DB_ST("DB_ST")
		;
		
		private String optName;
		
		
		private OPT_NAME(String optName) {
			this.optName = optName;
		}
		
		@Override
		public String toString(){
			return optName;
		}
}

