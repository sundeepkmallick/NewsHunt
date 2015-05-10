package com.ecommhunt.model;

import java.util.LinkedHashMap;

import org.json.JSONObject;

/**
 * Model class having getter & setter for <br/>
 * {@link #skuListObj} <br/>
 * {@link #skuHashMapList}
 * 
 * @author Health and Glow
 * 
 * @see {@link #getSkuHashMapList()}, {@link #setSkuHashMapList()},
 *      {@link #getSkuListObj()}, {@link #setSkuListObj(JSONObject)}
 *
 */
public class MyCartItems {
	LinkedHashMap<String, String> skuHashMapList;
	JSONObject skuListObj;

	/**
	 * @return LinkedHashMap<String, String>
	 */
	public LinkedHashMap<String, String> getSkuHashMapList() {
		return skuHashMapList;
	}

	/**
	 * @param skuHashMapList
	 */
	public void setSkuHashMapList(LinkedHashMap<String, String> skuHashMapList) {
		this.skuHashMapList = skuHashMapList;
	}

	/**
	 * @return skuListObj
	 */
	public JSONObject getSkuListObj() {
		return skuListObj;
	}

	/**
	 * @param skuListObj
	 */
	public void setSkuListObj(JSONObject skuListObj) {
		this.skuListObj = skuListObj;
	}
}
