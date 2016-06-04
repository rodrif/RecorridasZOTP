package com.example.facundo.recorridaszotp.NewTests;

import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.test.MoreAsserts.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by gonzalo on 04/06/16.
 */
@RunWith(AndroidJUnit4.class)
public class PersonTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void before() {
        SharedPreferences settings = mActivityRule.getActivity().getSharedPreferences(Utils.PREFS_NAME, 0x00);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.USER_EMAIL, "admin@gmail.com");
        editor.putString(Utils.USER_PASSWORD, "123456789");
        editor.putBoolean(Utils.USER_IS_LOGIN, true);
        editor.putInt(Utils.USER_ROL_ID, 1);//Admin role

        // Commit the edits!
        editor.commit();
    }

    @Test
    public void crearPersona(){
        onView(withContentDescription("Navegar hacia arriba")).perform(click());
        onData(hasToString(startsWith("Nueva Persona"))).perform(click());
        onView(withId(R.id.ETNombre)).perform(typeText("Juan"));
        onView(withId(R.id.ETApellido)).perform(typeText("Perez"));
        onView(withId(R.id.action_guardar)).perform(click());
        assertFalse("Incorrecta actualización de personas", PersonaDataAccess.get().todoActualizado());
        new Sincronizador(mActivityRule.getActivity()).execute();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("Incorrecta actualización de personas", PersonaDataAccess.get().todoActualizado());
    }
}
