package com.ecommhunt;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.ecommhunt.adapter.MenuCategoryAdapter;
import com.ecommhunt.db.DBOperations;
import com.ecommhunt.model.Catalog;
import com.ecommhunt.util.ConnectionUtils;
import com.ecommhunt.util.Utils;

public abstract class BaseActivity extends ActionBarActivity implements
		DrawerListener {

	private ArrayList<Catalog> mCatalogList;
	protected Context mContext;
	private boolean mToSetDrawer = true;
	protected ActionBar mActionBar;
	protected Menu mOptionMenu;
	private int mNumAddedProductToCart = 0;
	private ExpandableListView mDrawerExtendedList;
	private Bundle mSavedInstanceState;
	protected int mTitle;
	protected LayoutInflater mLayoutInflater;
	private ViewGroup mBaseView;
	protected ViewGroup mBaseLayout;
	private SearchView mSearchView;
	private int mPreviousGroup = -1;
	private int actionBarMenuState;
	// private BaseActivityHelper helper;
	private TextSwitcher mTxtAddedProductToCart = null;
	private ConnectionUtils mConnectionUtils;
	// private RelativeLayout mProgressContainer;

	private ArrayList<String> mMenuExtras;

	private ArrayList<Class> mMenuIntents;

	@Override
	protected void onResume() {
		supportInvalidateOptionsMenu();
		super.onResume();
	}

	@SuppressLint("NewApi")
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (mToSetDrawer) {
			int myLeft = mDrawerExtendedList.getRight() - 40;
			int myRight = mDrawerExtendedList.getWidth();
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
				mDrawerExtendedList.setIndicatorBounds(myLeft, myRight);
			} else {
				mDrawerExtendedList.setIndicatorBoundsRelative(myLeft, myRight);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		mMenuExtras = new ArrayList<String>();
		mMenuIntents = new ArrayList<Class>();
		mMenuExtras.add(Utils.getStringDataBasedOnLanguage(mContext,
				R.string.policies));
		mMenuIntents.add(MainActivity.class);
		mMenuExtras.add(Utils.getStringDataBasedOnLanguage(mContext,
				R.string.help));
		mMenuIntents.add(MainActivity.class);
		mMenuExtras.add(Utils.getStringDataBasedOnLanguage(mContext,
				R.string.invite_friends));
		mMenuIntents.add(MainActivity.class);
		mMenuExtras.add(Utils.getStringDataBasedOnLanguage(mContext,
				R.string.rate_this_app));
		mMenuIntents.add(MainActivity.class);
		mMenuExtras.add(Utils.getStringDataBasedOnLanguage(mContext,
				R.string.login));
		mMenuIntents.add(LoginActivity.class);

		mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int chileLay = getLayoutResource();
		if (chileLay > 0) {
			mBaseView = (ViewGroup) mLayoutInflater.inflate(chileLay, null);
		} else {
			mBaseView = (ViewGroup) mLayoutInflater.inflate(
					R.layout.base_activity, null);
		}

		setContentView(mBaseView);

		if (mToSetDrawer) {
			mBaseLayout = (RelativeLayout) mBaseView
					.findViewById(R.id.base_frame_container);
			mCatalogList = new ArrayList<Catalog>();
			if (mToSetDrawer) {
				setupDrawerMenu();
			}
		}

		/*
		 * Toolbar toolbar = (Toolbar) mBaseView
		 * .findViewById(R.id.my_awesome_toolbar); setSupportActionBar(toolbar);
		 * 
		 * Drawable myIcon = this.getResources().getDrawable(
		 * R.drawable.hnglogotoolbar); toolbar.setLogo(myIcon);
		 * toolbar.setNavigationOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (mToSetDrawer) { // random
		 * state actionBarMenuState = helper .generateState(actionBarMenuState);
		 * materialMenu .animatePressedState(intToState(actionBarMenuState));
		 * helper.openCloseDrawer(); } else { onBackPressed(); } } });
		 * 
		 * materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE,
		 * Stroke.THIN) {
		 * 
		 * @Override public int getToolbarViewId() { return
		 * R.id.my_awesome_toolbar; } };
		 * 
		 * materialMenu.setNeverDrawTouch(true); helper = new
		 * BaseActivityHelper(); if (mToSetDrawer) { // Init menu button
		 * helper.init(getWindow().getDecorView(), materialMenu); } else { //
		 * Set the back button materialMenu.animateState(IconState.ARROW); }
		 */

		this.mSavedInstanceState = savedInstanceState;
		// mActionBar = getSupportActionBar();
		if (mActionBar != null) {
			// mActionBar.show();
		}

	}

	public abstract int getLayoutResource();

	private void setupDrawerMenu() {
		/*
		 * mProgressContainer = (RelativeLayout) mBaseView
		 * .findViewById(R.id.progress_container);
		 */
		mDrawerExtendedList = (ExpandableListView) mBaseView
				.findViewById(R.id.base_list_slidermenuext);
		mDrawerExtendedList.setAdapter(new MenuCategoryAdapter(mContext,
				mCatalogList, onChildClickListener, null));
		// mProgressContainer.setVisibility(View.GONE);
		mDrawerExtendedList.setVisibility(View.VISIBLE);
		setUpExtraMenus();
		mDrawerExtendedList.setGroupIndicator(null);
		mDrawerExtendedList
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						if (groupPosition < mCatalogList.size()) {
							if (!mCatalogList.get(groupPosition)
									.isIsExtraMenu()) {
								if (groupPosition != mPreviousGroup)
									mDrawerExtendedList
											.collapseGroup(mPreviousGroup);
							}
							mPreviousGroup = groupPosition;
						}

					}
				});

		mDrawerExtendedList.setOnChildClickListener(onChildClickListener);

		mDrawerExtendedList.setOnGroupClickListener(new OnGroupClickListener() {
			int previousGroup = -1;

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				if (groupPosition < mCatalogList.size()) {
					if (mCatalogList.get(groupPosition).isIsExtraMenu()) {
						if (previousGroup != -1
								&& groupPosition != previousGroup
								&& mCatalogList.size() > previousGroup
								&& !mCatalogList.get(previousGroup)
										.isIsExtraMenu()) {
							mDrawerExtendedList.collapseGroup(previousGroup);
						}

						if (mCatalogList.get(groupPosition).getCatalogName()
								.equalsIgnoreCase("Logout")) {

							// showLogoutDialog();
							previousGroup = groupPosition;
							// helper.openCloseDrawer();
							// mDrawerLayout.closeDrawers();
						} else if (mCatalogList.get(groupPosition)
								.getCatalogName().equalsIgnoreCase("Login")) {
							Intent launch = new Intent(mContext, mCatalogList
									.get(groupPosition).getClassName());

							startActivityForResult(launch, 1001);
							previousGroup = groupPosition;
							// mDrawerLayout.closeDrawers();
							// helper.openCloseDrawer();
						} else if (mCatalogList.get(groupPosition)
								.getCatalogName()
								.equalsIgnoreCase("Invite friends")) {
							/*
							 * ShareUtils.GeneralShareForStoreLocator(mContext,
							 * "Hi!! Check out this amazing app at ",
							 * "googleplay.com/app");
							 */

						} else if (mCatalogList.get(groupPosition)
								.getCatalogName()
								.equalsIgnoreCase("Rate this app")) {
							/*
							 * if (Utils.isUserLoggedIn(mContext)) { Uri uri =
							 * Uri.parse("market://details?id=" +
							 * mContext.getPackageName()); Intent goToMarket =
							 * new Intent( Intent.ACTION_VIEW, uri); try {
							 * startActivity(goToMarket); } catch
							 * (ActivityNotFoundException e) { startActivity(new
							 * Intent( Intent.ACTION_VIEW, Uri.parse(
							 * "http://play.google.com/store/apps/details?id=" +
							 * mContext.getPackageName()))); } }
							 */

						}

						else {
							Intent launch = new Intent(mContext, mCatalogList
									.get(groupPosition).getClassName());
							startActivity(launch);
							previousGroup = groupPosition;
							// mDrawerLayout.closeDrawers();
							// helper.openCloseDrawer();
						}

						return false;
					}
				}
				return false;
			}
		});
		if (mActionBar == null) {
			// mActionBar = getSupportActionBar();
		}
		if (mActionBar != null) {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
		}

		if (mSavedInstanceState == null) {
			displayView(0);
		}
	}

	OnChildClickListener onChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

			System.out.println("previousGroup :: " + mPreviousGroup
					+ " :groupPosition: " + groupPosition + " :childPosition: "
					+ childPosition);

			String categoryId = mCatalogList.get(mPreviousGroup)
					.getCategoryList().get(groupPosition).getSubCategoryList()
					.get(childPosition).getSubCategoryId();

			String categoryName = mCatalogList.get(mPreviousGroup)
					.getCategoryList().get(groupPosition).getSubCategoryList()
					.get(childPosition).getSubCategoryName();

			Intent searchIntent = new Intent(mContext,
					ProductListActivity.class);

			searchIntent.putExtra("mSearchedQuery", categoryId);
			searchIntent.putExtra("categoryName", categoryName);
			searchIntent.putExtra("previousGroup",
					String.valueOf(mPreviousGroup));

			startActivity(searchIntent);
			mDrawerExtendedList.collapseGroup(mPreviousGroup);
			// mDrawerLayout.closeDrawers();
			// helper.openCloseDrawer();
			// If the OtherProductsActivity is launching from
			// OtherProductsActivity itself then finish the 1st activity.
			if (mContext instanceof ProductListActivity) {
				finish();
			}
			return true;
		}
	};

	public void setUpExtraMenus() {

		for (int i = 0; i < mMenuExtras.size(); i++) {

			/*
			 * if (mMenuExtras[i].equalsIgnoreCase("Invite Friends")) { Catalog
			 * catalog2 = new Catalog();
			 * catalog2.setCatalogName("Invite Friends");
			 * catalog2.setIsExtraMenu(true);
			 * catalog2.setmIsExtraMenuAdded(true); mCatalogList.add(catalog2);
			 * continue;
			 * 
			 * }
			 */
			Catalog catalog2 = new Catalog();
			catalog2.setCatalogName(mMenuExtras.get(i));
			catalog2.setIsExtraMenu(true);
			catalog2.setClassName(mMenuIntents.get(i));
			catalog2.setmIsExtraMenuAdded(true);

			mCatalogList.add(catalog2);

			/*
			 * mUserName = Utils.getPreferenceData(getBaseContext(),
			 * Utils.PREF_FIRST_NAME); if (!TextUtils.isEmpty(mUserName) &&
			 * mMenuExtras[i].equalsIgnoreCase(ConstantsParams.LOGIN)) {
			 * catalog2.setCatalogName(ConstantsParams.LOG_OUT); } else {
			 * catalog2.setCatalogName(mMenuExtras[i]); }
			 * 
			 * if (Utils.isUserLoggedIn(mContext)) { // Then only add the My
			 * Address mCatalogList.add(catalog2); } else if
			 * (!Utils.isUserLoggedIn(mContext) && (mMenuExtras[i]
			 * .equalsIgnoreCase(ConstantsParams.MY_ADDRESS) || mMenuExtras[i]
			 * .equalsIgnoreCase(ConstantsParams.MY_ORDERS) || mMenuExtras[i]
			 * .equalsIgnoreCase(ConstantsParams.MY_WISHLIST)) || mMenuExtras[i]
			 * .equalsIgnoreCase(ConstantsParams.RATETHISAPP)) {
			 * 
			 * Do not add My Address / My Order / My Wish list /Rate this App if
			 * user is not loggedin
			 * 
			 * }
			 * 
			 * else { mCatalogList.add(catalog2); }
			 */
		}
	}

	private void displayView(int position) {

	}

	public void hideHomeActionMenu(boolean toHide) {
		if (toHide) {
			MenuItem home = mOptionMenu.findItem(R.id.action_home);
			home.setVisible(false);
		}
	}

	SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
		@Override
		public boolean onQueryTextChange(String newText) {
			if (newText != null && newText.length() >= 3) {

				return true;
			}
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			return false;
		}
	};

	public void updateHotCount(final int new_hot_number) {
		mNumAddedProductToCart = new_hot_number;
		if (mTxtAddedProductToCart == null)
			return;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (new_hot_number == 0)
					mTxtAddedProductToCart.setVisibility(View.INVISIBLE);
				else {
					mTxtAddedProductToCart.setInAnimation(mContext,
							android.R.anim.fade_in);
					mTxtAddedProductToCart.setOutAnimation(mContext,
							android.R.anim.fade_out);
					mTxtAddedProductToCart.setVisibility(View.VISIBLE);
					mTxtAddedProductToCart.setText(String
							.valueOf(new_hot_number));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.dashboard, menu);
			this.mOptionMenu = menu;
			SearchManager searchManager = (SearchManager) getSystemService(mContext.SEARCH_SERVICE);
			mSearchView = (SearchView) menu.findItem(R.id.action_search)
					.getActionView();
			mSearchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			mSearchView.setOnQueryTextListener(queryTextListener);

			mSearchView.setOnSuggestionListener(new OnSuggestionListener() {

				@Override
				public boolean onSuggestionSelect(int arg0) {
					return false;
				}

				@Override
				public boolean onSuggestionClick(int position) {
					return true;
				}
			});

			mSearchView.setQueryHint("Search");

			MenuItem menuItemCart = menu.findItem(R.id.action_cart)
					.setActionView(R.layout.action_bar_notifitcation_icon);
			View menuItemCartView = menuItemCart.getActionView();
			mTxtAddedProductToCart = (TextSwitcher) menuItemCartView
					.findViewById(R.id.hotlist_hot);
			mTxtAddedProductToCart.setFactory(new ViewFactory() {
				@Override
				public View makeView() {
					TextView myText = new TextView(mContext);
					myText.setTextColor(Color.WHITE);
					myText.setTextSize(12);
					return myText;
				}
			});

			mNumAddedProductToCart = DBOperations.countCartItems(mContext,
					Utils.getPreferenceData(mContext, Utils.PREF_EMAIL));

			updateHotCount(mNumAddedProductToCart);
			new MyMenuItemStuffListener(menuItemCartView,
					"Showing added products") {
				@Override
				public void onClick(View v) {
					/*
					 * Intent intent = new Intent(mContext, MyCart.class);
					 * startActivity(intent);
					 */
				}
			};
		} else {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.dashboard, menu);
			this.mOptionMenu = menu;
			MenuItem item = menu.findItem(R.id.action_cart);
			MenuItemCompat.setActionView(item,
					R.layout.action_bar_notifitcation_icon);
			View menuItemCartView = MenuItemCompat.getActionView(item);
			mTxtAddedProductToCart = (TextSwitcher) menuItemCartView
					.findViewById(R.id.hotlist_hot);
			mTxtAddedProductToCart.setFactory(new ViewFactory() {
				@Override
				public View makeView() {
					TextView myText = new TextView(mContext);
					myText.setTextColor(Color.WHITE);
					myText.setTextSize(12);
					return myText;
				}
			});

			mNumAddedProductToCart = DBOperations.countCartItems(mContext,
					Utils.getPreferenceData(mContext, Utils.PREF_EMAIL));

			updateHotCount(mNumAddedProductToCart);
			new MyMenuItemStuffListener(menuItemCartView,
					"Showing added products") {
				@Override
				public void onClick(View v) {
					/*
					 * Intent intent = new Intent(mContext, MyCart.class);
					 * startActivity(intent);
					 */
				}
			};
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mToSetDrawer) {
		}

		switch (item.getItemId()) {
		case R.id.action_search:
			doSearch();
			return true;
		case R.id.action_cart:
			openMyCart();
			return true;
		case android.R.id.home:
			// finish();
			onBackPressed();
			return true;
		case R.id.action_home:
			onHomePressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void onHomePressed() {
		Intent launchHome = new Intent(mContext, DashboardActivity.class);
		launchHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(launchHome);
	}

	protected void openMyCart() {
		/*
		 * Intent intent = new Intent(mContext, MyCart.class);
		 * startActivity(intent);
		 */
	}

	protected void doSearch() {
		/*
		 * Intent intent = new Intent(mContext, SearchProducts.class);
		 * startActivity(intent);
		 */
	}

	public boolean isInternetConnected() {
		boolean isInternetConnected = false;

		mConnectionUtils = new ConnectionUtils(mContext);
		isInternetConnected = mConnectionUtils.isConnectingToInternet();

		return isInternetConnected;
	}

	protected void setDrawerMenu(boolean toSet) {
		this.mToSetDrawer = toSet;
	}

	protected void setActionImage(int image) {
		if (mActionBar != null) {
			mActionBar.setIcon(image);
		}
	}

	protected void enableBackButton(boolean enable) {
		if (mActionBar != null) {
			mActionBar.setDisplayHomeAsUpEnabled(enable);
		}
	}

	public void hideSearchActionMenu(boolean toHide) {
		if (toHide) {
			MenuItem home = mOptionMenu.findItem(R.id.action_search);
			home.setVisible(false);
		}
	}

	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}
}
