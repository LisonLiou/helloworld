package com.example.helloworld;

import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends Activity {

	private TextView textview1;
	private ProgressBar progressBar1;
	private Button btnProgressSwitcher, btnHandler, btnBroadCastReceiver, btnRegisterBroadcastReceiver, btnUnregisterBroadcastReceiver, btnStartWifi,
			btnStopWifi, btnCheckWifiState, btnShowWifiList;
	private WifiManager wifiManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.other);

		Intent intent = getIntent();
		String value = intent.getStringExtra("testIntent");

		textview1 = (TextView) super.findViewById(R.id.textview1);
		textview1.setText(R.string.other_app_name);

		progressBar1 = (ProgressBar) super.findViewById(R.id.progressBar1);
		btnProgressSwitcher = (Button) super.findViewById(R.id.btnProgressSwitcher);
		btnProgressSwitcher.setOnClickListener(new onclickListener());

		progressBar1.setMax(200);
		progressBar1.setVisibility(ProgressBar.GONE);

		Toast.makeText(OtherActivity.this, value, Toast.LENGTH_SHORT).show();

		btnHandler = (Button) super.findViewById(R.id.btnHandler);
		btnHandler.setOnClickListener(new onclickListener());

		btnBroadCastReceiver = (Button) super.findViewById(R.id.btnBroadCastReceiver);
		btnBroadCastReceiver.setOnClickListener(new onclickListener());

		btnRegisterBroadcastReceiver = (Button) super.findViewById(R.id.btnRegisterReceiver);
		btnRegisterBroadcastReceiver.setOnClickListener(new onclickListener());

		btnUnregisterBroadcastReceiver = (Button) super.findViewById(R.id.btnUnregisterReceiver);
		btnUnregisterBroadcastReceiver.setOnClickListener(new onclickListener());

		btnStartWifi = (Button) super.findViewById(R.id.btnStartWifi);
		btnStartWifi.setOnClickListener(new onclickListener());

		btnStopWifi = (Button) super.findViewById(R.id.btnStopWifi);
		btnStopWifi.setOnClickListener(new onclickListener());

		btnCheckWifiState = (Button) super.findViewById(R.id.btnCheckWifiState);
		btnCheckWifiState.setOnClickListener(new onclickListener());

		btnShowWifiList = (Button) super.findViewById(R.id.btnShowWifiList);
		btnShowWifiList.setOnClickListener(new onclickListener());

		wifiManager = (WifiManager) this.getSystemService(Service.WIFI_SERVICE);
	}

	class onclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {

			switch (arg0.getId()) {
			case R.id.btnProgressSwitcher:

				progressBar1.setVisibility(ProgressBar.VISIBLE);
				String text = btnProgressSwitcher.getText().toString();

				switch (text) {
				case "PROGRESS":
					progressBar1.setProgress(0); // 设置初始值
					btnProgressSwitcher.setText("PAUSE");
					handler.post(updateThread);
					break;
				case "PAUSE":
					btnProgressSwitcher.setText("RESUME");
					handler.removeCallbacks(updateThread);
					break;
				case "RESUME":
					btnProgressSwitcher.setText("PAUSE");
					handler.post(updateThread);
					break;
				}

				break;
			case R.id.btnHandler:
				HandlerThread handlerThread = new HandlerThread("handler_thread");
				handlerThread.start();

				HandlerMine myHandler = new HandlerMine(handlerThread.getLooper());
				Message m = myHandler.obtainMessage();

				Bundle bdl = new Bundle();
				bdl.putInt("bint", 23894);
				bdl.putString("bstring", "thisis bundle putstring value");

				m.setData(bdl);

				m.sendToTarget();

				Log.i("Handler Thread-->", String.valueOf(Thread.currentThread().getId()));

				break;
			case R.id.btnBroadCastReceiver:

				Intent i = new Intent();
				// 与ACTION_EDIT对应的常量值为：android.intent.action.EDIT
				// 因此需要在AndroidManifest.xml的receiver的action中进行配置
				i.setAction(Intent.ACTION_EDIT);
				OtherActivity.this.sendBroadcast(i);
				break;

			case R.id.btnRegisterReceiver:

				IntentFilter filter = new IntentFilter();
				filter.addAction("android.provider.Telephony.SMS_RECEIVED");

				OtherActivity.this.registerReceiver(new MyBroadCastReceiver(), filter);
				break;
			case R.id.btnUnregisterReceiver:
				OtherActivity.this.unregisterReceiver(new MyBroadCastReceiver());
				break;
			case R.id.btnStartWifi:
				wifiManager.setWifiEnabled(true);
				break;
			case R.id.btnStopWifi:
				wifiManager.setWifiEnabled(false);
				break;
			case R.id.btnCheckWifiState:
				String state = String.valueOf(wifiManager.getWifiState());
				Toast.makeText(OtherActivity.this, state, Toast.LENGTH_SHORT).show();
				break;
			case R.id.btnShowWifiList:
				List<WifiConfiguration> wifiList = wifiManager.getConfiguredNetworks();

				break;
			}
		}
	}

	class HandlerMine extends Handler {
		public HandlerMine(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message m) {
			Bundle bdl = m.getData();
			Log.i("Bundle Message-->", String.valueOf(bdl.getInt("bint")));
			Log.i("Bundle Message-->", bdl.getString("bstring"));

			Log.i("Handler Message-->", String.valueOf(Thread.currentThread().getId()));
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == -1) {
				btnProgressSwitcher.setText("PROGRESS");
			} else
				textview1.setText(String.valueOf(msg.what));

			super.handleMessage(msg);
		}
	};

	Runnable updateThread = new Runnable() {

		int progress = 0;

		@Override
		public void run() {
			Message m = handler.obtainMessage();
			if (progressBar1.getProgress() < progressBar1.getMax()) {
				progress += 1;
				progressBar1.setProgress(progress);

				m.what = progress;
				handler.sendMessage(m);

				handler.postDelayed(updateThread, 50);
			} else {
				m.what = -1;
				handler.sendMessage(m);
				progress = 0;
				handler.removeCallbacks(updateThread);
			}
		}
	};
}