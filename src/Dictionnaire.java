import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe qui modelise un dictionnaire et permet d'effectuer des opérations sur
 * un objet EntreeDictionnaire a l'aide des methodes de classe presentes.
 * @author Mohamed Bendourou, BENM23109703, groupe : 20
 * @version 26 juillet 2022
 */
public class Dictionnaire {

    //Constantes de classes
     private static final Predicate<String> lignesNonVides = s -> !s.isEmpty();
     private static final Predicate<String> lignesSansCommentaires =
            s -> !s.trim().startsWith("#");
     private static final Function<String,String> obtenirMot = s ->
            s.substring(0,s.indexOf('\t'));
     private static final Function<String,String> obtenirCat = s ->
            s.substring(s.indexOf('\t') + 1,s.lastIndexOf('\t'));
     private static final Function<String,String> obtenirDefinition = s ->
            s.substring(s.lastIndexOf('\t') + 1,s.length() - 1);
     private static final Function<String,EntreeDictionnaire> enEntreeDictionnaire
            = e -> new  EntreeDictionnaire(obtenirMot.apply(e),
            obtenirCat.apply(e),obtenirDefinition.apply(e));
     private static final BiPredicate<EntreeDictionnaire,String> entreeSelonCat =
            (EntreeDictionnaire e,String cat) -> e.getCatGramm().
                    equalsIgnoreCase(cat);
     private static final Function<EntreeDictionnaire,String> enNomEtCat =
             e -> (e.getMot() + e.getCatGramm()).toLowerCase();
     private static final BiPredicate<EntreeDictionnaire,String[]>
             entreeSelonPlusieursCat = (EntreeDictionnaire e, String[] t) -> {
         boolean retour = true;
         if (t != null) {
            retour = Arrays.stream(t).map(s -> s.toLowerCase()).collect(Collectors.
                    toList()).contains(e.getCatGramm().toLowerCase());
         }
         return retour;
     };
     private static final BiPredicate<EntreeDictionnaire,String> entreeSelonMot =
             (EntreeDictionnaire e, String mot) -> e.getMot().equalsIgnoreCase(mot);
     private static final Comparator<EntreeDictionnaire> trierSelonDefinition =
             (e1,e2) -> e1.getDefinition().compareToIgnoreCase(e2.getDefinition
                     ());
     private static final BiPredicate<EntreeDictionnaire,String>
             entreeSelonExpressionDef = (EntreeDictionnaire e,String expression)
             -> e.getDefinition().toLowerCase().contains(expression.toLowerCase
             ());
     private static final Comparator<EntreeDictionnaire> trierSelonCat = (e1,e2)
            -> e1.getCatGramm().compareToIgnoreCase(e2.getCatGramm());
     private static final Comparator<EntreeDictionnaire> trierSelonMot = (e1,e2)
            -> e1.getMot().compareToIgnoreCase(e2.getMot());

     private static final BiPredicate<EntreeDictionnaire,String>
            entreeSelonPatron = (EntreeDictionnaire e,String patron) -> {
        boolean correpsond = true;
        int i = 0;
        while (i < patron.length() && correpsond) {
            correpsond = patron.length() == e.getMot().length() &&
                    (patron.charAt(i) == '.' || e.getMot().toLowerCase().charAt
                            (i) == patron.toLowerCase().charAt(i));
            i++;
        }
        return correpsond;
     };
     private static final BiFunction<String,Character,Integer> trouverFrequence =
            (String def,Character c) -> {
                int i = 0;
                int j = 0;
                while (j < def.length()) {
                    if (def.charAt(j++) == c) {
                        i++;
                    }
                }
                return i;
            };
     private static final Comparator<String> trierSelonLongueur = (s1,s2) ->
             Integer.compare(s1.length(),s2.length());


    //Attribut d'instance
    private List<EntreeDictionnaire> dictionnaire; //A mettre private

    //Constructeur
    /**
     * Ce constructeur lit chacune des entrées de dictionnaire contenues dans le
     * fichier donné en paramètre et construit la liste des entrées de
     * dictionnaires qui est affectée à l'attribut d'instance dictionnaire.
     *
     * @param cheminFic Le chemin du fichier qui contient la liste des entrées
     * de dictionnaire que contiendra ce dictionnaire.
     */
    public Dictionnaire(String cheminFic) {
        dictionnaire = new ArrayList<>();
        try {
            Path path = Paths.get(cheminFic);
            Stream<String> strEntreeDic = Files.lines(path);
            strEntreeDic.distinct().filter(lignesNonVides.and
                            (lignesSansCommentaires)).map(enEntreeDictionnaire).
                    forEach(e -> dictionnaire.add(e));
        } catch (IOException ignored) { }
    }

    /**
     * Cette méthode retourne le nombre total d’entrées dans ce dictionnaire.
     * @return le nombre total d’entrées dans ce dictionnaire.
     */
    public long nombreEntreesDictionnaire () {
        return dictionnaire.size();
    }

    /**
     * Cette méthode retourne le nombre de mots distincts (sans tenir compte de
     * la casse) de la catégorie grammaticale categorieGramm donnée
     * (sans tenir compte de la casse), parmi toutes les entrées de ce
     * dictionnaire.
     * @param categorieGramm Catégorie grammaticale des mots recherchés.
     * @return le nombre de mots distincts e la catégorie grammaticale
     * categorieGramm donnée en parametre.
     */
    public long nombreDeMots (String categorieGramm) {
        return dictionnaire.stream().filter(e -> entreeSelonCat.test
                        (e,categorieGramm)).map(enNomEtCat).distinct().count();
    }


    /**
     * Cette méthode retourne une liste contenant toutes les entrées de dictionnaire
     * définissant le mot donné, et dont la catégorie grammaticale se trouve dans
     * le tableau categoriesGramm donné. Si aucune entrée de dictionnaire n’est
     * trouvée, cette méthode retourne une liste vide.
     * @param mot Les entrées de dictionnaire recherchées doivent définir ce mot (en plus
     * d’avoir une catégorie grammaticale, qui se trouve dans categoriesGramm)
     * @param categoriesGramm Liste des catégories grammaticales des entrées de
     * dictionnaires recherchées
     * @return une liste contenant toutes les entrées de dictionnaire définissant
     * le mot donné, et dont la catégorie grammaticale se trouve dans le tableau
     * categoriesGramm donné
     * @throws NullPointerException si le paramètre mot est null.
     */
    public List<EntreeDictionnaire> obtenirEntreesDictionnaire (String mot,
                         String[] categoriesGramm) throws NullPointerException {
        if (mot == null) {
            throw new NullPointerException();
        }
        return dictionnaire.stream().filter(e -> entreeSelonPlusieursCat.test
                (e,categoriesGramm)).filter(e -> entreeSelonMot.test(e,mot)).
                sorted(trierSelonDefinition.thenComparing(trierSelonCat)).
                collect(Collectors.toList());
    }

    /**
     * Cette méthode retourne une liste contenant toutes les entrées de dictionnaire
     * dont la définition contient l’expression donnée (sans tenir compte de la
     * casse).
     * @param expression Les entrées de dictionnaire recherchées doivent contenir
     * cette expression dans leur définition.
     * @return une liste contenant toutes les entrées de dictionnaire dont la
     * définition contient l’expression donnée.
     * @throws NullPointerException si le paramètre expression est null.
     */
    public List<EntreeDictionnaire> obtenirEntreesDictionnaire
            (String expression) throws NullPointerException {
        return dictionnaire.stream().filter(e -> entreeSelonExpressionDef.test
                (e,expression)).sorted(trierSelonMot.thenComparing
                (trierSelonDefinition.thenComparing(trierSelonCat))).
                collect(Collectors.toList());
    }

    /**
     * Cette méthode retourne un tableau de longueur minimale n contenant les n
     * mots distincts (sans doublons) qui correspondent (sans tenir compte de
     * la casse) au patron donné en paramètre, parmi les mots de toutes les
     * entrées de ce dictionnaire.
     * @param patron Le patron auquel doivent correspondre les mots retournés.
     * @return un tableau de String.
     */
    public String[] trouverMotsCorrespondants (String patron) {
        return dictionnaire.stream().filter(e -> entreeSelonPatron.test
                (e,patron)).map(e -> e.getMot().toLowerCase()).distinct().
                sorted().toArray(String[]::new);
    }

    /**
     * Cette méthode retourne un tableau de longueur minimale contenant les
     * nbrMots mots distincts (sans tenir compte de la casse) les plus longs ou
     * les plus courts (selon leur nombre de caractères), parmi les mots de
     * toutes les entrées de ce dictionnaire.
     * @param nbrMots Le nombre maximum de mots retournés.
     * @return un tableau de String.
     */
    public String[] trouverMotsParLongueur (int nbrMots) {
        List<String> listeMots = dictionnaire.stream().map(e -> e.getMot().
                        toLowerCase()).distinct().sorted(trierSelonLongueur).
                collect(Collectors.toList());
         if (nbrMots > 0 && nbrMots <= listeMots.size()) {
            listeMots = listeMots.stream().limit(nbrMots).collect
                    (Collectors.toList());
        } else if (nbrMots <= 0) {
             listeMots = listeMots.stream().sorted(Comparator.reverseOrder()).
                     limit(Math.abs(nbrMots)).collect(Collectors.toList());         }
        return listeMots.toArray(new String[0]);
    }

    /**
     * Cette méthode retourne la moyenne de la longueur des mots distincts
     * (sans tenir compte de la casse) de toutes les entrées de ce dictionnaire.
     * @return la moyenne de la longueur des mots.
     */
    public double moyenneLongueurMots () {
        double moyenne;
        if (!dictionnaire.isEmpty()) {
            moyenne = dictionnaire.stream().map(e -> e.getMot().toLowerCase()).
                    distinct().mapToInt(s -> s.length()).average().getAsDouble();
        } else
            moyenne = 0;
        return moyenne;
    }

    /**
     * Cette méthode retourne la fréquence (en pourcentage) du caractère donné en
     * paramètre (sans tenir compte de la casse) dans le corpus formé par les
     * définitions de toutes les entrées de ce dictionnaire. Cette méthode
     * n’élimine pas les doublons de définition, s’il y en a.
     * @param c Le caractère dont on veut la fréquence sur le corpus formé par
     * les définitions de toutes les entrées de ce dictionnaire.
     * @return la fréquence (en pourcentage) du caractère donné en
     *  paramètre.
     */
    public double frequence (char c) {
        List<String> listeDefinitions = dictionnaire.stream().map
                (e -> e.getDefinition().toLowerCase()).collect(Collectors.
                toList());
        double nombreTotalLettre = listeDefinitions.stream().mapToDouble
                (s -> s.length()).sum();
        double nombreOccurences = listeDefinitions.stream().mapToDouble
                (s -> trouverFrequence.apply(s,c)).sum();
        return nombreOccurences / nombreTotalLettre * 100;
    }
}
