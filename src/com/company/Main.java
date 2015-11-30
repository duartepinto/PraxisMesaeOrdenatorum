package com.company;

import com.company.models.Hierarquia;
import com.company.models.Praxista;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        ParseJSON parser = new ParseJSON("database.json");

        ArrayList<Praxista> praxistas = parser.unserialize();
        Ordenador ordenador = new Ordenador(praxistas);

        ordenador.display();
    }
}
