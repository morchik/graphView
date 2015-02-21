package com.vmordo.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;

public class SbGraphView extends GraphView {

	Paint p;
	Rect rect;
	public int lineX = 0;

	public SbGraphView(Context context) {
		super(context);
		p = new Paint();
		rect = new Rect();
		// настраиваем график
		this.getGridLabelRenderer().setVerticalAxisTitle("км/ч");
		this.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
		this.getGridLabelRenderer().setGridStyle(
				GridLabelRenderer.GridStyle.HORIZONTAL);
		this.getGridLabelRenderer().setVerticalLabelsColor(Color.RED);
		this.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.RIGHT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// рисуем линию от (100,100) до (500,50)
		int x = lineX + super.getGraphContentLeft();
		canvas.drawLine(x, super.getGraphContentTop(), x,
				super.getGraphContentTop() + super.getGraphContentHeight(), p);
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
