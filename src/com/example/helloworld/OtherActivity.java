package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
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

public class OtherActivity extends Activity{

	private TextView textview1;	
	private ProgressBar progressBar1;
	private Button btnProgressSwitcher,btnHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.other);
		
		Intent intent=getIntent();
		String value=intent.getStringExtra("testIntent");
		
		textview1=(TextView)super.findViewById(R.id.textview1);
		textview1.setText(R.string.other_app_name);
		
		progressBar1=(ProgressBar)super.findViewById(R.id.progressBar1);
		btnProgressSwitcher=(Button)super.findViewById(R.id.btnProgressSwitcher);
		btnProgressSwitcher.setOnClickListener(new onclickListener());
		
		progressBar1.setMax(200);
		progressBar1.setVisibility(ProgressBar.GONE);
		
		Toast.makeText(OtherActivity.this, value, Toast.LENGTH_SHORT).show();
		
		btnHandler=(Button)super.findViewById(R.id.btnHandler);
		btnHandler.setOnClickListener(new onclickListener());
	}
	
	class onclickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			
			switch(arg0.getId())
			{
			case R.id.btnProgressSwitcher:				
				
				progressBar1.setVisibility(ProgressBar.VISIBLE);				
				String text=btnProgressSwitcher.getText().toString();			
				
				switch(text)
				{
				case "PROGRESS":
					progressBar1.setProgress(0);		//设置初始值
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
				HandlerThread handlerThread=new HandlerThread("handler_thread");
				handlerThread.start();
				
				HandlerMine myHandler=new HandlerMine(handlerThread.getLooper());
				Message m=myHandler.obtainMessage();
				
				
				Bundle bdl=new Bundle();
				bdl.putInt("bint", 23894);
				bdl.putString("bstring", "thisis bundle putstring value");
				
				m.setData(bdl);
				
				m.sendToTarget();
				
				Log.i("Handler Thread-->", String.valueOf(Thread.currentThread().getId()));
				
				break;
			}			
		}
	}
	
	class HandlerMine extends Handler{
		public HandlerMine(Looper l)
		{
			super(l);
		}
		
		@Override
		public void handleMessage(Message m)
		{
			Bundle bdl=m.getData();
			Log.i("Bundle Message-->",String.valueOf(bdl.getInt("bint")));
			Log.i("Bundle Message-->",bdl.getString("bstring"));
			
			Log.i("Handler Message-->", String.valueOf(Thread.currentThread().getId()));
		}
	}

	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what==-1)
			{
				btnProgressSwitcher.setText("PROGRESS");
			}
			else 
				textview1.setText(String.valueOf(msg.what));
			
			super.handleMessage(msg);
		}};
	
	Runnable updateThread=new Runnable(){

		int progress=0;
		@Override
		public void run() {
			Message m=handler.obtainMessage();
			if(progressBar1.getProgress()<progressBar1.getMax())
			{
				progress+=1;
				progressBar1.setProgress(progress);
				
				m.what=progress;				
				handler.sendMessage(m);
				
				handler.postDelayed(updateThread,50);
			}			
			else 
			{
				m.what=-1;
				handler.sendMessage(m);
				progress=0;
				handler.removeCallbacks(updateThread);
			}
		}};
}