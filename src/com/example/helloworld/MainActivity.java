package com.example.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button button1, button2, button3, btnNewActivity, btnSendMsg,
			btnCalculate, btnDialog, btnProgress, btnListView, btnSqlite,
			btnXml;
	private android.app.AlertDialog.Builder dialog1, dialog2;
	private TextView textview1, textView4, textViewProgress;
	private EditText editText1, editText2;
	private RadioGroup radioGroupGender;
	private RadioButton radio0, radio1;
	private CheckBox checkbox1, checkbox2, checkbox3;
	private ProgressBar progressBar1;
	private int progress = 0x00;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button1 = (Button) super.findViewById(R.id.btnHandler);
		button1.setOnClickListener(this);

		button2 = (Button) super.findViewById(R.id.btnUnregisterReceiver);
		button2.setOnClickListener(this);

		button3 = (Button) super.findViewById(R.id.btnInsert);
		button3.setOnClickListener(this);

		btnNewActivity = (Button) super.findViewById(R.id.btnNewActivity);
		btnNewActivity.setOnClickListener(this);

		btnSendMsg = (Button) super.findViewById(R.id.btnSendMsg);
		btnSendMsg.setOnClickListener(this);

		btnCalculate = (Button) super.findViewById(R.id.btnCalculate);
		btnCalculate.setOnClickListener(this);

		btnDialog = (Button) super.findViewById(R.id.btnDialog);
		btnDialog.setOnClickListener(this);

		btnProgress = (Button) super.findViewById(R.id.btnProgress);
		btnProgress.setOnClickListener(this);

		radio0 = (RadioButton) super.findViewById(R.id.radio0);
		radio1 = (RadioButton) super.findViewById(R.id.radio1);

		radioGroupGender = (RadioGroup) super
				.findViewById(R.id.radioGroupGender);
		radioGroupGender.setOnCheckedChangeListener(radioCheckedListener);

		checkbox1 = (CheckBox) super.findViewById(R.id.checkBox1);
		checkbox1.setOnCheckedChangeListener(checkboxListener);

		checkbox2 = (CheckBox) super.findViewById(R.id.checkBox2);
		checkbox2.setOnCheckedChangeListener(checkboxListener);

		checkbox3 = (CheckBox) super.findViewById(R.id.checkBox3);
		checkbox3.setOnCheckedChangeListener(checkboxListener);

		progressBar1 = (ProgressBar) super.findViewById(R.id.progressBar1);
		textViewProgress = (TextView) super.findViewById(R.id.textViewProgress);

		btnListView = (Button) super.findViewById(R.id.btnListView);
		btnListView.setOnClickListener(this);

		btnSqlite = (Button) super.findViewById(R.id.btnSqlite);
		btnSqlite.setOnClickListener(this);

		btnXml = (Button) super.findViewById(R.id.btnXml);
		btnXml.setOnClickListener(this);
	}

	/*
	 * //自定义Listener class clickListener implements OnClickListener {
	 * 
	 * @Override public void onClick(View arg0) {
	 * 
	 * } }
	 */

	// CheckBox切换事件监听器
	public CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Toast.makeText(MainActivity.this,
					buttonView.getText().toString() + isChecked,
					Toast.LENGTH_SHORT).show();
		}
	};

	// RadioGroup切换事件监听器
	public RadioGroup.OnCheckedChangeListener radioCheckedListener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (group.getId()) {
			case R.id.radioGroupGender:
				String toastMsg = "";
				if (checkedId == radio0.getId()) {
					toastMsg = "MALE";
				} else if (checkedId == radio1.getId()) {
					toastMsg = "FEMALE";
				}
				Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT)
						.show();
				;
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		Button btn = (Button) v;

		switch (btn.getId()) {
		case R.id.btnHandler:
			dialog1 = new android.app.AlertDialog.Builder(MainActivity.this);
			dialog1.setTitle("温馨提示");
			dialog1.setMessage("Message from button1");
			dialog1.setNegativeButton("确定", null);
			dialog1.show();
			break;
		case R.id.btnUnregisterReceiver:
			Toast.makeText(MainActivity.this, "你点击了Button2", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.btnInsert:
			textview1 = (TextView) super.findViewById(R.id.textview1);
			textview1.setText("you clicked button3");
			break;
		case R.id.btnNewActivity:
			Intent intent = new Intent();
			intent.putExtra("testIntent", "123");
			intent.setClass(MainActivity.this, OtherActivity.class);
			startActivity(intent);
			break;
		case R.id.btnSendMsg:
			Uri uri = Uri.parse("smsto:15954890325");
			Intent intentSms = new Intent(Intent.ACTION_SENDTO, uri);
			intentSms.putExtra("sms title", "sms body");
			startActivity(intentSms);
			break;
		case R.id.btnCalculate:
			editText1 = (EditText) findViewById(R.id.editText1);
			editText2 = (EditText) findViewById(R.id.editText2);
			textView4 = (TextView) findViewById(R.id.textView4);

			int one = 0,
			two = 0,
			result = 0;
			try {
				one = Integer.parseInt(editText1.getText().toString().trim());
				two = Integer.parseInt(editText2.getText().toString().trim());
			} catch (Exception ex) {

			}
			result = one + two;
			textView4.setText(result + "");

			break;
		case R.id.btnDialog:
			Intent intentDialog = new Intent();
			intentDialog.putExtra("testdialog", "testdialog456");
			intentDialog.setClass(MainActivity.this, DialogActivity.class);
			startActivity(intentDialog);
			break;

		case R.id.btnProgress:

			String text = btnProgress.getText().toString();

			switch (text) {
			case "PROGRESS":
				progress = 0;
				progressBar1.setProgress(progress); // 设置初始值
				btnProgress.setText("PAUSE");
				handlerProcess.post(updateThread);
				handlerProcess.post(updateThread2);

				break;
			case "PAUSE":
				btnProgress.setText("RESUME");
				handlerProcess.removeCallbacks(updateThread);
				handlerProcess.removeCallbacks(updateThread2);
				break;
			case "RESUME":
				btnProgress.setText("PAUSE");
				handlerProcess.post(updateThread);
				handlerProcess.post(updateThread2);
				break;
			}
			break;
		case R.id.btnListView:
			Intent intentListView = new Intent();
			intentListView.setClass(MainActivity.this, ListViewActivity.class);
			startActivity(intentListView);
			break;
		case R.id.btnSqlite:
			Intent intentSqlite = new Intent();
			intentSqlite.setClass(MainActivity.this, SqliteActivity.class);
			super.startActivity(intentSqlite);
			break;
		case R.id.btnXml:
			Intent intentSax=new Intent();
			intentSax.setClass(MainActivity.this, SaxActivity.class);
			startActivity(intentSax);
			break;
		}
	}

	Handler handlerProcess = new Handler() {
		@Override
		public void handleMessage(Message m) {
			if (m.what == -1)
				handlerProcess.removeCallbacks(updateThread2);

			textViewProgress.setText(String.valueOf(progress));
		}
	};

	// 线程类
	Runnable updateThread = new Runnable() {

		@Override
		public void run() {
			Message m = handlerProcess.obtainMessage();
			if (progressBar1.getProgress() < progressBar1.getMax()) {
				progressBar1.incrementProgressBy(1);
				progress += 1;
			} else if (progressBar1.getProgress() == progressBar1.getMax()) // 已到达上限,重设初始
			{
				btnProgress.setText("PROGRESS");
				handlerProcess.removeCallbacks(updateThread);
			}

			m.what = progress;
			handlerProcess.handleMessage(m);

			// 重新将当前异步信息加入Handler消息队列
			handlerProcess.postDelayed(updateThread, 50);
		}
	};

	Runnable updateThread2 = new Runnable() {

		@Override
		public void run() {
			Message m = handlerProcess.obtainMessage();
			m.what = -1;
			handlerProcess.sendMessage(m);
		}
	};

	// Menu按钮事件
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 1, 1, R.string.exit);
		menu.add(0, 2, 2, R.string.about);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			finish();
			break;
		case 2:
			dialog2 = new AlertDialog.Builder(MainActivity.this);
			dialog2.setTitle("ABOUT");
			dialog2.setMessage("LISON LIOU ALL RIGHTS RESERVED");
			dialog2.setNegativeButton("确定", null);
			dialog2.show();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}