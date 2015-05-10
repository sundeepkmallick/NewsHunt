package com.ecommhunt.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ecommhunt.R;
import com.ecommhunt.adapter.FilterAdapter;
import com.ecommhunt.model.PriceRange;
import com.ecommhunt.util.ConstantsParams;

public class FilterDialog extends DialogFragment implements OnClickListener {

	ListView listFilterPrice;
	Button btnFilter;
	Button btnReset;
	int min = 0;
	int max = 0;
	int COUNT_SPLIT_RANGE = 5;
	ArrayList<PriceRange> listPriceRanges;
	ArrayList<PriceRange> selectedPriceRanges;
	FilterAdapter adapterFilter;
	ArrayList<PriceRange> savedPriceRanges;
	HashMap<String, Boolean> isPriceRangesSaved;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (getArguments().containsKey(ConstantsParams.BUNDLE_SAVED_PRICE_RANGES)) {
			savedPriceRanges = getArguments().getParcelableArrayList(ConstantsParams.BUNDLE_SAVED_PRICE_RANGES);
		}

		if (getArguments().containsKey(ConstantsParams.MIN)) {
			min = getArguments().getInt(ConstantsParams.MIN, 0);
		}

		if (getArguments().containsKey(ConstantsParams.MAX)) {
			max = getArguments().getInt(ConstantsParams.MAX, 0);
		}

		if (min == max) {
			Toast.makeText(getActivity(), "",
					Toast.LENGTH_LONG).show();
			dismiss();
		} else if (min > max) {
			Toast.makeText(getActivity(),
					"", Toast.LENGTH_LONG)
					.show();
			dismiss();
		} else {
			isPriceRangesSaved = new HashMap<String, Boolean>();
			listPriceRanges = new ArrayList<PriceRange>();
			int length_of_range = (max - min + 1) / COUNT_SPLIT_RANGE;

			for (int i = 0; i < COUNT_SPLIT_RANGE; i++) {
				int start_of_range = min + (length_of_range * i);
				int end_of_range = (i != COUNT_SPLIT_RANGE-1) ? (start_of_range
						+ length_of_range - 1)
						: (start_of_range + length_of_range);

				PriceRange priceRange = new PriceRange();
				priceRange.setMin(start_of_range);
				priceRange.setMax(end_of_range);
				listPriceRanges.add(priceRange);
				isPriceRangesSaved.put(start_of_range+"-"+end_of_range, false);
			}
			
			if(savedPriceRanges!=null && savedPriceRanges.size()>0)
			for(int i = 0; i < savedPriceRanges.size(); i++){
				isPriceRangesSaved.put(savedPriceRanges.get(i).getMin()+"-"+savedPriceRanges.get(i).getMax(), true);
			}
			
		}

		View rootView = inflater.inflate(R.layout.dialog_filter, container,
				false);
		getDialog().setTitle(getResources().getString(R.string.filter));

		listFilterPrice = (ListView) rootView
				.findViewById(R.id.listViewFilterPrice);
		btnFilter = (Button) rootView.findViewById(R.id.buttonFilter);
		btnFilter.setOnClickListener(this);
		
		btnReset = (Button) rootView.findViewById(R.id.buttonReset);
		btnReset.setOnClickListener(this);

		if (listPriceRanges.size() > 0) {
			adapterFilter = new FilterAdapter(getActivity(),
					listPriceRanges, isPriceRangesSaved);
			listFilterPrice.setAdapter(adapterFilter);
		}

		return rootView;
	}

	public interface FilterDialogListener {
		public void onFilterButtonClicked(ArrayList<PriceRange> selectedPriceRanges);
		public void onResetButtonClicked();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		FilterDialogListener activity = (FilterDialogListener) getActivity();
		switch (id) {
		case R.id.buttonFilter:
			selectedPriceRanges = new ArrayList<PriceRange>();
			SparseBooleanArray arrayCheckStates = adapterFilter.mCheckStates;
            for(int i=0;i<arrayCheckStates.size();i++){
            	int key = arrayCheckStates.keyAt(i);
                if(arrayCheckStates.get(key)){
                	selectedPriceRanges.add(listPriceRanges.get(key));
                	Log.d("TAG","MIN: "+listPriceRanges.get(key).getMin()+", MAX: "+listPriceRanges.get(key).getMax());
                }
            }
            activity.onFilterButtonClicked(selectedPriceRanges);
        	dismiss();
			break;
		case R.id.buttonReset:
			activity.onResetButtonClicked();
        	dismiss();
			break;
		default:

			break;
		}
	}

}
