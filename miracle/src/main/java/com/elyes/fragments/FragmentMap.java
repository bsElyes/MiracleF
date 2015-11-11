package com.elyes.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elyes.miracle.R;
import com.elyes.utils.GMapV2Direction;
import com.elyes.utils.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by Dark on 23/01/2015.
 */
public class FragmentMap extends Fragment {
    //MAP
    private GoogleMap mMap;
    private MapFragment mMapFragment;
    private GPSTracker mGPS ;
    public GMapV2Direction md;
    private static boolean showMap=true;
    LatLng fromPosition =null;
    LatLng toPosition = new LatLng(36.8498327,10.2203717);
    private static View view=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            mMapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapV2));
            mMap = mMapFragment.getMap();
            //mMapFragment.getView().setVisibility(View.INVISIBLE);
            MapDrawer(getActivity(), mMap);
        } catch (InflateException e) {

        }
        return view;
    }


    public void MapDrawer(Context context , GoogleMap mMap){
        mGPS=new GPSTracker(context);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        md = new GMapV2Direction();

        mMap.setMyLocationEnabled(true);
        //LatLng fromPosition = new LatLng(36.8339127,10.1789832);

        fromPosition= new LatLng(mGPS.getLatitude(),mGPS.getLongitude());
        LatLng coordinates = new LatLng(36.8339127,10.1789832);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16));

        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Vous ete ici !"));
        mMap.addMarker(new MarkerOptions().position(toPosition).title("Magasin Miracle"));

        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
        int duration = md.getDurationValue(doc);
        String distance = md.getDistanceText(doc);

        String start_address = md.getStartAddress(doc);
        String copy_right = md.getCopyRights(doc);


        Log.d("Durée", duration + "");
        Log.d("Distance :",distance+"");
        Log.d("start_address",start_address+"");
        Log.d("copy_right",copy_right+"");

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

        for(int i = 0 ; i < directionPoint.size() ; i++) {
            rectLine.add(directionPoint.get(i));
        }

        mMap.addPolyline(rectLine);
    }
}

