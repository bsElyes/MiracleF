package com.elyes.miracle;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.elyes.fragments.ProductsFragment;
import com.elyes.utils.ImageLoader;

public class ProductDetails extends Activity {
	ImageLoader imgLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		imgLoader = new ImageLoader(this);
		Intent intent = getIntent();
		String LibelleP = intent.getStringExtra("LibelleProduit");
		String PrixP = intent.getStringExtra("Prix");
		String ImgP = intent.getStringExtra("URLImage");
		
		
		ImageView imgP=(ImageView) findViewById(R.id.produitImageD);
		imgLoader.DisplayImage(ProductsFragment.ipServer+ImgP, imgP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
