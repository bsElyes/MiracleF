

package com.elyes.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elyes.miracle.R;

import it.gmariotti.cardslib.library.internal.CardExpand;


public class GoogleNowExpandCard extends CardExpand {

    public GoogleNowExpandCard(Context context) {
        super(context, R.layout.carddemo_googlenow_inner_expand);

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        //Retrieve TextView elements
        TextView tx1 = (TextView) view.findViewById(R.id.carddemo_expand_text1);
        TextView tx2 = (TextView) view.findViewById(R.id.carddemo_expand_text2);
        TextView tx3 = (TextView) view.findViewById(R.id.carddemo_expand_text3);
        TextView tx4 = (TextView) view.findViewById(R.id.carddemo_expand_text4);

        if (tx1 != null) {
            tx1.setText("ABCD");
        }

        if (tx2 != null) {
            tx2.setText("Salut Comment ça va ? ");
        }

    }


}
