package com.example.facundo.recorridaszotp._5_Presentation;

import android.test.ActivityInstrumentationTestCase2;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPersonas;

import java.util.List;

/**
 * Created by Facundo on 29/08/2015.
 */
/*public class ListaPersonasTest extends ActivityInstrumentationTestCase2<ListaPersonas> {
    private ListaPersonas mListaPersonasActivity;
    private ListView mListView;

    public ListaPersonasTest() {
        super(ListaPersonas.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mListaPersonasActivity = getActivity();
        mListView = (ListView) mListaPersonasActivity.findViewById(R.id.listaPersonas);
    }

    public void testPreconditions() {
        assertNotNull("mListaPersonasActivity is null", mListaPersonasActivity);
        assertNotNull("mListView is null", mListView);
    }

    public void testListaPersonasListView_labelText() throws Exception {
        List<Persona> personas = DBUtils.getPersonasTest();
        int personasCount = mListView.getAdapter().getCount();

        assertEquals(personas.size(), personasCount);

        for (int i = 0; i < personasCount; i++) {
            LinearLayout layout = (LinearLayout)mListView.getAdapter().getView(i, null, null);

            TextView textViewNombre = (TextView)layout.findViewById(R.id.lNombre);
            TextView textViewApellido = (TextView)layout.findViewById(R.id.lApellido);

            assertEquals(personas.get(i).getNombre(), textViewNombre.getText().toString());
            assertEquals(personas.get(i).getApellido(), textViewApellido.getText().toString());
        }
    }

}*/




