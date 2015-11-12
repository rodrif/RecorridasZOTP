package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPersonas;
import com.example.facundo.recorridaszotp._6_Test.ActivityTest;
import java.util.List;

/**
 * Created by Facundo on 29/08/2015.
 */
public class ListaPersonasTest extends ActivityInstrumentationTestCase2<ActivityTest> { //ListaPersonas
    private ActivityTest miActivity;
    private ListaPersonas mListaPersonasFragment;
    private ListView mListView;

    public ListaPersonasTest() {
        super(ActivityTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBUtils.loadDefaultDB();
        miActivity = getActivity();
        mListaPersonasFragment = (ListaPersonas)miActivity.frag;
        mListView = (ListView) mListaPersonasFragment.getView().findViewById(R.id.lista_personas);
    }

    public void testPreconditions() {
        assertNotNull("mListaPersonasFragment is null", mListaPersonasFragment);
        assertNotNull("mListView is null", mListView);
    }

    public void testListaPersonasListView_labelText() throws Exception {
        List<Persona> personas = DBUtils.getPersonasTest();
        int personasCount = mListView.getAdapter().getCount();

        assertEquals(personas.size(), personasCount);

        for (int i = 0; i < personasCount; i++) {
            LinearLayout layout = (LinearLayout)mListView.getAdapter().getView(i, null, null);

            TextView textViewNombre = (TextView)layout.findViewById(R.id.lNombre);
            //TextView textViewApellido = (TextView)layout.findViewById(R.id.lApellido);

            assertEquals(personas.get(i).getNombre(), textViewNombre.getText().toString());
            //assertEquals(personas.get(i).getApellido(), textViewApellido.getText().toString());
        }
    }
}




