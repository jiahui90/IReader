/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.codingwith.us.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that arranges its children in a grid. The size of the cells is set
 * by the {@link #setCellSize} method and the android:cell_width and
 * android:cell_height attributes in XML. The number of rows and columns is
 * determined at runtime. Each cell contains exactly one view, and they flow in
 * the natural child order (the order in which they were added, or the index in
 * {@link #addViewAt}. Views can not span multiple cells.
 */
public class FixedGridLayout extends ViewGroup {

	private int deviceHeight;

	public FixedGridLayout(Context context) {
		super(context);
		deviceHeight = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
	}

	public FixedGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		deviceHeight = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int cellWidthSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec / 2,
				MeasureSpec.AT_MOST);
		int cellHeightSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec / 4,
				MeasureSpec.AT_MOST);

		int count = getChildCount();
		for (int index = 0; index < count; index++) {
			final View child = getChildAt(index);
			child.measure(cellWidthSpec, cellHeightSpec);
		}
		// Use the size our parents gave us
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int columns = 2;
		int x = 0;
		int y = 0;
		int i = 0;
		int count = getChildCount();
		for (int index = 0; index < count; index++) {
			final View child = getChildAt(index);

			int w = child.getMeasuredWidth();
			int h = child.getMeasuredHeight();

			int margin_top = (Math.abs(t - b) - 4 * h) / 2;

			int left = x + 25;
			int top = y + margin_top;

			child.layout(left, top, left + w, top + h);
			if (i >= (columns - 1)) {
				// advance to next row
				i = 0;
				x = 0;
				y += child.getMeasuredHeight();
			} else {
				i++;
				x += child.getMeasuredWidth();
			}
		}
	}
}
