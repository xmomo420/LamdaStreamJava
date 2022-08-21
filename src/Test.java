import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        /*
        //endIndex : n'inclut pas le char
        Path path = Paths.get(".idea/echantillon.txt");
        Stream<String> strEntreeDic = Files.lines(path);
        Dictionnaire d = new Dictionnaire(".idea/echantillon.txt");
        List<String> l = new ArrayList<>();
        for (EntreeDictionnaire e : d.dictionnaire) {
            l.add(e.getDefinition());
        }

        System.out.println(d.nombreDeMots("n."));
        System.out.println(d.nombreEntreesDictionnaire());
        System.out.println(d.frequence('f'));
        System.out.println(d.moyenneLongueurMots());
        System.out.println(d.obtenirEntreesDictionnaire("ce"));
        System.out.println(Arrays.toString(d.trouverMotsCorrespondants(".....")));
        System.out.println(Arrays.toString(d.trouverMotsParLongueur(3)));



        /*
        System.out.println(d.nombreEntreesDictionnaire());
        System.out.println(d.nombreDeMots("n."));
        System.out.println(d.nombreDeMots("adj."));
        System.out.println(d.nombreDeMots("v."));

         */


    }


}
