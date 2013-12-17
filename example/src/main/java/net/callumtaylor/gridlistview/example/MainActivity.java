package net.callumtaylor.gridlistview.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import net.callumtaylor.gridlistview.GridListView;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GridListView list = (GridListView)findViewById(R.id.list_view);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.column, android.R.id.text1, new String[]
		{
			"1",
			"2",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"9",
			"10",
			"11",
			"12",
			"13",
			"14",
			"15",
			"16",
			"17",
			"18",
			"19",
			"20"
		});

		list.setAdapter(adapter);
	}
}
