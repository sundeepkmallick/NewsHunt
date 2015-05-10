package com.ecommhunt.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String status;
	String error;
	String data;
	String mBatchSize;
	String mTotalItemCount;
	String minPrice;
	String maxPrice;

	ArrayList<Product> products;

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * API to get the batch size.
	 * 
	 * @return
	 */
	public String getBatchSize() {
		return mBatchSize;
	}

	/**
	 * API to set the Batch Size.
	 * 
	 * @param mBatchSize
	 */
	public void setBatchSize(String mBatchSize) {
		this.mBatchSize = mBatchSize;
	}

	/**
	 * API used to get the total Item Count.
	 * 
	 * @return
	 */
	public String getTotalItemCount() {
		return mTotalItemCount;
	}

	/**
	 * API used to get the total item count.
	 * 
	 * @param mTotalItemCount
	 */
	public void setTotalItemCount(String mTotalItemCount) {
		this.mTotalItemCount = mTotalItemCount;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

}
