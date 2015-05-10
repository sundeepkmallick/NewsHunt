package com.ecommhunt;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.ecommhunt.material.MaterialMenu;
import com.ecommhunt.material.MaterialMenuDrawable;
import com.ecommhunt.material.MaterialMenuDrawable.IconState;
import com.ecommhunt.material.MaterialMenuView;


public class BaseActivityHelper implements View.OnClickListener {

	private MaterialMenuView materialMenuView;
	// private int materialButtonState;
	private MaterialMenu materialIcon;
	private DrawerLayout drawerLayout;
	private boolean direction;

	public void init(View parent, MaterialMenu actionBarIcon) {
		materialIcon = actionBarIcon;
		drawerLayout = ((DrawerLayout) parent.findViewById(R.id.drawer_layout));
		// drawerLayout.setScrimColor(Color.parseColor("#66000000"));
		drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				materialIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						direction ? 2 - slideOffset : slideOffset);
			}

			@Override
			public void onDrawerOpened(android.view.View drawerView) {
				direction = true;
				materialIcon.animateState(IconState.ARROW);
				// materialIcon.setTransformationOffset(
				// MaterialMenuDrawable.AnimationState.ARROW_X,
				// direction ? 2 - 1 : 2);

			}

			@Override
			public void onDrawerClosed(android.view.View drawerView) {
				direction = false;
				// materialIcon.animateState(IconState.BURGER);
				// materialIcon.setTransformationOffset(
				// MaterialMenuDrawable.AnimationState.BURGER_ARROW,
				// direction ? 2 - 1 : 2);
			}
		});

		drawerLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				// drawerLayout.openDrawer(Gravity.LEFT);
			}
		}, 1500);
	}

	public void openCloseDrawer() {
		if (drawerLayout != null) {
			if (direction) {
				drawerLayout.closeDrawers();
			} else {
				drawerLayout.openDrawer(Gravity.LEFT);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// final int id = v.getId();
		// switch (id) {
		// case R.id.animate_item_menu:
		// materialMenuView.animateState(IconState.BURGER);
		// break;
		// case R.id.animate_item_arrow:
		// materialMenuView.animateState(IconState.ARROW);
		// break;
		// case R.id.animate_item_x:
		// materialMenuView.animateState(IconState.X);
		// break;
		// case R.id.animate_item_check:
		// materialMenuView.animateState(IconState.CHECK);
		// break;
		// case R.id.switch_item_menu:
		// materialMenuView.setState(IconState.BURGER);
		// break;
		// case R.id.switch_item_arrow:
		// materialMenuView.setState(IconState.ARROW);
		// break;
		// case R.id.switch_item_x:
		// materialMenuView.setState(IconState.X);
		// break;
		// case R.id.switch_item_check:
		// materialMenuView.setState(IconState.CHECK);
		// break;
		// case R.id.material_menu_button:
		// setMainState();
		// break;
		// }
	}

	// private void setMainState() {
	// materialButtonState = generateState(materialButtonState);
	// materialMenuView.animatePressedState(intToState(materialButtonState));
	// }

	public void refreshDrawerState() {
		this.direction = drawerLayout.isDrawerOpen(Gravity.START);
	}

	public int generateState(int previous) {
		int generated = direction ? 1 : 0;// new Random().nextInt(2);
		return generated;
		// return generated != previous ? generated : generateState(previous);
	}

	public static IconState intToState(int state) {
		System.out.println("state::::: " + state);
		switch (state) {
		case 0:
			return IconState.BURGER;
		case 1:
			return IconState.ARROW;
		case 2:
			return IconState.X;
		case 3:
			return IconState.CHECK;
		}
		throw new IllegalArgumentException("Must be a number [0,3)");
	}

	public void removeView(View mProgressContainer) {
		if (drawerLayout != null) {
			drawerLayout.removeView(mProgressContainer);
			drawerLayout.invalidate();
		}
	}
}
