package mx.lania.registrogastos;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.lania.registrogastos.controller.GastosController;

public class RegistrarGasto extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_gasto);

        
    }

    public void onGuardar(View view) throws ParseException, InterruptedException {
        EditText txtCantidad = (EditText) findViewById(R.id.cantidadGasto);
        EditText txtDescripcion = (EditText) findViewById(R.id.descripcionGasto);
        EditText txtFecha = (EditText) findViewById(R.id.fechaGasto);
        EditText txtTipoGasto = (EditText) findViewById(R.id.tipoGasto);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Double cantidad = Double.parseDouble(txtCantidad.getText().toString());
        String descripcion = txtDescripcion.getText().toString();
        Date fecha = format.parse(txtFecha.getText().toString());
        String tipoGasto = txtTipoGasto.getText().toString();

        GastosController.insertGasto(this,descripcion,cantidad,fecha.toString(),1,tipoGasto);
        Toast.makeText(this, "Se ha guardado el gasto", Toast.LENGTH_LONG).show();
        Thread.sleep(Long.parseLong("1000"));
        finish();

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
