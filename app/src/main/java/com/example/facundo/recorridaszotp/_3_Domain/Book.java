package com.example.facundo.recorridaszotp._3_Domain;

import com.orm.SugarRecord;

/**
 * Created by Facundo on 27/03/2015.
 */
public class Book extends SugarRecord<Book> {
    String title;
    String edition;

    public Book(){
    }

    public Book(String title, String edition){
        this.title = title;
        this.edition = edition;
    }
}
