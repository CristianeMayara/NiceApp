package br.ufc.mdcc.nicempos.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import br.ufc.mdcc.hellompos.R;

public class ResultActivity extends Activity {
	
	String placeName;
	int ratingId;
	int nVotesPlace;
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		// Get place name from first activity
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		placeName = bundle.getString("placeNameKey");
		ratingId = bundle.getInt("ratingIdKey");
		nVotesPlace = bundle.getInt("nVotesPlaceKey");
		Log.i("dados recebidos", placeName +" "+ ratingId +" "+ nVotesPlace);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.containerResult, new ResultFragment(placeName, ratingId, nVotesPlace)).commit();
		}
		
		// Set color to activity background according to results 
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.containerResult);
		switch(ratingId){
			case 1: rl.setBackgroundColor(getResources().getColor(R.color.little_nice_color)); break;
			case 2: rl.setBackgroundColor(getResources().getColor(R.color.acceptable_color)); break;
			case 3: rl.setBackgroundColor(getResources().getColor(R.color.nice_color)); break;
			case 4: rl.setBackgroundColor(getResources().getColor(R.color.very_nice_color)); break;
			case 5: rl.setBackgroundColor(getResources().getColor(R.color.very_pleasant_color)); break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
