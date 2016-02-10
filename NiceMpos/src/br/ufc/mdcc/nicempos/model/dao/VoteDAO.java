package br.ufc.mdcc.nicempos.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.ufc.mdcc.nicempos.model.vo.Vote;

public class VoteDAO {
	
	private SQLiteDatabase db;
	private BaseDAO baseDao;
	
	private String[] columns = {
		BaseDAO.VOTE_PLACEID,
		BaseDAO.VOTE_NVOTESRATING1,
		BaseDAO.VOTE_NVOTESRATING2,
		BaseDAO.VOTE_NVOTESRATING3,
		BaseDAO.VOTE_NVOTESRATING4,
		BaseDAO.VOTE_NVOTESRATING5,
		BaseDAO.VOTE_NVOTESPLACE
	};
	
	public VoteDAO(Context context) {
		baseDao = new BaseDAO(context);
	}
	
	public void open() throws SQLException {
		baseDao.openDatabase();
		db = baseDao.getWritableDatabase();
	}
	
	public void close() {
		baseDao.close();
	}
	
	public void insert(Vote vote) {
		ContentValues values = new ContentValues();
		values.put(BaseDAO.VOTE_PLACEID, vote.getPlaceId());
		values.put(BaseDAO.VOTE_NVOTESRATING1, vote.getNVotesRating1());
		values.put(BaseDAO.VOTE_NVOTESRATING2, vote.getNVotesRating2());
		values.put(BaseDAO.VOTE_NVOTESRATING3, vote.getNVotesRating3());
		values.put(BaseDAO.VOTE_NVOTESRATING4, vote.getNVotesRating4());
		values.put(BaseDAO.VOTE_NVOTESRATING5, vote.getNVotesRating5());
		values.put(BaseDAO.VOTE_NVOTESPLACE, vote.getNVotesPlace());
		
		open();
		
		long id = db.insert(BaseDAO.VOTE_TABLE_NAME, null, values);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + id + "] record was inserted.");
	}
	
	public Vote search(int id) {
		Vote vote;
		
		open();
		
		Cursor c = db.query(BaseDAO.VOTE_TABLE_NAME, columns, 
				BaseDAO.VOTE_PLACEID + "=" + id, null, null, null, null);
		
		c.moveToFirst();
		
		int idFound = c.getInt(c.getColumnIndex(BaseDAO.VOTE_PLACEID));
		
		vote = new Vote (
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_PLACEID)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING1)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING2)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING3)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING4)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING5)),
				c.getInt(c.getColumnIndex(BaseDAO.VOTE_NVOTESPLACE))
				);
		
		c.close();
		close();
		
		Log.i(BaseDAO.DB_LOG, "" + idFound);
		return vote;
	}
	
	public void update(Vote vote) {
		ContentValues values = new ContentValues();
		values.put(BaseDAO.VOTE_PLACEID, vote.getPlaceId());
		values.put(BaseDAO.VOTE_NVOTESRATING1, vote.getNVotesRating1());
		values.put(BaseDAO.VOTE_NVOTESRATING2, vote.getNVotesRating2());
		values.put(BaseDAO.VOTE_NVOTESRATING3, vote.getNVotesRating3());
		values.put(BaseDAO.VOTE_NVOTESRATING4, vote.getNVotesRating4());
		values.put(BaseDAO.VOTE_NVOTESRATING5, vote.getNVotesRating5());
		values.put(BaseDAO.VOTE_NVOTESPLACE, vote.getNVotesPlace());
		
		open();
		
		int count = db.update(BaseDAO.VOTE_TABLE_NAME, values, 
				BaseDAO.VOTE_PLACEID + "=" + vote.getPlaceId(), null);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been updated.");
		Log.i(BaseDAO.DB_LOG, "[" + vote.getPlaceId() + "] record was updated.");
	}
	
	public void delete(Vote vote) {
		String where = BaseDAO.VOTE_PLACEID + "=?";
		String[] whereArgs = new String[] { String.valueOf(vote.getPlaceId()) };
		
		open();
		
		int count = db.delete(BaseDAO.VOTE_TABLE_NAME, where, whereArgs);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been deleted.");
	}
	
	public void list() {
		open();
		
		Cursor c = db.query(BaseDAO.VOTE_TABLE_NAME, columns, null, null,
				null, null, null, null);
		
		if(c.moveToFirst()){
			do{
				Log.i(BaseDAO.DB_LOG,
						"vote_placeid: "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_PLACEID))
						//+ " vote_nvotesrating1: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING1))
						//+ " vote_nvotesrating2: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING2))
						//+ " vote_nvotesrating3: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING3))
						//+ " vote_nvotesrating4: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING4))
						//+ " vote_nvotesrating5: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESRATING5))
						//+ " vote_nvotesplace: "
						+ " | "
						+ c.getString(c.getColumnIndex(BaseDAO.VOTE_NVOTESPLACE)));
				
			} while(c.moveToNext());
		}
		
		c.close();
		close();
	}

}
