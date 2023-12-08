package practicaia;
import java.awt.*;
import java.util.*;
import javax.swing.*;



public class MapaPanel extends JPanel {
    private final int pantallaLargo = 1024;
    private final int pantallaAncho = 1024;
    private ArrayList<Integer> trayecto;
    private Map<Integer, int[]> coordenadas;
    private Map<Integer, int[][]> conexiones;
    
    public MapaPanel(ArrayList<Integer> ruta){
        this.coordenadas = new HashMap();
        this.conexiones = new HashMap();
        this.trayecto = ruta;
        
        this.setPreferredSize(new Dimension(pantallaLargo, pantallaAncho));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        
        inicializarcoordenadas();
    }
    
    public MapaPanel(){
        this.coordenadas = new HashMap();
        this.conexiones = new HashMap();
        
        this.setPreferredSize(new Dimension(pantallaLargo, pantallaAncho));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        
        inicializarcoordenadas();
    }
    
    public void setRuta (ArrayList<Integer> ruta) {
    	trayecto = ruta;
    }
    
    private void inicializarcoordenadas() {
        //Estaciones con sus coordenadas en el JPanel
        coordenadas.put(1, new int[]{310, 145}); //Cuire
        coordenadas.put(2, new int[]{270, 230}); //Henon
        coordenadas.put(3, new int[]{300, 270}); //Croix-Rousse
        coordenadas.put(4, new int[]{330, 305}); //Croix Paquet
        coordenadas.put(5, new int[]{335, 350}); //Hotel de Ville
        coordenadas.put(6, new int[]{515, 320}); //Charpennes
        coordenadas.put(7, new int[]{505, 355}); //Brotteaux
        coordenadas.put(8, new int[]{485, 405}); //Gare Part-Dieu
        coordenadas.put(9, new int[]{420, 435}); //Place Guichard
        coordenadas.put(10, new int[]{415, 490}); //Saxe Gambetta
        coordenadas.put(11, new int[]{380, 580}); //Jean Mace
        coordenadas.put(12, new int[]{355, 645}); //Place Jean Jaures
        coordenadas.put(13, new int[]{315, 730}); //Debourg
        coordenadas.put(14, new int[]{295, 780}); //Stade de Gerland
        coordenadas.put(15, new int[]{180, 885}); //Gare d'Oullins
        coordenadas.put(16, new int[]{965, 405}); //Vaulx-en-Velin
        coordenadas.put(17, new int[]{860, 380}); //Laurent Bonnevay
        coordenadas.put(18, new int[]{790, 365}); //Cusset
        coordenadas.put(19, new int[]{720, 345}); //Flachet
        coordenadas.put(20, new int[]{655, 325}); //Gratte Ciel
        coordenadas.put(21, new int[]{605, 315}); //Republique Villeurbanne
        coordenadas.put(22, new int[]{465, 325}); //Massena
        coordenadas.put(23, new int[]{405, 335}); //Foch
        coordenadas.put(24, new int[]{335, 390}); //Cordeliers
        coordenadas.put(25, new int[]{310, 455}); //Bellecour
        coordenadas.put(26, new int[]{280, 500}); //Ampere-Victor Hugo
        coordenadas.put(27, new int[]{265, 540}); //Perrache
        coordenadas.put(28, new int[]{715, 985}); //Gare de Venissieux
        coordenadas.put(29, new int[]{710, 835}); //Parilly
        coordenadas.put(30, new int[]{705, 750}); //Mermoz-Pinel
        coordenadas.put(31, new int[]{690, 640}); //Laennec
        coordenadas.put(32, new int[]{650, 610}); //Grange Blanche
        coordenadas.put(33, new int[]{590, 580}); //Monplaisir Lumiere
        coordenadas.put(34, new int[]{545, 555}); //Sans Souci
        coordenadas.put(35, new int[]{475, 520}); //Garibaldi
        coordenadas.put(36, new int[]{385, 475}); //Guillotiere Gabriel Peri
        coordenadas.put(37, new int[]{260, 420}); //Vieux Lyon
        coordenadas.put(38, new int[]{110, 355}); //Gorge de Loup
        coordenadas.put(39, new int[]{110, 270}); //Valmy
        coordenadas.put(40, new int[]{105, 210}); //Gare de Vaise
        
        //Lineas que crean conexiones entre los puntos (paradas)
        conexiones.put(1, new int[][]{{coordenadas.get(1)[0]+10,coordenadas.get(1)[1]+20}, {coordenadas.get(2)[0]+16,coordenadas.get(2)[1]+2}});
        conexiones.put(2, new int[][]{{coordenadas.get(2)[0]+17,coordenadas.get(2)[1]+18}, {coordenadas.get(3)[0]+5,coordenadas.get(3)[1]+2}});
        conexiones.put(3, new int[][]{{coordenadas.get(3)[0]+17,coordenadas.get(3)[1]+18}, {coordenadas.get(4)[0]+5,coordenadas.get(4)[1]+2}});
        conexiones.put(4, new int[][]{{coordenadas.get(4)[0]+12,coordenadas.get(4)[1]+20}, {coordenadas.get(5)[0]+10,coordenadas.get(5)[1]}});
        conexiones.put(5, new int[][]{{coordenadas.get(6)[0]+10,coordenadas.get(6)[1]+20}, {coordenadas.get(7)[0]+14,coordenadas.get(7)[1]+1}});
        conexiones.put(6, new int[][]{{coordenadas.get(7)[0]+7,coordenadas.get(7)[1]+19}, {coordenadas.get(8)[0]+14,coordenadas.get(8)[1]+1}});
        conexiones.put(7, new int[][]{{coordenadas.get(8)[0]+3,coordenadas.get(8)[1]+16}, {coordenadas.get(9)[0]+19,coordenadas.get(9)[1]+4}});
        conexiones.put(8, new int[][]{{coordenadas.get(9)[0]+10,coordenadas.get(9)[1]+20}, {coordenadas.get(10)[0]+14,coordenadas.get(10)[1]}});
        conexiones.put(9, new int[][]{{coordenadas.get(10)[0]+7,coordenadas.get(10)[1]+19}, {coordenadas.get(11)[0]+14,coordenadas.get(11)[1]}});
        conexiones.put(10, new int[][]{{coordenadas.get(11)[0]+7,coordenadas.get(11)[1]+19}, {coordenadas.get(12)[0]+14,coordenadas.get(12)[1]}});
        conexiones.put(11, new int[][]{{coordenadas.get(12)[0]+7,coordenadas.get(12)[1]+19}, {coordenadas.get(13)[0]+14,coordenadas.get(13)[1]}});
        conexiones.put(12, new int[][]{{coordenadas.get(13)[0]+7,coordenadas.get(13)[1]+19}, {coordenadas.get(14)[0]+14,coordenadas.get(14)[1]}});
        conexiones.put(13, new int[][]{{coordenadas.get(14)[0]+3,coordenadas.get(14)[1]+17}, {coordenadas.get(15)[0]+17,coordenadas.get(15)[1]+2}});
        conexiones.put(14, new int[][]{{coordenadas.get(16)[0],coordenadas.get(16)[1]+10}, {coordenadas.get(17)[0]+20,coordenadas.get(17)[1]+12}});
        conexiones.put(15, new int[][]{{coordenadas.get(17)[0],coordenadas.get(17)[1]+8}, {coordenadas.get(18)[0]+19,coordenadas.get(18)[1]+12}});
        conexiones.put(16, new int[][]{{coordenadas.get(18)[0],coordenadas.get(18)[1]+8}, {coordenadas.get(19)[0]+20,coordenadas.get(19)[1]+12}});
        conexiones.put(17, new int[][]{{coordenadas.get(19)[0],coordenadas.get(19)[1]+8}, {coordenadas.get(20)[0]+20,coordenadas.get(20)[1]+13}});
        conexiones.put(18, new int[][]{{coordenadas.get(20)[0],coordenadas.get(20)[1]+10}, {coordenadas.get(21)[0]+19,coordenadas.get(21)[1]+13}});
        conexiones.put(19, new int[][]{{coordenadas.get(21)[0],coordenadas.get(21)[1]+11}, {coordenadas.get(6)[0]+20,coordenadas.get(6)[1]+8}});
        conexiones.put(20, new int[][]{{coordenadas.get(6)[0],coordenadas.get(6)[1]+11}, {coordenadas.get(22)[0]+20,coordenadas.get(22)[1]+9}});
        conexiones.put(21, new int[][]{{coordenadas.get(22)[0],coordenadas.get(22)[1]+12}, {coordenadas.get(23)[0]+20,coordenadas.get(23)[1]+8}});
        conexiones.put(22, new int[][]{{coordenadas.get(23)[0],coordenadas.get(23)[1]+10}, {coordenadas.get(5)[0]+20,coordenadas.get(5)[1]+6}});
        conexiones.put(23, new int[][]{{coordenadas.get(5)[0]+10,coordenadas.get(5)[1]+20}, {coordenadas.get(24)[0]+10,coordenadas.get(24)[1]}});
        conexiones.put(24, new int[][]{{coordenadas.get(24)[0]+7,coordenadas.get(24)[1]+19}, {coordenadas.get(25)[0]+17,coordenadas.get(25)[1]+2}});
        conexiones.put(25, new int[][]{{coordenadas.get(25)[0]+6,coordenadas.get(25)[1]+19}, {coordenadas.get(26)[0]+17,coordenadas.get(26)[1]+2}});
        conexiones.put(26, new int[][]{{coordenadas.get(26)[0]+7,coordenadas.get(26)[1]+20}, {coordenadas.get(27)[0]+15,coordenadas.get(27)[1]+2}});
        conexiones.put(27, new int[][]{{coordenadas.get(28)[0]+9,coordenadas.get(28)[1]}, {coordenadas.get(29)[0]+12,coordenadas.get(29)[1]+19}});
        conexiones.put(28, new int[][]{{coordenadas.get(29)[0]+10,coordenadas.get(29)[1]+2}, {coordenadas.get(30)[0]+10,coordenadas.get(30)[1]+20}});
        conexiones.put(29, new int[][]{{coordenadas.get(30)[0]+10,coordenadas.get(30)[1]+7}, {coordenadas.get(31)[0]+12,coordenadas.get(31)[1]+18}});
        conexiones.put(30, new int[][]{{coordenadas.get(31)[0]+1,coordenadas.get(31)[1]+6}, {coordenadas.get(32)[0]+17,coordenadas.get(32)[1]+17}});
        conexiones.put(31, new int[][]{{coordenadas.get(32)[0]+1,coordenadas.get(32)[1]+6}, {coordenadas.get(33)[0]+19,coordenadas.get(33)[1]+13}});
        conexiones.put(32, new int[][]{{coordenadas.get(33)[0]+1,coordenadas.get(33)[1]+6}, {coordenadas.get(34)[0]+19,coordenadas.get(34)[1]+15}});
        conexiones.put(33, new int[][]{{coordenadas.get(34)[0]+1,coordenadas.get(34)[1]+6}, {coordenadas.get(35)[0]+17,coordenadas.get(35)[1]+16}});
        conexiones.put(34, new int[][]{{coordenadas.get(35)[0]+1,coordenadas.get(35)[1]+6}, {coordenadas.get(10)[0]+19,coordenadas.get(10)[1]+14}});
        conexiones.put(35, new int[][]{{coordenadas.get(10)[0]+1,coordenadas.get(10)[1]+6}, {coordenadas.get(36)[0]+19,coordenadas.get(36)[1]+16}});
        conexiones.put(36, new int[][]{{coordenadas.get(36)[0],coordenadas.get(36)[1]+8}, {coordenadas.get(25)[0]+20,coordenadas.get(25)[1]+12}});
        conexiones.put(37, new int[][]{{coordenadas.get(25)[0]+1,coordenadas.get(25)[1]+5}, {coordenadas.get(37)[0]+17,coordenadas.get(37)[1]+17}});
        conexiones.put(38, new int[][]{{coordenadas.get(37)[0]+1,coordenadas.get(37)[1]+6}, {coordenadas.get(38)[0]+17,coordenadas.get(38)[1]+17}});
        conexiones.put(39, new int[][]{{coordenadas.get(38)[0]+8,coordenadas.get(38)[1]}, {coordenadas.get(39)[0]+10,coordenadas.get(39)[1]+20}});
        conexiones.put(40, new int[][]{{coordenadas.get(39)[0]+9,coordenadas.get(39)[1]+1}, {coordenadas.get(40)[0]+12,coordenadas.get(40)[1]+20}});
        }
    
    private void creacionMapa(Graphics2D g) {
        //Funcion que dibuja el mapa
        g.setColor(new Color(240,147,0));
        g.setStroke(new BasicStroke(7)); //Agranda las lineas que se dibujan
        for (int i = 1; i < 5; i++) {
            g.drawLine(conexiones.get(i)[0][0], conexiones.get(i)[0][1], conexiones.get(i)[1][0], conexiones.get(i)[1][1]);
        }
        g.setColor(new Color(0, 148, 215));
        for (int i = 5; i < 14; i++) {
            g.drawLine(conexiones.get(i)[0][0], conexiones.get(i)[0][1], conexiones.get(i)[1][0], conexiones.get(i)[1][1]);
        }
        g.setColor(new Color(224,0,26));
        for (int i = 14; i < 27; i++) {
            g.drawLine(conexiones.get(i)[0][0], conexiones.get(i)[0][1], conexiones.get(i)[1][0], conexiones.get(i)[1][1]);
        }
        g.setColor(new Color(0,143,54));
        for (int i = 27; i < 41; i++) {
            g.drawLine(conexiones.get(i)[0][0], conexiones.get(i)[0][1], conexiones.get(i)[1][0], conexiones.get(i)[1][1]);
        }
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2)); //Agranda las lineas que se dibujan
        for (int i = 1; i <= coordenadas.size(); i++){
            g.fillOval(coordenadas.get(i)[0], coordenadas.get(i)[1], 20, 20);
        }        
        g.setColor(Color.black); //Color de lo que se quiere dibujar
        g.setStroke(new BasicStroke(2)); //Agranda las lineas que se dibujan
        for (int i = 1; i <= coordenadas.size(); i++){
            g.drawOval(coordenadas.get(i)[0], coordenadas.get(i)[1], 20, 20);
        }
    }
    
    private void registrarParadas(Graphics2D g, ArrayList<Integer> lista) {
        /* Funcion que registra las paradas con colores.
        ** En verde registra el inicio, en cyan las intermedias y
        ** en rojo se registra la parada destino.
        */
        g.setColor(Color.green);
        g.fillOval(coordenadas.get(lista.get(0))[0]+1, coordenadas.get(lista.get(0))[1]+1, 19, 19);
        g.setColor(Color.CYAN);
        for (int i = 1; i < lista.size()-2; i++) {
            g.fillOval(coordenadas.get(lista.get(i))[0]+1, coordenadas.get(lista.get(i))[1]+1, 19, 19);
        }
        g.setColor(Color.red);
        g.fillOval(coordenadas.get(lista.get(lista.size()-2))[0]+1, coordenadas.get(lista.get(lista.size()-2))[1]+1, 19, 19);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //Codigo a mejorar
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        creacionMapa(g2);
        if (trayecto != null)
        	registrarParadas(g2, trayecto);
    }
}
