

// Comparable pour pouvoir trier les objets par hauteur
public class Objet implements Comparable<Objet> {


    private int index,hauteur;
    private int color;


    public Objet(int index, int hauteur) {
        this.index = index;
        this.hauteur = hauteur;
        this.color=0;
    }

    public Objet(int i) {
        this.index=i;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getIndex() {
        return index;
    }


    // Utilise pour trier une liste par hauteur des objets
    @Override
    public int compareTo(Objet objet) {
        return this.getHauteur()-objet.getHauteur();
    }


    public void colorier(int i) {
        this.color=i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Objet objet = (Objet) o;

        return index == objet.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    public int getColor() {
        return color;
    }
}
