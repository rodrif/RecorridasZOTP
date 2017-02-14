package com.example.facundo.recorridaszotp.NewTests.Persona;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.MenuItem;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;

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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
/**
 * Created by gonzalo on 06/06/16.
 */
public class BorrarPersona {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void borrarPersona() {
        //Login
        onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
        onView(withId(R.id.ETPassword)).perform(typeText("123456789"));
        onView(withId(R.id.bLogin)).perform(click());

        onView(withContentDescription("toolbarMenu")).perform(click());
        onData(hasToString(startsWith("Personas"))).perform(click());
        onData(allOf(instanceOf(Persona.class), hasToString("Juanb")))
                .inAdapterView(withId(R.id.lista_personas))
                .perform(click());
        onData(instanceOf(MenuItem.class)).atPosition(2).perform(click());

        onView(withId(R.id.action_borrar)).perform(click());
        onView(withId(16908313)).perform(click());

        assertTrue("Incorrecta borrado de persona1", PersonaDataAccess.get().hayConEstadoBorrado());
        new Sincronizador(mActivityRule.getActivity()).execute();

        onView(withContentDescription("toolbarMenu")).perform(click());
        assertFalse("Incorrecta borrado de persona2", PersonaDataAccess.get().hayConEstadoBorrado());
    }
}
