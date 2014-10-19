package com.example.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadCastReceiver extends BroadcastReceiver {

	public MyBroadCastReceiver() {
		Log.i("broadcast", "this is constructor");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Log.i("broadcast", "onReceived");

	}

}
