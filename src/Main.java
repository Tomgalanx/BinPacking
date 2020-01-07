import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Nombres d'objets à générer 125, 250, 500, 1000 ?");
            Scanner sc = new Scanner(System.in);
            int nb = sc.nextInt();

            ArbreConflit arbre;
            switch (nb){

                case 125:
                    arbre =FabriqueArbreConflit.fabrique125();
                    System.out.println("Génération de 125 objets et de l'arbre correspondant");
                    break;

                case 250:
                    arbre =FabriqueArbreConflit.fabrique250();
                    System.out.println("Génération de 250 objets et de l'arbre correspondant");
                    break;

                case 500:
                    arbre =FabriqueArbreConflit.fabrique500();
                    System.out.println("Génération de 500 objets et de l'arbre correspondant");
                    break;

                case 1000:
                    arbre =FabriqueArbreConflit.fabrique1000();
                    System.out.println("Génération de 1000 objets et de l'arbre correspondant");
                    break;

                default:
                    arbre =FabriqueArbreConflit.fabrique125();
                    System.out.println("Génération de 125 objets et de l'arbre correspondant");
                    nb=125;
                    break;
            }

            //System.out.println(arbre);

            BinPacking binPacking = new BinPacking(150);
            ArrayList<Objet> objets=genereationObjets(nb);

            System.out.println("Résultat pour FractionalPacking : "+binPacking.FractionalPacking(objets));
            System.out.println();
            System.out.println("Résultat pour FirstFitDecreasingPacking : "+binPacking.FirstFitDecreasing(objets,arbre));
            System.out.println("Résultat pour BestFitDecreasingPacking : "+binPacking.BestFitDecreasingPacking(objets,arbre));
            System.out.println();

            ArrayList<Objet> dsat=binPacking.Dsatur(arbre,objets);
            System.out.println("Résultat pour DsaturWithFFDpacking : "+binPacking.DsaturWithFFDpacking(dsat,arbre));
            System.out.println("Résultat pour DsaturWithBFDpacking : "+binPacking.DsaturWithBFDpacking(dsat,arbre));




        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Objet> genereationObjets(int nbObjets){


        Random r = new Random();
        int low = 10;
        int high = 51;
        int result;

        ArrayList<Objet> res=new ArrayList<>();
        for(int i=1;i<=nbObjets;i++) {
            result = r.nextInt(high - low) + low;
            res.add(new Objet(i,result));

        }

        return res;
    }
}
