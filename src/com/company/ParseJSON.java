package com.company;


import com.company.models.Hierarquia;
import com.company.models.Praxista;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by afonso on 30-11-2015.
 */
public class ParseJSON {
    private JSONArray praxistasArray;
    private String file;

    public ParseJSON(String file) {
        this.file = file;
        BufferedReader br;
        String lineT = "";
        try {
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + file));
            for (String line; (line = br.readLine()) != null;) {
                lineT += line;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            JSONObject praxistas = new JSONObject(lineT);
            praxistasArray = praxistas.getJSONArray("praxistas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Praxista> unserialize(){
        ArrayList<Praxista> ret = new ArrayList<>();
        for(int i = 0; i < praxistasArray.length(); ++i){
            JSONObject praxista;
            try {
                praxista = praxistasArray.getJSONObject(i);

                String name = praxista.getString("name");
                String hierarq = praxista.getString("hierarquia");
                int primeiraMatricula = praxista.getInt("primeiraMatricula");
                String dataNasc = praxista.getString("dataNascimento");
                Hierarquia hierarquia = parseHierarquia(hierarq);
                Date dataNascimento = parseDataNascimento(dataNasc);
                if(hierarquia == null || dataNascimento == null){
                    System.out.println("Erro a parsar o JSON.");
                    return null;
                }
                Praxista p = new Praxista(hierarquia,name,primeiraMatricula,dataNascimento);
                ret.add(p);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    private Date parseDataNascimento(String dataNasc) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dataNasc);
        } catch (ParseException e) {
            return null;
        }
    }

    private Hierarquia parseHierarquia(String hierarq) throws JSONException {
        switch(hierarq){
            case "SEGUNDANISTA":
                return Hierarquia.SEGUNDANISTA;
            case "SEMI_PUTO":
                return Hierarquia.SEMI_PUTO;
            case "TERCEIRANISTA":
                return Hierarquia.TERCEIRANISTA;
            case "PUTO":
                return Hierarquia.PUTO;
            case "QUARTANISTA":
                return Hierarquia.QUARTANISTA;
            case "DOUTOR_DE_MERDA":
                return Hierarquia.DOUTOR_DE_MERDA;
            case "QUINTANISTA":
                return Hierarquia.QUINTANISTA;
            case "MERDA_DOUTOR":
                return Hierarquia.MERDA_DOUTOR;
            case "VETERANO":
                return Hierarquia.VETERANO;
            case "DUX_FACULTIS":
                return Hierarquia.DUX_FACULTIS;
            case "DUX_VETERANORUM":
                return Hierarquia.DUX_VETERANORUM;
            default:
                return null;
        }
    }

}
