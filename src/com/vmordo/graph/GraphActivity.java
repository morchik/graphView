package com.vmordo.graph;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer.LegendAlign;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class GraphActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Cnt.set(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			SeekBar.OnSeekBarChangeListener {

		private SeekBar seekBar;
		private TextView textView;
		private GraphView graphView;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_graph,
					container, false);
			textView = (TextView) rootView.findViewById(R.id.textView);
			seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
			seekBar.setOnSeekBarChangeListener(this);

			graphView = new GraphView(Cnt.get());
			LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
			          new DataPoint(0, 1),
			          new DataPoint(1, 5),
			          new DataPoint(2, 3),
			          new DataPoint(3, 2),
			          new DataPoint(4, 6)
			});
			graphView.addSeries(series);
			// legend
			series.setTitle("foo");
			graphView.getLegendRenderer().setVisible(true);
			graphView.getLegendRenderer().setAlign(LegendAlign.TOP);		

			LinearLayout layout = (LinearLayout) rootView
					.findViewById(R.id.rl_graph);
			layout.addView(graphView);

			return rootView;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			textView.setText("progress=" + progress);
			// Toast.makeText(Cnt.get(), "progress="+progress,
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}
	}
}
