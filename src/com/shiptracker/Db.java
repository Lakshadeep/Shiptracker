package com.shiptracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Db {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_HOTNESS = "latitude";
	public static final String KEY_HOTNESS2 = "longitude";
	
	
	private static final String DATABASE_NAME = "Shiptracker";
	private static final String DATABASE_TABLE = "startlocation";
	private static final int DATABASE_VERSION = 1;
	
	private   DbHelper ourHelper;
	private final Context ourContext;
	private   SQLiteDatabase ourDatabase;
	 
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null , DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE  " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY," + 
			KEY_NAME + " TEXT NOT NULL," + KEY_HOTNESS + " TEXT NOT NULL,"+ KEY_HOTNESS2 + " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
			onCreate(db);
			
		}
	}
		public Db(Context c) throws SQLException{
			ourContext = c;
		}
		
		public Db open(){
			ourHelper = new DbHelper(ourContext);
			ourDatabase = ourHelper.getWritableDatabase();
			return this;
		}
		
		public void close(){
			ourHelper.close();
		}

		public long createEntry(String name, String hotness,String hotness2) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_ROWID,1);
			cv.put(KEY_NAME, name);
			cv.put(KEY_HOTNESS, hotness);
			cv.put(KEY_HOTNESS2, hotness2);
			return ourDatabase.replace(DATABASE_TABLE, null,cv);
			//db.execSQL("Replace into " + DATABASE_TABLE +"("+KEY_ROWID+","+KEY_NAME+","+KEY_HOTNESS+","+KEY_HOTNESS2+")"+" values("+ 1 +","+name+","+hotness+","+hotness2+");");
			
			
		}                            

		public String getData() {
			// TODO Auto-generated method stub
			
			String[] columns = new String[]{KEY_ROWID,KEY_NAME,KEY_HOTNESS};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
			String result = "UR RESULT IS\n";
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_NAME);
			int iHotness = c.getColumnIndex(KEY_HOTNESS);
			
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
				result = result + c.getString(iRow)+" "+ c.getString(iName)+" "+c.getString(iHotness)+ "\n";
			}
			
			
			return result;
		}

		public String getName(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_NAME,KEY_HOTNESS,KEY_HOTNESS2};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String namedb = c.getString(1);
				
				return namedb;
			}
			
			
			return null;
		}
		public String getLatitude(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_NAME,KEY_HOTNESS,KEY_HOTNESS2};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String namedb = c.getString(2);
				
				return namedb;
			}
			
			
			return null;
		}
		public String getLongitude(long l) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{KEY_ROWID,KEY_NAME,KEY_HOTNESS,KEY_HOTNESS2};
			Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_ROWID+"="+l,null , null, null, null);
			
			if(c!=null){
				c.moveToFirst();
				String namedb = c.getString(3);
				
				return namedb;
			}
			
			
			return null;
		}
			
		
	
		

}
