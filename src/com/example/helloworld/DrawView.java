package com.example.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View{

	//x，y坐标变量
	float currentX=40,currentY=50;
	
	//定义新画笔
	Paint paint=new Paint();
	
	//曲线路径
	Path path=new Path();
	
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DrawView(Context context,AttributeSet set)
	{
		super(context,set);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paint.setColor(Color.BLUE);
		canvas.drawCircle(currentX, currentY,6, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		currentX=event.getX();
		currentY=event.getY();
		
		this.invalidate();
		
		return super.onTouchEvent(event);
	}
}
