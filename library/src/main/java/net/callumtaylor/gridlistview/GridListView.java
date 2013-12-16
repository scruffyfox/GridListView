package net.callumtaylor.gridlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GridListView extends ListView
{
	private int columnCount = 1;

	public GridListView(Context context)
	{
		super(context);
	}

	public GridListView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public GridListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GridListView);
		columnCount = attributes.getInt(R.styleable.GridListView_column_count, 1);
	}

	@Override public void setAdapter(ListAdapter adapter)
	{
		if (!(adapter instanceof BaseAdapter))
		{
			super.setAdapter(adapter);
			return;
		}

		InternalAdapterImpl wrapper = new InternalAdapterImpl(getContext(), (BaseAdapter)adapter);
		super.setAdapter(wrapper);
	}

	private class InternalAdapterImpl extends BaseAdapter
	{
		private BaseAdapter baseAdapter;
		private Context context;

		public InternalAdapterImpl(Context context, BaseAdapter adapter)
		{
			this.baseAdapter = adapter;
			this.context = context;
		}

		@Override public int getCount()
		{
			return (int)Math.floor(baseAdapter.getCount() / (float)columnCount);
		}

		@Override public Object getItem(int position)
		{
			return baseAdapter.getItem(position * columnCount);
		}

		@Override public long getItemId(int position)
		{
			return baseAdapter.getItemId(position);
		}

		@Override public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
			}

			((LinearLayout)convertView).setWeightSum(columnCount);

			for (int index = 0; index < columnCount; index++)
			{
				View v = baseAdapter.getView((position * columnCount) + index, ((LinearLayout)convertView).getChildAt(index), (LinearLayout)convertView);
				((LinearLayout.LayoutParams)v.getLayoutParams()).width = 0;
				((LinearLayout.LayoutParams)v.getLayoutParams()).weight = 1;

				((LinearLayout)convertView).addView(v);
			}

			return convertView;
		}
	}
}
