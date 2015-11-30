package com.company;

import com.company.models.Hierarquia;
import com.company.models.Praxista;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {


        ArrayList<Praxista> praxistas = new ArrayList<Praxista>();
        Ordenador ordenador = new Ordenador(praxistas);

        ordenador.display();
    }
}
