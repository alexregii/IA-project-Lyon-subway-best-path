package practicaia;

import java.io.IOException;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Vertices {
    private ArrayList<Vertice> vertices;

   public static void main(String[] args) throws IOException {
        Vertices vTest = new Vertices();
        Rutas rTest = new Rutas(vTest);

        vTest.print();
        System.out.println("");
        rTest.print();
        System.out.println("");

        System.out.println("Look for nº 3 :" + vTest.buscarPorId(3).toString());
        System.out.println("Look for nº 31 :" + vTest.buscarPorId(31).toString());

        SvgMap mapa = new SvgMap(0,1,20,"Mapa Metro Lyon");
        //vTest.buscarPorId(4).pintaVerticeSVG(mapa);
        //rTest.buscarPorId("A").pintaRutaSVG(mapa);
        rTest.pintaRutasSVG(mapa);
        vTest.pintaVerticesSVG(mapa);
        mapa.cerrarSvg();
        mapa.print();
        mapa.createFile();
    }

    public Vertices(){
        this.vertices = loadVerticesCSV("data/estaciones.utf8");
    }
    public void print(){
        this.vertices.forEach(System.out::println);
    }
    public static ArrayList<Vertice> loadVerticesCSV(String fileName){
        Path path = Path.of(fileName);
        ArrayList<Vertice> vertexes= new ArrayList<Vertice>();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            vertexes = lines.skip(1).map(line ->   {
                                String[] arr = line.split(";");
                                return new Vertice(
                                        Integer.parseInt(arr[0]), 
                                        arr[1], 
                                        arr[2], 
                                        Integer.parseInt(arr[6]),
                                        Integer.parseInt(arr[7]));
                                })
                                .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return vertexes;
    }

    public Vertice buscarPorId(Integer argId){
        return this.vertices.stream()
                    .filter(v -> v.getId() == argId)
                    .findAny()
                    .orElse(null);
    }

    public void pintaVerticesSVG(SvgMap mapa){
        this.vertices.forEach(v -> v.pintaVerticeSVG(mapa));
    }
}
