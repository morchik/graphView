package com.vmordo.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class SbGraphView extends GraphView {

	Paint p;
	Rect rect;
	public int lineX = 0, lineY = 0;

	public SbGraphView(Context context) {
		super(context);
		p = new Paint();
		rect = new Rect();
		// ����������� ������
		this.getGridLabelRenderer().setVerticalAxisTitle("��/�");
		this.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
		this.getGridLabelRenderer().setGridStyle(
				GridLabelRenderer.GridStyle.HORIZONTAL);
		this.getGridLabelRenderer().setVerticalLabelsColor(Color.RED);
		this.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.RIGHT);
	}

	public void add(final DataPoint[] dp) {
		// ��������� ����� �������
		LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dp);
		series2.setOnDataPointTapListener(new OnDataPointTapListener() {

			@Override
			public void onTap(Series series, DataPointInterface dataPoint) {
				int diffX = getGraphContentWidth() / (dp.length - 1);
				int diffY = (int) (getGraphContentHeight() / (series.getHighestValueY()));
				lineX = (int) Math.round(dataPoint.getX() * diffX);
				lineY = (int) Math.round(dataPoint.getY() * diffY);
				invalidate();
			}
		});
		addSeries(series2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ������ ����� �� (100,100) �� (500,50)
		int x = lineX + super.getGraphContentLeft();
		int y = super.getGraphContentTop() + super.getGraphContentHeight() - lineY;
		canvas.drawLine(x, super.getGraphContentTop(), x, y, p);
		rect.left = super.getGraphContentLeft();
		// findDataPoint(x, y);
		canvas.drawCircle(x, super.getGraphContentTop(), 10, p);
		rect.left = super.getGraphContentLeft();
		rect.top = super.getGraphContentTop();
		rect.right = super.getGraphContentLeft() + super.getGraphContentWidth();
		rect.bottom = super.getGraphContentTop() + 5;// super.getGraphContentHeight();
		canvas.drawRect(rect, p);
	}

}
