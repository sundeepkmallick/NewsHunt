package com.ecommhunt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	private Context context;
	public DatabaseHelper DBHelper;
	public SQLiteDatabase db;

	/**
	 * @param ctx
	 * 
	 */

	public DBAdapter(Context ctx) {
		this.context = ctx;

		//SQLiteDatabase.loadLibs(context);

		DBHelper = new DatabaseHelper(context);
	}

	/**
	 * open database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DBAdapter open() throws SQLException {

		/*String strSqlitepassword = String.valueOf(Constants.SQLITE_PASSWORD);
		try {
			db = DBHelper.getWritableDatabase(strSqlitepassword);
			return this;

		} catch (SQLException ex) {
			db = DBHelper.getReadableDatabase(strSqlitepassword);
		}*/

		try {
			db = DBHelper.getWritableDatabase();
			return this;

		} catch (SQLException ex) {
			db = DBHelper.getReadableDatabase();
		}

		return this;
	}

	/**
	 * --Close database
	 */
	public void close() {

		DBHelper.close();
	}

	public boolean clearTableProductAndSku() {
		boolean isAllTableCleared = false;

		if (clearTableSku()) {
			isAllTableCleared = true;
		}

		return isAllTableCleared;
	}

	public boolean clearTableSku() {
		return db.delete(DatabaseHelper.TABLE_PRODUCT, "", null) > 0;
	}

	public long insertTableSku(String skuId, String skuName, String skuPrice,
			String skuOfferPrice, String skuCurrency, String skuAvailableUnit,
			String skuOfferDesc, String skuProductInfo, String skuIngredients,
			String skuDirections, String offerType) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseHelper.KEY_PRODUCT_ID, skuId);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_NAME, skuName);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_PRICE, skuPrice);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_OFFER_PRICE, skuOfferPrice);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_CURRENCY, skuCurrency);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_AVAIALBLE_UNIT,
				skuAvailableUnit);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_OFFER_DESCRIPTION,
				skuOfferDesc);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_PRODUCT_INFORMATION,
				skuProductInfo);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_INGREDIENTS, skuIngredients);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_DIRECTIONS, skuDirections);
		initialValues.put(DatabaseHelper.KEY_OFFER_TYPE, offerType);

		return db.insert(DatabaseHelper.TABLE_PRODUCT, null, initialValues);
	}

	public Cursor getSkuList(String offerType) {
		Cursor cursor = db.query(true, DatabaseHelper.TABLE_PRODUCT, new String[] {
				DatabaseHelper.KEY_PRODUCT_ID, DatabaseHelper.KEY_PRODUCT_NAME,
				DatabaseHelper.KEY_PRODUCT_PRICE,
				DatabaseHelper.KEY_PRODUCT_OFFER_PRICE,
				DatabaseHelper.KEY_PRODUCT_CURRENCY,
				DatabaseHelper.KEY_PRODUCT_AVAIALBLE_UNIT,
				DatabaseHelper.KEY_PRODUCT_OFFER_DESCRIPTION,
				DatabaseHelper.KEY_PRODUCT_PRODUCT_INFORMATION,
				DatabaseHelper.KEY_PRODUCT_INGREDIENTS,
				DatabaseHelper.KEY_PRODUCT_DIRECTIONS },
				DatabaseHelper.KEY_OFFER_TYPE + "= '" + offerType + "'", null,
				null, null, null, null);
		return cursor;
	}

	/**
	 * Function to insert item to cart table
	 * 
	 * @param
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long insertCartItem(String emailId, String skuId, int quantity) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseHelper.KEY_EMAIL_ID, emailId);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_ID, skuId);
		initialValues.put(DatabaseHelper.KEY_QUANTITY, quantity);

		return db.insert(DatabaseHelper.TABLE_CART_ITEMS, null, initialValues);
	}

	/**
	 * Function to fetch cart item list
	 * 
	 * @return Cursor object
	 */
	public Cursor getCartItems(String emailId) {
		return db.query(DatabaseHelper.TABLE_CART_ITEMS, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID, DatabaseHelper.KEY_QUANTITY },
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "' OR "
						+ DatabaseHelper.KEY_EMAIL_ID + "=''", null, null,
				null, null, null);
	}

	/**
	 * Function to fetch cart items which are not assigned
	 * 
	 * @return Cursor object
	 */
	public Cursor getUnassignedCartItems() {
		return db.query(DatabaseHelper.TABLE_CART_ITEMS, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID, DatabaseHelper.KEY_QUANTITY },
				DatabaseHelper.KEY_EMAIL_ID + "=''", null, null, null, null,
				null);
	}

	/**
	 * Function to fetch cart item based on email & skuId
	 * 
	 * @return Cursor object
	 */
	public Cursor getCartItem(String emailId, String skuId) {
		return db.query(DatabaseHelper.TABLE_CART_ITEMS, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID, DatabaseHelper.KEY_QUANTITY },
				DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId + "' AND ( "
						+ DatabaseHelper.KEY_EMAIL_ID + "='" + emailId
						+ "' OR " + DatabaseHelper.KEY_EMAIL_ID + "='' )",
				null, null, null, null, null);
	}

	/**
	 * Function to fetch cart item based on email & skuId
	 * 
	 * @return Cursor object
	 */
	public Cursor getAssignedCartItem(String emailId, String skuId) {
		return db.query(DatabaseHelper.TABLE_CART_ITEMS, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID, DatabaseHelper.KEY_QUANTITY },
				DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId + "' AND ( "
						+ DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + " )",
				null, null, null, null, null);
	}

	/**
	 * Function to delete cart item based on skuId
	 * 
	 * @return boolean
	 */
	public boolean removeCartItem(String emailId, String skuId) {
		return db.delete(DatabaseHelper.TABLE_CART_ITEMS,
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "' AND "
						+ DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId + "'", null) > 0;
	}

	/**
	 * Function to delete all cart items based on email id
	 * 
	 * @return boolean
	 */
	public boolean removeAllCartItems(String emailId) {
		return db.delete(DatabaseHelper.TABLE_CART_ITEMS,
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "'", null) > 0;
	}

	/**
	 * Function to clear cart table
	 * 
	 * @return boolean
	 */
	public boolean clearCartTable(String emailId) {
		return db.delete(DatabaseHelper.TABLE_CART_ITEMS, "1", null) > 0;
	}

	/**
	 * Function to update store data in cart table
	 * 
	 * @param
	 * @return
	 */
	public long updateCartItem(String emailId, String skuId, int quantity) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseHelper.KEY_QUANTITY, quantity);

		return db.update(DatabaseHelper.TABLE_CART_ITEMS, initialValues,
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "' AND "
						+ DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId + "'", null);
	}

	/**
	 * Function to update store data in cart table
	 * 
	 * @param
	 * @return
	 */
	public long updateEmailIdToUnassignedCartItem(String emailId) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseHelper.KEY_EMAIL_ID, emailId);

		return db.update(DatabaseHelper.TABLE_CART_ITEMS, initialValues,
				DatabaseHelper.KEY_EMAIL_ID + "=''", null);
	}

	/**
	 * Function to insert item to Wish List
	 * 
	 * @param
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long insertWishListItem(String emailId, String skuId) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseHelper.KEY_EMAIL_ID, emailId);
		initialValues.put(DatabaseHelper.KEY_PRODUCT_ID, skuId);

		return db.insert(DatabaseHelper.TABLE_WISH_LIST, null, initialValues);
	}

	/**
	 * Function to fetch My Wish list item based on email & skuId
	 * 
	 * @return Cursor object
	 */
	public Cursor getMyWishlistItem(String emailId) {
		return db.query(DatabaseHelper.TABLE_WISH_LIST, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID }, DatabaseHelper.KEY_EMAIL_ID + "='"
				+ emailId + "'", null, null, null, null, null);
	}

	/**
	 * Function to fetch My Wish list item based on email & skuId which are
	 * unique and not present in cart list
	 * 
	 * @return Cursor object
	 */
	public Cursor getMyWishlistUniqueItem(String emailId) {

		return db.rawQuery(" SELECT DISTINCT(" + DatabaseHelper.KEY_PRODUCT_ID
				+ ") FROM " + DatabaseHelper.TABLE_WISH_LIST + " WHERE "
				+ DatabaseHelper.KEY_PRODUCT_ID + " NOT IN ( SELECT DISTINCT ("
				+ DatabaseHelper.KEY_PRODUCT_ID + ") FROM "
				+ DatabaseHelper.TABLE_CART_ITEMS + " WHERE "
				+ DatabaseHelper.KEY_EMAIL_ID + " ='" + emailId + "' ) AND "
				+ DatabaseHelper.KEY_EMAIL_ID + " ='" + emailId + "'", null);

	}

	/**
	 * Function to fetch WishList item
	 * 
	 * @param
	 * @return Cursor object
	 */
	public Cursor getWishListItem(String emailId, String skuId) {
		return db.query(DatabaseHelper.TABLE_WISH_LIST, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID }, DatabaseHelper.KEY_EMAIL_ID + "='"
				+ emailId + "' AND " + DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId
				+ "'", null, null, null, null, null);
	}

	/**
	 * Function to fetch Wish List items
	 * 
	 * @param
	 * @return Cursor object
	 */
	public Cursor getWishListItems(String emailId) {
		return db.query(DatabaseHelper.TABLE_WISH_LIST, new String[] {
				DatabaseHelper.KEY_ID, DatabaseHelper.KEY_EMAIL_ID,
				DatabaseHelper.KEY_PRODUCT_ID }, DatabaseHelper.KEY_EMAIL_ID + "='"
				+ emailId + "'", null, null, null, null, null);
	}

	/**
	 * Function to delete Wish List item based on email id & skuId
	 * 
	 * @param
	 * @return boolean
	 */
	public boolean removeWishListItem(String emailId, String skuId) {
		return db.delete(DatabaseHelper.TABLE_WISH_LIST,
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "' AND "
						+ DatabaseHelper.KEY_PRODUCT_ID + "='" + skuId + "'", null) > 0;
	}

	/**
	 * Function to delete all Wish List items based on email id
	 * 
	 * @return boolean
	 */
	public boolean removeAllWishListItems(String emailId) {
		return db.delete(DatabaseHelper.TABLE_WISH_LIST,
				DatabaseHelper.KEY_EMAIL_ID + "='" + emailId + "'", null) > 0;
	}

	/**
	 * Function to clear Wish List table
	 * 
	 * @return boolean
	 */
	public boolean clearWishListTable(String emailId) {
		return db.delete(DatabaseHelper.TABLE_WISH_LIST, "1", null) > 0;
	}

	public Cursor getAllCartItems(String emailId) {
		return db.rawQuery(" SELECT DISTINCT(" + DatabaseHelper.KEY_PRODUCT_ID
				+ ") from " + DatabaseHelper.TABLE_CART_ITEMS + " WHERE "
				+ DatabaseHelper.KEY_EMAIL_ID + " ='" + emailId + "' ", null);

	}

}
