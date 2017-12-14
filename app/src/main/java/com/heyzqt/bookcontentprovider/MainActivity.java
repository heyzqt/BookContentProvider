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

		findAllBooks();
	}

	private void insertSomeData(){
		BookDataBaseHelper dataBaseHelper = BookDataBaseHelper.getInstance(mContext);
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		String insertStr1 = "INSERT INTO BOOK VALUES(null,\'Little Prince\',\'TYPEA001\')";
		String insertStr2 = "INSERT INTO BOOK VALUES(null,\'The Sorrows of Young Werther\',"
				+ "\'TYPEB001\')";
		String insertStr3 = "INSERT INTO BOOK VALUES(null,\'Harry Porter\',\'TYPEA002\')";
		String insertStr4 = "INSERT INTO BOOK VALUES(null,\'Sherlock Holmes\',\'TYPEC001\')";
		database.execSQL(insertStr1);
		database.execSQL(insertStr2);
		database.execSQL(insertStr3);
		database.execSQL(insertStr4);
	}

	private void findAllBooks() {
		Uri uri = Uri.parse(MainActivity.CONTENT + MainActivity.AUTHORITY);
		uri = Uri.withAppendedPath(uri, "book");
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					sb.append(cursor.getString(i) + ",");
				}
				Log.i(TAG, "" + sb);
			}
		}
		cursor.close();
	}
}