package com.ecommhunt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ecommhunt.util.Utils;

public class DashboardActivity extends BaseActivity {
	
	Context context;
	Button btnShopHere;
	Button btnReadNews;
	Button btnGossip;
	Button btnEbook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = DashboardActivity.this;
		setDrawerMenu(true);
		super.onCreate(savedInstanceState);
		mLayoutInflater.inflate(R.layout.activity_dashboard, mBaseLayout);
		setTitle("Dashboard");
		enableBackButton(true);
		
		btnShopHere = (Button) findViewById(R.id.buttonShopHere);
		btnReadNews = (Button) findViewById(R.id.buttonReadNews);
		btnGossip = (Button) findViewById(R.id.buttonGossip);
		btnEbook = (Button) findViewById(R.id.buttonEbook);
		
		btnShopHere.setText(Utils.getStringDataBasedOnLanguage(context, R.string.shop_here));
		btnReadNews.setText(Utils.getStringDataBasedOnLanguage(context, R.string.read_news));
		btnGossip.setText(Utils.getStringDataBasedOnLanguage(context, R.string.gossip));
		btnEbook.setText(Utils.getStringDataBasedOnLanguage(context, R.string.ebook));
		
		btnShopHere.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ProductListActivity.class);
				startActivity(intent);
			}
		});
		
		btnGossip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SelectTopicActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public int getLayoutResource() {
		return -1;
	}


}
