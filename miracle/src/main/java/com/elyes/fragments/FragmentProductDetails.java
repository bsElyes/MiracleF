package com.elyes.fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elyes.entities.Produit;
import com.elyes.utils.ImageLoader;
import com.elyes.miracle.R;

public class FragmentProductDetails extends Fragment {
	ImageLoader imgLoader;
	Produit p=null;
	public FragmentProductDetails(Produit p) {
		this.p=p;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v= inflater.inflate(R.layout.activity_product_details, container,false);
		ImageView imgProd = (ImageView) v.findViewById(R.id.produitImageD);
		TextView libelprod=(TextView) v.findViewById(R.id.productLibelleD);
        TextView prixProd=(TextView) v.findViewById(R.id.produitPrixD);
        TextView descProd=(TextView) v.findViewById(R.id.produitDescriptionD);
		imgLoader.DisplayImage(ProductsFragment.ipServer+p.getImagePath(), imgProd);
		libelprod.setText(p.getLibelle());
		prixProd.setText(p.getPrix()+"");
        descProd.setText(p.getDescription());
		return v;
	}	


}
