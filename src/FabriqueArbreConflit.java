import java.io.*;
import java.util.Scanner;

public class FabriqueArbreConflit {


    public static ArbreConflit fabrique125() throws IOException {

        ArbreConflit arbreConflit =new ArbreConflit();

        File file = new File("ressources/DSJC125.5.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String ligne;
        Scanner sc;
        while ((ligne=reader.readLine()) != null){
            if(ligne.charAt(0) == 'e'){
                sc=new Scanner(ligne).useDelimiter("[^\\d]+");
                int key = sc.nextInt();
                int value = sc.nextInt();

                arbreConflit.ajouterConflit(key,value);
            }

        }


        return arbreConflit;

    }
    public static ArbreConflit fabrique250() throws IOException {

        ArbreConflit arbreConflit =new ArbreConflit();

        File file = new File("ressources/DSJC250.5.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String ligne;
        Scanner sc;
        while ((ligne=reader.readLine()) != null){
            if(ligne.charAt(0) == 'e'){
                sc=new Scanner(ligne).useDelimiter("[^\\d]+");
                int key = sc.nextInt();
                int value = sc.nextInt();

                arbreConflit.ajouterConflit(key,value);
            }

        }


        return arbreConflit;

    }

    public static ArbreConflit fabrique500() throws IOException {

        ArbreConflit arbreConflit =new ArbreConflit();

        File file = new File("ressources/DSJC500.5.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String ligne;
        Scanner sc;
        while ((ligne=reader.readLine()) != null){
            if(ligne.charAt(0) == 'e'){
                sc=new Scanner(ligne).useDelimiter("[^\\d]+");
                int key = sc.nextInt();
                int value = sc.nextInt();

                arbreConflit.ajouterConflit(key,value);
            }

        }


        return arbreConflit;

    }

    public static ArbreConflit fabrique1000() throws IOException {

        ArbreConflit arbreConflit =new ArbreConflit();

        File file = new File("ressources/DSJC1000.5.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String ligne;
        Scanner sc;
        while ((ligne=reader.readLine()) != null){
            if(ligne.charAt(0) == 'e'){
                sc=new Scanner(ligne).useDelimiter("[^\\d]+");
                int key = sc.nextInt();
                int value = sc.nextInt();

                arbreConflit.ajouterConflit(key,value);
            }

        }


        return arbreConflit;

    }
}
