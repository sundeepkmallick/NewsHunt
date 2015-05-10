package com.ecommhunt.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ecommhunt.R;
import com.ecommhunt.model.PriceRange;
import com.ecommhunt.util.Utils;


public class FilterAdapter extends BaseAdapter implements
		OnCheckedChangeListener {

	Context context;
	ArrayList<PriceRange> listPriceRanges;
	LayoutInflater mInflater;
	public SparseBooleanArray mCheckStates;
	HashMap<String, Boolean> isPriceRangesSaved;

	public FilterAdapter(Context context,
			ArrayList<PriceRange> listPriceRanges,
			HashMap<String, Boolean> isPriceRangesSaved) {
		this.context = context;
		this.listPriceRanges = listPriceRanges;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mCheckStates = new SparseBooleanArray(listPriceRanges.size());
		this.isPriceRangesSaved = isPriceRangesSaved;
	}

	@Override
	public int getCount() {
		return listPriceRanges.size();
	}

	@Override
	public Object getItem(int position) {
		return listPriceRanges.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.dialog_filter_item, null);
			holder.txtPriceRange = (TextView) convertView
					.findViewById(R.id.textViewPrice);
			holder.chkPriceRange = (CheckBox) convertView
					.findViewById(R.id.checkBoxPrice);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtPriceRange.setText(Utils.getINRSymbol(context)
				+ listPriceRanges.get(position).getMin() + " - "
				+ Utils.getINRSymbol(context)
				+ listPriceRanges.get(position).getMax());

		holder.chkPriceRange.setTag(position);
		boolean isPriceRangeSaved = isPriceRangesSaved.get(listPriceRanges.get(position).getMin()
				+ "-" + listPriceRanges.get(position).getMax());
		if(isPriceRangeSaved){
			setChecked(position, isPriceRangeSaved);
		}
		holder.chkPriceRange.setChecked(mCheckStates.get(position, isPriceRangeSaved));
		
		//if price range is already selected
		//holder.chkPriceRange.setChecked();
		holder.chkPriceRange.setOnCheckedChangeListener(this);

		return convertView;
	}

	public static class ViewHolder {
		CheckBox chkPriceRange;
		TextView txtPriceRange;
	}

	public boolean isChecked(int position) {
		return mCheckStates.get(position, false);
	}

	public void setChecked(int position, boolean isChecked) {
		mCheckStates.put(position, isChecked);

	}

	public void toggle(int position) {
		setChecked(position, !isChecked(position));

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mCheckStates.put((Integer) buttonView.getTag(), isChecked);
	}

}
