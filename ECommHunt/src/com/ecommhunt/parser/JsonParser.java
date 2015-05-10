package com.ecommhunt.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ecommhunt.model.Catalog;
import com.ecommhunt.model.Category;
import com.ecommhunt.model.Product;
import com.ecommhunt.model.ResponseData;
import com.ecommhunt.model.SubCategory;
import com.ecommhunt.util.ConstantsParams;

public class JsonParser {
	public static String loadJSONFromAsset(Context context, String jsonFileName) {
		String json = null;
		try {
			InputStream is = context.getAssets().open(jsonFileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	private static String getStringFromObject(JSONObject obj, String param)
			throws JSONException {
		String parsedData = "";

		if (obj.has(param)) {
			parsedData = obj.getString(param);
		}

		return parsedData;
	}

	public static ResponseData parseResponse(String response) {
		ResponseData responseData = new ResponseData();

		try {
			JSONObject responseObj = new JSONObject(response);

			String status = getStringFromObject(responseObj,
					ConstantsParams.STATUS);
			String error = getStringFromObject(responseObj,
					ConstantsParams.ERROR);

			String minprice = getStringFromObject(responseObj,
					ConstantsParams.MIN_PRICE);
			String maxprice = getStringFromObject(responseObj,
					ConstantsParams.MAX_PRICE);
			String batchSize = getStringFromObject(responseObj,
					ConstantsParams.BATCH_SIZE);
			String totalItemCount = getStringFromObject(responseObj,
					ConstantsParams.TOTAL_ITEM_COUNT);
			String data = getStringFromObject(responseObj, ConstantsParams.DATA);
			responseData.setStatus(status);
			responseData.setError(error);
			responseData.setData(data);
			responseData.setBatchSize(batchSize);
			responseData.setTotalItemCount(totalItemCount);
			responseData.setMinPrice(minprice);
			responseData.setMaxPrice(maxprice);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return responseData;
	}

	public static ArrayList<Product> parseProductList(String data) {
		ArrayList<Product> listProduct = new ArrayList<Product>();

		try {

			JSONArray productArr = new JSONArray(data);

			for (int i = 0; i < productArr.length(); i++) {
				Product sku = new Product();
				JSONObject skuObj = productArr.getJSONObject(i);
				String catalogId = getStringFromObject(skuObj,
						ConstantsParams.CATALOG_ID);
				String categoryId = getStringFromObject(skuObj,
						ConstantsParams.CATEGORY_ID);
				String subcategoryId = getStringFromObject(skuObj,
						ConstantsParams.SUBCATEGORY_ID);
				String skuName = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_NAME);
				String skuId = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_ID);
				String skuPrice = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_PRICE);
				String skuOfferPrice = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_OFFER_PRICE);
				String skuCurrency = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_CURRENCY);
				String skuAvailableUnit = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_AVAILABLE_UNIT);
				String skuOfferDesc = getStringFromObject(skuObj,
						ConstantsParams.PRODUCT_OFFER_DESC);

				sku.setCatalogId(catalogId);
				sku.setCategoryId(categoryId);
				sku.setSubcategoryId(subcategoryId);
				sku.setProductId(skuId);
				sku.setProductName(skuName);
				sku.setProductPrice(skuPrice);
				sku.setProductOfferPrice(skuOfferPrice);
				sku.setProductCurrency(skuCurrency);
				sku.setProductAvailableUnit(skuAvailableUnit);
				sku.setProductOfferDesc(skuOfferDesc);

				listProduct.add(sku);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listProduct;
	}

	public static ArrayList<Catalog> parseCatalog(Context context, String data) {
		ArrayList<Catalog> catalogList = new ArrayList<Catalog>();

		try {
			// json array to store the data of Catalog Json
			JSONObject objData = new JSONObject(data);
			JSONArray arrData = objData.getJSONArray(ConstantsParams.CATALOG);
			for (int i = 0; i < arrData.length(); i++) {
				// extracts each catalog
				JSONObject catalogObj = arrData.getJSONObject(i);
				// instance of catalog Class
				Catalog catalog = new Catalog();

				String catalogId = getStringFromObject(catalogObj,
						ConstantsParams.CATALOG_ID);
				String catalogName = getStringFromObject(catalogObj,
						ConstantsParams.CATALOG_NAME);

				catalog.setCatalogId(catalogId);
				catalog.setCatalogName(catalogName);

				if (catalogObj.has(ConstantsParams.CATEGORY)) {
					// checks if categories exists in json
					JSONArray categorydataArray = catalogObj
							.getJSONArray(ConstantsParams.CATEGORY);
					// extract it into an JsonArray
					ArrayList<Category> arrCategory = new ArrayList<Category>();

					for (int j = 0; j < categorydataArray.length(); j++) {
						// extracts each Category
						JSONObject categoryObj = categorydataArray
								.getJSONObject(j);
						Category category = new Category();

						String categoryId = getStringFromObject(categoryObj,
								ConstantsParams.CATEGORY_ID);
						String categoryName = getStringFromObject(categoryObj,
								ConstantsParams.CATEGORY_NAME);

						category.setCategoryId(categoryId);
						category.setCategoryName(categoryName);

						if (categoryObj.has(ConstantsParams.SUBCATEGORY)) {
							JSONArray subcategoryArray = categoryObj
									.getJSONArray(ConstantsParams.SUBCATEGORY);
							ArrayList<SubCategory> arrsubcategory = new ArrayList<SubCategory>();

							for (int k = 0; k < subcategoryArray.length(); k++) {
								// extracts each subcategory
								JSONObject subcategoryObj = subcategoryArray
										.getJSONObject(k);
								SubCategory subcategory = new SubCategory();

								String subCategoryId = getStringFromObject(
										subcategoryObj,
										ConstantsParams.SUBCATEGORY_ID);
								String subCategoryName = getStringFromObject(
										subcategoryObj,
										ConstantsParams.SUBCATEGORY_NAME);

								subcategory.setSubCategoryId(subCategoryId);
								subcategory.setSubCategoryName(subCategoryName);
								arrsubcategory.add(subcategory);
							}
							category.setSubCategoryList(arrsubcategory);
						}
						arrCategory.add(category);
					}
					catalog.setCategoryList(arrCategory);
				}
				catalogList.add(catalog);
			}
		}

		catch (JSONException e) {
			e.printStackTrace();
		}
		return catalogList;
	}

}
