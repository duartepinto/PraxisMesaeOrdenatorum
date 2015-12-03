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
    JFrame addFrame = new JFrame("Add Doutor");
    JFrame deleteFrame = new JFrame("Delete Doutor");
    JButton add = new JButton("Adicionar");
    JButton delete = new JButton("Eliminar");

    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel mid = new JPanel();

    public GUI (Ordenador ord) {
        this.ord = ord;
    };

    public void initComponents() {

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

        addLeftRow();

        addRightRow();

        addCenterRow();

        configButtons();

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
        for (int i = 0; i < ord.getMesaVets().getVeteranos().size(); i++) {
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

                            if (ord.searchPraxista(textDoutor.getText())) {
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
                                //adicionar ao JSON
                                ord.getPraxistas().add(novo);
                                ParseJSON parse = new ParseJSON("database.json");
                                parse.serialize(ord.getPraxistas());
                                //voltar a ler o JSON
                                ord.setPraxistas(parse.unserialize());
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
                                //voltar a ler o JSON
                                ord.setPraxistas(parse.unserialize());

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

}
