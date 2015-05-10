package com.ecommhunt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

	String catalogId;
	String categoryId;
	String subcategoryId;
	String productName ="";
	String productId;
	String productPrice;
	String productOfferPrice;
	String productDiscount;
	String productCurrency;
	String productAvailableUnit;
	String productOfferDesc="";
	String productImageUrls;
	String purchasedUnit = "";

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(String subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
	if(!"null".equalsIgnoreCase(productName))
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductOfferPrice() {
		return productOfferPrice;
	}

	public void setProductOfferPrice(String productOfferPrice) {
		this.productOfferPrice = productOfferPrice;
	}

	public String getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(String productDiscount) {
		this.productDiscount = productDiscount;
	}

	public String getProductCurrency() {
		return productCurrency;
	}

	public void setProductCurrency(String productCurrency) {
		this.productCurrency = productCurrency;
	}

	public String getProductAvailableUnit() {
		return productAvailableUnit;
	}

	public void setProductAvailableUnit(String productAvailableUnit) {
		this.productAvailableUnit = productAvailableUnit;
	}

	public String getProductOfferDesc() {
		return productOfferDesc;
	}

	public void setProductOfferDesc(String productOfferDesc) {
		if(!"null".equalsIgnoreCase(productOfferDesc))
		this.productOfferDesc = productOfferDesc;
	}

	public String getProductImageUrls() {
		return productImageUrls;
	}

	public void setProductImageUrls(String productImageUrls) {
		this.productImageUrls = productImageUrls;
	}
	
	public String getPurchasedUnit() {
		return purchasedUnit;
	}

	public void setPurchasedUnit(String purchasedUnit) {
		this.purchasedUnit = purchasedUnit;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(catalogId);
		dest.writeString(categoryId);
		dest.writeString(subcategoryId);
		dest.writeString(productName);
		dest.writeString(productId);
		dest.writeString(productPrice);
		dest.writeString(productOfferPrice);
		dest.writeString(productDiscount);
		dest.writeString(productCurrency);
		dest.writeString(productAvailableUnit);
		dest.writeString(productOfferDesc);
		dest.writeString(productImageUrls);
		dest.writeString(purchasedUnit);
	}
	
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};

	private Product(Parcel in) {
		this();
		catalogId = in.readString();
		categoryId = in.readString();
		subcategoryId = in.readString();
		productName = in.readString();
		productId = in.readString();
		productPrice = in.readString();
		productOfferPrice = in.readString();
		productDiscount = in.readString();
		productCurrency = in.readString();
		productAvailableUnit = in.readString();
		productOfferDesc = in.readString();
		productImageUrls = in.readString();
		purchasedUnit = in.readString();
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}
	
}
