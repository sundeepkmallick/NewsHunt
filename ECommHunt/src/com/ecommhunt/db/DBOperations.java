package com.ecommhunt.db;

import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;

import com.ecommhunt.model.MyCartItems;
import com.ecommhunt.util.ConstantsParams;
import com.ecommhunt.util.Utils;

public class DBOperations {

	/**
	 * This method is used to add one item to the database table:
	 * {@link DatabaseHelper#TABLE_CART_ITEMS}
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return true if item gets added to database table, else false
	 */
	public static boolean addOneItemToBag(Context context, String emailId,
			String productId) {
		boolean isAddedToBag = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetCartItem = db.getCartItem(emailId, productId);
		if (curGetCartItem.getCount() == 0) {
			if (db.insertCartItem(emailId, productId, 1) > 0) {
				isAddedToBag = true;
			}
		}
		db.close();

		return isAddedToBag;
	}

	/**
	 * This method is used to add more than one items to the database table:
	 * {@link DatabaseHelper#TABLE_CART_ITEMS}
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @param quantity
	 * @return true if items get added to database table, else false
	 */
	public static boolean addMultipleItemToBag(Context context, String emailId,
			String productId, int quantity) {
		boolean isAddedToBag = false;
		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetCartItem = db.getCartItem(emailId, productId);
		if (curGetCartItem.getCount() == 0
				&& db.insertCartItem(emailId, productId, quantity) > 0) {
			isAddedToBag = true;
		} else if (db.updateCartItem(emailId, productId, quantity) > 0) {
			isAddedToBag = true;
		}
		db.close();
		return isAddedToBag;
	}

	/**
	 * This method is used to remove one item from the database table:
	 * {@link DatabaseHelper#TABLE_CART_ITEMS} based on below params
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return true if item gets removed from database table, else false
	 */
	public static boolean removeOneItemFromBag(Context context, String emailId,
			String productId) {
		boolean isAddedToBag = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		if (db.removeCartItem(emailId, productId)) {
			isAddedToBag = true;
		}
		db.close();

		return isAddedToBag;
	}

	/**
	 * This method is used to remove all cart items from the database table:
	 * {@link DatabaseHelper#TABLE_CART_ITEMS} based on below params
	 * 
	 * @param context
	 * @param emailId
	 * @return true if items get removed from database table, else false
	 */
	public static boolean removeAllItemFromBag(Context context, String emailId) {
		boolean isRemovedFromBag = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		if (db.removeAllCartItems(emailId)) {
			isRemovedFromBag = true;
		}
		db.close();

		return isRemovedFromBag;
	}

	/**
	 * This method is used to count no. of itemse added to
	 * {@link DatabaseHelper#TABLE_CART_ITEMS} based on below params
	 * 
	 * @param context
	 * @param emailId
	 * @return Integer
	 */
	public static int countCartItems(Context context, String emailId) {
		int numCartItems = 0;

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetCartItems = db.getCartItems(emailId);
		if (curGetCartItems != null) {
			numCartItems = curGetCartItems.getCount();
		}
		db.close();

		return numCartItems;
	}

	/**
	 * This method is used to get cursor having all added cart items
	 * 
	 * @param context
	 * @param emailId
	 * @return Cursor
	 */
	public static Cursor getCartItems(Context context, String emailId) {
		Cursor curGetCartItems = null;

		DBAdapter db = new DBAdapter(context);
		db.open();
		curGetCartItems = db.getCartItems(emailId);
		db.close();

		return curGetCartItems;
	}

	/**
	 * This method returns no. of quantity added for a specific product based on
	 * user email id
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return Integer
	 */
	public static int countCartItem(Context context, String emailId,
			String productId) {
		int numCartItem = 0;

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetCartItem = db.getCartItem(emailId, productId);
		if (curGetCartItem != null && curGetCartItem.moveToFirst()) {
			String productQuantity = curGetCartItem.getString(curGetCartItem
					.getColumnIndex(DatabaseHelper.KEY_QUANTITY));
			numCartItem = Utils.parseInt(productQuantity);
		}
		db.close();

		return numCartItem;
	}

	/**
	 * @param context
	 * @param emailId
	 * @return Object of {@link in.healthandglow.model.MyCartItems}
	 * @throws JSONException
	 */
	public static MyCartItems getMyCartItemIdObj(Context context, String emailId)
			throws JSONException {
		MyCartItems myCartItems = new MyCartItems();
		LinkedHashMap<String, String> productHashMapList = new LinkedHashMap<String, String>();

		DBAdapter db = new DBAdapter(context);
		db.open();
		JSONArray arrProductIds = new JSONArray();
		JSONObject objProductIds = new JSONObject();
		Cursor curGetCartItems = db.getCartItems(emailId);
		if (curGetCartItems != null && curGetCartItems.getCount() > 0) {
			for (int i = 0; i < curGetCartItems.getCount(); i++) {
				if (curGetCartItems.moveToPosition(i)) {
					JSONObject objProductId = new JSONObject();
					String productId = curGetCartItems
							.getString(curGetCartItems
									.getColumnIndex(DatabaseHelper.KEY_PRODUCT_ID));
					String productQuantity = curGetCartItems
							.getString(curGetCartItems
									.getColumnIndex(DatabaseHelper.KEY_QUANTITY));
					objProductId.put(ConstantsParams.PRODUCT_ID, productId);
					arrProductIds.put(objProductId);
					productHashMapList.put(productId, productQuantity);
				}
			}
			objProductIds.put(ConstantsParams.PRODUCT_IDS, arrProductIds);
		}
		db.close();

		myCartItems.setSkuHashMapList(productHashMapList);
		myCartItems.setSkuListObj(objProductIds);

		return myCartItems;
	}

	/**
	 * This method is used to assign the logged in email id for the column:
	 * {@link DatabaseHelper#KEY_EMAIL_ID} to databse table:
	 * {@link DatabaseHelper#TABLE_CART_ITEMS}
	 * 
	 * @param context
	 * @param emailId
	 * @return boolean
	 */
	public static boolean updateEmailIdToUnassignedCartItem(Context context,
			String emailId) {
		boolean isUpdated = false;
		DBAdapter db = new DBAdapter(context);
		db.open();
		if (db.updateEmailIdToUnassignedCartItem(emailId) > 0) {
			isUpdated = true;
		}
		db.close();
		return isUpdated;
	}

	/**
	 * This method is used to add one item to the database table:
	 * {@link DatabaseHelper#TABLE_WISH_LIST}
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return true if item gets added to database table, else false
	 */
	public static boolean addOneItemToWishList(Context context, String emailId,
			String productId) {
		boolean isAddedToWishList = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetWishListItem = db.getWishListItem(emailId, productId);
		if (curGetWishListItem.getCount() == 0) {
			if (db.insertWishListItem(emailId, productId) > 0) {
				isAddedToWishList = true;
			}
		}
		db.close();

		return isAddedToWishList;
	}

	/**
	 * This method let us know if item is already added to wish list based on
	 * user email id & product id
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return Integer
	 */
	public static boolean isItemAddedToWishList(Context context,
			String emailId, String productId) {
		boolean isItemAddedToWishList = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		Cursor curGetWishListItem = db.getWishListItem(emailId, productId);
		if (curGetWishListItem != null && curGetWishListItem.getCount() > 0) {
			isItemAddedToWishList = true;
		}
		db.close();

		return isItemAddedToWishList;
	}

	/**
	 * This method is used to remove one item from the database table:
	 * {@link DatabaseHelper#TABLE_WISH_LIST} based on below params
	 * 
	 * @param context
	 * @param emailId
	 * @param productId
	 * @return true if item gets removed from database table, else false
	 */
	public static boolean removeOneItemFromWishList(Context context,
			String emailId, String productId) {
		boolean isRemoved = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		if (db.removeWishListItem(emailId, productId)) {
			isRemoved = true;
		}
		db.close();

		return isRemoved;
	}

	/**
	 * This method is used to remove all wishlist items from the database table:
	 * {@link DatabaseHelper#TABLE_WISH_LIST} based on below params
	 * 
	 * @param context
	 * @param emailId
	 * @return true if items get removed from database table, else false
	 */
	public static boolean removeAllItemFromWishList(Context context,
			String emailId) {
		boolean isRemoved = false;

		DBAdapter db = new DBAdapter(context);
		db.open();
		if (db.removeAllWishListItems(emailId)) {
			isRemoved = true;
		}
		db.close();

		return isRemoved;
	}

	/**
	 * @param context
	 * @param emailId
	 * @return Object of {@link in.healthandglow.model.MyCartItems}
	 * @throws JSONException
	 */
	public static JSONObject getMyWishlistItemIdObj(Context context,
			String emailId) throws JSONException {

		DBAdapter db = new DBAdapter(context);
		db.open();
		JSONArray arrProductIds = new JSONArray();
		JSONObject objProductIds = new JSONObject();
		Cursor curGetMyWishlistItems = db.getMyWishlistItem(emailId);
		if (curGetMyWishlistItems != null
				&& curGetMyWishlistItems.getCount() > 0) {
			for (int i = 0; i < curGetMyWishlistItems.getCount(); i++) {
				if (curGetMyWishlistItems.moveToPosition(i)) {
					JSONObject objProductId = new JSONObject();
					String productId = curGetMyWishlistItems
							.getString(curGetMyWishlistItems
									.getColumnIndex(DatabaseHelper.KEY_PRODUCT_ID));
					objProductId.put(ConstantsParams.PRODUCT_ID, productId);
					arrProductIds.put(objProductId);
				}
			}
			objProductIds.put(ConstantsParams.PRODUCT_IDS, arrProductIds);
		}
		db.close();

		return objProductIds;
	}

	/**
	 * @param context
	 * @param emailId
	 * @return Object of {@link in.healthandglow.model.MyCartItems}
	 * @throws JSONException
	 */
	public static JSONObject getMyWishlistUniqueItemIdObj(Context context,
			String emailId) throws JSONException {

		DBAdapter db = new DBAdapter(context);
		db.open();
		JSONArray arrProductIds = new JSONArray();
		JSONObject objProductIds = new JSONObject();
		Cursor curGetMyWishlistItems = db.getMyWishlistUniqueItem(emailId);
		if (curGetMyWishlistItems != null
				&& curGetMyWishlistItems.getCount() > 0) {
			for (int i = 0; i < curGetMyWishlistItems.getCount(); i++) {
				if (curGetMyWishlistItems.moveToPosition(i)) {
					JSONObject objProductId = new JSONObject();
					String productId = curGetMyWishlistItems
							.getString(curGetMyWishlistItems
									.getColumnIndex(DatabaseHelper.KEY_PRODUCT_ID));
					objProductId.put(ConstantsParams.PRODUCT_ID, productId);
					arrProductIds.put(objProductId);
				}

			}
		}

		Cursor curGetCartItems = db.getAllCartItems(emailId);
		if (curGetCartItems != null && curGetCartItems.getCount() > 0) {
			for (int i = 0; i < curGetCartItems.getCount(); i++) {
				if (curGetCartItems.moveToPosition(i)) {
					JSONObject objCartProductId = new JSONObject();
					String productId = curGetCartItems
							.getString(curGetCartItems
									.getColumnIndex(DatabaseHelper.KEY_PRODUCT_ID));
					objCartProductId.put(ConstantsParams.PRODUCT_ID, productId);
					arrProductIds.put(objCartProductId);
				}
			}
		}

		objProductIds.put(ConstantsParams.PRODUCT_IDS, arrProductIds);

		db.close();

		return objProductIds;

	}

	private static JSONObject getCartJsonObject(Context context, String emailId)
			throws JSONException {
		// TODO Auto-generated method stub
		DBAdapter db = new DBAdapter(context);
		db.open();
		JSONArray arrCartProductIds = new JSONArray();
		JSONObject objCartProductIds = new JSONObject();
		// JSONObject objCartProductId = new JSONObject();
		Cursor curGetCartItems = db.getAllCartItems(emailId);
		if (curGetCartItems != null && curGetCartItems.getCount() > 0) {
			for (int i = 0; i < curGetCartItems.getCount(); i++) {
				if (curGetCartItems.moveToPosition(i)) {
					JSONObject objCartProductId = new JSONObject();
					String productId = curGetCartItems
							.getString(curGetCartItems
									.getColumnIndex(DatabaseHelper.KEY_PRODUCT_ID));
					objCartProductId.put(ConstantsParams.PRODUCT_ID, productId);
					arrCartProductIds.put(objCartProductId);
				}
			}
			objCartProductIds.put(ConstantsParams.PRODUCT_IDS,
					arrCartProductIds);
		}
		return objCartProductIds;
	}
}
