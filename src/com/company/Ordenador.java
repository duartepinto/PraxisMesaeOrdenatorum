package com.company;

import com.company.models.Hierarquia;
import com.company.models.Praxista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Duarte on 30/11/2015.
 */
public class Ordenador {
    private ArrayList<Praxista> praxistas;

    private ArrayList<Praxista> leftRow;
    private ArrayList<Praxista> rightRow;

    private MesaVeteranos mesaVets;

    JPanel panel = new JPanel(new FlowLayout());
    JFrame frame = new JFrame("Praxis Mesae Ordenatorum");
    JLabel rowLabel;

    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel mid = new JPanel();

    Ordenador(ArrayList<Praxista> praxistas){
        this.praxistas = praxistas;

        this.leftRow = new ArrayList<Praxista>();
        this.rightRow = new ArrayList<Praxista>();
        this.mesaVets = new MesaVeteranos();

        separarPorMesas();
    }

    public void separarPorMesas(){
        for (Praxista praxista : this.praxistas){
            colocarNaMesa(praxista);
        }
    }

    private void colocarNaMesa(Praxista praxista) {
        if (praxista.getHierarquia().ordinal()>= Hierarquia.VETERANO.ordinal()){
            this.mesaVets.addVeterano(praxista);
        }

        if(praxista.getHierarquia().ordinal() == Hierarquia.TERCEIRANISTA.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.PUTO.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.QUINTANISTA.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.MERDA_DOUTOR.ordinal()
                )
            this.leftRow.add(praxista);

        if(praxista.getHierarquia().ordinal() == Hierarquia.SEGUNDANISTA.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.SEMI_PUTO.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.QUARTANISTA.ordinal() ||
                praxista.getHierarquia().ordinal() == Hierarquia.DOUTOR_DE_MERDA.ordinal()
                )
            this.rightRow.add(praxista);
    }

    private int maiorNome(ArrayList<Praxista> row){
        int maxLength = 0;
        for(Praxista praxista : row){
            if (praxista.getNome().length() > maxLength)
                maxLength = praxista.getNome().length();
        }

        return  maxLength;
    }

    private void displayRows(int tamanhoMesaVets, int espacamentoInicial){
        if(leftRow.size() > rightRow.size()){
            for(int i = 0; i < leftRow.size(); i++){
                int espacamento = espacamentoInicial - leftRow.get(i).getNome().length()/2;
                System.out.print(leftRow.get(i).getNome());
                if(i < rightRow.size()){
                    String nome = rightRow.get(i).getNome();
                    for(int j = 0; j < (espacamento + tamanhoMesaVets - nome.length()/2); j++)
                        System.out.print(" ");

                    System.out.print(nome);
                }
                System.out.print('\n');
            }
        }else{
            for(int i = 0; i < rightRow.size(); i++){
                int espacamento = espacamentoInicial;
                if(i < leftRow.size()) {
                    espacamento -= leftRow.get(i).getNome().length()/2;
                    for(int j = 0; j < espacamento; j++)
                        System.out.print(" ");
                    System.out.print(leftRow.get(i).getNome());
                }

                String nome = rightRow.get(i).getNome();
                for(int j = 0; j < (espacamento + tamanhoMesaVets - nome.length()/2); j++)
                    System.out.print(" ");

                System.out.print(nome);
                System.out.print('\n');
            }
        }
    }

    private void displayMesaVeteranos(ArrayList<Praxista> mesaVeteranos, int espacamentoInicial){

        for(int i = 0; i < espacamentoInicial; i++)
            System.out.print(" ");
        for (Praxista veterano : mesaVeteranos){
            System.out.print(veterano.getNome() + " ");
        }
        System.out.print('\n');
    }

    public void display(){
        ArrayList<Praxista> mesaVeteranos = this.mesaVets.geraMesa();

        Collections.sort(leftRow);
        Collections.reverse(leftRow);
        Collections.sort(rightRow);
        Collections.reverse(rightRow);
        int tamanhoMesaVets = this.mesaVets.mesaLength();
        int espacamentoInicial = this.maiorNome(leftRow) / 2;
        displayMesaVeteranos(mesaVeteranos, espacamentoInicial);
        displayRows(tamanhoMesaVets, espacamentoInicial);

        initComponents();
    }

    private void initComponents() {
        //readImages();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = (JPanel)frame.getContentPane();
        panel.setLayout(null);

        for (int i = 0; i < leftRow.size(); i = i + 1) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table.jpg"));
            rowLabel = new JLabel(image);
            rowLabel.setBounds(300,i*100 + image.getIconHeight(), image.getIconWidth(), image.getIconHeight());
            panel.add(rowLabel);
        }

        frame.setBounds(0, 0, 1920, 1080);

        frame.setVisible(true);
    }

    private void readImages() {

        ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table.jpg"));
        rowLabel = new JLabel(image);
        rowLabel.setBounds(300, 400, 100, 100);
    }

}
