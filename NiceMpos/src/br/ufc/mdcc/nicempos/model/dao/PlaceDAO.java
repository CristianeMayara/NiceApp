package br.ufc.mdcc.nicempos.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.ufc.mdcc.nicempos.model.vo.Place;

public class PlaceDAO {
	
	private SQLiteDatabase db;
	private BaseDAO baseDao;
	
	private String[] columns = {
		BaseDAO.PLACE_PLACEID,
		BaseDAO.PLACE_NAME,
		BaseDAO.PLACE_LAT,
		BaseDAO.PLACE_LON,
		BaseDAO.PLACE_IMAGEID
	};
	
	public PlaceDAO(Context context) {
		baseDao = new BaseDAO(context);
	}
	
	public void open() throws SQLException {
		baseDao.openDatabase();
		db = baseDao.getWritableDatabase();
	}
	
	public void close() {
		baseDao.close();
	}
	
	public void insert(Place place) {
		ContentValues values = new ContentValues();
		values.put(BaseDAO.PLACE_PLACEID, place.getId());
		values.put(BaseDAO.PLACE_NAME, place.getName());
		values.put(BaseDAO.PLACE_LAT, place.getLat());
		values.put(BaseDAO.PLACE_LON, place.getLon());
		values.put(BaseDAO.PLACE_IMAGEID, place.getImageId());
		
		open();
		
		long id = db.insert(BaseDAO.PLACE_TABLE_NAME, null, values);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + id + "] record was inserted.");
	}
	
	public void search(int id) {
		String where = BaseDAO.PLACE_PLACEID + "=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		
		open();
		
		Cursor c = db.query(BaseDAO.PLACE_TABLE_NAME, columns, where,
				whereArgs, null, null, null);
		
		c.moveToFirst();
		
		int idFound = c.getInt(c.getColumnIndex(BaseDAO.PLACE_PLACEID));
		
		c.close();
		close();
		
		Log.i(BaseDAO.DB_LOG, "" + idFound);
	}
	
	public void update(Place place) {
		String where = BaseDAO.PLACE_PLACEID + "=?";
		String[] whereArgs = new String[] { String.valueOf(place.getId()) };
		
		ContentValues values = new ContentValues();
		values.put(BaseDAO.PLACE_NAME, place.getName());
		values.put(BaseDAO.PLACE_LAT, place.getLat());
		values.put(BaseDAO.PLACE_LON, place.getLon());
		values.put(BaseDAO.PLACE_IMAGEID, place.getImageId());
		
		open();
		
		int count = db.update(BaseDAO.PLACE_TABLE_NAME, values, where, whereArgs);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been updated.");
		Log.i(BaseDAO.DB_LOG, "[" + place.getId() + "] record was updated.");
	}
	
	public void delete(Place place) {
		String where = BaseDAO.PLACE_PLACEID + "=?";
		String[] whereArgs = new String[] { String.valueOf(place.getId()) };
		
		open();
		
		int count = db.delete(BaseDAO.PLACE_TABLE_NAME, where, whereArgs);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been deleted.");
	}
	
	public void list() {
		open();
		
		Cursor c = db.query(BaseDAO.PLACE_TABLE_NAME, columns, null, null,
				null, null, null, null);
		
		if(c.moveToFirst()){
			do{
				Log.i(BaseDAO.DB_LOG,
						"place_placeid: "
						+ c.getString(c.getColumnIndex(BaseDAO.PLACE_PLACEID))
						+ " place_name: "
						+ c.getString(c.getColumnIndex(BaseDAO.PLACE_NAME)));
				
			} while(c.moveToNext());
		}
		
		c.close();
		close();
	}

}
