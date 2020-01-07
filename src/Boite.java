import java.util.ArrayList;

public class Boite {


    private int capacite,taille;
    private ArrayList<Objet> liste;

    public Boite(int capacite) {
        this.capacite = capacite;
        this.taille = 0;
        this.liste = new ArrayList<>();
    }


    public void ajouterObjet(Objet objet){
        liste.add(objet);
        taille = taille+objet.getHauteur();

    }

    public int getTailleDispo() {
        return capacite-taille;
    }

    public ArrayList<Objet> getListe() {
        return liste;
    }
}
