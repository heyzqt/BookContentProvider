package com.heyzqt.bookcontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String CONTENT = "content://";

	private static final String AUTHORITY = "com.heyzqt.book";

	private Context mContext;

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
		Log.i(TAG, "onCreate: ");


		//insertSomeData();

//		queryAllBooks();
//		queryBookById(3);
//		queryBookByName("Sherlock Holmes");
//		queryBookByType("TYPEA001");
//		queryBookByUriID(6);
//		queryBookByUriName("The Sorrows of Young Werther");

		queryAllBooks();
		deleteBookById();
	}

	private void deleteBookById() {
		int deleteId = getContentResolver().delete(BookConstract.Book.CONTENT_URI,
				BookConstract.Book._ID + " = ?",
				new String[]{"1"});
		if (deleteId > 0) {
			Log.i(TAG, "deleteBookById: delete book" + deleteId);
			queryAllBooks();
		}
	}


	private void insertSomeData() {
		BookDataBaseHelper dataBaseHelper = BookDataBaseHelper.getInstance(mContext);
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		String insertStr1 = "INSERT INTO BOOK VALUES(null,\'Little Prince\',\'TYPEA001\',1)";
		String insertStr2 = "INSERT INTO BOOK VALUES(null,\'The Sorrows of Young Werther\',"
				+ "\'TYPEB001\',2)";
		String insertStr3 = "INSERT INTO BOOK VALUES(null,\'Harry Porter\',\'TYPEA002\',3)";
		String insertStr4 = "INSERT INTO BOOK VALUES(null,\'Sherlock Holmes\',\'TYPEC001\',1)";
		database.execSQL(insertStr1);
		database.execSQL(insertStr2);
		database.execSQL(insertStr3);
		database.execSQL(insertStr4);
	}

	private void queryAllBooks() {
		Cursor cursor = getContentResolver().query(BookConstract.Book.CONTENT_URI,
				null,
				null,
				null,
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void queryBookById(int id) {
		Cursor cursor = getContentResolver().query(BookConstract.Book.CONTENT_URI,
				null,
				BookConstract.Book._ID + " = ?",
				new String[]{id + ""},
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void queryBookByUriID(int id) {
		Uri uri = Uri.withAppendedPath(BookConstract.Book.CONTENT_URI, id + "");
		Cursor cursor = getContentResolver().query(uri,
				null,
				null,
				null,
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void queryBookByUriName(String name) {
		Uri tempuri = Uri.withAppendedPath(BookConstract.Book.CONTENT_URI, "name");
		Uri uri = Uri.withAppendedPath(tempuri, name);
		Cursor cursor = getContentResolver().query(uri,
				null,
				null,
				null,
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void queryBookByName(String name) {
		Cursor cursor = getContentResolver().query(BookConstract.Book.CONTENT_URI,
				null,
				BookConstract.Name.NAME + " = ?",
				new String[]{name},
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void queryBookByType(String type) {
		Cursor cursor = getContentResolver().query(BookConstract.Book.CONTENT_URI,
				null,
				BookConstract.Type.TYPE + " = ?",
				new String[]{type},
				null);
		if (cursor != null) {
			showCursor(cursor);
			cursor.close();
		}
	}

	private void showCursor(Cursor cursor) {
		try {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						sb.append(cursor.getString(i) + ",");
					}
					Log.i(TAG, "" + sb);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.i(TAG, "queryBookById: NullPointerException");
		}
	}
}