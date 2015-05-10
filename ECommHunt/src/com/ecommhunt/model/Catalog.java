package com.ecommhunt.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Catalog implements Serializable {
	String catalogId;
	String catalogName;
	ArrayList<Category> categoryList;
	boolean mIsExtraMenu = false;
	boolean mIsExtraMenuAdded = false;

	Class mClassName = null;

	public Class getClassName() {
		return mClassName;
	}

	public void setClassName(Class mClassName) {
		this.mClassName = mClassName;
	}

	public boolean ismIsExtraMenuAdded() {
		return mIsExtraMenuAdded;
	}

	public void setmIsExtraMenuAdded(boolean mIsExtraMenuAdded) {
		this.mIsExtraMenuAdded = mIsExtraMenuAdded;
	}

	public boolean isIsExtraMenu() {
		return mIsExtraMenu;
	}

	public void setIsExtraMenu(boolean mIsExtraMenu) {
		this.mIsExtraMenu = mIsExtraMenu;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Catalog() {

	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// dest.writeString(catalogId);
	// dest.writeString(catalogName);
	// dest.writeTypedList(categoryList);
	// }

	public static final Parcelable.Creator<Catalog> CREATOR = new Parcelable.Creator<Catalog>() {
		public Catalog createFromParcel(Parcel in) {
			return new Catalog(in);
		}

		public Catalog[] newArray(int size) {
			return new Catalog[size];
		}
	};

	private Catalog(Parcel in) {
		catalogId = in.readString();
		catalogName = in.readString();
		categoryList = in.readParcelable(Category.class.getClassLoader());
	}
}
