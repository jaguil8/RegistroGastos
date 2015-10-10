package mx.lania.registrogastos;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.lania.registrogastos.controller.GastosController;

public class historial extends AppCompatActivity {

    ListView gb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        gb =  (ListView)findViewById(R.id.gbhistorial);
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        try{
            List<String> c = GastosController.getAllGastos(this);
            Iterator<String> it = c.iterator();
            while (it.hasNext() ) {
                list.add(it.next());

            }

            gb.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int index, long arg3) {

                    // Can't manage to remove an item here

                    //Toast.makeText(this,gb.getItemAtPosition(index).toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, "Seleccionado:" + parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, "Seleccionado", Toast.LENGTH_LONG).show();
                    boolean eliminado =GastosController.deleteGasto(view.getContext(),parent.getId());
                    return eliminado;
                }
            });
            gb.setAdapter(dataAdapter);
        }catch (Exception e)
        {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        gb =  (ListView)findViewById(R.id.gbhistorial);
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        try{
            List<String> c = GastosController.getAllGastos(this);
            Iterator<String> it = c.iterator();
            while (it.hasNext() ) {
                list.add(it.next());

            }

            gb.setAdapter(dataAdapter);
        }catch (Exception e)
        {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial, menu);
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

    public void agregar(View view){
        startActivity(new Intent("mx.lania.registrogastos.RegistrarGasto"));
    }
}
