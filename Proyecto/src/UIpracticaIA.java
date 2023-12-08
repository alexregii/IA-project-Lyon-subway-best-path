package practicaia;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.stream.*;

import javax.swing.JFrame;
import javax.swing.border.Border;

public class UIpracticaIA {
    private static myFrame frame;
    
    public static void ini(){
        frame = new myFrame("Buscador metro Lyon");
        frame.inicializar();
        
        frame.setVisible(true);
        /*
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelParametros = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelMapa = new JPanel();
        JButton b = new JButton("PanelMapa");
        
        panelMapa.add(b);
        b.setVisible(true);
        JButton b1 = new JButton("PanelPArametros1");
        JButton b2 = new JButton("PanelPArametros1");
        JButton b3 = new JButton("PanelPArametros1");
        JButton b4 = new JButton("PanelPArametros1");
        
        panelParametros.add(b1);
        panelParametros.add(b2);
        panelParametros.add(b3);
        panelParametros.add(b4);
        b1.setVisible(true);
        b4.setVisible(true);
        b3.setVisible(true);
        b2.setVisible(true);
        panelPrincipal.add(panelParametros,BorderLayout.PAGE_START);
        panelPrincipal.add(panelMapa);
        frame.add(panelPrincipal);*/
    }
    
    
}

class myFrame extends JFrame {
    
	
    private final String title;
    private Toolkit myToolkit;
    private final int width;
    private final int height;
    private int x;
    private int y;
    
    //Parametros busqueda
    private String origen;
    private String destino;
    private int hora;
    
    //Combos y paneles para origen y destino
    private static JComboBox<String> comboLineaOrigen;
    private static JComboBox<String> comboLineaDestino;
    private static JComboBox<String> comboEstacionesOrigen;
    private static JComboBox<String> comboEstacionesDestino;
    
    private JPanel panelOrigen = new JPanel(new BorderLayout(10,10));
    private JPanel panelSeleccionOrigen = new JPanel();
    private JPanel panelDestino = new JPanel(new BorderLayout(10,10));
    private JPanel panelSeleccionDestino = new JPanel();

    private MapaPanel mapaPanel = new MapaPanel();
    
    //Combos y panel para hora
    private JComboBox<Integer> comboHora;
    private JComboBox<Integer> comboMinuto;
    
    private JPanel panelHora = new JPanel(new BorderLayout(10,10));
    private JPanel panelSeleccionHora = new JPanel();
    
    private JButton buscar;
    
    int codOrigen = -1;
    int codDestino = -1;
    
    ArrayList<Integer> trayecto = new ArrayList<>();
    
    public myFrame(String title){
        this.myToolkit = Toolkit.getDefaultToolkit();
        this.title = title;
        
        /*
         * se busca relacion 2:3 entre ancho y alto para dividir horizontalmente
         * en 2 partes con la de la derecha el doble de ancho que la de la izquierda.
         * La parte izquierda para meter parametros y la derecha para el mapa.
         * Con la relacion 2:3 la parte derecha es cuadrada.
         */
        
        this.width = myToolkit.getScreenSize().height*9/8;
        this.height = myToolkit.getScreenSize().height*3/4;
        
        this.x = (myToolkit.getScreenSize().width-this.width)/2;
        this.y = (myToolkit.getScreenSize().height-this.height)/2;
        this.comboLineaOrigen = new JComboBox(new String[]{" ","A","B","C","D"});
        this.comboLineaDestino = new JComboBox(new String[]{" ","A","B","C","D"});
        comboEstacionesOrigen = new JComboBox(new String[]{"Seleccione una linea"});
        comboEstacionesDestino = new JComboBox(new String[]{"Seleccione una linea"});
        
        comboLineaOrigen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loadCombo(comboEstacionesOrigen, comboLineaOrigen.getSelectedIndex());
            }
        });
        comboLineaDestino.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loadCombo(comboEstacionesDestino, comboLineaDestino.getSelectedIndex());
            }
        });
        
        comboHora = new JComboBox<>();
        comboMinuto = new JComboBox<>();       
        IntStream enterosHora = IntStream.range(0,24);
        IntStream enterosMinuto = IntStream.range(0,60);        
        enterosHora.forEachOrdered(num -> comboHora.addItem(num));
        enterosMinuto.forEachOrdered(num -> comboMinuto.addItem(num));
        comboHora.setEditable(true);
        comboMinuto.setEditable(true);        
        
        buscar = new JButton("Buscar");
        buscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String seleccionOrigen = (String) comboEstacionesOrigen.getSelectedItem();
                String seleccionDestino = (String) comboEstacionesDestino.getSelectedItem();
                if(seleccionOrigen.equals("Seleccione una linea")||seleccionDestino.equals("Seleccione una linea")){
                    JOptionPane.showMessageDialog(null, "Seleccione una estacion valida");
                }else if(seleccionOrigen.equals(seleccionDestino)){
                    JOptionPane.showMessageDialog(null, "El origen y destino son el mismo");
                }else{
                    boolean horaValida = false;
                    try{
                        int selHora = Integer.parseInt(String.valueOf(comboHora.getSelectedItem()));
                        int selMinuto = Integer.parseInt(String.valueOf(comboMinuto.getSelectedItem()));
                        if(selHora <0 || selHora > 23 || selMinuto <0 || selMinuto > 59)
                            JOptionPane.showMessageDialog(null, "Seleccione una hora valida");
                        else horaValida = true;
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Seleccione una hora valida");
                        return;
                    }
                    if(horaValida){
                        hora = 60*((int)comboHora.getSelectedItem())+((int)comboMinuto.getSelectedItem());
                        algoritmoAestrella.codEstacion.forEach((cod,estacion)->{
                                if(codOrigen==-1&&seleccionOrigen.equals(estacion))
                                    codOrigen = cod;
                                else if(codDestino==-1&&seleccionDestino.equals(estacion))
                                    codDestino = cod;
                                    });
                        algoritmoAestrella a = new algoritmoAestrella();
                        trayecto = a.Aestrella(codOrigen, codDestino, hora);
                        System.out.println(trayecto.toString());
                        mapaPanel.setRuta(trayecto);
                        mapaPanel.repaint();
                        //loadMapa(trayecto); //Falta implementacion
                    }
                }
                
            }
        });
    }
    
    public static void loadCombo(JComboBox<String> combo, int linea){
        
        combo.removeAllItems();
        switch(linea){
            case 0: //blank
                combo.addItem("Seleccione una linea");
                break;
            case 1: //linea A
                algoritmoAestrella.estacionesLineaA.forEach(idEstacion -> {
                    combo.addItem(algoritmoAestrella.codEstacion.get(idEstacion));
                });
                break;
            case 2: //linea B
                algoritmoAestrella.estacionesLineaB.forEach(idEstacion -> {
                    combo.addItem(algoritmoAestrella.codEstacion.get(idEstacion));
                });
                break;
            case 3://linea C
                algoritmoAestrella.estacionesLineaC.forEach(idEstacion -> {
                    combo.addItem(algoritmoAestrella.codEstacion.get(idEstacion));
                });
                break;
            case 4: //linea D
                algoritmoAestrella.estacionesLineaD.forEach(idEstacion -> {
                    combo.addItem(algoritmoAestrella.codEstacion.get(idEstacion));
                });
                break;
        }
    }
    
    public static void loadMapa(ArrayList<Integer> trayecto){
        //code
    }
    
    
    public void inicializar(){
        
        this.setBounds(this.x,this.y,this.width,this.height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        //this.setExtended...
        this.setTitle(title);
        this.getContentPane().setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        
        //Layout origen, destino y hora
        //Origen
        JLabel lineaLabelOrigen = new JLabel("Linea: ");
        JLabel estacionLabelOrigen = new JLabel("Estacion: ");
        
        GroupLayout layoutOrigen = new GroupLayout(panelSeleccionOrigen);
        layoutOrigen.setAutoCreateContainerGaps(true);
        layoutOrigen.setAutoCreateGaps(true);
        
        layoutOrigen.setHorizontalGroup(
            layoutOrigen.createSequentialGroup()
                .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lineaLabelOrigen)
                    .addComponent(comboLineaOrigen)).addGap(20)
                .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(estacionLabelOrigen)
                    .addComponent(comboEstacionesOrigen))
        );
        
        layoutOrigen.setVerticalGroup(
            layoutOrigen.createSequentialGroup()
                .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(lineaLabelOrigen)
                         .addComponent(estacionLabelOrigen))
                .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(comboLineaOrigen)
                        .addComponent(comboEstacionesOrigen))
        );
        panelSeleccionOrigen.setLayout(layoutOrigen);
        
        //Destino
        JLabel lineaLabelDestino = new JLabel("Linea: ");
        JLabel estacionLabelDestino = new JLabel("Estacion: ");
        
        GroupLayout layoutDestino = new GroupLayout(panelSeleccionDestino);
        layoutDestino.setAutoCreateContainerGaps(true);
        layoutDestino.setAutoCreateGaps(true);
        
        layoutDestino.setHorizontalGroup(
            layoutDestino.createSequentialGroup()
                .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lineaLabelDestino)
                    .addComponent(comboLineaDestino)).addGap(20)
                .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(estacionLabelDestino)
                    .addComponent(comboEstacionesDestino))
        );
        
        layoutDestino.setVerticalGroup(
            layoutDestino.createSequentialGroup()
                .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(lineaLabelDestino)
                         .addComponent(estacionLabelDestino))
                .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(comboLineaDestino)
                        .addComponent(comboEstacionesDestino))
        );
        panelSeleccionDestino.setLayout(layoutDestino);
        
        //Hora      
        GroupLayout layoutHora = new GroupLayout(panelSeleccionHora);
        layoutHora.setAutoCreateContainerGaps(true);
        layoutHora.setAutoCreateGaps(true);
        JLabel separador = new JLabel(":");
        layoutHora.setHorizontalGroup(
            layoutHora.createSequentialGroup()
                    .addComponent(comboHora).addGap(10)
                    .addComponent(separador).addGap(10)
                    .addComponent(comboMinuto)
        );
        
        layoutHora.setVerticalGroup(
            layoutHora.createSequentialGroup()
                .addGroup(layoutHora.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(comboHora).addGap(10)
                        .addComponent(separador).addGap(10)
                        .addComponent(comboMinuto))
        );
        panelSeleccionHora.setLayout(layoutHora);
        
        /*
         *  Layout del Frame principal
         */
        
        //Panel del Mapa
        SVGApplication app = new SVGApplication();
        //El panel mapa empieza en 1,0 y ocupa 3 filas y 2 columnas
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(10,0,0,50);
        this.getContentPane().add(mapaPanel,constraints);
        
        //Panel origen
        
        JLabel origenLabel = new JLabel("Origen");
        origenLabel.setVerticalAlignment(SwingConstants.CENTER);
        origenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        origenLabel.setFont(new Font("Dialog",Font.BOLD,18));
        
        panelOrigen.add(origenLabel,BorderLayout.PAGE_START);
        panelOrigen.add(panelSeleccionOrigen,BorderLayout.CENTER);
        Border bordeOrigen = BorderFactory.createLineBorder(Color.black);
        panelOrigen.setBorder(bordeOrigen);
        //El panel origen empieza en 0,0 y ocupa una fila y columna
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 1.0; //1
        constraints.weightx = 0.5; //0.5
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        this.getContentPane().add(panelOrigen,constraints);
        
        //Panel Destino
        
        JLabel destinoLabel = new JLabel("Destino");
        destinoLabel.setVerticalAlignment(SwingConstants.CENTER);
        destinoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        destinoLabel.setFont(new Font("Dialog",Font.BOLD,18));
        
        panelDestino.add(destinoLabel,BorderLayout.PAGE_START);
        panelDestino.add(panelSeleccionDestino,BorderLayout.CENTER);
        Border bordeDestino = BorderFactory.createLineBorder(Color.black);
        panelHora.setBorder(bordeDestino);
        //El panel destino empieza en 0,1 y ocupa una fila y columna
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        this.getContentPane().add(panelDestino,constraints);

        //Panel hora
        
        JLabel horaLabel = new JLabel("Hora: (hh:mm)");
        horaLabel.setVerticalAlignment(SwingConstants.CENTER);
        horaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        horaLabel.setFont(new Font("Dialog",Font.BOLD,18));
        
        panelHora.add(horaLabel,BorderLayout.PAGE_START);
        panelHora.add(panelSeleccionHora,BorderLayout.CENTER);
        Border bordeHora = BorderFactory.createLineBorder(Color.black);
        panelDestino.setBorder(bordeHora);
        //El panel otherParam empieza en 0,2 y ocupa una fila y columna
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        this.getContentPane().add(panelHora,constraints);
        
        //Panel buscar
        
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        this.getContentPane().add(buscar,constraints);
        
        //Espacio al final
        JLabel espacio = new JLabel(" ");
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;
        constraints.weighty = 0.05;
        this.getContentPane().add(espacio,constraints);
        
        //esapcio a la derecha del boton
        JLabel espacio1 = new JLabel(" ");
        
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0.05;
        this.getContentPane().add(espacio1,constraints);
        
    }
}