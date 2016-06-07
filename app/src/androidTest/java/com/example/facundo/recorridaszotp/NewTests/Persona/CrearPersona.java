package com.example.facundo.recorridaszotp.NewTests.Persona;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
@RunWith(AndroidJUnit4.class)
public class CrearPersona {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void crearPersonaConDatosMinimos() {
        //Login
        onView(ViewMatchers.withId(R.id.ETEmail)).perform(typeText("admin@gmail.com"));
        onView(withId(R.id.ETPassword)).perform(typeText("123456789"));
        onView(withId(R.id.bLogin)).perform(click());

        //Crear persona
        onView(withContentDescription("toolbarMenu")).perform(click());
        onData(hasToString(startsWith("Nueva Persona"))).perform(click());
        onView(withId(R.id.ETNombre)).perform(typeText("Juanb"));
        onView(withId(R.id.spinner_zona)).perform(scrollTo()).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Haedo"))).perform(click());
        onView(withId(R.id.action_guardar)).perform(click());

        assertTrue("Incorrecta actualización de personas1", PersonaDataAccess.get().hayConEstadoModificado());
        new Sincronizador(mActivityRule.getActivity()).execute();

        onView(withContentDescription("toolbarMenu")).perform(click());
        assertFalse("Incorrecta actualización de personas2", PersonaDataAccess.get().hayConEstadoModificado());
    }
}