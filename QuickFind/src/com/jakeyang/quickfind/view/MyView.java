package com.jakeyang.quickfind.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	private Paint mPaint;
	private Rect mBounds;
	private static final String[] LETTERS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
	"Z" };

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setPaint();
	}

	public void setPaint() {
		mPaint = new Paint();
		mBounds = new Rect();
		mPaint.setTextSize(15);
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPaint();
	}

	public MyView(Context context) {
		super(context);
		setPaint();
	}

	private float y = 0;
	private float mHeight;
	private float mWidth;
	private float mHeightSize; 
	private float mWidthText;
	private float mHeightText;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < LETTERS.length; i++) {
			mWidthText = mPaint.measureText(LETTERS[i]);
			mPaint.getTextBounds(LETTERS[i], 0, LETTERS[i].length(), mBounds);
			mHeightText = mBounds.height();
			if (location == i) {
				mPaint.setColor(Color.BLUE);
			}else {
				mPaint.setColor(Color.GRAY);
			}
			//y = mHeightSize*i +mHeightText/2 + mHeightSize/2 ;
			canvas.drawText(LETTERS[i], mWidth/2 - mWidthText/2, mHeightSize*i +mHeightText/2 + mHeightSize/2, mPaint);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHeight = getMeasuredHeight();
		mWidth = getMeasuredWidth();
		mHeightSize = mHeight*1.0f/LETTERS.length;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setBackgroundColor(Color.parseColor("#aaaaaaaa"));
			fixY(event.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			fixY(event.getY());
			break;
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.TRANSPARENT);
			fixY(event.getY());
			location = -1;
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}

	private int location = -1;
	private void fixY(float y) {
		int ys = (int) (y/mHeightSize);
		if (location != ys) {
			if (ys < LETTERS.length) {
				location = ys;
				if (mListener != null && ys >= 0) {
					mListener.onLetterChanger(LETTERS[ys]);
				}
				invalidate();
			}
		}else {
			location = ys;
		}
	}

	private LetterChangerListener mListener;
	/**
	 * 定义回调监听事件接口
	 * @author Administrator
	 *
	 */
	public interface LetterChangerListener {
		public void onLetterChanger(String letter);
	}

	public void setLetterChangerListener(LetterChangerListener listener) {
		this.mListener = listener;
	}
	
}
