package com.ecommhunt;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommhunt.adapter.OtherProductsAdapter;
import com.ecommhunt.fragment.FilterDialog;
import com.ecommhunt.fragment.SortDialog;
import com.ecommhunt.model.Product;
import com.ecommhunt.model.ResponseData;
import com.ecommhunt.parser.JsonParser;
import com.ecommhunt.util.ConstantsParams;
import com.ecommhunt.util.Utils;

public class ProductListActivity extends BaseActivity {

	Context context;
	ProgressDialog progressDialog;
	ListView listProducts;
	GridView gridProducts;
	ArrayList<Product> listProduct;
	OtherProductsAdapter productsAdapter;
	String mCurrentDisplay = "list";
	ImageView show_list_grid;
	boolean isListItem = true;
	private GridView mOtherProductGrid;
	private TextView no_products;
	private LinearLayout llSortFilter;
	Button btnSort;
	Button btnFilter;
	int max_price;
	int min_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = ProductListActivity.this;
		setDrawerMenu(true);
		super.onCreate(savedInstanceState);
		mLayoutInflater.inflate(R.layout.activity_product_list, mBaseLayout);
		setTitle("Product List");
		enableBackButton(true);

		progressDialog = new ProgressDialog(context);
		listProducts = (ListView) findViewById(R.id.other_products_list_view);
		gridProducts = (GridView) findViewById(R.id.other_products_grid_view);

		listProduct = new ArrayList<Product>();
		productsAdapter = new OtherProductsAdapter(context, listProduct);

		show_list_grid = (ImageView) findViewById(R.id.show_list_grid);
		show_list_grid.setImageResource(R.drawable.show_list);
		setRecycleView();
		show_list_grid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isListItem = setRecycleView();
				ChangeView();

			}
		});

		mOtherProductGrid = (GridView) findViewById(R.id.other_products_grid_view);
		mOtherProductGrid.setFastScrollEnabled(false);
		listProducts = (ListView) findViewById(R.id.other_products_list_view);
		listProducts.setFastScrollEnabled(false);
		ChangeView();
		
		no_products = (TextView) findViewById(R.id.no_products);
		llSortFilter = (LinearLayout) findViewById(R.id.linearlayoutSortFilter);

		GetProductList getProductList = new GetProductList();
		getProductList.execute();
	}

	@Override
	public int getLayoutResource() {
		return -1;
	}

	protected void ChangeView() {

		if (isListItem) {
			mOtherProductGrid.setVisibility(View.GONE);
			listProducts.setVisibility(View.VISIBLE);
			listProducts.setAdapter(productsAdapter);
		} else {
			listProducts.setVisibility(View.GONE);

			mOtherProductGrid.setVisibility(View.VISIBLE);
			mOtherProductGrid.setAdapter(productsAdapter);
		}
	}

	@SuppressLint("NewApi")
	private boolean setRecycleView() {

		if (mCurrentDisplay.equalsIgnoreCase("grid")) {
			mCurrentDisplay = "list";
			show_list_grid.setImageResource(R.drawable.show_grid);
			isListItem = true;
		} else if (mCurrentDisplay.equalsIgnoreCase("list")) {
			mCurrentDisplay = "grid";
			show_list_grid.setImageResource(R.drawable.show_list);
			isListItem = false;
		}

		return isListItem;
	}

	private class GetProductList extends AsyncTask<Void, Void, Void> {

		String errorMsg = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (progressDialog != null && !progressDialog.isShowing()) {
				progressDialog.setTitle("Please wait!!");
				progressDialog.setMessage("Loading items..");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Product> productList = new ArrayList<Product>();
			String response = "";
			String requestStr = "";

			if (Utils.getPreferenceData(context, Utils.PREF_LANGUAGE).equals(
					context.getResources().getString(R.string.hindi))) {
				response = JsonParser
						.loadJSONFromAsset(context, "productlist_hi.json");
			}else{
				response = JsonParser
						.loadJSONFromAsset(context, "productlist.json");
			}
			
			ResponseData responseData = JsonParser.parseResponse(response);
			if (responseData.getStatus().equalsIgnoreCase(ConstantsParams.PASS)) {
				String data = responseData.getData();
				productList = JsonParser.parseProductList(data);
			} else {
				errorMsg = responseData.getError();
			}

			if (productList.size() > 0) {
				listProduct.addAll(productList);
				no_products.setVisibility(View.GONE);
				llSortFilter.setVisibility(View.VISIBLE);
				
				min_price = Utils.parseInt("30");
				max_price = Utils.parseInt("600");
				
				btnSort = (Button) findViewById(R.id.buttonSort);
				btnFilter = (Button) findViewById(R.id.buttonFilter);
				btnSort.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DialogFragment dialogSort = new SortDialog();
						dialogSort.show(
								getSupportFragmentManager(),
								"Sort");
					}
				});
				btnFilter
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								
								if (min_price == max_price) {} else if (min_price > max_price) {
}else{
									DialogFragment dialogFilter = new FilterDialog();
									Bundle bundle = new Bundle();
									bundle.putInt(
											ConstantsParams.MIN,
											min_price);
									/*bundle.putParcelableArrayList(
											ConstantsParams.BUNDLE_SAVED_PRICE_RANGES,
											mSelectedPriceRanges);*/
									bundle.putInt(
											ConstantsParams.MAX,
											(max_price > 0) ? max_price
													: 10000);
									dialogFilter
											.setArguments(bundle);
									dialogFilter
											.show(getSupportFragmentManager(),
													"Filter");
								}
							}
						});
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (!TextUtils.isEmpty(errorMsg)) {
				Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
			}else{
				productsAdapter.notifyDataSetChanged();
			}

		}
	}

}
