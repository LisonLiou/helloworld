package com.example.helloworld;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sqlite.db.DatabaseHelper;

public class SqliteActivity extends Activity {

	private Button btnCreateDatabase, btnUpdateDatabase, btnInsert, btnUpdate,
			btnQuery;
	private ListView listviewSqlite;
	private EditText editText1, editText2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.sqlite);

		listviewSqlite = (ListView) super.findViewById(R.id.listviewSqlite);
		listviewSqlite.setAdapter(null);

		btnCreateDatabase = (Button) super.findViewById(R.id.btnCreateDatabase);
		btnCreateDatabase.setOnClickListener(new btnOnClickListener());

		btnInsert = (Button) super.findViewById(R.id.btnInsert);
		btnInsert.setOnClickListener(new btnOnClickListener());

		btnQuery = (Button) super.findViewById(R.id.btnQuery);
		btnQuery.setOnClickListener(new btnOnClickListener());

		editText1 = (EditText) super.findViewById(R.id.editText1);
		editText2 = (EditText) super.findViewById(R.id.editText2);

		// listview增加item单击事件,注意class要继承Activity,自定义listView的Activity时不要继承默认的ListViewActivity,
		// 因为它会自动寻找id为Android:id/list这样的listView,而我们的listView的id又是自定义的,所以会找不到
		listviewSqlite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	class btnOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			DatabaseHelper helper = new DatabaseHelper(SqliteActivity.this,
					"helloworld.db");

			SQLiteDatabase db = helper.getReadableDatabase();

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
				values.put("deviceIP", editText1.getText().toString());
				values.put("deviceName", editText2.getText().toString());

				Toast.makeText(SqliteActivity.this,
						String.valueOf(db.insert("device", "", values)),
						Toast.LENGTH_SHORT).show();
				;

				break;
			case R.id.btnUpdate:
				break;
			case R.id.btnQuery:

				Cursor cursor = db.query("device", new String[] { "Id", "deviceIp", "deviceName", "createTime" }, "", null, "", "", "createTime desc", "");
				ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String, String>>();

				while (cursor.moveToNext()) {
					Log.i("sqlite3-->", cursor.getString(cursor.getColumnIndex("id")));
					Log.i("sqlite3-->", cursor.getString(cursor.getColumnIndex("deviceIp")));
					Log.i("sqlite3-->", cursor.getString(cursor.getColumnIndex("deviceName")));
					Log.i("sqlite3-->", cursor.getString(cursor.getColumnIndex("createTime")));

					HashMap<String, String> hash1 = new HashMap<String, String>();
					hash1.put("id", String.valueOf(cursor.getColumnIndex("id")));
					hash1.put("deviceIp", String.valueOf(cursor.getColumnIndex("deviceIp")));
					hash1.put("deviceName", String.valueOf(cursor.getColumnIndex("deviceName")));
					hash1.put("createTime", String.valueOf(cursor.getColumnIndex("createTime")));

					hashList.add(hash1);
				}

				SimpleAdapter adapter = new SimpleAdapter(SqliteActivity.this,
						hashList, R.layout.sqlite_rowitem, new String[] { "id", "deviceIp", "deviceName", "createTime" },
						new int[] { R.id.textViewId, R.id.textViewDeviceIp, R.id.textViewDeviceName, R.id.textViewCreateTime });
				
				listviewSqlite.setAdapter(adapter);

				break;
			}
		}
	}
}
