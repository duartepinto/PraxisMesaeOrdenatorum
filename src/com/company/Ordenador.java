package com.company;

import com.company.models.Hierarquia;
import com.company.models.Praxista;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Duarte on 30/11/2015.
 */
public class Ordenador {
    private ArrayList<Praxista> praxistas;

    private ArrayList<Praxista> leftRow;
    private ArrayList<Praxista> rightRow;

    private MesaVeteranos mesaVets;

    final int espacamento_doutores = 300;

    final double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    int tamanhoMesaVetWidth = 0;
    int tamanhoMesaVetHeight = 0;
    int tamanhoMesaDotHeight = 0;
    int tamanhoMesaDotWidth = 0;

    int mesaVetY = 125;
    int mesaEsquerda = 150;

    JPanel panel = new JPanel(new FlowLayout());
    JFrame frame = new JFrame("Praxis Mesae Ordenatorum");
    JFrame addFrame = new JFrame("Add Doutor");
    JFrame deleteFrame = new JFrame("Delete Doutor");
    JButton add = new JButton("Adicionar");
    JButton delete = new JButton("Eliminar");

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

        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setBounds(400, 200, 1000, 800);

        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.setBounds(400, 200, 1000, 800);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
        image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/10, Image.SCALE_SMOOTH));
        tamanhoMesaDotWidth = image.getIconWidth();
        tamanhoMesaDotHeight = image.getIconHeight();

        image = new ImageIcon(this.getClass().getResource("resources/table_veteranos.jpg"));
        image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/10, Image.SCALE_SMOOTH));
        tamanhoMesaVetWidth = image.getIconWidth();
        tamanhoMesaVetHeight = image.getIconHeight();

        panel = (JPanel)frame.getContentPane();
        panel.setLayout(null);

        addLeftRow();

        addRightRow();

        addCenterRow();

        configButtons();

        frame.setBounds(0, 0, (int) width, (int) height);

        frame.setVisible(true);
    }

    public void addLeftRow() {
        for (int i = 0; i < leftRow.size(); i = i + 1) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/10, Image.SCALE_SMOOTH));
            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(leftRow.get(i).getNome());
            nome.setBounds(mesaEsquerda - 100,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void addRightRow() {
        for (int i = 0; i < rightRow.size(); i = i + 1) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/10, Image.SCALE_SMOOTH));


            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda + (mesaVets.getVeteranos().size()+1) * tamanhoMesaVetWidth,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(rightRow.get(i).getNome());
            nome.setBounds(mesaEsquerda + image.getIconWidth() + 25 + (mesaVets.getVeteranos().size() + 1) * tamanhoMesaVetWidth,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void addCenterRow() {
        for (int i = 0; i < mesaVets.getVeteranos().size(); i++) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_veteranos.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/10, Image.SCALE_SMOOTH));

            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda + tamanhoMesaDotWidth + i * image.getIconWidth(), mesaVetY, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(mesaVets.getDisposicaoMesaVets().get(i).getNome());
            nome.setBounds((mesaEsquerda + tamanhoMesaDotWidth + 25) + i * image.getIconWidth(), mesaVetY - 100, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void configButtons() {
        add.setBounds(1400, 150, 200, 100);
        delete.setBounds(1400, 275, 200, 100);
        panel.add(add);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel nomeDoutor = new JLabel("Nome da pessoa que quer adicionar");

                JTextField textDoutor = new JTextField();

                JLabel idadeDoutor = new JLabel("Nascimento da pessoa que quer adicionar");

                JTextField ageDoutor = new JTextField();

                JLabel matriculaDoutor = new JLabel("Primeira matrícula da pessoa que quer adicionar");

                JTextField matriDoutor = new JTextField();

                JLabel hierarqDoutor = new JLabel("Hierarquia da pessoa que quer adicionar");

                JTextField hierDoutor = new JTextField();

                JButton adicionar = new JButton("Adicionar");

                adicionar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (textDoutor.getText().equals("") || ageDoutor.getText().equals("") || matriDoutor.getText().equals("") || hierDoutor.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                        }
                        else {

                            if (searchPraxista(textDoutor.getText())) {
                                JOptionPane.showMessageDialog(null, "Este praxista já existe.");
                            } else {
                                String nome = textDoutor.getText();

                                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                                Date data = new Date();
                                try {
                                    data = df.parse(ageDoutor.getText());
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                                int matricula = Integer.parseInt(matriDoutor.getText());
                                Hierarquia hier = null;
                                try {
                                    hier = ParseJSON.parseHierarquia(hierDoutor.getText());
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                                Praxista novo = new Praxista(hier, nome, matricula, data);
                                //adicionar novo ao JSON
                                praxistas.add(novo);
                                ParseJSON parse = new ParseJSON("database.json");
                                parse.serialize(praxistas);
                                //voltar a ler o JSON
                                praxistas = parse.unserialize();
                            }
                        }
                    }
                });

                nomeDoutor.setBounds(100, 100, 400, 30);
                textDoutor.setBounds(600, 100, 100, 30);
                idadeDoutor.setBounds(100, 200, 400, 30);
                ageDoutor.setBounds(600, 200, 100, 30);
                matriculaDoutor.setBounds(100, 300, 400, 30);
                matriDoutor.setBounds(600, 300, 100, 30);
                hierarqDoutor.setBounds(100, 400, 400, 30);
                hierDoutor.setBounds(600, 400, 100, 30);

                adicionar.setBounds(250, 500, 200, 50);

                addFrame.add(nomeDoutor);
                addFrame.add(textDoutor);
                addFrame.add(idadeDoutor);
                addFrame.add(ageDoutor);
                addFrame.add(matriculaDoutor);
                addFrame.add(matriDoutor);
                addFrame.add(hierarqDoutor);
                addFrame.add(hierDoutor);
                addFrame.add(adicionar);
                addFrame.setLayout(null);
                addFrame.setVisible(true);
            }
        });

        panel.add(delete);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JLabel nomeDoutor = new JLabel("Nome da pessoa que quer adicionar");

                JTextField textDoutor = new JTextField();

                JButton remover = new JButton("Eliminar");

                remover.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (textDoutor.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                        }
                        else {
                            if (searchPraxista(textDoutor.getText())) {
                                Praxista novo = getPraxistaByName(textDoutor.getText());

                                //remover novo ao JSON
                                //voltar a ler o JSON
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Este praxista não existe.");
                            }
                        }
                    }
                });

                nomeDoutor.setBounds(100, 100, 400, 30);
                textDoutor.setBounds(600, 100, 100, 30);
                remover.setBounds(250, 500, 200, 50);

                deleteFrame.add(nomeDoutor);
                deleteFrame.add(textDoutor);
                deleteFrame.add(remover);
                deleteFrame.setLayout(null);

                deleteFrame.setVisible(true);
            }
        });

    }

    public boolean searchPraxista(String name) {
        for (int i = 0; i < praxistas.size(); i++) {
            if (praxistas.get(i).getNome().equals(name))
                return true;
        }
        return false;
    }

    public Praxista getPraxistaByName(String name) {
        for (int i = 0; i < praxistas.size(); i++) {
            if (praxistas.get(i).getNome().equals(name))
                return praxistas.get(i);
        }
        return null;
    }


}
