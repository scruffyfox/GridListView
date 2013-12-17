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
	private int mNumColumns = 1;

	private OnItemClickListener clickListener;

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
		mNumColumns = attributes.getInt(R.styleable.GridListView_column_count, 1);
		attributes.recycle();
	}

	public void setNumColumns(int columnCount)
	{
		this.mNumColumns = columnCount;

		if (getAdapter() != null)
		{
			((InternalAdapterImpl)getAdapter()).notifyDataSetChanged();
		}
	}

	public int getNumColumns()
	{
		return this.mNumColumns;
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

	@Override public void setOnItemClickListener(OnItemClickListener listener)
	{
		this.clickListener = listener;
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

		private int getBaseCount()
		{
			return baseAdapter.getCount();
		}

		@Override public int getCount()
		{
			return (int)Math.ceil(baseAdapter.getCount() / (float)mNumColumns);
		}

		@Override public Object getItem(int position)
		{
			return baseAdapter.getItem(position * mNumColumns);
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

			((LinearLayout)convertView).setWeightSum(mNumColumns);
			View[] convertViews = new View[mNumColumns];

			for (int index = 0; index < mNumColumns; index++)
			{
				if ((position * mNumColumns) + index >= getBaseCount()) break;

				GridCellWrapper wrapper;

				if ((wrapper = (GridCellWrapper)((LinearLayout)convertView).getChildAt(index)) == null)
				{
					wrapper = new GridCellWrapper(context);
				}

				View v = baseAdapter.getView((position * mNumColumns) + index, wrapper.getChildAt(0), wrapper);
				wrapper.removeAllViews();
				wrapper.addView(v);

				convertViews[index] = wrapper;
			}

			((LinearLayout)convertView).removeAllViews();

			for (int index = 0; index < mNumColumns; index++)
			{
				if (convertViews[index] == null) break;

				if (baseAdapter.isEnabled((position * mNumColumns) + index))
				{
					final int pos = (position * mNumColumns) + index;
					convertViews[index].setOnClickListener(new OnClickListener()
					{
						@Override public void onClick(View v)
						{
							if (clickListener != null)
							{
								clickListener.onItemClick(GridListView.this, v, pos, baseAdapter.getItemId(pos));
							}
						}
					});
				}

				((LinearLayout)convertView).addView(convertViews[index], index);
			}

			return convertView;
		}
	}
}
