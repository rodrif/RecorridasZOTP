package com.example.facundo.recorridaszotp._5_Presentation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;
import com.example.facundo.recorridaszotp._4_Services.PersonaService;

import java.util.logging.Logger;

public class FormPersonaActivity extends ActionBarActivity {
    private EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_persona);

        this.nombre = (EditText) findViewById(R.id.editTextNombre);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_persona, menu);
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

    public void GuardarPersonaClick(View view){
        Log.d(Utils.APPTAG, "GuardarPersonaClick");

        String nombre = this.nombre.getText().toString();

        Persona persona = new Persona();
        persona.setNombre(nombre);

        new PersonaService().Save(persona);

        PersonaQuery query = new PersonaQuery();
        query.nombre = nombre;

        Persona p = new PersonaService().Find(query);
        Toast unToast = Toast.makeText(this, " ", 3);
        if(p == null) {
           unToast.setText("Error al grabar");
        } else {
            unToast.setText("Se grabo: " + p.getNombre());
        }
        unToast.show();
    }
}
