package com.elyes.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.elyes.entities.Produit;
import com.elyes.miracle.R;
import com.elyes.utils.HelperHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;

public class ProductsFragmentV2 extends Fragment {

    ArrayList<Card> cards;
    public  List<Produit> produits=new ArrayList<>();
    CardArrayAdapter mCard;
    CardListView listV;

    int i=1;

    public static String  ipServer="http://192.168.1.5:81";
    String urlCat="/scripts/produits.php?id=";

    boolean done=false;

    public ProductsFragmentV2(){
        urlCat=ipServer+urlCat+""+i;

    }

    public ProductsFragmentV2(int i){
        urlCat=ipServer+urlCat+""+i;
        this.i=i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_products_fragment_v2,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AsycGetProducts().execute();

    }


    public void initCards(List<Produit>p){
        cards = new ArrayList<Card>();
        for(int i = 0 ; i<p.size(); i ++){
            CardExample card=new CardExample(getActivity());

            CardHeader header = new CardHeader(getActivity());
            header.setTitle(p.get(i).getLibelle());
            header.setPopupMenu(R.menu.menu_main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    //Toast.makeText(getActivity(), "Click on card menu" + i + " item=" + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            //card.addCardHeader(header);

            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setDrawableResource(R.drawable.dr_shop);
            card.addCardThumbnail(thumb);
            card.name=p.get(i).getLibelle();
            card.description=p.get(i).getDescription();
            card.rating=(float)(Math.random()*(5.0));
            card.price=p.get(i).getPrix()+"" ;

            card.setId(p.get(i).getId()+"");

            cards.add(card);
        }
    }

    public void initAdpater() {

        mCard = new MyAdapter(getActivity(), cards);
        CardListView listV = (CardListView) getActivity().findViewById(R.id.myList);

        if(listV!=null){
            listV.setAdapter(mCard);
        }

    }

    public class RightDismissableManager extends DefaultDismissableManager {

        @Override
        public SwipeDirection getSwipeDirectionAllowed() {
            return SwipeDirection.RIGHT;
        }
    }

    public class MyAdapter extends  CardArrayAdapter {
        public MyAdapter(Context c , List<Card> cards){

            super(c,cards);
            this.setInnerViewTypeCount(3);
            this.setDismissable(new RightDismissableManager());
            this.setEnableUndo(true);
            this.setUndoBarUIElements(new UndoBarController.DefaultUndoBarUIElements(){


                @Override
                public SwipeDirectionEnabled isEnabledUndoBarSwipeAction() {
                    return SwipeDirectionEnabled.TOPBOTTOM;   // it enables a swipe action from TOP to BOTTOM
                    //return SwipeDirectionEnabled.LEFTRIGHT; // it enables a swipe action FROM LEFT RO RIGHT
                    //return SwipeDirectionEnabled.NONEM      // it is the default. No action.
                }

                @Override
                public AnimationType getAnimationType() {
                    return AnimationType.TOPBOTTOM;  // Translation from bottom to top
                    //return AnimationType.ALPHA;    // Default: alpha animator
                }
            });
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
    }

    public class CardExample extends Card{
        TextView mName;
        TextView mDescription;
        RatingBar mRatingBar;
        TextView mPrice;

        String name;
        String description;
        float rating;
        String price;
        @Override
        public int getType(){
            return 2;
        }

        public CardExample(Context c){
            super(c,R.layout.product_inner_layout);
            setSwipeable(true);
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {

                }
            });

        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            mName=(TextView) parent.findViewById(R.id.productName);
            mRatingBar = (RatingBar) parent.findViewById(R.id.productRating);
            mDescription = (TextView) parent.findViewById(R.id.productDescription);
            mPrice = (TextView) parent.findViewById(R.id.productPrice);

            if(mName !=null){
                mName.setText(name);
            }
            if(mPrice !=null){
                mPrice.setText(price);
            }
            if(mDescription !=null){
                mDescription.setText(description);
            }

            if(mRatingBar !=null){
                mRatingBar.setNumStars(5);
                mRatingBar.setMax(5);
                mRatingBar.setStepSize(0.5f);
                mRatingBar.setRating(rating);
            }
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

    class AsycGetProducts extends AsyncTask<String, String, String> {
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
            Log.d("onPreExecute ", "Done");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            done=true;
            String jsonProduit= HelperHttp.getJSONResponseFromURL(urlCat);
            parseJsonProduit(produits, jsonProduit);

            Log.v("product",produits+"");

            Log.d("doInBackGroud ","Done");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("onPostExecute ","start");

            initCards(produits);
            initAdpater();
            barProgressDialog.dismiss();
            Log.d("onPostExecute ","Done");
            super.onPostExecute(result);
        }

    }
}
