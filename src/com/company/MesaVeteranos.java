package com.company;

import com.company.models.Hierarquia;
import com.company.models.Praxista;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Duarte on 30/11/2015.
 */
public class MesaVeteranos {
    private ArrayList<Praxista> veteranos;
    private ArrayList<Praxista> disposicaoMesaVets;

    public boolean addVeterano(Praxista veterano){
        if(veterano.getHierarquia().ordinal() < Hierarquia.VETERANO.ordinal()){
            return false;
        }

        this.veteranos.add(veterano);
        return true;
    }

    public ArrayList<Praxista> geraMesa(){
        Collections.sort(veteranos);

        disposicaoMesaVets.clear();
        for (int i = 0; i < veteranos.size(); i++){
            if(i % 2 == 0){
                disposicaoMesaVets.add(veteranos.get(i));
            }else{
                disposicaoMesaVets.add(0, veteranos.get(i));
            }
        }
        return disposicaoMesaVets;
    }

    public int mesaLength(){
        int length = 0;
        for(Praxista veterano : veteranos){
            length += veterano.getNome().length();
        }

        length += veteranos.size() -1;
        return length;
    }
}
