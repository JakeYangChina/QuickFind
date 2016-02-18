package com.jakeyang.quickfind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.item.quickfind.R;
import com.jakeyang.quickfind.bean.Cheeses;
import com.jakeyang.quickfind.bean.GoodMan;
import com.jakeyang.quickfind.view.MyView;
import com.jakeyang.quickfind.view.MyView.LetterChangerListener;

public class MainActivity extends Activity {

	private MyView mView;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		init();
		//设置监听
		setListener();
		//设置数据
		setData();
	}
	private MyAdapter mAdapter;
	/**
	 * 设置数据
	 */
	private void setData() {
		setListData();

		if (mAdapter == null) {
			mAdapter = new MyAdapter();
			mListView.setAdapter(mAdapter);
		}else {
			mAdapter.notifyDataSetChanged();
		}
	}

	private List<GoodMan> mList = new ArrayList<GoodMan>();
	/**
	 * 创建数据存储到集合
	 */
	private void setListData() {
		for(String cheeString : Cheeses.NAMES) {
			mList.add(new GoodMan(cheeString));
		}
		Collections.sort(mList);
	}

	private Handler mHandler = new Handler();
	private ListView mListView;

	/**
	 * 设置监听
	 */
	private void setListener() {
		mView.setLetterChangerListener(new LetterChangerListener() {

			@Override
			public void onLetterChanger(String letter) {
				mTextView.setVisibility(View.VISIBLE);
				mTextView.setText(letter);
				for (int i = 0; i < mList.size(); i++) {
					if (TextUtils.equals(String.valueOf(mList.get(i).mPinyin.charAt(0)), letter)) {
						//定位
						mListView.setSelection(i);
						break;
					}
				}
				mHandler.removeCallbacksAndMessages(null);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mTextView.setVisibility(View.GONE);
					}
				}, 600);
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		mView = (MyView) findViewById(R.id.myView);
		mListView = (ListView) findViewById(R.id.listView);
		mTextView = (TextView) findViewById(R.id.textView);
	}

	public class MyAdapter extends BaseAdapter {

		private String mPreString;

		@Override
		public int getCount() {

			return mList.size();
		}

		@Override
		public Object getItem(int position) {

			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(getApplicationContext(), R.layout.item, null);
				holder.name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.pinyin = (TextView) convertView.findViewById(R.id.tv_pinyin);
				convertView.setTag(holder);
			}else {
				holder = (Holder) convertView.getTag();
			}

			if (position == 0) {
				holder.pinyin.setVisibility(View.VISIBLE);
				holder.pinyin.setText(String.valueOf(mList.get(position).mPinyin.charAt(0)));
			}else {
				GoodMan goodMan = (GoodMan) getItem(position - 1);
				mPreString = String.valueOf(goodMan.mPinyin.charAt(0));
				if (TextUtils.equals(mPreString, String.valueOf(mList.get(position).mPinyin.charAt(0)))) {
					holder.pinyin.setVisibility(View.GONE);
				}else {
					holder.pinyin.setVisibility(View.VISIBLE);
					holder.pinyin.setText(String.valueOf(mList.get(position).mPinyin.charAt(0)));
				}
			}

			holder.name.setText(mList.get(position).mName);

			return convertView;
		}

	}

	public static class Holder {
		public TextView name;
		public TextView pinyin;
	}
}
