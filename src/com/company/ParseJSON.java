package com.company;


import com.company.models.Hierarquia;
import com.company.models.Praxista;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.company.models.Hierarquia.*;

/**
 * Created by afonso on 30-11-2015.
 */
public class ParseJSON {
    private String file;

    public ParseJSON(String file) {
        this.file = file;
    }

    public String serialize(ArrayList<Praxista> praxistas){
        List<JSONObject> praxistaList = new LinkedList<>();

        /* Parse each Praxista element */
        for (Praxista praxista1 : praxistas) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", praxista1.getNome());
                jsonObject.put("hierarquia", toString(praxista1.getHierarquia()));
                jsonObject.put("primeiraMatricula", praxista1.getPrimeiraMatricula());
                jsonObject.put("dataNascimento", toString(praxista1.getDataNascimento()));
                praxistaList.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /* Arrange them to be in JSON format */

        JSONArray praxistasArray = new JSONArray(praxistaList);
        JSONObject praxista;
        String json = "{\"praxistas\":[";
        for(int i = 0; i < praxistasArray.length(); ++i){
            try {
                praxista = praxistasArray.getJSONObject(i);
                json+="\n\t" + praxista + ",";
            } catch (JSONException e) {
                e.printStackTrace();
            }
          }
        json+="\n]}";

        /* Create the file */
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(json);
            writer.close();
            return json;
        }
        catch ( IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Praxista> unserialize(){
        /* Read the JSON file */
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

        /* Store the info into a JSONArray*/
        JSONArray praxistasArray = new JSONArray();
        try {
            JSONObject praxistas = new JSONObject(lineT);
            praxistasArray = praxistas.getJSONArray("praxistas");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /* Parse the array into an arraylist */
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

    private String toString(Hierarquia hierarquia){
        switch (hierarquia){
            case SEGUNDANISTA:
                return "SEGUNDANISTA";
            case SEMI_PUTO:
                return "SEMI_PUTO";
            case TERCEIRANISTA:
                return "TERCEIRANISTA";
            case PUTO:
                return "PUTO";
            case QUARTANISTA:
                return "QUARTANISTA";
            case DOUTOR_DE_MERDA:
                return "DOUTOR_DE_MERDA";
            case QUINTANISTA:
                return "QUINTANISTA";
            case MERDA_DOUTOR:
                return "MERDA_DOUTOR";
            case VETERANO:
                return "VETERANO";
            case DUX_FACULTIS:
                return "DUX_FACULTIS";
            case DUX_VETERANORUM:
                return "DUX_VETERANORUM";
            default:
                return null;
        }
    }

    private String toString(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (month < 10)
            return year + "-" + "0" + month + "-" + day;
        else
            return year + "-" + month + "-" + day;
    }

    private Hierarquia parseHierarquia(String hierarq) throws JSONException {
        switch(hierarq){
            case "SEGUNDANISTA":
                return SEGUNDANISTA;
            case "SEMI_PUTO":
                return SEMI_PUTO;
            case "TERCEIRANISTA":
                return TERCEIRANISTA;
            case "PUTO":
                return PUTO;
            case "QUARTANISTA":
                return QUARTANISTA;
            case "DOUTOR_DE_MERDA":
                return DOUTOR_DE_MERDA;
            case "QUINTANISTA":
                return QUINTANISTA;
            case "MERDA_DOUTOR":
                return MERDA_DOUTOR;
            case "VETERANO":
                return VETERANO;
            case "DUX_FACULTIS":
                return DUX_FACULTIS;
            case "DUX_VETERANORUM":
                return DUX_VETERANORUM;
            default:
                return null;
        }
    }

}
