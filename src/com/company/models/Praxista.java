package com.company.models;

import java.util.Date;

/**
 * Created by Duarte on 30/11/2015.
 */
public class Praxista implements Comparable {
    private Hierarquia hierarquia;
    private String nome;
    private Date dataNascimento;
    private int primeiraMatricula;

    public Praxista(Hierarquia hierarquia, String nome, int primeiraMatricula ,Date dataNascimento){
        this.setDataNascimento(dataNascimento);
        this.setHierarquia(hierarquia);
        this.setNome(nome);
        this.primeiraMatricula = primeiraMatricula;
    }

    public int getPrimeiraMatricula() {
        return primeiraMatricula;
    }

    public void setPrimeiraMatricula(int primeiraMatricula) {
        this.primeiraMatricula = primeiraMatricula;
    }


    public Hierarquia getHierarquia() {
        return hierarquia;
    }

    public void setHierarquia(Hierarquia hierarquia) {
        this.hierarquia = hierarquia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Praxista praxista = (Praxista) o;

        if (hierarquia != praxista.hierarquia) return false;
        if (nome != null ? !nome.equals(praxista.nome) : praxista.nome != null) return false;
        return !(dataNascimento != null ? !dataNascimento.equals(praxista.dataNascimento) : praxista.dataNascimento != null);
    }

    @Override
    public int hashCode() {
        int result = hierarquia != null ? hierarquia.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (dataNascimento != null ? dataNascimento.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Praxista p2 = (Praxista) o;

        if(this.hierarquia.ordinal() == p2.hierarquia.ordinal()){
            if(this.primeiraMatricula == p2.primeiraMatricula)
                return this.dataNascimento.compareTo(p2.dataNascimento);
            else
                if (this.primeiraMatricula < p2.primeiraMatricula)
                    return 1;
                else
                    return -1;
        }else{
            if(this.hierarquia.ordinal() > p2.hierarquia.ordinal()){
                return 1;
            }else{
                return -1;
            }
        }
    }
}
