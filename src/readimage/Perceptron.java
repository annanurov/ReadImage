/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readimage;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author Bayram
 */
public class Perceptron {

    private int m; //number of input features
    private int n; //number of samples
    private double[] w; //weights
    private double initW = 0.025;//initial value of weights
    private double initRangeW = 0.02;//range for initial weights
    private double learningRatio = 0.015;
//    private ArrayList<ArrayList> data;
    private List<List<Double>> data = new ArrayList<>();
    private List<List<Double>> testingData = new ArrayList<>();
    private List<Double> Ys = new ArrayList<>();
//    private double [] ys;
//    private int N;

    private String separator = " ";
    
    public Perceptron() {
    }

    public Perceptron(int newM) {
        this.m = newM;
    }

    public void initiateWeights() {
        initiateWeights(initW, false);
    }//end of initiateWeights()

    public void initiateWeights(boolean isRandom) {
        initiateWeights(initW, isRandom);
    }//end of initiateWeights()

    public void initiateWeights(double initialWeight, boolean isRandom) {
        w = new double[m + 1];//last weight is the bias
        for (int i = 0; i <= m; i++) {
            w[i] = initialWeight;
        }//end of for
        if (isRandom) {
            Random randomGenerator = new Random();
            for (int i = 0; i <= m; i++) {
                w[i] += (0.5 - randomGenerator.nextFloat()) * initRangeW;
            }//end of for
        }//end of if
    }//end of initiateWeights(double initalWeight, boolean isRandom)

    public void file2data(String dataFileName) throws Exception {
        file2data(dataFileName, data, Ys, true, false);
    }

    public void file2data(String dataFileName, List<List<Double>> vectorX, List<Double> vectorY, boolean isTraining, boolean hasHeader) throws Exception {
//        Ys = new ArrayList();
        double y;
        vectorY = new ArrayList();
//        data = new ArrayList();
        vectorX = new ArrayList();
        File dataFile = new File(dataFileName);
        Scanner input = new Scanner(dataFile);
        String header;
        
        if (hasHeader){
            header = input.nextLine();
            //--get number of features:
            if (isTraining) {
                m = header.split(" ").length;
            } else {
                m = header.split(" ").length - 1;
            }
        }
//        ys = new double[m];
        ArrayList currentLine;
        int count = 0;
        //--read from file:
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] items = line.split(",");
            currentLine = new ArrayList();
//            m = items.length - 1;
            for (int i = 0; i < m; i++) {
                currentLine.add(Double.valueOf(items[i]));
            }//end of for, i
            if (isTraining) {
                y = Double.valueOf(items[m].charAt(6) - 48);
                vectorY.add(y);
            }
//            System.out.println(y);
//            Ys.add(y);          
//            ys[count] = y; count++; 
//            data.add(currentLine);
            vectorX.add(currentLine);
        }//end of while
        System.out.println(" samples: " + vectorX.size());//System.out.println(" samples: " + data.size());
        if (isTraining) {
            System.out.println("  labels: " + vectorY.size());//System.out.println("  labels: " + Ys.size());
        } 
        else
            System.out.println("no labels for training");
        //       System.out.println("features: " + vectorX.get(2).size());//System.out.println("features: " + data.get(2).size());
//        n = data.size();
        n = vectorX.size();
//        System.out.println(data.get(2));
    }//end of file2data

    private double getClass(String c) {
        return (double) (Integer.valueOf(c.charAt(c.length() - 1)));
    }//end of getCalss(String)

    private double activation(double x) {
        return 1 / (1 + java.lang.Math.exp(-1 * x));
    }//end of activation

    private int activationInt(double x) {
        return (int) (10 / (1 + java.lang.Math.exp(-1 * x)));
    }//end of activationInt(double)

    private void updateW(double error, double yhat, ArrayList sample, double learningRatio) {
//        M = w.length;
        double update = learningRatio * yhat * (1 - yhat) * error;
        w[m - 1] += update;
        for (int i = 0; i < m - 1; i++) {
            w[i] += update * ((double) sample.get(i));
        }//end of for, i
    }//end of update(double, arraylist, double)

    private void updateW(double error, double yhat, List sample, double learningRatio) {
//        M = w.length;
        double update = learningRatio * yhat * (1 - yhat) * error;
        w[m - 1] += update;
        for (int i = 0; i < m - 1; i++) {
            w[i] += update * ((double) sample.get(i));
        }//end of for, i
    }//end of update(double, list, double)

    public void train() throws Exception {
        train(100);
    }//end of train()

    public void train(int iterations) throws Exception {
        double linearSum;
        double totalError;
        File errorLog = new File("errorLog");
        PrintWriter errorPen = new PrintWriter(errorLog);
        for (int k = 0; k < iterations; k++) {
            totalError = 0;
            for (int i = 0; i < data.size(); i++) {
                linearSum = 0;
                for (int j = 0; j < data.get(i).size(); j++) {
//                    linearSum += w[j] * ((double) data.get(i).get(j));
                    linearSum += w[j] * ((double) data.get(i).get(j));
                }//end for for, j
                //--calculated outputs:
                double yhat = activation(linearSum);
                //--errors:
//              double error = (double) Ys.get(i) - yhat;//<<use this if Ys is ArrayList<>
                double error = Ys.get(i) / 10 - yhat;//<<use this if Ys is List<double>
                totalError += Math.abs(error);
                updateW(error, yhat, data.get(i), learningRatio);
            }//end for for, i
            System.out.println(k + "\t Average Iteration error:" + totalError / n);
//            errorPen.write(k + "\t Average Iteration error:" + (totalError / n) + "\n");
            errorPen.write((totalError / n) + "\n");
        }//end of for, k
        errorPen.close();
    }//end of train(int)

    
    public double getY(String dataFileName/*, int iterations*/) throws Exception {
        double linearSum = 0;
        double totalError;
        File dataFile = new File(dataFileName);
        java.util.Scanner dataScanner = new Scanner(dataFile);
        File errorLog = new File("errorLog");
        PrintWriter errorPen = new PrintWriter(errorLog);
//        for (int k = 0; k < 1; k++) {
            totalError = 0;
            //for (int i = 0; i < data.size(); i++) {
            while (dataScanner.hasNext()){
                linearSum = 0;
                String line = dataScanner.nextLine();
                String [] items = line.split(" ");
                for (int i =0; i <  items.length; i++){
                    linearSum += this.w[i] * Integer.parseInt(items[i]);
                }
                
                /**calculated outputs:*/
                
                /**errors:**/
//              double error = (double) Ys.get(i) - yhat;//<<use this if Ys is ArrayList<>
//                double error = Ys.get(i) / 10 - yhat;//<<use this if Ys is List<double>
//                totalError += Math.abs(error);
                //updateW(error, yhat, data.get(i), learningRatio);
//            }//end for for, i
            
//            System.out.println(k + "\t Average Iteration error:" + totalError / n);
//            errorPen.write(k + "\t Average Iteration error:" + (totalError / n) + "\n");
//            errorPen.write((totalError / n) + "\n");
        }//end of for, k
//        errorPen.close();
        double yhat = activation(linearSum);
            return yhat;
    }//end of getY
    
    public void saveWeights(String filename) throws Exception {
        File weightFile = new File(filename);
        PrintWriter pen = new PrintWriter(weightFile);
        for (int i = 0; i <= m; i++) {
            pen.print(w[i]);
            pen.print("\n");
        }//end of for, i
        pen.close();
    }//end of saveWeights(String filename) 

    public void test() {
        test(data, Ys);
    }

    public void test(List<List<Double>> vectorX, List<Double> vectorY) {
        double testingError = 0;
        double linearSum;
        for (int i = 0; i < vectorX.size(); i++) {
            linearSum = 0;
            for (int j = 0; j < vectorX.get(i).size(); j++) {
                linearSum += w[j] * ((double) vectorX.get(i).get(j));
            }//end for for, j
            //--calculated outputs:
            double yhat = activation(linearSum);
            //--errors:
//              double error = (double) Ys.get(i) - yhat;//<<use this if Ys is ArrayList<>
            double error = vectorY.get(i) / 10 - yhat;//<<use this if Ys is List<double>
            testingError += Math.abs(error);
        }//end of for, i
        System.out.println(testingError / n);
    }//end of test()

    public void test(String testingDataFile) throws Exception{
        List<List<Double>> testData = new ArrayList<>();
        List<Double> dummyList = new ArrayList<>();
        file2data(testingDataFile, testData, dummyList, false, false);
        test(testData, dummyList);
    }//end of test(String testingData)
    
    
    public void   setSeparator(String newSep){this.separator = newSep;}
    public String setSeparator(){return this.separator;}
}//end of class Percentron
