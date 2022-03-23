package jeu;

/** Type correspondant a une carte du jeu*/
public class Carte {
    // premier numero de carte possible
    private static int numCarteDisponible=1;
    // numero de la carte
    private int numéro;
    // penalite de la carte
    private int pénalité;
    
    public Carte() {
    	numéro=numCarteDisponible++;
    	setPénalité();
    }
    
    // utilise uniquement pour les tests
    public Carte(int numCarte){
        numéro=numCarte;
        numCarteDisponible=numCarte+1;
        setPénalité();
    }

    /**
     * Initialise la penalite de la carte en fonction du numéro
    */
    private void setPénalité(){
        if (numéro%5==0 && !(numéro%10 == 0)) pénalité+= 2; 
        if (numéro%10 == 0) pénalité+= 3;
        if (numéro%11==0) pénalité+= 5;
        if (pénalité==0) pénalité= 1;
    }
    
    /**
     * Retourne le numéro de la carte
     * @return le numéro de la carte
    */
    public int getNuméro(){
        return numéro;
    }
    
    /**
     * Retourne les pénalités de la carte
     * @return les pénalités de la carte
    */
    public int getPénalité(){
        return pénalité;
    }
    
    /**
     * Retourne la difference entre deux cartes
     * @return la difference
    */
    public int difference(final Carte c){
        return numéro-c.numéro;
    }
    
    /**
     * Verifie que la carte passée en parametre est plus grande
     * @return vrai si la carte passée en parametre est plus grande
    */
    public boolean estPlusPetit(final Carte carte2){
        return (numéro < carte2.numéro);
    }
    
    /**
     * Verifie que la carte porte le numéro passé en parametre
     * @return vrai si la carte a pour numéro le nombre passé en parametre
    */
    public boolean correspond(final int i) {
    	return numéro==i; 
    }
    
    /**
     * Retourne la chaine de caracteres representant la carte
     * @return le numéro de la carte et sa pénalité
    */
    public String toString(){
        if (pénalité== 1) return Integer.toString(numéro);
        else return numéro + " (" + pénalité+ ")";
    }

}

