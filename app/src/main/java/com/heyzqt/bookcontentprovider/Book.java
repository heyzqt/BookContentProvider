package com.heyzqt.bookcontentprovider;

/**
 * Created by heyzqt on 12/12/2017.
 */

public class Book {

	private int _ID;

	private String name;

	private String type;

	public int get_ID() {
		return _ID;
	}

	public void set_ID(int _ID) {
		this._ID = _ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Book{" +
				"_ID=" + _ID +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
