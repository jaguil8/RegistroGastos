package mx.lania.registrogastos;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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

public class SelectMapa  extends Activity implements OnMapReadyCallback {

    protected static final String TAG = "SelectMapa";

    GoogleMap googleMap ;
    Location locationG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mapa);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();

        googleMap.setMyLocationEnabled(true);

        //locationG= googleMap.getMyLocation();



        /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(19.1998, -87.2272), 13
        ));*/

        //mapFragment.getContext(


        /*double longitude = googleMap.getMyLocation().getLongitude();
        double latitude = googleMap.getMyLocation().getLatitude();
*/
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
                Intent intent=new Intent();
                double[] arrayDouble = new double[2];
                arrayDouble[0]=loc.latitude;
                arrayDouble[1]=loc.longitude;
                intent.putExtra("COOR",arrayDouble);
                intent.putExtra("LATITUDE", loc.latitude);
                intent.putExtra("LONGITUDE",loc.longitude);
                Toast.makeText(SelectMapa.this,"Ha seleccionado las coordenadas latitud:" + loc.latitude + " longitud:" + loc.longitude,Toast.LENGTH_LONG).show();
                setResult(1,intent);
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
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(loc));
            if(googleMap != null){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
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
                new LatLng(19.4555471, -96.959272), 13
        ));

    }
}
