package com.ecommhunt.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecommhunt.R;
import com.ecommhunt.model.Catalog;
import com.ecommhunt.model.Category;
import com.ecommhunt.model.SubCategory;
import com.ecommhunt.util.CustExpListview;

public class MenuCategoryAdapter extends BaseExpandableListAdapter {

	Context context;
	ArrayList<Catalog> catalogList;
	OnChildClickListener onChildClickListener;
	private ArrayList<Catalog> catalogListFiltered;
	private ArrayList<Catalog> catalogListOriginal;
	Handler mHandler;
	public static final int FILTER_CATALOG_LIST = 1003;
	boolean isExpanded;

	public MenuCategoryAdapter(Context context, ArrayList<Catalog> catalog,
			OnChildClickListener onChildClickListener, Handler handler) {
		this.context = context;
		this.mHandler = handler;
		this.catalogList = catalog;
		this.catalogListFiltered = new ArrayList<Catalog>();
		this.catalogListFiltered.addAll(catalog);
		this.onChildClickListener = onChildClickListener;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return catalogList.get(groupPosition).getCategoryList();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final CustExpListview SecondLevelexplv = new CustExpListview(context);
		// SecondLevelexplv.setBackgroundColor(color)
		SecondLevelexplv.setOnChildClickListener(onChildClickListener);
		
		SecondLevelexplv.setOnGroupExpandListener(new OnGroupExpandListener() {
			int previousGroup = -1;

			@Override
			public void onGroupExpand(int groupPosition) {
				if(!isExpanded){
					if (groupPosition != previousGroup)
						SecondLevelexplv.collapseGroup(previousGroup);
				}
				previousGroup = groupPosition;
			}
		});
		
		if (catalogList.get(groupPosition).getCategoryList() != null) {
			MenuSubcategoryAdapter adapterMenuSubCat = new MenuSubcategoryAdapter(context,
					catalogList.get(groupPosition).getCategoryList());
			SecondLevelexplv.setAdapter(adapterMenuSubCat);
			SecondLevelexplv.setGroupIndicator(null);
			
			if (isExpanded) {
				int count = adapterMenuSubCat.getGroupCount();
				for (int i = 0; i < count; i++) {
					SecondLevelexplv.expandGroup(i);
				}
			}
		}

		return SecondLevelexplv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return catalogList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return catalogList.size();
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
	        convertView = mInflater.inflate(R.layout.list_catalog, null);
	        holder = new ViewHolder();
	        holder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
	        holder.indicator = (ImageView) convertView.findViewById(R.id.imageView1);
	        convertView.setTag(holder);
	    } else{
	    	 holder = (ViewHolder) convertView.getTag();
	    }
	    
	    String headerTitle = catalogList.get(groupPosition).getCatalogName();
	  
	    holder.lblListHeader.setText((!TextUtils.isEmpty(headerTitle))?headerTitle.toUpperCase():headerTitle);
	    
	    if (catalogList.get(groupPosition).isIsExtraMenu()) {
	    	 holder.indicator.setVisibility(View.INVISIBLE);
		} else {
			 holder.indicator.setVisibility(View.VISIBLE);
			 holder.indicator.setImageResource(isExpanded ? R.drawable.ic_minus
					: R.drawable.ic_plus);
			 holder.lblListHeader.setTypeface(null, isExpanded ? Typeface.BOLD : Typeface.BOLD);
			 holder.lblListHeader.setTextColor(isExpanded ? context.getResources()
					.getColor(R.color.menu_header_color) : context
					.getResources().getColor(R.color.txt_black));
		}
	    
	    return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	/* private view holder class */
	private class ViewHolder {
	    ImageView indicator;
	    TextView lblListHeader;
	}
	
	public void setExpanded(boolean isExpanded){
		this.isExpanded = isExpanded;
	}
	
	public void setOriginalCatalogList(ArrayList<Catalog> catalog){
		this.catalogListOriginal = new ArrayList<Catalog>();
		this.catalogListOriginal.addAll(catalog);
	}
	
	public void filterData(String query) {

		query = query.toLowerCase();
		Log.v("MenuSubcategoryAdapter", String.valueOf(catalogListFiltered.size()));
		catalogListFiltered.clear();
		
		Message msg = mHandler.obtainMessage();
		msg.arg2 = 1;

		if (query.isEmpty()) {
			catalogListFiltered.addAll(catalogListOriginal);
			msg.arg2 = 0;
		} else {
			
			ArrayList<Catalog> newListCatalog = new ArrayList<Catalog>();
			for(Catalog catalog : catalogListOriginal){
				
				Catalog catalognew = new Catalog();
				catalognew.setCatalogId(catalog.getCatalogId());
				catalognew.setCatalogName(catalog.getCatalogName());
				ArrayList<Category> categoryListNew = new ArrayList<Category>();
				
				ArrayList<Category> categoryList = catalog.getCategoryList();
				for(Category category : categoryList){
					
					Category categorynew = new Category();
					categorynew.setCategoryId(category.getCategoryId());
					categorynew.setCategoryName(category.getCategoryName());
					ArrayList<SubCategory> subCategoryListNew = new ArrayList<SubCategory>();
					
					ArrayList<SubCategory> subCategoryList = category.getSubCategoryList();
					for (SubCategory subcategory : subCategoryList) {
						SubCategory subcategorynew = new SubCategory();
						if (subcategory.getSubCategoryName().toLowerCase().contains(query)) {
							subcategorynew.setSubCategoryId(subcategory.getSubCategoryId());	
							subcategorynew.setSubCategoryName(subcategory.getSubCategoryName());
							subCategoryListNew.add(subcategorynew);
						}
					}
					
					if(subCategoryListNew.size()>0){
						categorynew.setSubCategoryList(subCategoryListNew);
						categoryListNew.add(categorynew);
					}
				}
				
				if(categoryListNew.size()>0){
					catalognew.setCategoryList(categoryListNew);
					newListCatalog.add(catalognew);
				}
				
			}
			
			if(newListCatalog.size() > 0){
				catalogListFiltered.addAll(newListCatalog);
			}
		}

		Log.v("MyListAdapter", String.valueOf(catalogListFiltered.size()));
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("bundkecataloglist", catalogListFiltered);
		msg.arg1 = 1;
		msg.setData(bundle);
		msg.what = FILTER_CATALOG_LIST;
		mHandler.sendMessage(msg);
		//notifyDataSetChanged();

	}

}
