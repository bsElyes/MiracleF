package com.elyes.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elyes.entities.Produit;
import com.elyes.fragments.ProductsFragment;
import com.elyes.miracle.MainMenuV2;
import com.elyes.utils.DatabaseHelper;
import com.elyes.utils.ImageLoader;
import com.elyes.miracle.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.readystatesoftware.viewbadger.BadgeView;


public class ProduitAdapter extends ArrayAdapter<Produit>{
	
	int layoutResourceId;
	ImageLoader imageLoader;
	Context context;
	List<Produit>produits=new ArrayList<Produit>();
	BadgeView badge2;
	DatabaseHelper dbHelper;
	RuntimeExceptionDao<Produit, Integer> prodDao;
	public ProduitAdapter(Context context, int layoutResourceId
			, List<Produit> produits) {
		super(context, layoutResourceId, produits);
		
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.produits = produits;
		imageLoader = new ImageLoader(context.getApplicationContext());
		
	}
	
	void addProduct(Produit p){
			if(prodDao.queryForId(p.getId()) == null){
			prodDao.create(p);
			Log.d("Product Added",p.toString());
			}else{
				Toast.makeText(getContext(), "Product Already added !" , Toast.LENGTH_SHORT);
			}
			List<Produit> listProduits = prodDao.queryForAll();
			//Log.d("Shopping Cart List Produits ", listProduits.toString());
			OpenHelperManager.releaseHelper();
		
	}
@Override
public int getPosition(Produit item) {
    return super.getPosition(item);
}

@Override
public Produit getItem(int position) {
    return produits.get(position);
}

@Override
public int getCount() {
    return produits.size();
}

@Override
public long getItemId(int position) {
    return super.getItemId(position);
}


	
	@SuppressLint("SdCardPath")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
			final int pos=position;
			ImageView produitImg = (ImageView) row.findViewById(R.id.img_produit);
			TextView produitLibelle = (TextView) row.findViewById(R.id.tv_produit_name);
			TextView produitPrix = (TextView) row.findViewById(R.id.tv_produit_prix);
			TextView produitDesc=(TextView)row.findViewById(R.id.tv_description_produit);
			Button produitAdd=(Button) row.findViewById(R.id.btn_addCart);
			
			produitAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try{
						dbHelper=OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
						prodDao = dbHelper.getProdRuntimeExceptionDao();
						

					prodDao.create(produits.get(pos));
					Toast.makeText(getContext(),produits.get(pos).getLibelle()+"Add To Cart", Toast.LENGTH_SHORT).show();
                    MainMenuV2.cart.setNotificationsText(prodDao.queryForAll().size()+"");
                    }catch(Exception e){
						Toast.makeText(getContext(),"Product Already Added !", Toast.LENGTH_SHORT).show();
						List<Produit>listProd=prodDao.queryForAll();
						Log.d("erreur","ERROR TO ADD"+listProd.toString());
					}
				}
			});
		
		Produit p = produits.get(position);
		System.out.println(p.getId()+"  "+p.getLibelle()+ " *** "+p.getImagePath());
//		if(position==1){
//			badge2 = new BadgeView(getContext(), holder.produitLibelle);
//	    	badge2.setText("New!");
//	    	badge2.setTextColor(Color.BLUE);
//	    	badge2.setBadgeBackgroundColor(Color.YELLOW);
//	    	badge2.setTextSize(12);
//	    	badge2.show();
//		}
		imageLoader.DisplayImage(ProductsFragment.ipServer+p.getImagePath(), produitImg);
		produitLibelle.setText(p.getLibelle());
		produitPrix.setText(p.getPrix()+" DT");
		produitDesc.setText(p.getDescription());
		return row;
	}

	
}
