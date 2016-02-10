package br.ufc.mdcc.nicempos.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.ufc.mdcc.hellompos.R;
import br.ufc.mdcc.mpos.MposFramework;
import br.ufc.mdcc.mpos.config.Inject;
import br.ufc.mdcc.mpos.config.MposConfig;
import br.ufc.mdcc.nicempos.business.CalculadoraImpl;
import br.ufc.mdcc.nicempos.business.ICalculadora;
import br.ufc.mdcc.nicempos.model.dao.VoteDAO;
import br.ufc.mdcc.nicempos.model.vo.Input;
import br.ufc.mdcc.nicempos.model.vo.Place;
import br.ufc.mdcc.nicempos.model.vo.Vote;

@SuppressLint("NewApi")
//@MposConfig(endpointSecondary = "10.99.206.104")
@MposConfig
public class MainActivity extends Activity {
	
	// Mpos
	@Inject(CalculadoraImpl.class)
	private ICalculadora calc;
	
	private boolean quit = false;
	
	// Location
	private String provider;
	boolean isKnownPlace = false;
	// flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meter
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 ; // 1 second
    // The distance radius to identify place in meters
    private static final long DISTANCE_RADIUS_IDENTIFY_PLACE = 80; // 200 meters
    // Declaring a Location Manager
    protected LocationManager locationManager;
 
    Location location; // delete
    double latitude;
    double longitude;
	
	// DB
    int idImage;
	ArrayList<Place> knownPlacesList;
	private VoteDAO voteDao;
	Vote currentVote; // from server
	
	// Functionality
	int currentPlaceId;  // placeId starts in 0
	int currentVoteId;   // from user
	int currentRatingId; // from server

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
		}
		MposFramework.getInstance().start(this);
		
		TextView instructionTv = (TextView)findViewById(R.id.instruction_tv);
		if (instructionTv != null) instructionTv.setTextColor(getResources().getColor(R.color.black));
		
		voteDao = (new VoteDAO(this));
		
		// Initialize list of places
		knownPlacesList = new ArrayList<Place>();
		idImage = -1;
		initPlaceList();
		
		// Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    provider = LocationManager.GPS_PROVIDER;
	    
		// Verify availability of localization providers 
		verifyLocalizationProviders();
		
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	    		MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Log.v("location-method", "onStatusChanged");
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				Log.v("location-method", "onProviderEnabled");
				Toast.makeText(getApplicationContext(), "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				Log.v("location-method", "onProviderDisabled");
				Toast.makeText(getApplicationContext(), "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onLocationChanged(Location location) {
				Log.v("location-method", "onLocationChanged");
				TextView tv = (TextView)findViewById(R.id.location_tv);
				if (location != null)
					tv.setText(getResources().getString(R.string.location_str) +" "+ 
							(double) location.getLatitude() +" "+ 
							(double) location.getLongitude());
				else
					tv.setText("location is null");
				
				// atualizar latitude e longitude
				if (location != null){
					latitude = location.getLatitude();
					longitude = location.getLongitude();
					
					// verificar se eh de um canto conhecido
					int placeId = isKnownPlace (location);
					showPlaceInformations(placeId);
					if (isKnownPlace) isKnownPlace = false;
				}
			}
		});
	    
	    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (quit) {
			MposFramework.getInstance().stop();
			Process.killProcess(Process.myPid());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("location-method", "Entrou onResume");
		// Request updates at startup
		//locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("location-method", "Entrou onPause");
		// Remove the locationlistener updates when Activity is paused
		//locationManager.removeUpdates(this);
	}
	
	/**
	 * A placeholder fragment (MainFragment) containing a simple view.
	 */
	public final class MainFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			initButtons(rootView);
			return rootView;
		}

		private void initButtons(View root) {
			Button answer1Button = (Button) root.findViewById(R.id.option_one_btn);
			answer1Button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentVoteId = 1;
					executeCommand("1");
				}
			});

			Button answer2Button = (Button) root.findViewById(R.id.option_two_btn);
			answer2Button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentVoteId = 2;
					executeCommand("2");
				}
			});

			Button answer3Button = (Button) root.findViewById(R.id.option_three_btn);
			answer3Button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentVoteId = 3;
					executeCommand("3");
				}
			});

			Button answer4Button = (Button) root.findViewById(R.id.option_four_btn);
			answer4Button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentVoteId = 4;
					executeCommand("4");
				}
			});
			
			Button answer5Button = (Button) root.findViewById(R.id.option_five_btn);
			answer5Button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentVoteId = 5;
					executeCommand("5");
				}
			});
		}

		private void executeCommand(String function) {
			View root = getView();
			//EditText number01 = (EditText) root.findViewById(R.id.number1_et);
			//EditText number02 = (EditText) root.findViewById(R.id.number2_et);
			
			// votes of the current place
			currentVote = voteDao.search(currentPlaceId);
			
			Command command = new Command();
			//command.input = new Input(currentPlaceId,currentVoteId); // dados obtidos da entrada do usuario
			command.input = new Input(currentVote, currentVoteId);
			command.function = function;

			new CalculatorTask(root).execute(command);
		}
	}

	// helper object to transfer data from UI to backend process
	private final class Command {
		private Input input;
		private String function;
	}
	
	// your process is recommend executed in parallel the thread UI, to avoid
	// exceptions
	final class CalculatorTask extends AsyncTask<Command, Void, Object> {

		private View root;

		public CalculatorTask(View root) {
			this.root = root;
		}

		@Override
		protected Object doInBackground(Command... params) {
			Command command = params[0];

			switch (command.function) {
			case "1":
				return calc.calculatesClassificationPlace(command.input);

			case "2":
				return calc.calculatesClassificationPlace(command.input);

			case "3":
				return calc.calculatesClassificationPlace(command.input);

			case "4":
				return calc.calculatesClassificationPlace(command.input);
			
			case "5":
				return calc.calculatesClassificationPlace(command.input);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
		    TextView nameTv = (TextView)findViewById(R.id.place_name_tv);
		    
		    // update place votes in the local database
		    switch((int)result){
				case 1:
					currentVote.setNVotesRating1(currentVote.getNVotesRating1()+1);
					break;
				case 2:
					currentVote.setNVotesRating2(currentVote.getNVotesRating2()+1);
					break;
				case 3:
					currentVote.setNVotesRating3(currentVote.getNVotesRating3()+1);
					break;
				case 4:
					currentVote.setNVotesRating4(currentVote.getNVotesRating4()+1);
					break;
				case 5:
					currentVote.setNVotesRating5(currentVote.getNVotesRating5()+1);
					break;
			}
		    currentVote.setNVotesPlace(currentVote.getNVotesPlace()+1);
		    voteDao.update(currentVote);
		    voteDao.list();
		    
		    Bundle bundle = new Bundle();
		    
		    bundle.putString("placeNameKey", nameTv.getText().toString());
            bundle.putInt("ratingIdKey", (int)result);
            bundle.putInt("nVotesPlaceKey", currentVote.getNVotesPlace());
            
            Intent resultActivity = new Intent(getApplicationContext(), ResultActivity.class);
            resultActivity.putExtras(bundle);
            startActivity(resultActivity);
		}
	}
	
	/**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    
    public void verifyLocalizationProviders () {
    	// Get GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.i("flag", "isGPSEnabled: " + isGPSEnabled);

        // Get network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("flag", "isNetworkEnabled: " + isNetworkEnabled);
        
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        	Log.i("flag", "no network provider is enabled");
        	showSettingsAlert();
        } else {
        	Log.i("flag", "network provider is enabled");
            this.canGetLocation = true;
        }
    }
    
    public void getLatitude () {
    	if (canGetLocation()){
    		
    	}
    }
    
    public void getLongitude () {
    	if (canGetLocation()){
    		
    	}
    }
    
	public void initPlaceList () {
		addPlace("Lagoa Campus do Pici",-3.742994,-38.574781);
		addPlace("Restaurante Universitário UFC",-3.744741,-38.572775);
		addPlace("Departamento da Computação UFC",-3.745846,-38.574147);
		addPlace("Biblioteca da Física UFC",-3.746942,-38.575325);
		addPlace("Praça das Mangueiras UFC",-3.746001,-38.574882);
		addPlace("GREat UFC",-3.746536,-38.578153);
		addPlace("Estátua Praia de Iracema",-3.720572,-38.509571);
		addPlace("Theatro José de Alencar",-3.727711,-38.53169);
		addPlace("North Shopping",-3.735455,-38.565949);
		addPlace("Jericoacoara",-2.7956,-40.5142);
		addPlace("Cumbuco",-3.618444,-38.748628);
		addPlace("Dragão do Mar",-3.721306,-38.520198);
	}
	
	public void addPlace (String name, double lat, double lon) {
		knownPlacesList.add(new Place(name, lat, lon, idImage++));
	}
	
	public int isKnownPlace (Location currentLocation) {
		for(int i = 0; i < knownPlacesList.size(); i++){
			if (isNearThePlace(currentLocation, i)){
				isKnownPlace = true;
				currentPlaceId = i;
				return i;
			}
		}
		return -1;
	}
	
	public boolean isNearThePlace (Location currentLocation, int placeId) {
		Location l = new Location(provider);
		l.setLatitude(knownPlacesList.get(placeId).getLat());
		l.setLongitude(knownPlacesList.get(placeId).getLon());
		
		Log.i("distance", "" + l.distanceTo(currentLocation) + "metros");
		if (l.distanceTo(currentLocation) <= DISTANCE_RADIUS_IDENTIFY_PLACE)
			return true;
		else
			return false;
	}
	
	// Function to show settings alert dialog
	public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        
        alertDialog.setTitle("Configurações de GPS");
        alertDialog.setMessage("GPS não está ativado. Você gostaria de ir para o menu de configurações?");
        
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });
        
        alertDialog.show();
    }
	
	public void showPlaceInformations (int placeId) {
		TextView nameTv = (TextView)findViewById(R.id.place_name_tv);
		ImageView imageIv = (ImageView)findViewById(R.id.place_image_iv);
		
		@SuppressWarnings("deprecation")
		Drawable drawable = getResources().getDrawable(getImageIdInR(placeId));
		
		if (placeId == -1)
			nameTv.setText(R.string.unavailable_place);
		else
			nameTv.setText(knownPlacesList.get(placeId).getName());
		imageIv.setImageDrawable(drawable);
	}
	
	// O id da imagem deve ser a posicao do local na lista
	public int getImageIdInR (int id) {	
		switch (id){
			case -1: return R.drawable.place_nophoto;
			case 0: return R.drawable.place_campuspici;
			case 1: return R.drawable.place_ru;
			case 2: return R.drawable.place_dc;
			case 3: return R.drawable.place_bibliofisica;
			case 4: return R.drawable.place_mangueiras;
			case 5: return R.drawable.place_great;
			case 6: return R.drawable.place_iracema;
			case 7: return R.drawable.place_teatrojosealencar;
			case 8: return R.drawable.place_northshopping;
			case 9: return R.drawable.place_jericoacoara;
			case 10: return R.drawable.place_cumbuco;
			case 11: return R.drawable.place_dragaodomar;
		}
		return 0;
		
	}
	
}
