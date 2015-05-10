package com.ecommhunt.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ecommhunt.R;
import com.ecommhunt.util.ConstantsParams;

public class SortDialog extends DialogFragment implements OnClickListener{

	LinearLayout layoutPriceHighToLow;
	LinearLayout layoutPriceLowToHigh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.dialog_sort, container, false);
		getDialog().setTitle(getResources().getString(R.string.sort_by));

		layoutPriceHighToLow = (LinearLayout) rootView
				.findViewById(R.id.layoutPriceHighToLow);
		layoutPriceLowToHigh = (LinearLayout) rootView
				.findViewById(R.id.layoutPriceLowToHigh);
		
		layoutPriceHighToLow.setOnClickListener(this);
		layoutPriceLowToHigh.setOnClickListener(this);

		return rootView;
	}
	
	public interface SortDialogListener {
	    public void onSortSelected(String sort_by, String sort_order);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		SortDialogListener activity = (SortDialogListener) getActivity();
		switch(id){
			case R.id.layoutPriceHighToLow:
				activity.onSortSelected(ConstantsParams.PRICE, ConstantsParams.DESC);
				dismiss();
				break;
			case R.id.layoutPriceLowToHigh:
				activity.onSortSelected(ConstantsParams.PRICE, ConstantsParams.ASC);
				dismiss();
				break;
			default:
				
				break;
		}
	}
	
	
}
