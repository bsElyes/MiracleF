package com.elyes.utils;

import java.io.IOException;
import java.sql.SQLException;

import com.elyes.entities.Produit;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil{

	final static Class<?>[] classes =new Class[]{Produit.class};
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("database_config.txt",classes);
	}

}
