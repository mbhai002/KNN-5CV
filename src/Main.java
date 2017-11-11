import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Hello World!");


        Scanner sc = new Scanner(System.in);
        String x="";


        File fileName= new File("adult.train.5fold.csv");
        Scanner scan = new Scanner( fileName ); // scanner for file dictionary
        Scanner replace = new Scanner(System.in);
        PrintStream print2 = new PrintStream(new FileOutputStream(fileName,true)); //printer in dictionary (add)

        ArrayList<ArrayList<String>> outer = new ArrayList<>();
        ArrayList<String> inner = new ArrayList<String>();


//read from file and add each sample in arraylist
        while(scan.hasNextLine())   // check for end of file
        {
            inner = new ArrayList<String>(); // create a new inner list that has the same content as
            x=scan.nextLine();
            x=x.replaceAll("[,]", " ");
            for(String word : x.split(" ")) {
                inner.add(word);
            }
            outer.add(inner);

        }
        scan.close();


//removing attributes that we don't need
       for(int i=0;i<outer.size();i++){
           outer.get(i).remove(1);outer.get(i).remove(1);outer.get(i).remove(1);outer.get(i).remove(2);
           outer.get(i).remove(3);outer.get(i).remove(3);outer.get(i).remove(4);outer.get(i).remove(4);outer.get(i).remove(5);
       }

       //scaling age
for(int j=1; j<outer.size(); j++){
    float scaleage=(parseFloat(outer.get(j).get(0))-17)/73;
    outer.get(j).set(0,String.valueOf(scaleage));
}

//male = 1 , female = 0   ( 7353 Male , 3647 Female)

        System.out.println(outer.get(0));

        for(int i=1; i<outer.size(); i++){
            if(outer.get(i).get(3).contentEquals("Male")){
                outer.get(i).set(3,"1");


            }

            else{
                outer.get(i).set(3,"0");

            }


        }

        System.out.println(outer.get(1).get(3));


for(int fold=1; fold <=5; fold++){



}







    }
}

