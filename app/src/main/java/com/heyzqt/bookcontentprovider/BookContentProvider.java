package com.heyzqt.bookcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by heyzqt on 12/12/2017.
 */

public class BookContentProvider extends ContentProvider {

	private static final int BOOK = 1;
	private static final int BOOK_ID = 2;
	private static final int BOOK_NAME = 3;
	private static final int BOOK_NUMBER = 4;

	private static final String AUTHORITY = "com.heyzqt.book";

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/book";

	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/book";

	private static final UriMatcher mBookMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	private BookDataBaseHelper mHelper;

	private static final String TAG = "BookContentProvider";

	static {
		mBookMatcher.addURI(AUTHORITY, "book", BOOK);
		mBookMatcher.addURI(AUTHORITY, "book/#", BOOK_ID);
		mBookMatcher.addURI(AUTHORITY, "book/*", BOOK_NAME);
		mBookMatcher.addURI(AUTHORITY, "book/#/number", BOOK_NUMBER);
	}

	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate: ");
		Context context = getContext();
		mHelper = BookDataBaseHelper.getInstance(context);
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
			selection,
			@Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Log.i(TAG, "query: ");
		final SQLiteDatabase db = mHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		final int match = mBookMatcher.match(uri);
		switch (match) {
			case BOOK:
				String table = uri.getPathSegments().get(0);
				return db.query(table, projection, selection,
						selectionArgs, null, null, sortOrder);
			case BOOK_ID:
				List<String> pathSegments = uri.getPathSegments();
				qb.setTables(pathSegments.get(0));
				return qb.query(db, projection, selection, selectionArgs,
						null,
						null,
						sortOrder);
		}
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		Log.i(TAG, "getType: ");
		int match = mBookMatcher.match(uri);
		switch (match) {
			case BOOK:
				return BookContentProvider.CONTENT_TYPE;
			case BOOK_ID:
			case BOOK_NAME:
			case BOOK_NUMBER:
				return BookContentProvider.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Log.i(TAG, "insert: ");
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection,
			@Nullable String[] selectionArgs) {
		Log.i(TAG, "delete: ");
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
			@Nullable String[] selectionArgs) {
		Log.i(TAG, "update: ");
		return 0;
	}

	public BookDataBaseHelper getBookDataBaseHelper() {
		return mHelper;
	}
}
