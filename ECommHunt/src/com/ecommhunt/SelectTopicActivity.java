package com.ecommhunt;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.ecommhunt.adapter.MenuCategoryAdapter;
import com.ecommhunt.model.Catalog;
import com.ecommhunt.model.ResponseData;
import com.ecommhunt.parser.JsonParser;
import com.ecommhunt.util.ConstantsParams;

public class SelectTopicActivity extends BaseActivity {

	Context context;
	ExpandableListView mDrawerExtendedList;
	ArrayList<Catalog> mCategoryList;
	ProgressDialog progressDialog;
	MenuCategoryAdapter adapterMenuCategory;
	private int mPreviousGroup = -1;

	EditText txtSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setDrawerMenu(true);
		super.onCreate(savedInstanceState);
		mLayoutInflater.inflate(R.layout.activity_select_topic, mBaseLayout);
		// set content view should not be called if used inflater.
		setTitle("Select Topic");
		// setContentView(R.layout.activity_my_cart);
		enableBackButton(true);

		context = SelectTopicActivity.this;
		progressDialog = new ProgressDialog(context);
		mCategoryList = new ArrayList<Catalog>();

		mDrawerExtendedList = (ExpandableListView) findViewById(R.id.expandableListViewCategory);
		txtSearch = (EditText) findViewById(R.id.editTextSearch);

		adapterMenuCategory = new MenuCategoryAdapter(mContext, mCategoryList,
				childClickListener, handler);
		mDrawerExtendedList.setAdapter(adapterMenuCategory);
		mDrawerExtendedList
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						if (groupPosition < mCategoryList.size()) {
							if (!mCategoryList.get(groupPosition)
									.isIsExtraMenu()) {
								if (groupPosition != mPreviousGroup)
									mDrawerExtendedList
											.collapseGroup(mPreviousGroup);
							}
							mPreviousGroup = groupPosition;
						}

					}
				});

		mDrawerExtendedList.setOnGroupClickListener(new OnGroupClickListener() {
			int previousGroup = -1;

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				if (groupPosition < mCategoryList.size()) {
					if (mCategoryList.get(groupPosition).isIsExtraMenu()) {
						if (previousGroup != -1
								&& groupPosition != previousGroup
								&& mCategoryList.size() > previousGroup
								&& !mCategoryList.get(previousGroup)
										.isIsExtraMenu()) {
							mDrawerExtendedList.collapseGroup(previousGroup);
						}

						Intent launch = new Intent(mContext, mCategoryList.get(
								groupPosition).getClassName());
						startActivity(launch);
						previousGroup = groupPosition;

						return false;
					}
				}
				return false;
			}
		});

		txtSearch.addTextChangedListener(filterTextWatcher);

		GetCategoryListAsync categoryListAsync = new GetCategoryListAsync();
		categoryListAsync.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean superOption = super.onCreateOptionsMenu(menu);
		hideSearchActionMenu(true);

		return superOption;
	}

	@Override
	public int getLayoutResource() {
		return -1;
	}

	TextWatcher filterTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			adapterMenuCategory.filterData(txtSearch.getText().toString());
		}
	};

	private class GetCategoryListAsync extends
			AsyncTask<Void, Void, ArrayList<Catalog>> {

		String errorMsg = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (progressDialog != null && !progressDialog.isShowing()) {
				progressDialog.setTitle("Please wait!!");
				progressDialog.setMessage("Loading topics..");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}

		}

		@Override
		protected ArrayList<Catalog> doInBackground(Void... params) {
			String response = "";
			ArrayList<Catalog> result = new ArrayList<Catalog>();
			response = JsonParser.loadJSONFromAsset(mContext, "category.json");

			if (!TextUtils.isEmpty(response)) {
				ResponseData responseData = JsonParser.parseResponse(response);
				if (responseData.getStatus().equalsIgnoreCase(
						ConstantsParams.PASS)) {
					String data = responseData.getData();
					result = JsonParser.parseCatalog(context, data);
				} else {
					errorMsg = responseData.getError();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<Catalog> result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (!TextUtils.isEmpty(errorMsg)) {
				Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
			} else {
				if (result != null && result.size() > 0) {
					mCategoryList.clear();
					mCategoryList.addAll(result);
					adapterMenuCategory.setOriginalCatalogList(result);
					adapterMenuCategory.notifyDataSetChanged();
				}
			}

		}
	}

	OnChildClickListener childClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

			System.out.println("previousGroup :: " + mPreviousGroup
					+ " :groupPosition: " + groupPosition + " :childPosition: "
					+ childPosition);

			String categoryId = mCategoryList.get(mPreviousGroup)
					.getCategoryList().get(groupPosition).getSubCategoryList()
					.get(childPosition).getSubCategoryId();

			String categoryName = mCategoryList.get(mPreviousGroup)
					.getCategoryList().get(groupPosition).getSubCategoryList()
					.get(childPosition).getSubCategoryName();

			/*Intent searchIntent = new Intent(mContext,
					TopicDicsussion.class);

			searchIntent.putExtra("mSearchedQuery", categoryId);
			searchIntent.putExtra("categoryName", categoryName);
			searchIntent.putExtra("previousGroup",
					String.valueOf(mPreviousGroup));

			startActivity(searchIntent);*/
			mDrawerExtendedList.collapseGroup(mPreviousGroup);
			// If the OtherProductsActivity is launching from
			// OtherProductsActivity itself then finish the 1st activity.
			/*
			 * if (mContext instanceof OtherProductsActivity) { finish(); }
			 */
			return true;
		}
	};

	Handler handler = new Handler(new Handler.Callback() {
		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {

			case MenuCategoryAdapter.FILTER_CATALOG_LIST:
				int arg1 = msg.arg1;
				int arg2 = msg.arg2;
				if (arg1 == 1) {
					Bundle bundle = msg.getData();

					ArrayList<Catalog> filtered_result = (ArrayList<Catalog>) bundle
							.getSerializable(ConstantsParams.BUNDLE_CATALOG_LIST);

					if (filtered_result != null && filtered_result.size() > 0) {
						mCategoryList.clear();
						mCategoryList.addAll(filtered_result);

						if (arg2 == 1) {
							adapterMenuCategory.setExpanded(true);
							for (int i = 0; i < adapterMenuCategory
									.getGroupCount(); i++) {
								mDrawerExtendedList.expandGroup(i);
							}
						} else if (arg2 == 0) {
							adapterMenuCategory.setExpanded(false);
							for (int i = 0; i < adapterMenuCategory
									.getGroupCount(); i++) {
								mDrawerExtendedList.collapseGroup(i);
							}
						}

						adapterMenuCategory.notifyDataSetChanged();
					}
				}
				break;
			default:

				break;
			}
			return true;
		}
	});

}
