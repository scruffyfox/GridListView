#GridListView

Grid list view is a simple ListView wrapper that acts like a Grid View allowing the use of headers/footers and other ListView specific functionality whilst maintaining the dynamics of a Grid View.

##Usage

Simply use

```xml
<net.callumtaylor.gridlistview.GridListView
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:id="@+id/list_view"
	app:numColumns="3"
/>
```

instead of your list view. Remember to define the xmlns (`xmlns:app="http://schemas.android.com/apk/res-auto"`) in the root tag of your view to access the `app` prefix.

##GridAdapter

You can optionally use `GridAdapter` to display different amount of columns defined on a row-to-row basis.

Example:

```java
GridAdapter adapter = new GridAdapter()
{
	@Override public int getCount()
	{
		return items.length;
	}

	@Override public String getItem(int position)
	{
		return items[position];
	}

	@Override public long getItemId(int position)
	{
		return 0;
	}

	@Override public int getColumnCount(int rowIndex)
	{
		if (rowIndex % 2 == 0)
		{
			return 1;
		}
		else if (rowIndex % 3 == 0)
		{
			return 2;
		}

		return super.getColumnCount(rowIndex);
	}

	@Override public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = getLayoutInflater().inflate(R.layout.column, parent, false);
		}

		((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position));

		return convertView;
	}
};
```

##Other notes

Because GridListView uses an internal adapter wrapper to handle the columns in the view, you should call `GridView#getBaseAdapter()` to get the original adapter you are using when calling `GridListView#setAdapter()`. Calling `GridListView#getAdapter()` will return the internal adapter implementation and may produce unexpected results.
