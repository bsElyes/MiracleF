package com.elyes.fragments;

import java.util.HashMap;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.elyes.utils.ImageLoader;
import com.elyes.miracle.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SliderFragment extends Fragment implements OnSliderClickListener {
	
	
	private SliderLayout mDemoSlider;
	ImageLoader imgLoad;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_slider_fragment, container,false);
			
		
		mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);
		
		HashMap<String,String> url_maps = new HashMap<String, String>();
		
		url_maps.put("Prod 1", "http://miracle.net46.net/scripts/img/miracle/image_1.jpg");
		url_maps.put("Prod 2", "http://4.bp.blogspot.com/-Tz7pIQtKG90/UMAwb8ToA2I/AAAAAAAAOjs/7o4_RrUxc0c/s400/Green+Nature+Wallpapers+10.jpg");
		url_maps.put("Prod 3", "http://3.bp.blogspot.com/-Gn6A47xVxv8/Tfg5por2vgI/AAAAAAAAEMc/jED8GsjqCV4/s400/Latest-Wallpapers.png");
		url_maps.put("Prod 4", "http://miracle.net46.net/scripts/img/miracle/image_4.jpg");
		url_maps.put("Prod 5", "http://localhost:81/scripts/img/miracle/image_5.jpg");

		
		HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Prod 1",R.drawable.prod);
        file_maps.put("Prod 2",R.drawable.prod);
        file_maps.put("Prod 3",R.drawable.prod);
        file_maps.put("Prod 4", R.drawable.prod);
        file_maps.put("Prod 5", R.drawable.prod);
        for(String name:url_maps.keySet() ){
        	TextSliderView txSV = new TextSliderView(getActivity());
        	txSV.description(name)
        	.image(url_maps.get(name))
        	.setScaleType(BaseSliderView.ScaleType.Fit)
        	.setOnSliderClickListener(this);
        	
        	txSV.getBundle().putString("extra", name);
            mDemoSlider.addSlider(txSV);

        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        
		return v;
	}
	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub
		
	}
	
}
