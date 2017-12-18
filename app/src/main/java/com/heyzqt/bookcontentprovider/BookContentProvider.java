package com.heyzqt.bookcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
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
	private static final int BOOK_TYPE = 4;

	private static final String AUTHORITY = "com.heyzqt.book";

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/book";

	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/book";

	private static final UriMatcher mBookMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	private BookDataBaseHelper mHelper;

	private static final String TAG = "BookContentProvider";

	static {
		mBookMatcher.addURI(AUTHORITY, "book", BOOK);
		mBookMatcher.addURI(AUTHORITY, "book/#", BOOK_ID);
		mBookMatcher.addURI(AUTHORITY, "book/name/*", BOOK_NAME);
		mBookMatcher.addURI(AUTHORITY, "book/type/*", BOOK_TYPE);
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
		final SQLiteDatabase db = mHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		final int match = mBookMatcher.match(uri);
		Log.i(TAG, "query: match = " + match);
		switch (match) {
			case BOOK:
				String table = uri.getPathSegments().get(0);
				qb.setTables(table);
				break;
			case BOOK_ID:
				long bookId = ContentUris.parseId(uri);
				table = uri.getPathSegments().get(0);
				qb.setTables(table);
				selectionArgs = insertSelectionArg(selectionArgs, String.valueOf(bookId));
				qb.appendWhere(BookConstract.Book._ID + " = ?");
				break;
			case BOOK_NAME:
				List<String> pathSegments = uri.getPathSegments();
				final String mimeTypeIsNameExpression = BookDataBaseHelper.BookColumns
						.MIMETYPE_ID + " = " + mHelper.getMimeTypeIdForName();
				table = pathSegments.get(0);
				qb.setTables(table);

				qb.appendWhere("1 AND " + mimeTypeIsNameExpression);
				if ("name".equals(pathSegments.get(1)) && pathSegments.get(2) != null) {
					qb.appendWhere(" AND " + BookConstract.Name.NAME + " = "
							+ '\'' + pathSegments.get(2) + '\'');
				}
				break;
		}
		return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
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
			case BOOK_TYPE:
				return BookContentProvider.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Log.i(TAG, "insert: ");
		final SQLiteDatabase db = mHelper.getWritableDatabase();
		long id = 0;

		final int match = mBookMatcher.match(uri);
		Log.i(TAG, "insert: match = " + match);
		switch (match) {
			case BOOK:
				id = db.insert(BookDataBaseHelper.Tables.BOOK,
						BookConstract.Book._ID,
						values);
				break;
			case BOOK_ID:
				break;
			case BOOK_NAME:
				break;
		}

		if (id < 0) {
			return null;
		}
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection,
			@Nullable String[] selectionArgs) {
		int result = -1;
		final SQLiteDatabase db = mHelper.getWritableDatabase();

		final int match = mBookMatcher.match(uri);
		Log.i(TAG, "delete: match = " + match);
		switch (match) {
			case BOOK:
				result = db.delete(BookDataBaseHelper.Tables.BOOK, selection, selectionArgs);
				break;
			case BOOK_ID:
				long bookId = ContentUris.parseId(uri);
				String[] newSelectionArgs = insertSelectionArg(selectionArgs,
						String.valueOf(bookId));
				StringBuilder sb = new StringBuilder();
				String newSelection = "";
				if (selection == null || "".equals(selection)) {
					newSelection = BookConstract.Book._ID + " = ?";
					sb.append(newSelection);
				} else {
					newSelection = " AND " + BookConstract.Book._ID + " = ?";
					sb.append(selection);
					sb.append(newSelection);
				}

				result = db.delete(BookDataBaseHelper.Tables.BOOK, sb.toString(),
						newSelectionArgs);
				break;
			case BOOK_NAME:
				result = -1;
				List<String> pathSegments = uri.getPathSegments();
				final String mimeTypeIsNameExpression =
						BookDataBaseHelper.BookColumns.MIMETYPE_ID + " = "
								+ mHelper.getMimeTypeIdForName();

				String[] selectionWithNameArgs = null;
				if (pathSegments.get(2) != null || !"".equals(pathSegments.get(2))) {
					selectionWithNameArgs = insertSelectionArg(selectionArgs, pathSegments
							.get(2));
				} else {
					selectionWithNameArgs = selectionArgs;
				}

				StringBuilder nameSb = new StringBuilder();
				String nameSelection = "";
				if (selection == null || "".equals(selection)) {
					nameSelection = BookConstract.Name.NAME + " = ?";
					nameSb.append(nameSelection);
				} else {
					nameSelection = " AND " + BookConstract.Name.NAME + " = ?";
					nameSb.append(selection);
					nameSb.append(nameSelection);
				}

				result = db.delete(BookDataBaseHelper.Tables.BOOK, nameSb.toString(),
						selectionWithNameArgs);
				break;
		}
		return result;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
			@Nullable String[] selectionArgs) {
		final SQLiteDatabase db = mHelper.getWritableDatabase();
		int result = -1;

		final int match = mBookMatcher.match(uri);
		Log.i(TAG, "update: match = " + match);
		switch (match) {
			case BOOK:
				result = db.update(BookDataBaseHelper.Tables.BOOK, values, selection,
						selectionArgs);
				break;
		}
		return result;
	}

	public BookDataBaseHelper getBookDataBaseHelper() {
		return mHelper;
	}

	private String[] insertSelectionArg(String[] args, String str) {
		if (args == null) {
			return new String[]{str};
		}

		int newLength = args.length + 1;
		String[] newSelectionArgs = new String[newLength];
		newSelectionArgs[0] = str;
		System.arraycopy(args, 0, newSelectionArgs, 1, args.length);
		return newSelectionArgs;
	}
}
