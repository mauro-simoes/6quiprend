package jeu;

/** Type correspondant a une carte du jeu*/
public class Carte {
    // premier numero de carte possible
    private static int numCarteDisponible=1;
    // numero de la carte
    private int num�ro;
    // penalite de la carte
    private int p�nalit�;
    
    public Carte() {
    	num�ro=numCarteDisponible++;
    	setP�nalit�();
    }
    
    // utilise uniquement pour les tests
    public Carte(int numCarte){
        num�ro=numCarte;
        numCarteDisponible=numCarte+1;
        setP�nalit�();
    }

    /**
     * Initialise la penalite de la carte en fonction du num�ro
    */
    private void setP�nalit�(){
        if (num�ro%5==0 && !(num�ro%10 == 0)) p�nalit�+= 2; 
        if (num�ro%10 == 0) p�nalit�+= 3;
        if (num�ro%11==0) p�nalit�+= 5;
        if (p�nalit�==0) p�nalit�= 1;
    }
    
    /**
     * Retourne le num�ro de la carte
     * @return le num�ro de la carte
    */
    public int getNum�ro(){
        return num�ro;
    }
    
    /**
     * Retourne les p�nalit�s de la carte
     * @return les p�nalit�s de la carte
    */
    public int getP�nalit�(){
        return p�nalit�;
    }
    
    /**
     * Retourne la difference entre deux cartes
     * @return la difference
    */
    public int difference(final Carte c){
        return num�ro-c.num�ro;
    }
    
    /**
     * Verifie que la carte pass�e en parametre est plus grande
     * @return vrai si la carte pass�e en parametre est plus grande
    */
    public boolean estPlusPetit(final Carte carte2){
        return (num�ro < carte2.num�ro);
    }
    
    /**
     * Verifie que la carte porte le num�ro pass� en parametre
     * @return vrai si la carte a pour num�ro le nombre pass� en parametre
    */
    public boolean correspond(final int i) {
    	return num�ro==i; 
    }
    
    /**
     * Retourne la chaine de caracteres representant la carte
     * @return le num�ro de la carte et sa p�nalit�
    */
    public String toString(){
        if (p�nalit�== 1) return Integer.toString(num�ro);
        else return num�ro + " (" + p�nalit�+ ")";
    }

}

