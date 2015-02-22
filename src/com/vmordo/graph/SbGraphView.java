package com.vmordo.graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SbGraphView extends GraphView {
	DataPoint[] mDPs;
	Paint p, pr;
	Rect rect;
	RectF rectf;
	DataPointInterface dtp;
	LineGraphSeries<DataPoint> series2;

	public SbGraphView(Context context) {
		super(context);
		p = new Paint();
		p.setColor(Color.BLUE);
		p.setTextSize(12);
		rect = new Rect();
		rectf = new RectF();
		pr = new Paint(); // для прямоугольника
		pr.setColor(Color.MAGENTA);
		// настраиваем график
		this.getGridLabelRenderer().setVerticalAxisTitle("км/ч");
		this.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
		this.getGridLabelRenderer().setGridStyle(
				GridLabelRenderer.GridStyle.HORIZONTAL);
		this.getGridLabelRenderer().setVerticalLabelsColor(Color.RED);
		this.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.RIGHT);
	}

	public void add(final DataPoint[] dp) {
		mDPs = dp; // сохраняем данные они потом потребуются
		// добавляем линию графика
		dtp = mDPs[0];
		series2 = new LineGraphSeries<DataPoint>(dp);
		addSeries(series2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (dtp != null) {
			DataPoint dp = getViewXY(dtp);
			// рисуем линию и точку
			int x = (int) (dp.getX()) + getGraphContentLeft() + 1;
			int y = getGraphContentTop() + getGraphContentHeight() - (int) dp.getY();
			canvas.drawLine(x, getGraphContentTop(), x, getGraphContentTop()
					+ getGraphContentHeight(), p);
			canvas.drawCircle(x, y, 3, p);
			// выводим текст
			String text = "" + (int) dtp.getY() + " км/ч";
			// ширина текста
			int width = (int) p.measureText(text);
			p.setTextAlign(Paint.Align.CENTER);
			rectf.left = x - width / 2 - 3;
			rectf.top = y - 11 - p.getTextSize();
			rectf.right = x + width / 2 + 3;
			rectf.bottom = y - 9;
			canvas.drawRoundRect(rectf, 5f, 5f, pr);
			canvas.drawText(text, x, y - 10, p);
			// имитация прорисовки seekBar
			canvas.drawCircle(x, getGraphContentTop(), 7, p);
			rect.left = getGraphContentLeft();
			rect.top = getGraphContentTop();
			rect.right = getGraphContentLeft() + getGraphContentWidth();
			rect.bottom = getGraphContentTop() + 3;
			canvas.drawRect(rect, p);
		}
	}

	// вычисляем координаты lineX, lineY (взято из jjoe64)
	public DataPoint getViewXY(DataPointInterface dataPoint) {
		double minX = getViewport().getMinX(false);
		double maxX = getViewport().getMaxX(false);
		double diffX = maxX - minX;
		double valX = dataPoint.getX() - minX;
		double ratX = valX / diffX;
		double x = getGraphContentWidth() * ratX;

		double maxY = getViewport().getMaxY(false);
		double minY = getViewport().getMinY(false);
		double diffY = maxY - minY;
		double valY = dataPoint.getY() - minY;
		double ratY = valY / diffY;
		double y = getGraphContentHeight() * ratY;

		return new DataPoint(x, y);
	}

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
		double maxX = getViewport().getMaxX(false);
		double minX = getViewport().getMinX(false);

		if (event.getX() >= (getGraphContentWidth()+getGraphContentLeft()))
			dtp = mDPs[mDPs.length-1];
		else if (event.getX() <= getGraphContentLeft())
			dtp = mDPs[0];
		else{
			double valX = (maxX- minX)*(event.getX() - getGraphContentLeft())/getGraphContentWidth();
			int ind = (int) Math.round(valX);
			if (ind > mDPs.length-1 )
				ind = mDPs.length-1;
			dtp = mDPs[ind];
		}
		invalidate();
		return true;
	}

}
