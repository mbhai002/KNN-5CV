import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

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
        ArrayList<String> inner = new ArrayList<>();


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
/*
for(int j=1; j<outer.size(); j++){
    float scaleage=(parseFloat(outer.get(j).get(0))-17)/73;
    //float scaleeduc=(parseFloat(outer.get(j).get(1))-1)/15;
    outer.get(j).set(0,String.valueOf(scaleage));
   // outer.get(j).set(1,String.valueOf(scaleeduc));

}*/

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

        System.out.println(outer.get(1).get(0));
        System.out.println(outer.get(2).get(0));
        System.out.println(outer.get(3).get(0));
        System.out.println(outer.get(4).get(0));
        System.out.println(outer.get(5).get(0));
        System.out.println(outer.get(6).get(0));


        System.out.println(outer.get(1).get(3));

        float ageS;
        float educationS;
        float ageT;
        float educationT;
        float hoursS;
        float hoursT;
        float sexeS;
        float sexeT;
        float distance=0;
        float distanceMin=100000;
        int nn=0;
        int count=0;
        float check;
        int lessfifty=0; int morefifty=0; String word="";int d;


        ArrayList<ArrayList<Float>> samplem = new ArrayList<>();
        ArrayList<Float> distancem = new ArrayList<>();
        ArrayList<Float> distancecheck=new ArrayList<>();
        distancecheck.add((float) 10);



        for(int sample=1; sample<outer.size(); sample++){
            System.out.println("sample n°"+sample);



            if(outer.get(sample).get(6).contentEquals("5")){

                samplem.clear();
                distancem.clear();
                distancecheck.clear();
                distancecheck.add((float) 10);

                lessfifty=0;
                morefifty=0;
                word="";
                ageS=parseFloat(outer.get(sample).get(0)); educationS=parseFloat(outer.get(sample).get(1)); hoursS=parseFloat(outer.get(sample).get(4)); sexeS=parseFloat(outer.get(sample).get(3));


                for(int test=1; test<outer.size(); test++){

                    if(parseInt(outer.get(test).get(6))!=5){

                        ageT=parseFloat(outer.get(test).get(0)); educationT=parseFloat(outer.get(test).get(1)); hoursT=parseFloat(outer.get(test).get(4)); sexeT=parseFloat(outer.get(test).get(3));
                        distance= (float) Math.pow(ageT-ageS,2)+(float)Math.pow(educationT-educationS,2)+(float)Math.pow(hoursT-hoursS,2)+(float)Math.pow(sexeT-sexeS,2);
                        distance= (float) Math.sqrt(distance);

                        if(distancecheck.size()<35){
                            distancecheck.add(distance);
                            Collections.sort(distancecheck);
                            distancem.add(distance);
                            distancem.add((float) outer.indexOf(outer.get(test)));
                            samplem.add(distancem);
                        }
                        if(distancecheck.size()==35){
                            if(distance<distancecheck.get(34)){
                                distancecheck.remove(34);
                                distancecheck.add(distance);
                                distancem.add(distance);
                                distancem.add((float) outer.indexOf(outer.get(test)));
                                samplem.add(distancem);
                            }
                            Collections.sort(distancecheck);

                        }

                       // distancecheck.add(distance);


                        //distancem.add(distance);
                        //distancem.add((float) outer.indexOf(outer.get(test)));
                       // samplem.add(distancem);
                    }
                }

                // get K
               // System.out.println(distancecheck);
                //Collections.sort(distancecheck);
                check=distancecheck.get(34);
                System.out.println("distancecheck "+check);

                //remove all element if distance is larger then K
                for(int i=0; i<samplem.size();i++){
                    if(samplem.get(i).get(0)<=check){

                        float c= samplem.get(i).get(1);
                        d= Math.round(c);

                        if(outer.get(d).get(5).contentEquals("<=50K")){
                            lessfifty++;
                        }

                        if(outer.get(d).get(5).contentEquals(">50K")){
                            morefifty++;
                        }

                        //samplem.remove(i);
                       // i--;
                    }



                }


                if(morefifty>lessfifty)  {
                    word=">50K";
                }
                else {
                    word="<=50K";
                }

                if(outer.get(sample).get(5).contentEquals(word)){
                    count++;
                    System.out.println(count + "count");
                }
            }

        }

        System.out.println(count + "count");



}


    }



