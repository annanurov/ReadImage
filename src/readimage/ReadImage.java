package readimage;

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Bayram
 */
public class ReadImage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //divide Image And Save To Text File:
        //useSyllableSample();

//        File directory = new File ("KH-backup120316\\binaryPixels2");
//        File directory = new File ("KH-backup120316\\binaryPixelsFIVE");
//        File [] files = directory.listFiles();
//        for(File f : files){
//            if (f.getName().endsWith(".txt")){
//                System.out.println(f.getName());
//                 makeARFFfile(f);
////                 break;
//            }
//        }
        
        useSampleSet();
//        makeARFFfile("binaryPixelst80");
//        makeARFFfile("binaryPixelst90");
//        makeARFFfile("binaryPixelst95");
//        makeARFFfile("binaryPixelst98");
        
//        cutMargins("KH-cons310316", "CUT");
        
        
        
        
    }//end of main

    
    
    public static void cutMargins(String dir, String name){
        sampleSet bukvy;
//        bukvy = new sampleSet(dir);
//        bukvy.cutOffMargins(name, 0xFF * 3 - 1);
    
        bukvy = new sampleSet("KH-cons310316\\Cut");
        bukvy.resizeAll("CUT");
    }//end of cutMargins
    
    
    
    public static void useSampleSet() {
        sampleSet bukvy;
        bukvy = new sampleSet("resized");
        int batchSize = 5;
        int pixelThreshold = (int)(batchSize * batchSize * 0.8);//20;
        int colorThreshold = 700;
        String sampleFile = "resized" + Integer.toString(batchSize)+ ".txt" ;
        bukvy.convertSamplesToText(colorThreshold, pixelThreshold, sampleFile, batchSize);
//        //        bukvy = new sampleSet("KH-backup120316\\s1a");
//        //        bukvy.convertSamplesToText("binaryPixels1A");
//
//        String dirname ;
//        String  sampleFile;
//        int batchSize =5;
//        int pixelTreshold = 20;//90//define as a portion of (batchSize^2)
//        int colorTreshold = 600;
//        for (int i = 1; i < 10; i++){
//            dirname = "KH-backup120316\\s" +String.valueOf(i)  + "a";
//            sampleFile = "binaryPixelsFIVE" + String.valueOf(i)+ "A";
//            
//            
//            bukvy = new sampleSet(dirname);
////            bukvy.convertSamplesToText(sampleFile);
//            bukvy.convertSamplesToText(colorTreshold, pixelTreshold, sampleFile, batchSize);
//            
//            dirname = "KH-backup120316\\s" +String.valueOf(i)  + "b";            
//            sampleFile = "binaryPixelsFIVE" + String.valueOf(i)+ "B";
//
//            bukvy = new sampleSet(dirname);
////            bukvy.convertSamplesToText(sampleFile);
//            bukvy.convertSamplesToText(colorTreshold, pixelTreshold, sampleFile, batchSize);
//            
//        System.out.println(i + "------------------------");
//        }//end of for, i
        
    }//end of useSampleSet

    public static void useSyllableSample() throws Exception {

        SyllableSample g = new SyllableSample();

        /**
         * Use the following settings in SyllableSample class for the first part
         * of data KHSyl1.jpg: int LMargin = 15;//0;//left margin int RMargin =
         * 15;//0;//right margin int UMargin = 10;//3;//upper margin int DMargin
         * = 10;//7;//"down"er margin int firstCon = 0x1780;// Unicode for "Ka"
         * int firstVow = 0x17b6;// Unicode for "a" int lineThikness =
         * 10;//table line thickness, pixels int initX = 682;//x-coordinate of
         * top-left corner, pixels int initY = 562;//y-coordinate of top-left
         * corner, pixels int gap = 172; int BWthreshold2 = 100;//whether to put
         * 1 or 0 to text file.
         */
//        g = new ImageSample("KHsyllablespg1.jpg");
//        g = new SyllableSample("KHSyl1.jpg");
//        g.divideImageAndSaveToTextFile(16,31,"KHSyl", "KHSyllables.txt");
        /**
         * Use the following settings in SyllableSample class for the second
         * part of data KHSyl2.jpg int LMargin = 15;//0;//left margin int
         * RMargin = 15;//0;//right margin int UMargin = 10;//3;//upper margin
         * int DMargin = 10;//7;//"down"er margin int firstCon = 0x1790;//
         * Unicode for "Tha" int firstVow = 0x17b6;// Unicode for "a" int
         * lineThikness = 10;//table line thickness, pixels int initX =
         * 682;//x-coordinate of top-left pixel of the table int initY =
         * 562;//y-coordinate of top-left pixel of the table int gap = 172; int
         * BWthreshold2 = 100;//whether to put 1 or 0 to text file.
         */
        g = new SyllableSample("newSamples\\s9b.jpg");
        /*
         * divideImageAndSaveToTextFile(int rows, int cols, String sampleIconName, String textDataFileName)
         * rows = 2* (int)(how many consonants/ 2) 
         * cols = how many vowels
         * sampleIconName = what do we call each syllable image
         * textDataFileName = pixels are converted to text and written here
         */

        g.divideImageAndSaveToTextFile(18, 31, "sample9B-", "samples9B-.txt");

        //rows: 8
        //coloms: 33
        //makeARFFfile("SHORT-KH-TextData");
        ///save samples as text in the same file "textImagetxt"

    }//end of useSyllableSample

    public static void makeARFFfile(String dataFileName) throws Exception {
        //        File inputFile = new File(dataFile + ".txt");
        File inputFile = new File(dataFileName);
        makeARFFfile(inputFile);
    }//end of makeARFFfile(String)

public static void makeARFFfile(File dataFile) throws Exception {
//        File inputFile = new File(dataFile + ".txt");
//        File inputFile = new File(dataFile);
        Scanner input = new Scanner(dataFile);

        File outputFile = new File(dataFile + ".arff");
        PrintWriter pen = new PrintWriter(outputFile);

        pen.write("@RELATION KHletters");
//        pen.write(dataFile.substring(13, 15));
        pen.write(dataFile.getName().substring(13, 15));
        pen.write("\n\n");
        ArrayList consonants = new ArrayList();
        ArrayList vowels = new ArrayList();

        //keep track og how many pixels:
        int pixels = 0;

        String y;
        int L;
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] items = line.split(" ");
            L = items.length;
            if (pixels < L - 2) {
                pixels = L - 2;
            }
            y = items[L - 2];
            if (!(consonants.contains(y))) {
                consonants.add(y);
            }
            y = items[ L - 1];
            if (!(vowels.contains(y))) {
                vowels.add(y);
            }
        }
        input.close();

        //make arff file header:

        for (int i = 0; i < pixels; i++) {
//            pen.write("@ATTRIBUTE pixel" + Integer.toString(i) + "\tINTEGER\n");
            pen.write("@ATTRIBUTE pixel" + Integer.toString(i) + "\t{0,1}\n");
        }
        pen.write("@ATTRIBUTE consonant\t{");
        for (int i = 0; i < consonants.size() - 1; i++) {
            pen.write(consonants.get(i) + ",");

        }
        pen.write(consonants.get(consonants.size() - 1).toString());
        pen.write("}\n");
        
        pen.write("@ATTRIBUTE vowel\t{");
        for (int i = 0; i < vowels.size() - 1; i++) {
            pen.write(vowels.get(i) + ",");
        }

        pen.write(vowels.get(vowels.size() - 1).toString());
        pen.write("}\n\n");
        
        //reopen the oginial data file:
//        input = new Scanner(inputFile);
        input = new Scanner(dataFile);

        pen.write("@DATA\n");
        String line;
        int t = 0;//line counter
        while (input.hasNext()) {

            line = input.nextLine();
            String[] items = line.split(" ");
            for (int i = 0; i < items.length - 1; i++) {
                pen.write(items[i]);
                pen.write(",");
            }
            pen.write(items[items.length - 1]);
            pen.write("\n");
//            System.out.printf("line-%3d ", t++);
//            if(t%15 == 0)
//                System.out.println();            


        }
        pen.write("%\n");
        pen.write("%\n");
        pen.write("%\n");
        pen.close();
        input.close();
    }//end of makeARFFfile(File)

    public static void TestPerceptron() throws Exception {
        TestPerceptron(6881, 26, 10, "textImage.txt", "weights");
    }

    public static void TestPerceptron(
            int inputWeights,
            int numberOfLetters,
            int numberOfABCs,
            String dataFileName,
            String weightFileName) throws Exception {
//        int numberOfLetters = 26;
//        int numberOfABCs = 10;
//        String dataFileName;
//        dataFileName = "textImage.txt";

//        String weightFileName = "weights";

        Perceptron p1 = new Perceptron(inputWeights);

        //read data from file:
        p1.file2data(dataFileName);
        //initialize weights:
        p1.initiateWeights(true);
        //train perceptron, number of cycles as parameter:
        p1.train(500);
        //save weights:
        p1.saveWeights(weightFileName);
        //test the weights:
        p1.test();//test on same dataFile

        String testData = "testData.csv";
        //test on a different data file. 
        //p1.test(testData); //////<<need to REwrite testing for "no label"

    }
}//end of class ReadImage.java
