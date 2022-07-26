
import java.util.Objects;
import java.util.Arrays;

/**
 * Cette classe modelise une entree de dictionnaire qui contient le mot defini, 
 * sa categorie grammaticale, et sa definition.
 * INF2120-20 E22 : classe fournie dans le cadre du TP3.
 * @author melanie lord
 * @version Ete 2022
 */
public class EntreeDictionnaire {
   
   //---------------------------
   // CONSTANTES DE CLASSE
   //---------------------------
   
   //Categories grammaticales valides
   public static final String CAT_GRAMM_NOM = "n.";
   public static final String CAT_GRAMM_VERBE = "v.";
   public static final String CAT_GRAMM_ADJECTIF = "adj.";
   public static final String CAT_GRAMM_ADVERBE = "adv.";
   
   //Tableau des categories grammaticales valides.
   public static final String [] CATEGORIES_GRAMM = {
      CAT_GRAMM_NOM, CAT_GRAMM_VERBE, CAT_GRAMM_ADJECTIF, CAT_GRAMM_ADVERBE};
   
   //---------------------------
   // ATTRIBUTS D'INSTANCE
   //---------------------------
   private String mot;        //le mot defini
   private String catGramm;   //la cat. grammaticale du mot
   private String definition; //la definition du mot
   
   //---------------------
   // CONSTRUCTEUR
   //---------------------
   
   /**
    * Construit une EntreeDictionnaire dont le mot defini, la categorie 
    * grammaticale, et la definition sont initialise(e)s avec les valeurs 
    * donnees en parametres.
    * @param mot le mot defini dans cette entree
    * @param catGramm la categorie grammaticale du mot defini
    * @param definition la definition du mot defini.
    */
   public EntreeDictionnaire (String mot, String catGramm, String definition) {
      if (mot == null || catGramm == null || definition == null) {
         throw new NullPointerException();
      }
      
      this.mot = mot;
      this.catGramm = catGramm;
      this.definition = definition;
   }
   
   //---------------------
   // METHODES D'INSTANCE
   //---------------------

   /**
    * Permet d'obtenir le mot defini de cette entree de dictionnaire.
    * @return le mot defini de cette entree de dictionnaire
    */
   public String getMot() {
      return mot;
   }

   /**
    * Permet de modifier le mot defini dans cette entree de dictionnaire.
    * @param mot le nouveau mot a assigner a cette entree de dictionnaire
    */
   public void setMot(String mot) {
      this.mot = mot;
   }

   /**
    * Permet d'obtenir la categorie grammaticale du mot defini dans cette entree 
    * de dictionnaire.
    * @return la categorie grammaticale du mot defini dans cette entree 
    *         de dictionnaire
    */
   public String getCatGramm() {
      return catGramm;
   }

   /**
    * Permet de modifier la categorie grammaticale du mot defini dans cette 
    * entree de dictionnaire.
    * @param catGramm la nouvelle categorie grammaticale a assigner a cette 
    *        entree de dictionnaire
    */
   public void setCatGramm(String catGramm) {
      this.catGramm = catGramm;
   }

   /**
    * Permet d'obtenir la definition du mot defini dans cette entree de 
    * dictionnaire.
    * @return la definition du mot defini dans cette entree de dictionnaire
    */
   public String getDefinition() {
      return definition;
   }

   /**
    * Permet de modifier la definition du mot defini dans cette entree de 
    * dictionnaire.
    * @param definition la nouvelle definition a assigner a cette entree de 
    *        dictionnaire
    */
   public void setDefinition(String definition) {
      this.definition = definition;
   }

   /**
    * Retourne une representation sous forme de chaine de caracteres de cette
    * entree de dictionnaire sour le format : mot|catGramm|definition.
    * @return une representation sous forme de chaine de caracteres de cette
    *         entree de dictionnaire
    */
   public String toString() {
      return mot + "|" + catGramm + "|" + definition;
   }
   
   /**
    * Deux entrees de dictionnaires sont consideres egales si leurs attributs 
    * mot (sans tenir compte de la casse), 
    * categorie grammaticale (sans tenir compte de la casse), et 
    * definition (sans tenir compte de la casse) sont egaux.
    * Si obj est null, cette methode retourne false.
    * 
    * @param obj l'entree de dictionnaire avec laquelle on veut comparer cette 
    *            entree de dictionnaire.
    * @return true si cette entree de dictionnaire est egale a obj, false sinon.
    */
   @Override
   public boolean equals(Object obj) {
      EntreeDictionnaire autreEntreeDict;
      boolean egaux = this == obj;
      
      if (!egaux) {
         if (obj != null && getClass() == obj.getClass()) {
            autreEntreeDict = (EntreeDictionnaire)obj;
         
            egaux = Objects.equals(this.mot.toLowerCase(), 
                     autreEntreeDict.mot.toLowerCase())
                  && Objects.equals(this.catGramm.toLowerCase(), 
                     autreEntreeDict.catGramm.toLowerCase())
                  && Objects.equals(this.definition.toLowerCase(), 
                     autreEntreeDict.definition.toLowerCase());
         }
      }
      return egaux;
   }
   
   /**
    * Construit et retourne un hashcode pour cette entree de dictionnaire.  
    * Si deux entrees de dictionnaire e1 et e2 sont egales 
    * (e1.equals(e2) retourne true), cette methode retourne le meme hashcode 
    * pour e1 et e2.
    * 
    * Note : 
    *    Cette methode est necessaire pour que l'utilisation de la methode 
    *    Stream.distinct() sur un stream d'entrees de dictionnaire fonctionne.
    * 
    * @return un hashcode pour cette entree de dictionnaire. 
    */
   @Override
   public int hashCode() {
      int hash = 17;
      hash = 31 * hash + Objects.hashCode(this.mot.toLowerCase());
      hash = 31 * hash + Objects.hashCode(this.catGramm.toLowerCase());
      hash = 31 * hash + Objects.hashCode(this.definition.toLowerCase());
         
      return hash;
   }
   
   //---------------------
   // METHODE DE CLASSE
   //---------------------
   
   /** 
    * Teste si catGramm est une categorie valide, c.-a-d. qu'elle se trouve 
    * (peu importe la casse) dans le tableau CATEGORIES_GRAMM.
    * @param catGramm la categorie grammaticale dont on veut verifier la validite.
    * @return true si catGramm est valide, false sinon.
    */
   public static boolean estCatGrammValide(String catGramm) {
      return Arrays.asList(CATEGORIES_GRAMM).contains(catGramm.toLowerCase());
   }

}
