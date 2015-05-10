package com.ecommhunt.util;

import android.content.Context;
import android.widget.ExpandableListView;

public class CustExpListview extends ExpandableListView {
	int intGroupPosition, intChildPosition, intGroupid;

	public CustExpListview(Context context) {
		super(context);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*widthMeasureSpec = MeasureSpec.makeMeasureSpec(960,
				MeasureSpec.AT_MOST);*/
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(7000,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}