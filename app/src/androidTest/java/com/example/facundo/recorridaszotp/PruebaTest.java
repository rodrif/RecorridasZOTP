package com.example.facundo.recorridaszotp;

import com.example.facundo.recorridaszotp._3_Domain.Persona;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class PruebaTest extends TestCase {
    public void testString() throws JSONException {
        String unString = null;
        String resultado;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("antes", "sAntes");
        jsonObj.put("unString", unString);
        jsonObj.put("despues", "sDespues");

        resultado = jsonObj.toString();
    }
}
