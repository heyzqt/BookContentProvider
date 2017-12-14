package com.heyzqt.bookcontentprovider;

import android.app.Application;
import android.util.Log;

/**
 * Created by heyzqt on 12/14/2017.
 */

public class BookApplication extends Application {

	private static final String TAG = "BookApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate: ");
	}
}
