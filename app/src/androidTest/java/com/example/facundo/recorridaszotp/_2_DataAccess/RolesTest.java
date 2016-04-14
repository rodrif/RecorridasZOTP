package com.example.facundo.recorridaszotp._2_DataAccess;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Roles;

import junit.framework.TestCase;

/**
 * Created by gonzalo on 13/04/16.
 */
public class RolesTest extends TestCase {
    public void testAdminRole(){
        assertEquals(true, Roles.getInstance().hasPermission(1, Utils.PUEDE_EDITAR_PERSONA));
    }
}
