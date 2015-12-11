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
 * Created by Miguel on 03/12/2015.
 */
public class GUI {

    Ordenador ord;

    final int espacamento_doutores = 200;

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

    JPanel addPanel = new JPanel(new FlowLayout());
    JFrame addFrame = new JFrame("Add Doutor");

    JPanel deletePanel = new JPanel(new FlowLayout());
    JFrame deleteFrame = new JFrame("Delete Doutor");

    JButton add = new JButton("Adicionar");
    JButton delete = new JButton("Eliminar");

    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel mid = new JPanel();

    ArrayList<Praxista> disposicaoMesaVets = new ArrayList<Praxista>();
    ParseJSON parse = new ParseJSON("database.json");

    public GUI (Ordenador ord) {
        this.ord = ord;
    };

    public void initComponents() {
        disposicaoMesaVets = ord.getMesaVets().geraMesa();

            panel.removeAll();
            addPanel.removeAll();
            deletePanel.removeAll();

            addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addFrame.setBounds(400, 200, 1000, 800);

            deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            deleteFrame.setBounds(400, 200, 1000, 800);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/20, Image.SCALE_SMOOTH));
            tamanhoMesaDotWidth = image.getIconWidth();
            tamanhoMesaDotHeight = image.getIconHeight();

            image = new ImageIcon(this.getClass().getResource("resources/table_veteranos.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/30, (int)height/15, Image.SCALE_SMOOTH));
            tamanhoMesaVetWidth = image.getIconWidth();
            tamanhoMesaVetHeight = image.getIconHeight();

            panel = (JPanel)frame.getContentPane();
            panel.setLayout(null);

            addPanel = (JPanel)addFrame.getContentPane();
            addPanel.setLayout(null);

            deletePanel = (JPanel)deleteFrame.getContentPane();
            deletePanel.setLayout(null);

            addLeftRow();

            addRightRow();

            addCenterRow();

            configButtons();

            panel.validate();
            panel.repaint();
            deletePanel.validate();
            deletePanel.repaint();
            addPanel.validate();
            addPanel.repaint();

        frame.setBounds(0, 0, (int) width, (int) height);

        frame.setVisible(true);
    }

    public void addLeftRow() {
        for (int i = 0; i < ord.getLeftRow().size(); i = i + 1) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/20, Image.SCALE_SMOOTH));
            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(ord.getLeftRow().get(i).getNome());
            nome.setBounds(mesaEsquerda - 100,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void addRightRow() {
        for (int i = 0; i < ord.getRightRow().size(); i = i + 1) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_doutores.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/20, (int)height/20, Image.SCALE_SMOOTH));


            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda + tamanhoMesaDotWidth + ord.getMesaVets().getVeteranos().size() * tamanhoMesaVetWidth,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(ord.getRightRow().get(i).getNome());
            nome.setBounds(mesaEsquerda + tamanhoMesaDotWidth + image.getIconWidth() + 25 + ord.getMesaVets().getVeteranos().size() * tamanhoMesaVetWidth,i*image.getIconHeight() + espacamento_doutores, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void addCenterRow() {

        for (int i = 0; i < ord.getMesaVets().getDisposicaoMesaVets().size(); i++) {
            ImageIcon image = new ImageIcon(this.getClass().getResource("resources/table_veteranos.jpg"));
            image.setImage(image.getImage().getScaledInstance((int)width/30, (int)height/15, Image.SCALE_SMOOTH));

            JLabel rowLabel = new JLabel(image);
            rowLabel.setBounds(mesaEsquerda + tamanhoMesaDotWidth + i * image.getIconWidth(), mesaVetY, image.getIconWidth(), image.getIconHeight());

            JLabel nome = new JLabel(ord.getMesaVets().getDisposicaoMesaVets().get(i).getNome());
            nome.setBounds((mesaEsquerda + tamanhoMesaDotWidth + 15) + i * image.getIconWidth(), mesaVetY - 100, image.getIconWidth(), image.getIconHeight());

            panel.add(nome);
            panel.add(rowLabel);
        }
    }

    public void configButtons() {
        add.setBounds(mesaEsquerda + ord.getMesaVets().getDisposicaoMesaVets().size() * tamanhoMesaVetWidth + 2 * tamanhoMesaDotWidth + 200, 150, 200, 100);
        delete.setBounds(mesaEsquerda + ord.getMesaVets().getDisposicaoMesaVets().size() * tamanhoMesaVetWidth + 2 * tamanhoMesaDotWidth + 200, 275, 200, 100);
        panel.add(add);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel nomeDoutor = new JLabel("Nome da pessoa que quer adicionar:  ");

                JTextField textDoutor = new JTextField();

                JLabel idadeDoutor = new JLabel("Nascimento da pessoa que quer adicionar (yyyy-mm-dd):");

                JTextField ageDoutor = new JTextField();

                JLabel matriculaDoutor = new JLabel("Primeira matrícula da pessoa que quer adicionar:");

                JTextField matriDoutor = new JTextField();

                JLabel hierarqDoutor = new JLabel("Hierarquia da pessoa que quer adicionar:");

                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement("Segundanista");
                model.addElement("Semi Puto");
                model.addElement("Terceiranista");
                model.addElement("Puto");
                model.addElement("Quartanista");
                model.addElement("Doutor de Merda");
                model.addElement("Quintanista");
                model.addElement("Merda de Doutor");
                model.addElement("Veterano");
                model.addElement("Dux Facultis");
                model.addElement("Dux Veteranorum");
                JComboBox comboBox = new JComboBox(model);

                JButton adicionar = new JButton("Adicionar");

                adicionar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean tent = true;

                        if (textDoutor.getText().equals("") || ageDoutor.getText().equals("") || matriDoutor.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                        }
                        else {

                            if (ord.searchPraxista(textDoutor.getText())) {
                                JOptionPane.showMessageDialog(null, "Este praxista já existe.");
                            } else {
                                String nome = textDoutor.getText();

                                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                                Date data = new Date();
                                try {
                                    data = df.parse(ageDoutor.getText());
                                } catch (ParseException e1) {
                                    JOptionPane.showMessageDialog(null, "O formato da data tem de ser yyyy-mm-dd.");
                                    e1.printStackTrace();
                                    tent = false;
                                }

                                int matricula = 0;
                                try {
                                    matricula = Integer.parseInt(matriDoutor.getText());
                                } catch (NumberFormatException n1) {
                                    JOptionPane.showMessageDialog(null, "O ano da primeira matrícula tem de ser um número (i.e. 2014).");
                                    n1.printStackTrace();
                                    tent = false;
                                }
                                Hierarquia hier = null;
                                try {
                                    hier = ParseJSON.parseHierarquia(returnHier((String)comboBox.getSelectedItem()));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                    tent = false;
                                }

                                if (hier == null) {
                                    JOptionPane.showMessageDialog(null, "A hierarquia tem de ser SEGUNDANISTA, SEMI_PUTO, TERCEIRANISTA, PUTO, QUARTANISTA, DOUTOR_DE_MERDA, QUINTANISTA, MERDA_DE_DOUTOR, VETERANO, DUX_FACULTIS, DUX_VETERANORUM");
                                    tent = false;
                                }
                                if (tent) {
                                    Praxista novo = new Praxista(hier, nome, matricula, data);
                                    //adicionar ao JSON
                                    ord.getPraxistas().add(novo);
                                    setOrdenador();
                                    parse.serialize(ord.getPraxistas());
                                    //voltar a ler o JSON
                                    addFrame.setVisible(false);
                                    initComponents();
                                }
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
                comboBox.setBounds(600, 400, 150, 40);

                adicionar.setBounds(250, 500, 200, 50);


                addPanel.add(nomeDoutor);
                addPanel.add(textDoutor);
                addPanel.add(idadeDoutor);
                addPanel.add(ageDoutor);
                addPanel.add(matriculaDoutor);
                addPanel.add(hierarqDoutor);
                addPanel.add(comboBox);
                addPanel.add(adicionar);
                addPanel.add(matriDoutor);

                addFrame.setVisible(true);
            }
        });

        panel.add(delete);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JLabel nomeDoutor = new JLabel("Nome da pessoa que quer eliminar");

                JTextField textDoutor = new JTextField();

                JButton remover = new JButton("Eliminar");

                remover.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (textDoutor.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                        }
                        else {
                            if (ord.searchPraxista(textDoutor.getText())) {
                                Praxista novo = ord.getPraxistaByName(textDoutor.getText());

                                //remover novo ao JSON
                                ord.getPraxistas().remove(novo);
                                ParseJSON parse = new ParseJSON("database.json");
                                parse.serialize(ord.getPraxistas());
                                setOrdenador();
                                //voltar a ler o JSON
                                deleteFrame.setVisible(false);
                                initComponents();

                            } else {
                                JOptionPane.showMessageDialog(null, "Este praxista não existe.");
                            }
                        }
                    }
                });

                nomeDoutor.setBounds(100, 100, 400, 30);
                textDoutor.setBounds(600, 100, 100, 30);
                remover.setBounds(250, 500, 200, 50);

                deletePanel.add(nomeDoutor);
                deletePanel.add(textDoutor);
                deletePanel.add(remover);

                deleteFrame.setVisible(true);
            }
        });

    }

    private void setOrdenador() {
        ord = new Ordenador(ord.getPraxistas());

        Collections.sort(ord.getLeftRow());
        Collections.reverse(ord.getLeftRow());
        Collections.sort(ord.getRightRow());
        Collections.reverse(ord.getRightRow());

        disposicaoMesaVets = ord.getMesaVets().geraMesa();
    }

    private String returnHier(String h) {
        switch (h) {
            case "Segundanista":
                return "SEGUNDANISTA";
            case "Semi Puto":
                return "SEMI_PUTO";
            case "Terceiranista":
                return "TERCEIRANISTA";
            case "Puto":
                return "PUTO";
            case "Quartanista":
                return "QUARTANISTA";
            case "Doutor de Merda":
                return "DOUTOR_DE_MERDA";
            case "Quintanista":
                return "QUINTANISTA";
            case "Merda de Doutor":
                return "MERDA_DE_DOUTOR";
            case "Veterano":
                return "VETERANO";
            case "Dux Facultis":
                return "DUX_FACULTIS";
            case "Dux Veteranorum":
                return "DUX_VETERANORUM";
            default:
                break;
        }
        return new String();
    }

}
