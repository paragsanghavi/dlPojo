import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.*;
import java.io.PrintWriter;

public class Main {

    static Integer _cnt = new Integer(2);
    static deeplearning_3bd2bdf8_e406_4687_b8c7_b4fef62da4bd dlmodel = new deeplearning_3bd2bdf8_e406_4687_b8c7_b4fef62da4bd();
    // get the predictions
    static double[] preds = new double [deeplearning_3bd2bdf8_e406_4687_b8c7_b4fef62da4bd.NCLASSES+1];
    //p.predict(data, preds);



    public static void main(String[] args) {
        // write your code here
        //System.out.println("Hello World");

        double data[] = readDatafromCSV();
        // get the predictions
    }

    public static  double [] readDatafromCSV() {

        File file = new File("/Users/paragsanghavi/Downloads/rh.csv");  // EDIT ME TO YOUR PATH!
        String[] observation=null;
        double data[] = null;
        int j = 0;
        int column_counter = 2;
        String key;
        RowData row = new RowData();
        try {
            PrintWriter writer = new PrintWriter("results.txt", "UTF-8");
            String line="";
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null ) {
                //System.out.println(j);
                observation = line.split(",");
                //data = new double[observation.length - 2];
                data = new double[observation.length - 1];
                //System.out.println(observation.length);
                if(j!=0) {//skip header
                    // the score pojo requires a single double[] of input.
                    //row.put("C2", 0d);
                    data[0] = 0d;//constant value added because of the svm bug
                    column_counter = 3;
                    for (int i = 2; i < observation.length; ++i) { // Ignore column C1 ( UUID) and C2 (LOB)
                        key = "C" + column_counter;

                         //System.out.print(key + ": ");
                        /*if(Double.valueOf(observation[i])!=0){
                            System.out.print(key);
                            System.out.println(Double.valueOf(observation[i]));
                        }*/
                        //row.put(key, Double.valueOf(observation[i]));
                        data[i - 1] = Double.valueOf(observation[i]);
                        //System.out.println(Double.valueOf(observation[i]));
                        column_counter++;
                    }
                    //System.out.println("First value of data: " + data[0]);
                    //System.out.println("Second value of data: " + data[1]);
                    //System.out.println("Third value of data: " + data[2]);
                    //System.out.println("Last value of data: " + data[data.length-1]);
                    dlmodel.score0(data, preds);
                    System.out.print(preds[0]);
                    writer.println(preds[0]);
                    System.out.println(" ");
                    //System.out.println(preds[1]);
                    //System.out.println(preds[2]);



                    /*EasyPredictModelWrapper model = new EasyPredictModelWrapper(dlmodel);
                    MultinomialModelPrediction p = model.predictMultinomial(row);
                    System.out.print(" Class selected " + p.labelIndex + ": ");
                    writer.println(p.labelIndex);
                    for (int k=0; k<p.classProbabilities.length;k++) {
                        System.out.print("p[" + k + "]: " + p.classProbabilities[k]+ ",");
                    }
                    System.out.println();*/
                }
                j++;
                if(j==200000) break;
            }//end of while

            System.out.println("Number of records processed: " + j );
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            _cnt=0;
        }
        return data;

    }

}

