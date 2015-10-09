package mx.lania.registrogastos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mx.lania.registrogastos.controller.UsuarioController;
import mx.lania.registrogastos.database.DBAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* String nombre = "Jesus";
        String email = "jaguil@lania.mx";

        ContactsController.insertContact(this, nombre, email);
        ContactsController.displayContact(this, 1);*/
    }

    public void Ingresar (View view){

    }

    public void Registrar (View view){
        EditText txtUsuario =  (EditText)findViewById(R.id.txtUsuario);
        EditText txtPassword =  (EditText)findViewById(R.id.txtPassword);

        if(!txtUsuario.getText().toString().equals("") || txtPassword.getText().toString().equals("")) {
            boolean existe = UsuarioController.existeUsuario(this, txtUsuario.getText().toString());
            if(existe)
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
            else {
                UsuarioController.insertUsuario(this,txtUsuario.getText().toString(),txtPassword.getText().toString());
            }
        }
        else
        {
            Toast.makeText(this, "Debe introducir un login y password", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
