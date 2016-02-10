package br.ufc.mdcc.nicempos.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.ufc.mdcc.hellompos.R;

@SuppressLint("NewApi")
public class ResultFragment extends Fragment {
	
	String placeName;
	int ratingId;
	int nVotesPlace;
	
	public ResultFragment(String placeName, int ratingId, int nVotesPlace) {
		this.placeName = placeName;
		this.ratingId = ratingId;
		this.nVotesPlace = nVotesPlace;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_result, container, false);
		
		// Set place name
		TextView nameTv = (TextView)rootView.findViewById(R.id.place_name_tv_result);
		if (nameTv != null) nameTv.setText(placeName);
		
		// Set place number of votes
		TextView nVotesTv = (TextView)rootView.findViewById(R.id.number_votes_tv);
		nVotesTv.setText(getResources().getString(R.string.number_votes_init) +" "+ nVotesPlace +" "+ 
				getResources().getString(R.string.number_votes_final));
		
		Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/STENCIL.TTF"); 
		TextView ratingTv = (TextView)rootView.findViewById(R.id.rating_tv);
		if (ratingTv != null) ratingTv.setTypeface(tf);
		
		// Set classification to text view according to results
		switch(ratingId){
			case 1: ratingTv.setText(getResources().getString(R.string.rating_little_nice)); break;
			case 2: ratingTv.setText(getResources().getString(R.string.rating_acceptable)); break;
			case 3: ratingTv.setText(getResources().getString(R.string.rating_nice)); break;
			case 4: ratingTv.setText(getResources().getString(R.string.rating_very_nice)); break;
			case 5: ratingTv.setText(getResources().getString(R.string.rating_very_pleasant)); break;
		}
		
		return rootView;
	}

}
