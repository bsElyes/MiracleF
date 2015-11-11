package com.elyes.utils;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.elyes.entities.Produit;
import com.elyes.miracle.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

	private static final String DATABASE_NAME="Miracle";
	
	private static final int DATABASE_VERSION=1;
	

	
	private Dao<Produit, Integer> prodDao=null;
	private RuntimeExceptionDao<Produit, Integer> prodRuntimeDao = null;
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION,R.raw.db_conf);
	}


	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			
			TableUtils.createTable(connectionSource, Produit.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Produit.class, true);
			onCreate(database,connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Dao<Produit,Integer>getDao(){
		if(prodDao==null){
			try {
				prodDao = getDao(Produit.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return prodDao;
	}
	
	public RuntimeExceptionDao<Produit, Integer> getProdRuntimeExceptionDao(){
		if(prodRuntimeDao==null){
			prodRuntimeDao= getRuntimeExceptionDao(Produit.class);
		}
		return prodRuntimeDao;
	}

}
