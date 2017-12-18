package com.heyzqt.bookcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by heyzqt on 12/12/2017.
 */

public class BookDataBaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "books.db";

	public interface Tables {
		public static final String BOOK = "book";
		public static final String MIMETYPES = "mimetypes";
	}

	public interface BookColumns {
		public static final String _ID = BaseColumns._ID;
		public static final String NAME = "name";
		public static final String TYPE = "type";
		public static final String MIMETYPE_ID = "mimetype_id";
	}

	public interface MimetypesColumns {
		public static final String _ID = BaseColumns._ID;
		public static final String MIMETYPE = "mimetype";
	}

	private static BookDataBaseHelper mSingleton = null;

	private static final String TAG = "BookDataBaseHelper";

	public static synchronized BookDataBaseHelper getInstance(Context context) {
		if (mSingleton == null) {
			mSingleton = new BookDataBaseHelper(context.getApplicationContext(), DATABASE_NAME,
					null,
					DATABASE_VERSION);
		}
		return mSingleton;
	}

	private BookDataBaseHelper(Context context, String name,
			SQLiteDatabase.CursorFactory cursorFactory,
			int version) {
		super(context, name, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "onCreate: ");
		//create book table
		db.execSQL("CREATE TABLE " + Tables.BOOK + "(" + BookColumns._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT," +
				BookColumns.NAME + " TEXT," +
				BookColumns.TYPE + " TEXT," +
				BookColumns.MIMETYPE_ID + " TEXT);");

		//create mimetypes table
		db.execSQL("CREATE TABLE " + Tables.MIMETYPES + "(" + MimetypesColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
				MimetypesColumns.MIMETYPE + " TEXT);");

		//insert some datas to mimetypes table
		db.execSQL("INSERT INTO " + Tables.MIMETYPES + " VALUES(null"
				+ ",\'vnd.android.cursor.item/book\')");
		db.execSQL("INSERT INTO " + Tables.MIMETYPES + " VALUES(null"
				+ ",\'vnd.android.cursor.item/name\')");
		db.execSQL("INSERT INTO " + Tables.MIMETYPES + " VALUES(null"
				+ ",\'vnd.android.cursor.item/type\')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade: ");
	}

	public long getMimeTypeIdForName() {
		return lookupMimeTypeId(getWritableDatabase(), BookConstract.Name.CONTENT_ITEM_TYPE);
	}

	private long lookupMimeTypeId(SQLiteDatabase db, String mimeType) {
		long id = -1;
		String sql = "SELECT " + MimetypesColumns._ID + " FROM " + Tables.MIMETYPES + " WHERE "
				+ "" + MimetypesColumns.MIMETYPE + " = ?";

		id = queryIdWithOneArg(db, sql, mimeType);

		if (id < 0) {
			Log.e(TAG, "Mimetype " + mimeType + " not found in the MIMETYPES table");
		}
		return id;
	}

	static long queryIdWithOneArg(SQLiteDatabase db, String sql, String sqlArgument) {
		final SQLiteStatement sqLiteStatement = db.compileStatement(sql);
		try {
			bindString(sqLiteStatement, 1, sqlArgument);
			try {
				return sqLiteStatement.simpleQueryForLong();
			} catch (SQLiteException notFouns) {
				return -1;
			}
		} finally {
			sqLiteStatement.close();
		}
	}

	private static void bindString(SQLiteStatement stmt, int index, String value) {
		if (value == null) {
			stmt.bindNull(index);
		} else {
			stmt.bindString(index, value);
		}
	}
}
