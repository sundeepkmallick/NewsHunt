package com.ecommhunt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "appdatabase";

	// Table Names
	public static final String TABLE_PRODUCT = "product";
	/**
	 * SQLite database table having columns: <br/>
	 * {@link #KEY_ID} <br/>
	 * {@link #KEY_EMAIL_ID} <br/>
	 * {@link #KEY_PRODUCT_ID} <br/>
	 * {@link #KEY_QUANTITY}
	 */
	public static final String TABLE_CART_ITEMS = "cart_items";
	public static final String TABLE_WISH_LIST = "wish_list";

	// Common column name
	public static final String KEY_ID = "id";

	// STORELOCATOR Table - column names
	public static final String KEY_STORE_ID = "store_id";
	public static final String KEY_STORE_CITY_ID = "store_city_id";
	public static final String KEY_STORE_CITY_NAME = "store_city_name";
	public static final String KEY_STORE_COUNTRY_ID = "store_country_id";
	public static final String KEY_STORE_COUNTRY_NAME = "store_country_name";
	public static final String KEY_STORENAME = "store_name";
	public static final String KEY_STORE_ADDRESS = "store_address";
	public static final String KEY_STORE_LANDMARK = "store_landmark";
	public static final String KEY_STORE_PINCODE = "store_pincode";
	public static final String KEY_STORE_VOICELINE = "store_voiceline";
	public static final String KEY_STORE_EMAIL_ID = "store_emailid";
	public static final String KEY_STORE_LATITUDE = "store_latitude";
	public static final String KEY_STORE_LONGITUDE = "store_longitude";

	// 0 - Key Offer & 1 - Offer
	public static final String KEY_OFFER_TYPE = "offer_type";

	// PRODUCT Table - column names
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_PRODUCT_NAME = "product_name";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_PRODUCT_OFFER_PRICE = "product_offer_price";
	public static final String KEY_PRODUCT_CURRENCY = "product_currency";
	public static final String KEY_PRODUCT_AVAIALBLE_UNIT = "product_available_unit";
	public static final String KEY_PRODUCT_OFFER_DESCRIPTION = "product_offer_desc";
	public static final String KEY_PRODUCT_PRODUCT_INFORMATION = "product_product_information";
	public static final String KEY_PRODUCT_INGREDIENTS = "product_ingredients";
	public static final String KEY_PRODUCT_DIRECTIONS = "product_directions";

	// SUB_PRODUCT_IMAGES Table - column names
	public static final String KEY_IMAGE_URL = "sub_image_url";

	// CART Table - column names
	public static final String KEY_QUANTITY = "quantity";
	public static final String KEY_EMAIL_ID = "email_id";

	public static final String CACHE_KEY = "key";
	public static final String CACHE_VALUE = "value";

	// CREATE_TABLE_PRODUCT table create statement
	public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
			+ TABLE_PRODUCT + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_ID + " TEXT,"
			+ KEY_PRODUCT_NAME + " TEXT, " + KEY_PRODUCT_PRICE + " TEXT, "
			+ KEY_PRODUCT_OFFER_PRICE + " TEXT, " + KEY_PRODUCT_CURRENCY
			+ " TEXT, " + KEY_PRODUCT_AVAIALBLE_UNIT + " TEXT, "
			+ KEY_PRODUCT_OFFER_DESCRIPTION + " TEXT, "
			+ KEY_PRODUCT_PRODUCT_INFORMATION + " TEXT, "
			+ KEY_PRODUCT_INGREDIENTS + " TEXT, " + KEY_PRODUCT_DIRECTIONS
			+ " TEXT, " + KEY_OFFER_TYPE + " TEXT " + ")";

	// CREATE_TABLE_CART table create statement
	public static final String CREATE_TABLE_CART = "CREATE TABLE "
			+ TABLE_CART_ITEMS + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL_ID + " TEXT, "
			+ KEY_PRODUCT_ID + " TEXT, " + KEY_QUANTITY + " TEXT" + ")";

	// Wish List Table
	public static final String CREATE_TABLE_WISH_LIST = "CREATE TABLE "
			+ TABLE_WISH_LIST + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL_ID + " TEXT, "
			+ KEY_PRODUCT_ID + " TEXT )";

	/**
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sqlcipher.database.SQLiteOpenHelper#onCreate(net.sqlcipher.database
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_CART);
		// Cache table
		db.execSQL(CREATE_TABLE_WISH_LIST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sqlcipher.database.SQLiteOpenHelper#onUpgrade(net.sqlcipher.database
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CART);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_WISH_LIST);
		// create new tables
		onCreate(db);
	}

}
