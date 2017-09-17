package com.example.mbenben.studydemo.layout.pickerview.adapter;

public interface WheelAdapter<T> {
	/**
	 * Gets items count
	 * @return the count of wheel items
	 */
	int getItemsCount();
	
	/**
	 * Gets a wheel item_guide_view by index.
	 * @param index the item_guide_view index
	 * @return the wheel item_guide_view text or null
	 */
	T getItem(int index);
	
	/**
	 * Gets maximum item_guide_view length. It is used to determine the wheel width.
	 * If -1 is returned there will be used the default wheel width.
	 * @param o
	 * @return the maximum item_guide_view length or -1
     */
	int indexOf(T o);
}
