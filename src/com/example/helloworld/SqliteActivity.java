package com.example.helloworld;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlite.db.DatabaseHelper;

public class SqliteActivity extends Activity {

	private Button btnCreateDatabase, btnUpdateDatabase, btnInsert, btnUpdate,
			btnQuery,btnDelete;
	private ListView listviewSqlite;
	private EditText editText1, editText2;
	private ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String, String>>();
	private String currentId;		//保存当前操作的数据id
	
	DatabaseHelper helper;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.sqlite);
		
		//ContentResolver resolver=super.getContentResolver();
		
		
		helper=new DatabaseHelper(SqliteActivity.this, "helloworld.db");
		db= helper.getReadableDatabase();

		listviewSqlite = (ListView) super.findViewById(R.id.listviewSqlite);

		btnCreateDatabase = (Button) super.findViewById(R.id.btnCreateDatabase);
		btnCreateDatabase.setOnClickListener(new btnOnClickListener());

		btnInsert = (Button) super.findViewById(R.id.btnInsert);
		btnInsert.setOnClickListener(new btnOnClickListener());

		btnQuery = (Button) super.findViewById(R.id.btnQuery);
		btnQuery.setOnClickListener(new btnOnClickListener());
		
		btnUpdate=(Button)super.findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(new btnOnClickListener());
		
		btnDelete=(Button)super.findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(new btnOnClickListener());

		editText1 = (EditText) super.findViewById(R.id.editText1);
		editText2 = (EditText) super.findViewById(R.id.editText2);

		// listview增加item单击事件,注意class要继承Activity,自定义listView的Activity时不要继承默认的ListViewActivity,
		// 因为它会自动寻找id为Android:id/list这样的listView,而我们的listView的id又是自定义的,所以会找不到
		listviewSqlite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				HashMap<String, String> selected = (HashMap<String, String>) hashList.get(arg2);
				Log.i("listview------------->", selected.get("id"));
				Log.i("listview------------->", selected.get("deviceIp"));
				Log.i("listview------------->", selected.get("deviceName"));
				Log.i("listview------------->", selected.get("createTime"));
				
				currentId=selected.get("id");
				editText1.setText(selected.get("deviceName"));
				editText2.setText(selected.get("deviceIp"));
			}
		});
	}

	class MyListViewAdapter extends BaseAdapter {
		private ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String, String>>();
		private Activity activity;

		public MyListViewAdapter(Activity activity,
				ArrayList<HashMap<String, String>> hash) {
			super();
			this.activity = activity;
			this.hashList = hash;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return hashList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub

			return hashList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public class ViewHolder {
			TextView txtId;
			TextView txtDeviceIp;
			TextView txtDeviceName;
			TextView txtCreateTime;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			ViewHolder holder;
			LayoutInflater inflater = activity.getLayoutInflater();

			if (arg1 == null) {
				arg1 = inflater.inflate(R.layout.sqlite_rowitem, null);
				holder = new ViewHolder();
				holder.txtId = (TextView) arg1.findViewById(R.id.textViewId);
				holder.txtDeviceIp = (TextView) arg1
						.findViewById(R.id.textViewDeviceIp);
				holder.txtDeviceName = (TextView) arg1
						.findViewById(R.id.textViewDeviceName);
				holder.txtCreateTime = (TextView) arg1
						.findViewById(R.id.textViewCreateTime);

				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			HashMap<String, String> map = hashList.get(arg0);
			holder.txtId.setText(map.get("id"));
			holder.txtDeviceIp.setText(map.get("deviceIp"));
			holder.txtDeviceName.setText(map.get("deviceName"));
			holder.txtCreateTime.setText(map.get("createTime"));

			return arg1;
		}
	}

	//绑定ListView
	void BindList()
	{		
		Cursor cursor = db.query("device", new String[] { "id",
				"deviceIp", "deviceName", "createTime" }, "", null, "",
				"", "createTime desc", "");

		hashList.clear();
		
		while (cursor.moveToNext()) {
			Log.i("sqlite3-->",
					cursor.getString(cursor.getColumnIndex("id")));
			Log.i("sqlite3-->",
					cursor.getString(cursor.getColumnIndex("deviceIp")));
			Log.i("sqlite3-->", cursor.getString(cursor
					.getColumnIndex("deviceName")));
			Log.i("sqlite3-->", cursor.getString(cursor
					.getColumnIndex("createTime")));

			HashMap<String, String> hash1 = new HashMap<String, String>();
			hash1.put("id",
					cursor.getString(cursor.getColumnIndex("id")));
			hash1.put("deviceIp",
					cursor.getString(cursor.getColumnIndex("deviceIp")));
			hash1.put("deviceName", cursor.getString(cursor
					.getColumnIndex("deviceName")));
			hash1.put("createTime", cursor.getString(cursor
					.getColumnIndex("createTime")));

			hashList.add(hash1);
		}

		SimpleAdapter adapter = new SimpleAdapter(SqliteActivity.this,
				hashList, R.layout.sqlite_rowitem, new String[] { "id",
						"deviceIp", "deviceName", "createTime" },
				new int[] { R.id.textViewId, R.id.textViewDeviceIp,
						R.id.textViewDeviceName,
						R.id.textViewCreateTime });

		// MyListViewAdapter adapter=new
		// MyListViewAdapter(SqliteActivity.this,hashList);
		listviewSqlite.setAdapter(adapter);
	}
	
	class btnOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {

			switch (arg0.getId()) {
			case R.id.btnCreateDatabase:
				break;
			case R.id.btnUpdateDatabase:
				break;
			case R.id.btnInsert:

				String msg = "";

				if (editText1.getText().toString() == "")
					msg = "请输入Device Name";
				else if (editText2.getText().toString() == "")
					msg = "请输入Device IP";

				if (msg != "")
					Toast.makeText(SqliteActivity.this, msg, Toast.LENGTH_SHORT)
							.show();
				;

				ContentValues values = new ContentValues();
				values.put("deviceName", editText1.getText().toString());
				values.put("deviceIP", editText2.getText().toString());

				Toast.makeText(SqliteActivity.this,
						String.valueOf(db.insert("device", "", values)),
						Toast.LENGTH_SHORT).show();
				;

				break;
			case R.id.btnUpdate:
				
				if(String.valueOf(currentId)=="")
				{
					Toast.makeText(SqliteActivity.this, "unknow update id", Toast.LENGTH_SHORT).show();
				}
				else 
				{					
					String name=editText1.getText().toString();
					String ip=editText2.getText().toString();
					
					ContentValues vals=new ContentValues();
					vals.put("deviceName", name);
					vals.put("deviceIp", ip);					
					
					int r=db.update("device", vals, "id=?", new String[]{currentId});
					
					String result="";
					if(r>0)
					{
						result="update success";
						BindList();
					}
					else 
						result="update fail";
						
					Toast.makeText(SqliteActivity.this, result, Toast.LENGTH_SHORT).show();
				}
				
				break;
			case R.id.btnQuery:

				BindList();
				break;
				
			case R.id.btnDelete:
				
				if(String.valueOf(currentId)=="")
				{
					Toast.makeText(SqliteActivity.this, "unknow delete id", Toast.LENGTH_SHORT).show();
				}
				else 
				{
					int r=db.delete("device", "id=?", new String[]{currentId});
					String result="";
					if(r>0)
						result="delete success";
					else 
						result="delete fail";
					
					Toast.makeText(SqliteActivity.this, result, Toast.LENGTH_SHORT).show();
				}
				
				break;
			}
		}
	}
}
