package com.example.facundo.recorridaszotp._7_Interfaces;

import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

/**
 * Created by gonzalo on 23/05/16.
 */
public interface iVisitaHandler {
    void crearVisita(Persona persona, iFragmentChanger fragmentChanger);
    void mostrarVisita(Visita visita, iFragmentChanger fragmentChanger);
    void listarVisitas(long personaId, iFragmentChanger fragmentChanger);
}
