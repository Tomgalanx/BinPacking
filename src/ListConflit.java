import java.util.ArrayList;

// Comparable pour trier par degres
public class ListConflit implements Comparable<ListConflit> {

    private ArrayList<Integer> conflits;

    public ListConflit() {
        this.conflits = new ArrayList<>();
    }


    public ArrayList<Integer> getList() {
        return conflits;
    }

    public void ajoute(int value) {
        conflits.add(value);
    }

    // retourne le degres le plus grand
    @Override
    public int compareTo(ListConflit listConflit) {
        return  listConflit.conflits.size()-this.conflits.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListConflit that = (ListConflit) o;

        return conflits != null ? conflits.equals(that.conflits) : that.conflits == null;
    }

    @Override
    public int hashCode() {
        return conflits != null ? conflits.hashCode() : 0;
    }

    public String toString(){

        return conflits.size()+"";
    }
}
