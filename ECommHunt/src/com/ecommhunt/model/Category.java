package com.ecommhunt.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
	String categoryId;
	String categoryName;
	ArrayList<SubCategory> subCategoryList;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public ArrayList<SubCategory> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(ArrayList<SubCategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
	public Category(){
		
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(categoryId);
		dest.writeString(categoryName);
		dest.writeTypedList(subCategoryList);
	}

	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}

		public Category[] newArray(int size) {
			return new Category[size];
		}
	};

	private Category(Parcel in) {
		categoryId = in.readString();
		categoryName = in.readString();
		subCategoryList = in.readParcelable(SubCategory.class.getClassLoader());
	}
}
