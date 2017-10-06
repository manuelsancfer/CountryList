package edu.upc.eseiaat.pma.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    private ArrayList<String> country_list; //contenedor secuencial(0,1,2..) se puede meter.
    //elementos donde sea, borrar,etc. Tablas tipo string[] son fijas una vez creadas así se quedan.
    private ArrayAdapter<String> adapter;   //Para hacerle mostrar la lista a la tabla.
                                            //actualizar la pantalla cuando bajas con el dedo.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);


        String [] countries = getResources().getStringArray(R.array.countries); //cargamos la lista.

        country_list = new ArrayList<>(Arrays.asList(countries)); //Convertir countres en countrylist
        //primero se convierte a lista (asList lo convierte en List <string>).
        ListView list = (ListView) findViewById(R.id.country_list); //referencia a la lista.

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list); //creamos adapter
        list.setAdapter(adapter);           //Le decimos a la lista que toda tiene adaptador.

        //Para saber cuando se clica un item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {

                Toast.makeText(
                        CountryListActivity.this,
                        String.format("Has escogido '%s'", country_list.get(pos)),
                        Toast.LENGTH_SHORT
                        ).show();
            }
        });

        //Borrar cuando se mantiene clicado un item
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                //cuadro que avisa si quieres borrar realmente
                builder.setTitle(R.string.confirm);
                String msg = getResources().getString(R.string.confirm_message);
                builder.setMessage(msg + country_list.get(pos) + "?"); //el mensaje del dialog
                //para aceptar que quieres salir
                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        country_list.remove(pos);   //Indicar cual borras
                        adapter.notifyDataSetChanged(); //para que se quite el país que eliminamos(actualizar adapter)
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);   //Para cancelar
                builder.create().show();

                return true;                //True para decir que hemos hecho long y que no haga short
            }
        });

    }
}
