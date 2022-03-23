package jeu;

import java.util.ArrayList;

/** Type representant une serie */
public class Série {
	// nombre de cartes maximale dans une serie
    private final int MAX_CARTES = 5;
    // numero de serie disponible
    private static int numSerie=1;
    // numero de la serie
    private int numéro;
    //liste de cartes dans la serie 
    private ArrayList <Carte> serie ;
    
    public Série(Carte c){
    	serie = new ArrayList<> ();
    	serie.add(c);
        numéro=numSerie++;
    }
    
    /**
     * Ajoute une carte a la serie
     * @param la carte a ajouter
    */
    public void ajouter(Carte c){
        assert(peutEtrePosee(c));
    	serie.add(c);
    }
    
    /**
     * Retourne la derniere carte de la serie
     * @return la carte
    */
    public Carte getDerniereCarte(){
        return serie.get(serie.size()-1);
    }
    
    /**
     * Verifie si la serie est pleine
     * @return vrai si la serie est pleine
    */
    public boolean estPleine(){
        return (serie.size() == MAX_CARTES);
    }
    
    /**
     * Verifie si la carte peut etre posee dans la serie
     * @param la carte
     * @return vrai si la carte peut etre posee
    */
    public boolean peutEtrePosee(final Carte c) {
    	return getDerniereCarte().estPlusPetit(c);
    }
    
    /**
     * Verifie si la serie contient la carte
     * @param la carte
     * @return vrai si la serie contient la carte
    */
    public boolean contient(final Carte c) {
    	return serie.contains(c);
    }
    
    /**
     * Remplace la serie par une carte et retourne les cartes de l'ancienne serie
     * @return la liste de cartes
    */
    public ArrayList <Carte> remplacer(final Carte c){
    	ArrayList <Carte> copie = new ArrayList <>(serie);
    	serie.clear();
    	serie.add(c);
    	return copie;
    }

    /**
     * Retourne le numero de la serie ainsi que les cartes qu'elle contient
     * @return la chaine de caracteres
    */
    public String toString(){
        StringBuilder s=new StringBuilder("- série n° ");
        s.append((numéro + " : " + serie.get(0)));
        if (serie.size()>1) 
            for (int i= 1;i< serie.size();++i) s.append(", " + serie.get(i));
        s.append("\n");
        return s.toString();        
    }
}
