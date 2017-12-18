package com.heyzqt.bookcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
				BookColumns.TYPE + " TEXT);");

		//create mimetypes table
		db.execSQL("CREATE TABLE " + Tables.MIMETYPES + "(" + MimetypesColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
				MimetypesColumns.MIMETYPE + " TEXT);");

		//insert 2 datas to table mimetypes
		db.execSQL("INSERT INTO " + Tables.MIMETYPES + " VALUES(null"
				+ ",\'vnd.android.cursor.item/name\')");
		db.execSQL("INSERT INTO " + Tables.MIMETYPES + " VALUES(null"
				+ ",\'vnd.android.cursor.item/type\')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade: ");
	}
}
