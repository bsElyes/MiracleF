package com.elyes.miracle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.elyes.entities.Produit;
import com.elyes.fragments.AboutFragment;
import com.elyes.fragments.CartFragment;
import com.elyes.fragments.FragmentMap;
import com.elyes.fragments.MyCardFragment;
import com.elyes.fragments.ProductsFragment;
import com.elyes.fragments.ProductsFragmentV2;
import com.elyes.fragments.SliderFragment;
import com.elyes.utils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;


public class MainMenuV2 extends MaterialNavigationDrawer implements MaterialAccountListener {
    public static MaterialSection acceuil, cart, myCard,cat1,cat2,cat3,cat4,cat5,cat6,cat7,magz, about;
    //DB

    DatabaseHelper dbHelper;
    RuntimeExceptionDao<Produit, Integer> prodDao;
    List<Produit> produitsCart;
    public static  int ID_USER;
    @Override
    public void init(Bundle savedInstanceState) {

        dbHelper= OpenHelperManager.getHelper(this, DatabaseHelper.class);
        prodDao = dbHelper.getProdRuntimeExceptionDao();
        produitsCart=prodDao.queryForAll();
        //Parse.initialize(this, "ErsDQnOTOxjucKChMSNktHGHvSSEXQpr9s2TTRcU", "AeFz6Eq08EyukARexiU5nRy3tjGSiTUP5DhPd1u9");

        // add first account
        MaterialAccount account = new MaterialAccount(this.getResources(), "Elyes Ben Salah","Elyes.bensalah@esprit.tn",R.drawable.photo,R.drawable.dr_bg);
        this.addAccount(account);
        ID_USER =2015;
        // set listener
        this.setAccountListener(this);

        // create sections
        acceuil = this.newSection("Acceuil",this.getResources().getDrawable(R.drawable.dr_acceuil),new SliderFragment()).setSectionColor(Color.parseColor("#000000"));
        cart = this.newSection("Cart",this.getResources().getDrawable(R.drawable.shop),new CartFragment()).setNotifications(produitsCart.size()).setSectionColor(Color.parseColor("#000000"));
        magz = this.newSection("Magasine", this.getResources().getDrawable(R.drawable.dr_map), new FragmentMap()).setSectionColor(Color.parseColor("#000000"));

        /*section2 = this.newSection("Basket",new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                Toast.makeText(getApplicationContext(), "Section 2 Clicked", Toast.LENGTH_SHORT).show();

                // deselect section when is clicked
               section.unSelect();
            }
        });*/

        // recorder section with icon and 10 notifications

        // night section with icon, section color and notifications
        cat1 = this.newSection(getString(R.string.title_section1), this.getResources().getDrawable(R.drawable.dr_1),   new ProductsFragmentV2(1))
                .setSectionColor(Color.parseColor("#2196f3"),Color.parseColor("#1565c0"));
        cat2 = this.newSection(getString(R.string.title_section2), this.getResources().getDrawable(R.drawable.dr_2),   new ProductsFragment(2))
                .setSectionColor(Color.parseColor("#2196f3"),Color.parseColor("#1565c0"));
        cat3 = this.newSection(getString(R.string.title_section3), this.getResources().getDrawable(R.drawable.dr_3),   new ProductsFragment(3))
                .setSectionColor(Color.parseColor("#2196f3"), Color.parseColor("#1565c0"));
        cat4 = this.newSection(getString(R.string.title_section4), this.getResources().getDrawable(R.drawable.dr_4),   new ProductsFragment(4))
                .setSectionColor(Color.parseColor("#000000"));
        cat5 = this.newSection(getString(R.string.title_section5), this.getResources().getDrawable(R.drawable.dr_5),   new ProductsFragment(5))
                .setSectionColor(Color.parseColor("#2196f3"),Color.parseColor("#1565c0"));
        cat6 = this.newSection(getString(R.string.title_section6), this.getResources().getDrawable(R.drawable.dr_6),   new ProductsFragment(6))
                .setSectionColor(Color.parseColor("#2196f3"),Color.parseColor("#1565c0"));
        cat7 = this.newSection(getString(R.string.title_section7), this.getResources().getDrawable(R.drawable.dr_7),   new ProductsFragment(7))
                .setSectionColor(Color.parseColor("#2196f3"),Color.parseColor("#1565c0"));
        // night section with section color
        MyCardFragment fragCart= new MyCardFragment();
        myCard = this.newSection("My Card", this.getResources().getDrawable(R.drawable.dr_fidelity),fragCart).setSectionColor(Color.parseColor("#000000"),Color.parseColor("#ff9800"));

        Intent i = new Intent(this,Profile.class);
        about = this.newSection("About US",this.getResources().getDrawable(R.drawable.dr_about),new AboutFragment());

        this.addSection(acceuil);
        this.addSection(cart);
        this.addSection(myCard);
        this.addSection(magz);
        this.addSubheader("Categories");
        this.addSection(cat1);
        this.addSection(cat2);
        this.addSection(cat3);
        this.addSection(cat4);
        this.addSection(cat5);
        this.addSection(cat6);
        this.addSection(cat7);
        this.addDivisor();
        this.addBottomSection(about);
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

    }

    @Override
    public void onAccountOpening(MaterialAccount account) {
        // open profile activity

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        // when another account is selected
    }
}