package mx.lania.registrogastos;

import android.app.Activity;

import android.content.Intent;
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
        Uri uri = Uri.parse("geo:23.5541314,-102.6205");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("mx.lania.registrogastos.MapsActivity");
        startActivity(intent);
    }

    public void onCancelar (View view){
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
            GastosController.insertGasto(this, descripcion, cantidad, fechaString, idUsuario, tipoGasto);
            Toast.makeText(this, "Se ha guardado el gasto", Toast.LENGTH_LONG).show();
            //Thread.sleep(Long.parseLong("1000"));
            finish();

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
