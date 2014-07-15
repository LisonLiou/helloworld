package com.example.helloworld;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListViewActivity extends ListActivity{
	ArrayList<HashMap<String,String>> hashList=new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.listview);

		
		HashMap<String,String> map1=new HashMap<String,String>();
		HashMap<String,String> map2=new HashMap<String,String>();
		HashMap<String,String> map3=new HashMap<String,String>();
		HashMap<String,String> map4=new HashMap<String,String>();
		HashMap<String,String> map5=new HashMap<String,String>();
		HashMap<String,String> map6=new HashMap<String,String>();
		HashMap<String,String> map7=new HashMap<String,String>();
		HashMap<String,String> map8=new HashMap<String,String>();
		
		map1.put("DeviceIP", "192.168.1.1");
		map1.put("DeviceName", "Gateway");
		
		map2.put("DeviceIP","192.168.1.2");
		map2.put("DeviceName","Acer-Laptop");
		
		map3.put("DeviceIP","192.168.1.3");
		map3.put("DeviceName","HP-Laptop");
		
		map4.put("DeviceIP","192.168.1.4");
		map4.put("DeviceName","Founder-Laptop");
		
		map5.put("DeviceIP","192.168.1.5");
		map5.put("DeviceName","Virtual-Debian7-PC");
		
		map5.put("DeviceIP","192.168.1.6");
		map5.put("DeviceName","SAMSUMG-Android-Cellphone");
		
		map6.put("DeviceIP", "192.168.1.7");
		map6.put("DeviceName", "Motorola-Android-Cellphone");
		
		map7.put("DeviceIP","192.168.1.104");
		map7.put("DeviceName","Raspberry-PI");
		
		map8.put("DeviceIP","192.168.1.8");
		map8.put("DeviceName","iPhone-4S");
		
		hashList.add(map1);
		hashList.add(map2);
		hashList.add(map3);
		hashList.add(map4);
		hashList.add(map5);
		hashList.add(map6);
		hashList.add(map7);
		hashList.add(map8);
		
		SimpleAdapter adapter=new SimpleAdapter(ListViewActivity.this,hashList,R.layout.listview,new String[]{"DeviceIP","DeviceName"},new int[]{R.id.textviewIP,R.id.textViewName});
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	
		//
		//TODO:	position与id只是listview中当前行的索引,如何得到当前点击的TextView的内容,已经搞定了
		//			看下面的Log.i
		//
		
		HashMap<String,String> current=(HashMap<String,String>)hashList.get(position);
		
		Log.i("device--------->",current.get("DeviceIP"));
		Log.i("device--------->",current.get("DeviceName"));		
		
		Toast.makeText(ListViewActivity.this, "------------------------"+id, Toast.LENGTH_SHORT).show();
	}
}
