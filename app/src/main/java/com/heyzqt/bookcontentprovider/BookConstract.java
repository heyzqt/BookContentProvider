package com.heyzqt.bookcontentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by heyzqt on 12/14/2017.
 */

public final class BookConstract {

	public static final String AUTHORITY = "com.heyzqt.book";

	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

	public static class Book implements BaseColumns {

		private Book() {
		}

		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "book");

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/book";

		public static final String MIMETYPE_ID = "mimetype_id";
	}

	public static final class Name implements BaseColumns {

		public Name() {
		}

		public static final Uri CONTENT_URI = Uri.withAppendedPath(Book.CONTENT_URI, "name");

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name";

		public static final String NAME = "name";
	}

	public static final class Type implements BaseColumns {

		public Type() {
		}

		public static final Uri CONTENT_URI = Uri.withAppendedPath(Book.CONTENT_URI, "type");

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/type";

		public static final String TYPE = "type";
	}
}
