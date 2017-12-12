package com.heyzqt.bookcontentprovider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BookDataBaseHelper dataBaseHelper = BookDataBaseHelper.getInstance(this);
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		String insertStr = "INSERT INTO BOOK VALUES(null,\'Little Prince\',\'TYPEA001\')";
		database.execSQL(insertStr);

		Cursor cursor = database.query(BookDataBaseHelper.Tables.BOOK, null,
				null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					Log.i(TAG, "onCreate: " + cursor.getString(i));
				}
			}
		}
	}
}
