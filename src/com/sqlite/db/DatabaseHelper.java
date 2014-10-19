package com.sqlite.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static final int VERSION=1;
	
	//Context:Activity 對象
	//name:表名
	//factory:
	//version:版本
	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	//參數不同的構造函數
	public DatabaseHelper(Context context,String name)
	{
		this(context, name,null,VERSION);
	}
	//參數不同的構造函數	
	public DatabaseHelper(Context context,String name,int version)
	{
		this(context, name,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.i("sqlite", "create database");
		
		//初始化數據庫表
		db.execSQL("create table device"
				+ "(id integer PRIMARY KEY autoincrement,"
				+ "deviceIp varchar(20),"
				+ "deviceName varchar(20),"
				+ "createTime TIMESTAMP default (datetime('now','localtime'))"
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		Log.i("sqlite","update database");
	}
}
