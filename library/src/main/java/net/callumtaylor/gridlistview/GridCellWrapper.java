package net.callumtaylor.gridlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

class GridCellWrapper extends FrameLayout
{
	public GridCellWrapper(Context context)
	{
		super(context);

		setBackgroundResource(android.R.drawable.list_selector_background);

		setLayoutParams(new LinearLayout.LayoutParams(0, -1));
		((LinearLayout.LayoutParams)getLayoutParams()).weight = 1;
	}

	public GridCellWrapper(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		setBackgroundResource(android.R.drawable.list_selector_background);
	}

	public GridCellWrapper(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		setBackgroundResource(android.R.drawable.list_selector_background);
	}
}
