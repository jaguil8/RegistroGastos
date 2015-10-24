package mx.lania.registrogastos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import mx.lania.registrogastos.controller.GastosController;
import mx.lania.registrogastos.database.GastosDB;

public class historial extends AppCompatActivity {

    ListView gb;
    int idUsuario=0;
    DatePicker txt_fecha;
    String descpcionDialog="";

    private TextToSpeech ttsObject;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        gb =  (ListView)findViewById(R.id.gbhistorial);

        txt_fecha =(DatePicker)this.findViewById(R.id.fecha);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaHoy= new Date();
        txt_fecha.updateDate(fechaHoy.getYear(),fechaHoy.getMonth(),fechaHoy.getDay());

        idUsuario = getIntent().getExtras().getInt("ID_USUARIO");

        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        try{
            List<String> c = GastosController.getAllGastosByIdUsuario(this, idUsuario);
            Iterator<String> it = c.iterator();
            while (it.hasNext() ) {
                list.add(it.next());

            }

            gb.setClickable(true);
            gb.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int index, long arg3) {
                    Object obj = gb.getAdapter().getItem(index);
                    String value = obj.toString();
                    Log.d("MyLog", "Value is: " + value);
                    String[] split = value.split(" ");
                    //String fecha = txt_fecha.getYear() + "/" + txt_fecha.getMonth() + "/" + txt_fecha.getDayOfMonth();
                    int id = GastosController.getGastoByDescripcion(view.getContext(), split[0].toString());
                    //onCreateDialog(id);
                    descpcionDialog = GastosController.getGastoById(view.getContext(), id);
                    Toast.makeText(getApplicationContext(), descpcionDialog, Toast.LENGTH_SHORT).show();
                    if(result != TextToSpeech.LANG_NOT_SUPPORTED && result != TextToSpeech.LANG_MISSING_DATA)
                    {
                        ttsObject.speak(descpcionDialog,TextToSpeech.QUEUE_FLUSH,null);
                    }
                }
            });

            gb.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int index, long arg3) {

                    // Can't manage to remove an item here

                    //Toast.makeText(this,gb.getItemAtPosition(index).toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, "Seleccionado:" + parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(this, "Seleccionado", Toast.LENGTH_LONG).show();
                    Object obj = gb.getAdapter().getItem(index);
                    String value = obj.toString();
                    Log.d("MyLog", "Value is: " + value);
                    String[] split = value.split(" ");
                    //String fecha = txt_fecha.getYear() + "/" + txt_fecha.getMonth() + "/" + txt_fecha.getDayOfMonth();
                    int id = GastosController.getGastoByDescripcion(view.getContext(), split[0].toString());
                    boolean eliminado = GastosController.deleteGasto(view.getContext(), id);
                    if (eliminado) {
                        Toast.makeText(historial.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                        llenarListView();
                    }
                    return eliminado;
                }
            });
            gb.setAdapter(dataAdapter);

            //se define objeto text to speech y se carga listener
            ttsObject = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS){
                        result = ttsObject.setLanguage(Locale.getDefault());
                    }
                    else{
                        Toast.makeText(historial.this,"Error: Tu dispositivo no soporta conversion de texto a voz",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ttsObject!= null){
            ttsObject.stop();
            ttsObject.shutdown();
        }
    }

    public void llenarListView(){
        gb =  (ListView)findViewById(R.id.gbhistorial);
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        try{
            List<String> c = GastosController.getAllGastosByIdUsuario(this, idUsuario);
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
    protected Dialog onCreateDialog(int id)
    {
        //long idGasto = (long)id;
        descpcionDialog =GastosController.getGastoById(this,id);

                return new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Dialog")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int witchButton) {

                                        Toast.makeText(getBaseContext(),
                                                "Datos: "+descpcionDialog, Toast.LENGTH_SHORT).show();

                                    }
                                }

                        ).create();


    }


    @Override
    protected void onRestart() {
        super.onRestart();

        llenarListView();
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
        if(result != TextToSpeech.LANG_NOT_SUPPORTED && result != TextToSpeech.LANG_MISSING_DATA)
        {
            ttsObject.speak("Agregar",TextToSpeech.QUEUE_FLUSH,null);
        }

        Intent intent = new Intent("mx.lania.registrogastos.RegistrarGasto");

        intent.putExtra("ID_USUARIO", idUsuario);
        startActivity(intent);

    }
}
