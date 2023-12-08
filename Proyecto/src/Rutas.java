package practicaia;

import java.io.IOException;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

public class Rutas {
    private ArrayList<Ruta> rutas;   
    private Vertices vertices; 

    public Rutas(Vertices vertices){
        this.vertices = vertices;
        this.rutas = loadRutasCSV("data/rutas.utf8");
    }
    public void print(){
        this.rutas.forEach(System.out::println);
    }
    public ArrayList<Ruta> loadRutasCSV(String fileName){
        Path path = Path.of(fileName);
        ArrayList<Ruta> rutas= new ArrayList<>();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            rutas = lines.skip(1).map(line ->   {
                                String[] arr = line.split(";");
                                return new Ruta(
                                        arr[0], 
                                        arr[0], 
                                        arr[1],
                                        arr[2],
                                        new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(arr, 3, arr.length))),
                                        this.vertices
                                        );
                                })
                                .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rutas;
    }

    public Ruta buscarPorId(String argId){
        System.out.println(argId);
        this.rutas.forEach(r -> System.out.println(r.getId()));
        return this.rutas.stream()
                    .filter(r -> r.getId().equals(argId))
                    .findAny()
                    .orElse(null);
    }

    public void pintaRutasSVG(SvgMap mapa){
        this.rutas.forEach(r -> r.pintaRutaSVG(mapa));
    }
}
