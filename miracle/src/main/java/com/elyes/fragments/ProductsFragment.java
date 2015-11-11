package com.elyes.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.elyes.adapter.ProduitAdapter;
import com.elyes.entities.Produit;
import com.elyes.miracle.MainMenuV2;
import com.elyes.miracle.ProductDetails;
import com.elyes.miracle.R;
import com.elyes.utils.HelperHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProductsFragment extends Fragment {
	ListView listProduitsList;
	GridView listProduitsGrid;
	public  List<Produit> produits=new ArrayList<>();
	ProduitAdapter produitAdapter;
	public static String  ipServer="http://192.168.1.2:81";
	String urlCat="/scripts/produits.php?id=";
	boolean done=false;
    int i=1;
	public ProductsFragment(){
		urlCat=ipServer+urlCat+""+i;
            //new AsycGetProducts().execute();


	}
	
	public ProductsFragment(int i) {
		urlCat=ipServer+urlCat+""+i;
        this.i = i;

       // new AsycGetProducts().execute();

    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_produits, container , false);
				
		listProduitsList=(ListView) v.findViewById(R.id.lv_produitsList);		
		if(produits.size()!=0){
            produitAdapter = new ProduitAdapter(getActivity(), R.layout.produit_detail_row, produits);

            listProduitsList.setAdapter(produitAdapter);
            listProduitsGrid.setAdapter(produitAdapter);
        }
		listProduitsList.setOnItemClickListener(new OnItemClickListener(
				) {
					@Override
					public void onItemClick(AdapterView<?> arg0, View v,
							int arg2, long arg3) {
						Intent intent=new Intent(getActivity(),ProductDetails.class);
						intent.putExtra("URLImage",produits.get(arg2).getImagePath());
						intent.putExtra("LibelleProduit",produits.get(arg2).getLibelle());
						intent.putExtra("Prix",produits.get(arg2).getPrix());
						getActivity().startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
		});
		listProduitsGrid=(GridView)v.findViewById(R.id.lv_produitsGrid);
		listProduitsGrid.setOnItemClickListener(new OnItemClickListener(
				) {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					int arg2, long arg3) {
				Intent intent=new Intent(getActivity(),ProductDetails.class);
				intent.putExtra("URLImage",produits.get(arg2).getImagePath());
				intent.putExtra("LibelleProduit",produits.get(arg2).getLibelle());
				intent.putExtra("Prix",produits.get(arg2).getPrix());
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
});

		return v;
	}
	
	@Override
	public void onStart() {
        onConfigurationChanged(getResources().getConfiguration());
        if (!done) {
            new AsycGetProducts().execute();
        }

        super.onStart();

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			listProduitsList.setVisibility(View.VISIBLE);			
			listProduitsGrid.setVisibility(View.GONE);
		}

		else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			listProduitsList.setVisibility(View.GONE);			
			listProduitsGrid.setVisibility(View.VISIBLE);
		}

	}
	
	
	void parseJsonProduit (List<Produit> list,String json){
		try {
			JSONArray array=new JSONArray(json);
			
			for(int i = 0 ;i<array.length();i++){
				JSONObject j=array.getJSONObject(i);
				Produit p = new Produit();
				p.setId(j.optInt("idProduit"));
				p.setDispo(j.optBoolean("Disponible"));
				p.setImagePath(j.optString("ImagePath"));
				p.setLibelle(j.optString("LibelleProduit"));
				p.setPrix(j.optDouble("PrixProduit"));
				p.setQuantite(j.optInt("QuantiteProduit"));
				p.setDescription(j.optString("Description"));
				p.setIdC(j.optInt("Categorie"));
				//p.setNew(j.optInt("isNew"));
				p.setDateAjout(new Date(System.currentTimeMillis()));
				
				list.add(p);
			}
		} catch (JSONException e) {
			Log.d("JSON ",e.getMessage());
		}
		Log.d("parseJsonProduit ","Done");
	}
	
	class AsycGetProducts extends AsyncTask<String, String, String>{
		ProgressDialog barProgressDialog = new ProgressDialog(getActivity());

        public List<Produit> getList(){
            return produits;
        }
		@Override
		protected void onPreExecute() {
			barProgressDialog.setTitle("Loading...");
			barProgressDialog.setMessage("Please Wait...");
			barProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			
			barProgressDialog.show();
			Log.d("onPreExecute ","Done");
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			done=true;
			String jsonProduit=HelperHttp.getJSONResponseFromURL(urlCat);
			parseJsonProduit(produits, jsonProduit);
			
			Log.v("product",produits+"");
			
			Log.d("doInBackGroud ","Done");
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.d("onPostExecute ","start");

			produitAdapter = new ProduitAdapter(getActivity(), R.layout.produit_detail_row, produits);

				listProduitsList.setAdapter(produitAdapter);
				listProduitsGrid.setAdapter(produitAdapter);
            switch(i){
                case 1 : MainMenuV2.cat1.setNotifications(produits.size());break;
                case 2 : MainMenuV2.cat2.setNotifications(produits.size());break;
                case 3 : MainMenuV2.cat3.setNotifications(produits.size());break;
                case 4 : MainMenuV2.cat4.setNotifications(produits.size());break;
                case 5 : MainMenuV2.cat5.setNotifications(produits.size());break;
                case 6 : MainMenuV2.cat6.setNotifications(produits.size());break;
                case 7 : MainMenuV2.cat7.setNotifications(produits.size());break;

            }
			barProgressDialog.dismiss();
			Log.d("onPostExecute ","Done");
			super.onPostExecute(result);
		}
		
	}
	
}