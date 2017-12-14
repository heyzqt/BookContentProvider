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

		public static final Uri NAME_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "name");

		public static final Uri TYPE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "type");

		public static final String NAME = "name";

		public static final String TYPE = "type";
	}
}
