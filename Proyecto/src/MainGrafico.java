import java.util.ArrayList;
import javax.swing.*;


public class MainGrafico {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mapa");
        
        ArrayList<Integer> lista = new ArrayList();
        /* Ejemplo que toma una ruta:
        ** Croix-Paquet -- > Place Jean Jaures
        */
        lista.add(4); lista.add(5); lista.add(24);
        lista.add(25); lista.add(36); lista.add(10);
        lista.add(11); lista.add(12);
        MapaPanel mapaPanel = new MapaPanel(lista);
        window.add(mapaPanel);
        
        window.pack(); //Junta el JFrame con el JPanel
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

        







