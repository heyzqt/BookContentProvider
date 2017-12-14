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
	}

	public interface BookColumns {
		public static final String BOOK_ID = BaseColumns._ID;
		public static final String BOOK_NAME = "name";
		public static final String BOOK_NUMBER = "number";
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
		db.execSQL("CREATE TABLE " + Tables.BOOK + "(" + BookColumns.BOOK_ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT," +
				BookColumns.BOOK_NAME + " TEXT," +
				BookColumns.BOOK_NUMBER + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade: ");
	}
}
