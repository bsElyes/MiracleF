package com.elyes.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elyes.entities.Category;
import com.elyes.miracle.R;


public class CategoryAdapter extends BaseAdapter{
	ArrayList<Category> categorys=new ArrayList<Category>();
	@Override
	public int getCount() {
		
		return categorys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		if(view==null){
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view=inflater.inflate(R.layout.item_category,parent, false);
		}
		
		TextView tv_cat=(TextView) view.findViewById(R.id.tv_category);
		Category cat = categorys.get(position);
		
		tv_cat.setText(cat.getLibelle());
		
		return view;
	}

}
