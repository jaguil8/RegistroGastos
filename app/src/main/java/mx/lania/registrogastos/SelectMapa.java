package mx.lania.registrogastos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectMapa extends Activity implements OnMapReadyCallback, LocationListener {

    protected static final String TAG = "SelectMapa";

    GoogleMap googleMap;

    boolean primerActivo = true;
    Location locationG;

    double longitude=0, latitude=0, actividad = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mapa);

        //getActivity().onBackPressed();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();

        googleMap.setMyLocationEnabled(true);

        //locationG= googleMap.getMyLocation();

        int actividad = getIntent().getExtras().getInt("actividad");
        if (actividad == 2) {
            latitude = (Double.parseDouble(getIntent().getExtras().getString("coordenadaLatitude")));
            longitude = (Double.parseDouble(getIntent().getExtras().getString("coordenadaLongitude")));
            //primerActivo=false;
            LatLng loc = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(loc));

        } else if (actividad == 1) {
            primerActivo = true;
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);



            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);

                // Showing the current location in Google Map
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                // Zoom in the Google Map
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, (LocationListener) this);
        }


        //googleMap.setOnMyLocationChangeListener(myLocationChangeListener);
        googleMap.setOnMarkerClickListener(myMarkerChangeListener);
        googleMap.setOnMapLongClickListener(myLongClickListener);


        //Log.d(TAG, "Coordenadas " + longitude + " , " + latitude);*/
        mapFragment.getMapAsync(this);

        Toast.makeText(this,"Deje presionado un punto en el mapa de su compra",Toast.LENGTH_LONG).show();

    }

    private GoogleMap.OnMapLongClickListener myLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            Log.d(TAG, "LongClick ");
            LatLng loc =latLng;
            googleMap.addMarker(new MarkerOptions().position(loc));
            if(googleMap != null){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
                int actividad=getIntent().getExtras().getInt("actividad");
                if(actividad==1) {
                    Intent intent = new Intent();
                    double[] arrayDouble = new double[2];
                    arrayDouble[0] = loc.latitude;
                    arrayDouble[1] = loc.longitude;
                    intent.putExtra("COOR", arrayDouble);

                    intent.putExtra("LATITUDE", loc.latitude);
                    intent.putExtra("LONGITUDE", loc.longitude);
                    Toast.makeText(SelectMapa.this, "Ha seleccionado las coordenadas latitud:" + loc.latitude + " longitud:" + loc.longitude, Toast.LENGTH_LONG).show();
                    setResult(1, intent);
                }
                finish();//finishing activity
            }

        }
    };

    private GoogleMap.OnMarkerClickListener myMarkerChangeListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d(TAG, "Marker ");
            return false;
        }
    };


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            /*if(primerActivo) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(loc));
                if (googleMap != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    primerActivo = false;
                    latitude=loc.latitude;
                    longitude=loc.longitude;
                }
            }*/
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 16
        ));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //super.onBackPressed();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //super.onBackPressed();

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
