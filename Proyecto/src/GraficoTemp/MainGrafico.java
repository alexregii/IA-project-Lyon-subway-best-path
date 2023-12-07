import javax.swing.*;


public class MainGrafico {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mapa");
        
        MapaPanel mapaPanel = new MapaPanel();
        window.add(mapaPanel);
        
        window.pack(); //Junta el JFrame con el JPanel
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        mapaPanel.startMapa(); //Inicia el thread de MapaPanel
    }
}
