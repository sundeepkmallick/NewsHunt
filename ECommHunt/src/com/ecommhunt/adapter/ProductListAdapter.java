package com.ecommhunt.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommhunt.R;
import com.ecommhunt.db.DBOperations;
import com.ecommhunt.model.Product;
import com.ecommhunt.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ProductListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Product> data;
	LayoutInflater mInflater;
	DisplayImageOptions options;

	public ProductListAdapter(Context c, ArrayList<Product> basicList) {
		mContext = c;
		data = basicList;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_empty)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder grid;

		if (convertView == null) {
			grid = new ViewHolder();
			convertView = mInflater.inflate(R.layout.other_products_list_item,
					null);
			grid.mHeaderText = (TextView) convertView
					.findViewById(R.id.header_text);
			grid.header_sub_text = (TextView) convertView
					.findViewById(R.id.header_sub_text);
			grid.tv_offer_price = (TextView) convertView
					.findViewById(R.id.tv_offer_price);
			grid.tv_original_price = (TextView) convertView
					.findViewById(R.id.tv_original_price);
			grid.offer_image = (ImageView) convertView
					.findViewById(R.id.img_Product);
			/*
			 * grid.BtnShare = (ImageView) convertView
			 * .findViewById(R.id.imageViewShare);
			 */
			grid.txtOfferTittle = (TextView) convertView
					.findViewById(R.id.textView3);
			grid.btnBuyNow = (Button) convertView
					.findViewById(R.id.buttonBuyNow);
			grid.relative_other = (RelativeLayout) convertView
					.findViewById(R.id.relative_other);
			grid.txtStockAvailable = (TextView) convertView
					.findViewById(R.id.txt_stock);
			convertView.setTag(grid);
		} else {
			grid = (ViewHolder) convertView.getTag();
		}
		grid.mHeaderText.setText(data.get(position).getProductName());
		// grid.header_sub_text.setText(data.get(position).getProductOfferDesc());

		int stockQuantity;
		stockQuantity = Utils
				.parseInt(data.get(position).getProductAvailableUnit());
		if (stockQuantity > 0) {
			grid.txtStockAvailable.setText(mContext.getResources().getString(
					R.string.in_stock));
			grid.txtStockAvailable.setTextColor(mContext.getResources()
					.getColor(R.color.txt_black));
		} else {
			grid.txtStockAvailable.setText(mContext.getResources().getString(
					R.string.out_of_stock));
			grid.txtStockAvailable.setTextColor(mContext.getResources()
					.getColor(R.color.red));
		}

		if (Utils.parsePrice(data.get(position).getProductPrice())
				.equalsIgnoreCase(Utils.parsePrice(data.get(position).getProductOfferPrice()))) {
			grid.tv_offer_price.setText("");
			grid.txtOfferTittle.setText("");
			String original_price = mContext.getResources().getString(R.string.Rs)+" "
					+ Utils.parsePrice(data.get(position).getProductPrice());
			grid.tv_original_price.setText(original_price);

		} else {
			grid.txtOfferTittle.setText("Our Price: ");
			String offer_price = mContext.getResources().getString(R.string.Rs)+" "
					+ Utils.parsePrice(data.get(position).getProductOfferPrice());
			grid.tv_offer_price.setText(offer_price);
			String original_price = mContext.getResources().getString(R.string.Rs)+" "
					+ Utils.parsePrice(data.get(position).getProductPrice());
			grid.tv_original_price.setText(original_price);
		}

		/*
		 * final ProgressBar progressBar = (ProgressBar) convertView
		 * .findViewById(R.id.progress);
		 */
		ImageView offer_image = grid.offer_image;
		String imagePath = data.get(position)
				.getProductImageUrls();
		if (!TextUtils.isEmpty(imagePath)) {

			ImageLoader.getInstance().displayImage(imagePath, offer_image,
					options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							/*
							 * progressBar.setProgress(0);
							 * progressBar.setVisibility(View.VISIBLE);
							 */
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							// progressBar.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri,
								View view, int current, int total) {
							/*
							 * progressBar.setProgress(Math.round(100.0f *
							 * current / total));
							 */
						}
					});
		}
		/*
		 * grid.BtnShare.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * ShareUtils.GeneralShare(mContext, data.get(position) .getProductName(),
		 * data.get(position).getProductCurrency() + " " +
		 * data.get(position).getProductOfferPrice(), data
		 * .get(position).getProductImageUrls());
		 * 
		 * } });
		 */

		grid.btnBuyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int totalItemsTobeAdded = totalItemsTobeAdded(data
						.get(position).getProductId(), data.get(position)
						.getProductAvailableUnit());
				if (totalItemsTobeAdded > 0) {
					if (DBOperations.addMultipleItemToBag(
							mContext,
							Utils.getPreferenceData(mContext, Utils.PREF_EMAIL),
							data.get(position).getProductId(), totalItemsTobeAdded)) {
						((ActionBarActivity) mContext)
								.supportInvalidateOptionsMenu();
						// Intent intent = new Intent(mContext, MyCart.class);
						// mContext.startActivity(intent);
						Toast.makeText(
								mContext,
								data.get(position).getProductName() + " One item added to cart!!",
								Toast.LENGTH_SHORT).show();

					}
				}
			}
		});

		grid.relative_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*Intent podDetails = new Intent(mContext, ProductDetails.class);
				podDetails.putExtra(ConstantsParams.BUNDLE_PRODUCT,
						data.get(position));
				mContext.startActivity(podDetails);*/

			}
		});
		return convertView;
	}

	private class ViewHolder {
		public TextView mHeaderText;
		public TextView header_sub_text;
		public TextView tv_offer_price, txtOfferTittle;
		public TextView tv_original_price;
		public ImageView offer_image;
		public ImageView BtnShare;
		public Button btnBuyNow;
		public RelativeLayout relative_other;
		public TextView txtStockAvailable;
	}

	public void filter(String charText) {
		// charText = charText.toLowerCase(Locale.getDefault());
		// data.clear();
		// if (charText.length() == 0) {
		// data.addAll(arraylist);
		// } else {
		// for (OfferBean wp : arraylist) {
		// if (wp.getOffertittle().toLowerCase().contains(charText)) {
		// data.add(wp);
		// }
		// }
		// }
		notifyDataSetChanged();
	}

	private int totalItemsTobeAdded(String skuId, String stockQuantityStr) {
		int totalItemsTobeAdded = -1;

		int numAlreadyInCart = DBOperations.countCartItem(mContext,
				Utils.getPreferenceData(mContext, Utils.PREF_EMAIL), skuId);
		// 1 item to be added when clicking on Buy Now button
		totalItemsTobeAdded = numAlreadyInCart + 1;

		int stockQuantity = Utils.parseInt(stockQuantityStr);
		if (totalItemsTobeAdded >= stockQuantity) {
			totalItemsTobeAdded = -1;
			Toast.makeText(mContext,
					"Qunatity selected is more than stock available!",
					Toast.LENGTH_SHORT).show();
		}

		return totalItemsTobeAdded;
	}
}