package com.elyes.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.elyes.miracle.R;

import it.gmariotti.cardslib.library.view.CardView;

public class AboutFragment extends Fragment {

    protected ScrollView mScrollView;
    private CardView cardView;
    private GoogleNowBirthCard birthCard;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_about, container,false);
		
		
		return v;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
        initCard();
    }

    private void initCard() {
        GoogleNowBirthCard card3 = new GoogleNowBirthCard(getActivity());
        card3.setId("About");
        card3.USE_VIGNETTE=1;

        //Set card in the cardView
        CardView cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth3);
        cardView3.setCard(card3);
    }


}
