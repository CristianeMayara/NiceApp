package br.ufc.mdcc.nicempos.model.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDAO extends SQLiteOpenHelper {

	private SQLiteDatabase database;
	private final Context context;
	private static String DB_PATH = "";
	private static final String DB_NAME = "dbnicempos.db";
	private static final int DB_VERSION = 1;
	protected static final String DB_LOG = "DB";
	
	protected static final String PLACE_TABLE_NAME = "place";
	protected static final String PLACE_PLACEID = "place_placeid";
	protected static final String PLACE_NAME = "place_name";
	protected static final String PLACE_LAT = "place_lat";
	protected static final String PLACE_LON = "place_lon";
	protected static final String PLACE_IMAGEID = "place_imageid";
	
	protected static final String VOTE_TABLE_NAME = "vote";
	protected static final String VOTE_PLACEID = "vote_placeid";
	protected static final String VOTE_NVOTESRATING1 = "vote_nvotesrating1";
	protected static final String VOTE_NVOTESRATING2 = "vote_nvotesrating2";
	protected static final String VOTE_NVOTESRATING3 = "vote_nvotesrating3";
	protected static final String VOTE_NVOTESRATING4 = "vote_nvotesrating4";
	protected static final String VOTE_NVOTESRATING5 = "vote_nvotesrating5";
	protected static final String VOTE_NVOTESPLACE = "vote_nvotesplace";
	
	public BaseDAO(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		Log.v(DB_LOG, " " + DB_PATH);
		this.context = context;
		onCreate(database);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// executes create or copy of database
		try {
			this.createDatabase();
		} catch (IOException mIOException) {
			Log.e(DB_LOG, mIOException.toString()
					+ "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(DB_LOG, "Atualizando a versao: " + oldVersion
				+ " para " + newVersion
				+ ". Todos os registros serao removidos.");
		onCreate(db);
		
	}
	
	@Override
	public synchronized void close() {
		if (database != null)
			database.close();
		super.close();
	}
	
	private void createDatabase() throws IOException {
		// If database not exists copy it from the assets
		boolean databaseExist = checkDatabase();
		if (!databaseExist) {
			this.getReadableDatabase();
			this.close();
			try {
				// Copy the database from assests
				copyDatabase();
				Log.v(DB_LOG, "Database created");
			} catch (IOException mIOException) {
				throw new Error("ErrorCopyingDataBase");

			}
		}
	}
	
	// Open the database, so we can query it
	public boolean openDatabase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		database = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		return database != null;
	}
	
	// Check that the database exists here: /data/data/your package/databases/
	private boolean checkDatabase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		Log.v(DB_LOG, dbFile + "   " + dbFile.exists());
		return dbFile.exists();
	}
	
	// Copy the database from assets
	private void copyDatabase() throws IOException {
		Log.v(DB_LOG, "copying database from assets. Open:" + DB_NAME);
		InputStream input = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream output = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
		
		output.flush();
		output.close();
		input.close();
	}
	
	
	
	
}
