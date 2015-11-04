package mx.lania.registrogastos;

import android.app.Activity;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.lania.registrogastos.controller.GastosController;

public class RegistrarGasto extends Activity {


    String latitudeLong="0";
    String longitudeLong="0";
    int idUsuario=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_gasto);
        Spinner spinner = (Spinner) findViewById(R.id.tipoGasto);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipogasto, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        idUsuario = getIntent().getExtras().getInt("ID_USUARIO");

    }



    public void onUbicar (View view){

        latitudeLong="0";
        longitudeLong="0";
        //super.onBackPressed();
        Intent intent = new Intent("mx.lania.registrogastos.SelectMapa");
        intent.putExtra("actividad", 1);
        startActivityForResult(intent, 1);

    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
           /* intent.putExtra("LATITUDE",loc.latitude);
            intent.putExtra("LONGITUDE",loc.longitude);*/
            if(data.getExtras().getDouble("LATITUDE")!= 0) {
                Double latitude = data.getExtras().getDouble("LATITUDE");
                Double longitude = data.getExtras().getDouble("LONGITUDE");
                latitudeLong = latitude.toString();
                longitudeLong = longitude.toString();
            }
            //textView1.setText(message);
        }
    }

    public void onCancelar (View view){
        latitudeLong="0";
        longitudeLong="0";
        finish();
    }

    public void onGuardar(View view) {
        try {
            EditText txtCantidad = (EditText) findViewById(R.id.cantidadGasto);
            EditText txtDescripcion = (EditText) findViewById(R.id.descripcionGasto);
            //EditText txtFecha = (EditText) findViewById(R.id.fechaGasto);
            Spinner txtTipoGasto = (Spinner) findViewById(R.id.tipoGasto);
            DatePicker txt_fecha =(DatePicker)this.findViewById(R.id.fecha);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Double cantidad = Double.parseDouble(txtCantidad.getText().toString());
            String descripcion = txtDescripcion.getText().toString();
            //Date fecha = format.parse(txtFecha.getText().toString());
            String tipoGasto = txtTipoGasto.getSelectedItem().toString();

            int mes = txt_fecha.getMonth();
            int dia = txt_fecha.getDayOfMonth();
            int anio = txt_fecha.getYear();

            String anioS = String.valueOf(anio);
            String mesS = String.valueOf((mes + 1));
            if ((mes + 1) < 10) {
                mesS = "0" + String.valueOf((mes + 1));
            }



            String fechaString = dia + "/"+ mes + "/"+ anioS;
            GastosController.insertGasto(this, descripcion, cantidad, fechaString, idUsuario, tipoGasto,latitudeLong,longitudeLong);
            latitudeLong="0";
            longitudeLong="0";
            Toast.makeText(this, "Se ha guardado el gasto", Toast.LENGTH_LONG).show();
            //Thread.sleep(Long.parseLong("1000"));
            Intent intent = new Intent("mx.lania.registrogastos.historial");

            intent.putExtra("ID_USUARIO", idUsuario);
            startActivity(intent);
            //finish();

        }catch (Exception e)
        {
            Toast.makeText(this,"Error :" +e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_gasto, menu);
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
}
