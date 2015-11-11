package com.elyes.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.elyes.adapter.ShoppingCartAdapter;
import com.elyes.entities.Produit;
import com.elyes.miracle.MainMenuV2;
import com.elyes.miracle.R;
import com.elyes.utils.DatabaseHelper;
import com.elyes.utils.JSONParserSend;
import com.gc.materialdesign.views.ButtonRectangle;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
	public ListView listItemCart;
	public  List<Produit> produitsCart=null;
	
	String urlRes= ProductsFragment.ipServer+"/scripts/addRes.php";
	private ProgressDialog pDialog;
	JSONParserSend jsonParser = new JSONParserSend();
	private static final String TAG_SUCCESS = "success";
    public ShoppingCartAdapter adapter;
	 //DB
		DatabaseHelper dbHelper;
		RuntimeExceptionDao<Produit, Integer> prodDao;
	
	@SuppressLint("ValidFragment")
    public CartFragment(List<Produit>list) {
		produitsCart=list;

	}
	
	public CartFragment() {
        dbHelper=OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        prodDao = dbHelper.getProdRuntimeExceptionDao();
        produitsCart=prodDao.queryForAll();

    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        dbHelper=OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        prodDao = dbHelper.getProdRuntimeExceptionDao();
        produitsCart=prodDao.queryForAll();
        View v = inflater.inflate(R.layout.fragment_shoppingcart, container,false);
		listItemCart = (ListView)v.findViewById(R.id.lv_shopping);
        dbHelper=OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        prodDao = dbHelper.getProdRuntimeExceptionDao();
        produitsCart=prodDao.queryForAll();
        adapter=new ShoppingCartAdapter(getActivity(), R.layout.shopping_detail_row, produitsCart);
        listItemCart.setAdapter(adapter);





		ButtonRectangle process = (ButtonRectangle) v.findViewById(R.id.btn_checkout);
		process.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
            @Override
			public void onClick(View v) {
				if(produitsCart.size()==0){
					Toast.makeText(getActivity(), "List est Vide", Toast.LENGTH_LONG).show();
				}
				else{
					new AddReservation(produitsCart).execute();
					//TODO
					//Fragment fg = getActivity().getFragmentManager().findFragmentById(R.layout.fragment_shoppingcart).;

				}
			}
		});
		return v;
	}
	
	public class AddReservation extends AsyncTask<String, String,String>{
		public List<Produit> listP=null;
		DatabaseHelper dbHelper;
		RuntimeExceptionDao<Produit, Integer> prodDao;
		ListView listShop;
		public AddReservation(List<Produit> list) {
			listP=list;
			dbHelper=OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
			prodDao = dbHelper.getProdRuntimeExceptionDao();
		}	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Add Reservation");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
            JSONObject  json=null;
			for(int i=0;i<listP.size();i++){
			List<NameValuePair> val = new ArrayList<NameValuePair>();
			val.add(new BasicNameValuePair("idClient", MainMenuV2.ID_USER+""));
			val.add(new BasicNameValuePair("idProduit", listP.get(i).getId() + ""));
            try{
                 json = jsonParser.makeHttpRequest(urlRes,"POST", val);

            }catch(Exception e){
                Log.d("Create Response", json.toString());
                Log.d("Exception",e.getMessage());
            }


			try {
                int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
                    prodDao.delete(listP);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
                            prodDao = dbHelper.getProdRuntimeExceptionDao();
                            produitsCart = prodDao.queryForAll();
                            MainMenuV2.cart.setNotificationsText(produitsCart.size()+"");
                            adapter = new ShoppingCartAdapter(getActivity(), R.layout.shopping_detail_row, produitsCart);
                            adapter.notifyDataSetChanged();
                            listItemCart.setAdapter(adapter);
                        }
                    });

				} else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Cannot Connect to database Server Try later", Toast.LENGTH_LONG).show();
                            Log.d("ERREUR", "ERREUR d'ajoute a la base de donnee");

                        }
                    });

                    }
                } catch (Exception e) {
				Log.d("RAISON",e.getMessage());
			}
			}
			
			
			return null;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

		}
		
		
		
	}
}