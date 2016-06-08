package com.example.facundo.recorridaszotp.NewTests.Visita;

/**
 * Created by gonzalo on 08/06/16.
 */
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ModificarVisita {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void modificarObservacionEnVisita() {
        //Login
        onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
        onView(withId(R.id.ETPassword)).perform(typeText("123456789"));
        onView(withId(R.id.bLogin)).perform(click());


        onView(withContentDescription("toolbarMenu")).perform(click());
        onData(hasToString(startsWith("Personas"))).perform(click());
        onData(allOf(instanceOf(Persona.class), hasToString("Juanb")))
                .inAdapterView(withId(R.id.lista_personas))
                .perform(click());
        onData(instanceOf(MenuItem.class)).atPosition(1).perform(click());
        onData(instanceOf(Visita.class))
                .inAdapterView(withId(R.id.lista_visitas))
                .atPosition(1)
                .perform(click());
        onView(withId(R.id.ETObservacioneVisita)).perform(replaceText("Tiene gato"));
        onView(withId(R.id.action_guardar)).perform(click());

        Persona juanb = new Select()
                .from(Persona.class)
                .where("Estado = ?", Utils.EST_ACTUALIZADO)
                .where("Nombre = ?", "Juanb")
                .executeSingle();

        List<Visita> visitas = VisitaDataAccess.get().findTodasVisitas(juanb);

        assertEquals("No se encontraron las 2 visitas", 2, visitas.size());
        assertEquals("Primera Visita", visitas.get(0).getDescripcion());
        assertEquals("Tiene gato", visitas.get(1).getDescripcion());

        assertTrue("Incorrecta modificacion de visita1", VisitaDataAccess.get().hayConEstadoModificado());
        new Sincronizador(mActivityRule.getActivity()).execute();

        onView(withContentDescription("toolbarMenu")).perform(click());
        assertFalse("Incorrecta modificacion de visita2", VisitaDataAccess.get().hayConEstadoModificado());
    }
}
