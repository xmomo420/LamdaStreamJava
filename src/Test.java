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
        //endIndex : n'inclut pas le char
        Path path = Paths.get(".idea/echantillon.txt");
        Stream<String> strEntreeDic = Files.lines(path);
        Dictionnaire d = new Dictionnaire(".idea/echantillon.txt");
        List<String> l = new ArrayList<>();
        for (EntreeDictionnaire e : d.dictionnaire) {
            l.add(e.getDefinition());
        }
        System.out.println(l.size());
        System.out.println(d.nombreDeMots("n."));
        System.out.println(d.nombreDeMots("v."));
        System.out.println(d.nombreDeMots("adj."));
        System.out.println(d.nombreDeMots("adv."));

        d.test("n.");

        /*
        System.out.println(d.nombreEntreesDictionnaire());
        System.out.println(d.nombreDeMots("n."));
        System.out.println(d.nombreDeMots("adj."));
        System.out.println(d.nombreDeMots("v."));

         */

    }


}
