package com.vmordo.graph;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class GraphActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Cnt.set(getApplicationContext());
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
		PointsGraphSeries<DataPoint> series;

		DataPoint[] dp = new DataPoint[9];

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			for (int i = 0; i < dp.length; i++) {
				dp[i] = new DataPoint(i, Math.round(Math.random() * 160));
			}
			View rootView = inflater.inflate(R.layout.fragment_graph,
					container, false);
			textView = (TextView) rootView.findViewById(R.id.textView);
			// ����������� ������
			graphView = new GraphView(Cnt.get());
			graphView.getGridLabelRenderer().setVerticalAxisTitle("��/�");
			graphView.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
			graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
			graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.RED);
			graphView.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.RIGHT);
			// ��������� ����� �������
			LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dp);
			graphView.addSeries(series2);
			// ��������� ���� ������
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.rl_graph);
			layout.addView(graphView);
			// �������� ����������� !����� ������ ��� ����� �������!
			seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
			seekBar.setOnSeekBarChangeListener(this);
			seekBar.setMax(dp.length - 1);
			return rootView;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (series != null)
				graphView.removeSeries(series);
			//textView.setText(""+dp[progress].getY());
			series = new PointsGraphSeries<DataPoint>(
					new DataPoint[] { new DataPoint(dp[progress].getX(),
							dp[progress].getY()) });
			//series.setTitle(""+dp[progress].getY());
			series.setColor(Color.RED);
			series.setSize(12f);
			graphView.addSeries(series);
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
