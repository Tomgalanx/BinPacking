import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                    System.out.println("Génération de 125 objets et de l'arbre correspondant");
                    arbre =FabriqueArbreConflit.fabrique125();

                    break;

                case 250:
                    System.out.println("Génération de 250 objets et de l'arbre correspondant");
                    arbre =FabriqueArbreConflit.fabrique250();

                    break;

                case 500:
                    arbre =FabriqueArbreConflit.fabrique500();
                    System.out.println("Génération de 500 objets et de l'arbre correspondant");
                    break;

                case 1000:
                    System.out.println("Génération de 1000 objets et de l'arbre correspondant");
                    arbre =FabriqueArbreConflit.fabrique1000();

                    break;

                default:
                    System.out.println("Génération de 125 objets et de l'arbre correspondant");
                    arbre =FabriqueArbreConflit.fabrique125();

                    nb=125;
                    break;
            }

            //System.out.println(arbre);
            System.out.println();

            BinPacking binPacking = new BinPacking(150);
            ArrayList<Objet> objets=genereationObjets(nb);

            int fract=binPacking.FractionalPacking(objets);
            System.out.println("Résultat pour FractionalPacking : "+fract);
            System.out.println();
            int first =binPacking.FirstFitDecreasing(objets,arbre);
            int best=binPacking.BestFitDecreasingPacking(objets,arbre);
            System.out.println("Résultat pour FirstFitDecreasingPacking : "+first);
            System.out.println("Résultat pour BestFitDecreasingPacking : "+best);
            System.out.println();

            ArrayList<Objet> dsat=binPacking.Dsatur(arbre,objets);
            int ffd=binPacking.DsaturWithFFDpacking(dsat,arbre);
            int bfd=binPacking.DsaturWithBFDpacking(dsat,arbre);
            System.out.println("Résultat pour DsaturWithFFDpacking : "+ffd);
            System.out.println("Résultat pour DsaturWithBFDpacking : "+bfd);

            // Create Chart
            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Graphique").xAxisTitle("Nom des algorithmes").yAxisTitle("Résultat").build();

            // Customize Chart
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            chart.getStyler().setHasAnnotations(true);

            // Series
            chart.addSeries("Résultat", Arrays.asList(new String[] { "Fact", "FirstFit", "BestFit","FFDpacking", "BFDpacking" }), Arrays.asList(new Integer[] { fract, first, best, ffd, bfd }));


            new SwingWrapper<CategoryChart>(chart).displayChart();


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
