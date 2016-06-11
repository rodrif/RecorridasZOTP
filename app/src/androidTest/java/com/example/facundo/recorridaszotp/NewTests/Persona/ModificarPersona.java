package com.example.facundo.recorridaszotp.NewTests.Persona;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by gonzalo on 07/06/16.
 */
@RunWith(AndroidJUnit4.class)
public class ModificarPersona {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void modificarPersona() {
        //Login
        onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
        onView(withId(R.id.ETPassword)).perform(typeText("123456789"));
        onView(withId(R.id.bLogin)).perform(click());

        //Editar Persona
        onView(withContentDescription("toolbarMenu")).perform(click());
        onData(hasToString(startsWith("Personas"))).perform(click());
        onData(allOf(instanceOf(Persona.class), hasToString("Juanb")))
                .inAdapterView(withId(R.id.lista_personas))
                .perform(click());
        onData(instanceOf(MenuItem.class)).atPosition(2).perform(click());

        //Agregar apellido
        onView(withId(R.id.ETApellido)).perform(typeText("Perezb"));
        onView(withId(R.id.action_guardar)).perform(click());

        Persona juan = new Select()
                .from(Persona.class)
                .where("Estado = ?", Utils.EST_MODIFICADO)
                .where("Nombre = ?", "Juanb")
                .executeSingle();

        assertEquals("Nombre incorrecto", juan.getNombre(), "Juanb");
        assertEquals("Apellido incorrecto", juan.getApellido(), "Perezb");

        assertTrue("Incorrecta modificacion de personas1", PersonaDataAccess.get().hayConEstadoModificado());
        new Sincronizador(mActivityRule.getActivity()).execute();

        onView(withContentDescription("toolbarMenu")).perform(click());
        assertFalse("Incorrecta modificacion de personas2", PersonaDataAccess.get().hayConEstadoModificado());

    }
}
