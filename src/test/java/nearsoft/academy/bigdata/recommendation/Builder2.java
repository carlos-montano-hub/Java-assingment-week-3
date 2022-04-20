package nearsoft.academy.bigdata.recommendation;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Builder2 {
    String pathTxt; //"src/test/java/nearsoft/academy/bigdata/recommendation/movies.txt";
    //String pathCsv ="src/test/java/nearsoft/academy/bigdata/recommendation/movies.csv";
    String pathNumericCSVFile ="src/test/java/nearsoft/academy/bigdata/recommendation/NumericCSVFile.csv";
    File numericCSVFile = new File(pathNumericCSVFile);
    private final BufferedReader reader;
    //private final PrintWriter writer;
    private final PrintWriter writer2;
    List<String> userList = new ArrayList<>();
    Map<String,Long> userDictionary = new Hashtable<>();
    List<String> productList = new ArrayList<>();
    Map<String, Long> productDictionary = new Hashtable<>();
    int scoreCount = 0;


    public Builder2(String pathTxt) throws IOException {
        this.reader = new BufferedReader(new FileReader((pathTxt)));
        //this.writer = new PrintWriter(new BufferedWriter(new FileWriter(pathCsv)));
        this.writer2 = new PrintWriter(new BufferedWriter(new FileWriter(pathNumericCSVFile)));
        this.pathTxt = pathTxt;

        //File movies = new File(pathCsv);
        //if (movies.createNewFile()) {
            //System.out.println("File created: " + movies.getName());}
        //else {
            //System.out.println("File" + movies.getName() + " already exists.");}


        if (numericCSVFile.createNewFile()) {
            System.out.println("File created: " + numericCSVFile.getName());
        } else {
            System.out.println("File " + numericCSVFile.getName() + " already exists.");
        }
        searcher();
    }



    public void searcher() throws IOException {
        //long counterP = 0;
        //int counterU = 0;
        String score;
        String userID = null;
        String line;
        String productID = null;

    while ( (line = this.reader.readLine()) != null ) {
        Pattern searchStrings = Pattern.compile("review/userId|product/productId|review/score");
        Matcher match = searchStrings.matcher(line);

        
        if (match.find()) {

            
            if (match.group().equals("product/productId")){
                productID = line.replaceAll("product/productId: ", "");
                //counterP = productList.size();
                //System.out.println(productID + " " + counter);
                //productList.add(productID);
                if (productDictionary.get(productID) == null) {
                    productList.add(productID);
                    productDictionary.put(productID,(long)productList.size()-1);
                }
            }

            if (match.group().equals("review/userId")){
                userID = line.replaceAll("review/userId: ", "");
                //counterU = userDictionary.size();
                if (userDictionary.get(userID) == null) {
                    userList.add(userID);
                    userDictionary.put(userID, (long)userList.size()-1);
                    //System.out.println(userID + " " + counter);
                }
            }

            if (match.group().equals("review/score")){
                score = line.replaceAll("review/score: ", "");
                //score = score.replaceAll(".0", "");
                //System.out.println(score);
                //writer.println(userID + "," + productID + "," + score);
                writer2.println(userDictionary.get(userID) + "," + (productDictionary.get(productID)) + "," + score);
                //System.out.println(counterU + "," + counterP + "," + score);
                scoreCount = scoreCount + 1;
                //System.out.println(userList.size());
                //Thread.sleep(1000);
            }

        }

    }
    //writer.close();
    writer2.close();

}


}
