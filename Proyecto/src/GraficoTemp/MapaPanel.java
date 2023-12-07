import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MapaPanel extends JPanel implements Runnable {
    final int pantallaLargo = 1280;
    final int pantallaAncho = 720;
    Line2D lineaMarcada;
    int posicionMapa = 0;
    
    //Creacion temporal de variables para saber si funciona el programa
    Line2D linea1 = new Line2D.Double(207, 207, 393, 393);
    Line2D linea2 = new Line2D.Double(407, 407, 493, 493);
    Line2D linea3 = new Line2D.Double(507, 493, 593, 407);
    Ellipse2D.Double cuire = new Ellipse2D.Double(190, 190, 20, 20);
    Ellipse2D.Double henon = new Ellipse2D.Double(390, 390, 20, 20);
    Ellipse2D.Double charpennes = new Ellipse2D.Double(490, 490, 20, 20);
    Ellipse2D.Double brotteaux = new Ellipse2D.Double(590, 390, 20, 20);
    //static ArrayList<String> lista = new ArrayList<String>();
    
    Thread hilo;
    
    public MapaPanel(){
        this.setPreferredSize(new Dimension(pantallaLargo, pantallaAncho));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        lineaMarcada = new Line2D.Double(200, 200, 400, 400);
        
    }
    
    public void startMapa() {
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        while(!hilo.isInterrupted()) {
            try {
                System.out.println("funciona"); //Comprobacion de un ciclo
                actualizar(); //Actualiza los datos
                repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(MapaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void actualizar() throws InterruptedException{
        //A mano para verificar que pinta el cambio de estaciones bien
        switch (posicionMapa) {
            case 0 -> {
                lineaMarcada = linea1;
                posicionMapa = 1;
                Thread.sleep(1000); //Hago el sleep para todos los casos para ver mejor los cambios
            }
            case 1 -> {
                lineaMarcada = linea2;
                posicionMapa = 2;
                Thread.sleep(1000);
            }
            case 2 -> {
                lineaMarcada = linea3;
                posicionMapa = 3;
                Thread.sleep(1000);
                hilo.interrupt(); //Congela el thread, parando el programa
            }
            default -> {
            }
        }
    }
    
    public void creacionMapa(Graphics2D g) {
        //Funcion que dibuja el mapa
        g.setColor(Color.black); //Color de lo que se quiere dibujar
        g.setStroke(new BasicStroke(3)); //Agranda las lineas que se dibujan
        g.draw(linea1);
        g.draw(linea2);
        g.draw(linea3);
        g.draw(cuire); //Punto de la estacion dibujada
        g.draw(henon);
        g.draw(brotteaux);
        g.draw(charpennes);
        g.drawString("Cuire", 180, 180);
        g.drawString("Henon", 350, 410);
        g.drawString("Charpennes", 470, 530);
        g.drawString("Brotteaux", 575, 385);
    }
    
    /*public void paradaRegistrada() { //Metodo para pintar la proxima parada
    }*/
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //Dibuja el mapa
        creacionMapa(g2); //Igual hay alguna forma para no tener que dibujar de forma constante el mapa desde 0
        g2.setColor(Color.red);
        g2.draw(lineaMarcada); //Marca la ruta en la que se deberia encontrar
        g2.fillOval(195, 195, 10, 10); //Marco la primera parada
        //^^ Hay que crear un metodo para detectar las paradas y pintarlas
    }
    
}
