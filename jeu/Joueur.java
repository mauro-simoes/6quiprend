package jeu;

import java.util.ArrayList;

/** Type de donnee representant un joueur*/
public class Joueur {
	private final int MAX_CARTES=10;
	/** nom du joueur */
    private String nom;
    /** penalites ramasses par le joueur */
    private int pénalités;
    /** nombre de tetes de boeufs ramassés par tour */
    private int nbTetes;
    /** les cartes que le joueur possede */
    private ArrayList<Carte> cartesJ;
    
    public Joueur(String nomJoueur){
        nom=nomJoueur;
        cartesJ=new ArrayList<Carte>(10);
        pénalités=0;
        nbTetes=0;
    }
    
    
    /**
     * Permet au joueur de prendre une carte lors de la distribution des cartes
     * @param la carte
    */
    public void prendre(final Carte c){
    	assert(cartesJ.size()<MAX_CARTES);
        cartesJ.add(c);
    }
    
    /**
     * Ajoute au joueur les penalites de la carte qu'il ramasse 
     * @param la carte
    */
    public void ramasser(final Carte c){
        pénalités+=c.getPénalité();
        nbTetes+=c.getPénalité();
    }
    
    /**
     * Retourne le cumul des penalites du joueur
     * @return la totalite des penalites du joueur
    */
    public int getPénalités(){
        return pénalités;
    }
    
    /**
     * Retourne le nombre de tetes de boeufs ramassé pendant le tour actuel
     * @return le nombre de tetes
    */
    public int getNbTetes(){
        return nbTetes;
    }
    
    
    /**
     * Pose la carte correspondant au numéro
     * @param le numero de la carte
     * @return la carte correspondante
    */
    public Carte poser(int nombre){
        assert(possede(nombre));
    	Carte cte = new Carte();
        for (Carte c: cartesJ) {
            if (c.correspond(nombre)){
                cte=c;
                cartesJ.remove(c);
                break;
            }
        } 
        return cte;
    }
    
    /**
     * Verifie si le joueur possede la carte
     * @param la numero de la carte
     * @return vrai si le joueur possede la carte
    */
    public boolean possede(final int nombre){
        boolean contient=false;
        for (Carte c: cartesJ) 
            if (c.correspond(nombre)) contient = true;    
        return contient;
    }
    
    /**
     * Remets le nombre de tetes de boeufs ramassés a 0
    */
    public void reinitialiserPoints() {
    	nbTetes=0;
    }
   
    /**
     * Retourne le nom joueur
     * @return nom du joueur
    */
    public String toString(){
    	return nom;
    }
    
    /**
     * Retourne le deck de cartes du joueur
     * @return le deck de cartes du joueur
    */
    public String afficherCartes() {
    	String s="- Vos cartes : " + cartesJ.get(0);
        if (cartesJ.size()>1) 
            for (int i= 1;i< cartesJ.size();++i) s+= ", " + cartesJ.get(i);
        return s; 
    }
    

}

