package com.ecommhunt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubCategory implements Parcelable {
	String subCategoryId;
	String subCategoryName;
	
	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public SubCategory() {

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(subCategoryId);
		dest.writeString(subCategoryName);
	}

	public static final Parcelable.Creator<SubCategory> CREATOR = new Parcelable.Creator<SubCategory>() {
		public SubCategory createFromParcel(Parcel in) {
			return new SubCategory(in);
		}

		public SubCategory[] newArray(int size) {
			return new SubCategory[size];
		}
	};

	private SubCategory(Parcel in) {
		subCategoryId = in.readString();
		subCategoryName = in.readString();
	}
}
