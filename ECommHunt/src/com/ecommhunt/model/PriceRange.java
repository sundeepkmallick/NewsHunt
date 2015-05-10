package com.ecommhunt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PriceRange implements Parcelable {
	int min;
	int max;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public PriceRange() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(min);
		dest.writeInt(max);
	}

	public static final Parcelable.Creator<PriceRange> CREATOR = new Parcelable.Creator<PriceRange>() {
		public PriceRange createFromParcel(Parcel in) {
			return new PriceRange(in);
		}

		public PriceRange[] newArray(int size) {
			return new PriceRange[size];
		}
	};

	private PriceRange(Parcel in) {
		min = in.readInt();
		max = in.readInt();
	}
	
	@Override
	public boolean equals(Object other) {

		PriceRange that = (PriceRange) other;

		// Custom equality check here.
		return (this.min == that.min)
				&& (this.max == that.max);
	}

}
