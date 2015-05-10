package com.ecommhunt.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ecommhunt.R;
import com.ecommhunt.model.Category;

public class MenuSubcategoryAdapter extends BaseExpandableListAdapter {

	Context context;
	ArrayList<Category> categoryList;

	public MenuSubcategoryAdapter(Context context,
			ArrayList<Category> categoryList) {
		this.context = context;
		this.categoryList = categoryList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return categoryList.get(groupPosition).getSubCategoryList()
				.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String headerTitle = categoryList.get(groupPosition)
				.getSubCategoryList().get(childPosition).getSubCategoryName();
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater
					.inflate(R.layout.list_subcategory, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setText(headerTitle);
		lblListHeader.setTextColor(context.getResources().getColor(
				R.color.txt_blue_sh));

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return categoryList.get(groupPosition).getSubCategoryList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return categoryList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return categoryList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_category, null);
			holder = new ViewHolder();
			holder.lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String headerTitle = categoryList.get(groupPosition).getCategoryName();
		holder.lblListHeader.setText(headerTitle);
		holder.lblListHeader.setTextColor(isExpanded ? context.getResources()
				.getColor(R.color.txt_orange) : context.getResources()
				.getColor(R.color.txt_orange));
		holder.lblListHeader.setTypeface(null, isExpanded ? Typeface.BOLD
				: Typeface.NORMAL);


		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	/* private view holder class */
	private class ViewHolder {
		TextView lblListHeader;
	}

}
