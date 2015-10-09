package mx.lania.registrogastos;

import android.content.Context;
import android.content.Intent;
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
        EditText txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();

        String token = UsuarioController.getPassword(this, usuario);

        if(password.equals(token)){
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();
            startActivity(new Intent("mx.lania.registrogastos.RegistrarGasto"));
        }else{
            Toast.makeText(this, token + password, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Error de usuario y/o contraseña", Toast.LENGTH_LONG).show();
        }

    }

    public void Registrar (View view){
        EditText txtUsuario =  (EditText)findViewById(R.id.txtUsuario);
        EditText txtPassword =  (EditText)findViewById(R.id.txtPassword);
        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();

        UsuarioController.displayUsuario(this, usuario);
        UsuarioController.insertUsuario(this, usuario, password);

        /*if(!txtUsuario.getText().toString().equals("") || txtPassword.getText().toString().equals("")) {
            boolean existe = UsuarioController.existeUsuario(this, txtUsuario.getText().toString());
            if(existe)
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
            else {
                UsuarioController.insertUsuario(this,txtUsuario.getText().toString(),txtPassword.getText().toString());
                Toast.makeText(this, "El usuario se insertó", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Debe introducir un login y password", Toast.LENGTH_LONG).show();
        }*/

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
