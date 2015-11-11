package com.elyes.utils;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.elyes.entities.Produit;
import com.elyes.miracle.R;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.dismiss.DefaultDismissableManager;

/**
 * Created by Dark on 15/02/2015.
 */
public class MyCards {
    public MyCards(){

    }
    public void initCards(Context c , List<Card> cards,List<Produit> p){
        cards = new ArrayList<Card>();
        for(int i = 0 ; i<p.size(); i ++){
            CardExample card=new CardExample(c);

            CardHeader header = new CardHeader(c);
            header.setTitle(p.get(i).getLibelle());
            header.setPopupMenu(R.menu.menu_main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    //Toast.makeText(getActivity(), "Click on card menu" + i + " item=" + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            //card.addCardHeader(header);

            CardThumbnail thumb = new CardThumbnail(c);
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

    public void initAdpater(Context c , List<Card> cards) {

        CardArrayAdapter mCard = new MyAdapter(c, cards);

        mCard.setInnerViewTypeCount(3);
        mCard.setDismissable(new RightDismissableManager());
//        mCard.setEnableUndo(true);
        View v=new View(c);
        CardListView listV = (CardListView) v.findViewById(R.id.myList);
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
}
