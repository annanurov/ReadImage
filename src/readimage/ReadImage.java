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
        //useSyllableSample();
        useSampleSet();
    }//edn of main
    
     
    
    
    public static void useSampleSet()  {
        File directory = new File("KH-backup120316");
        File [] files = directory.listFiles();
        
        sampleSet bukvy = new sampleSet("KH-backup120316\\s1a-Cut");
        
        bukvy.convertSamplesToText("binaryPixels");
    }//end of useSampleSet
    
    
    public static void useSyllableSample() throws Exception {

        SyllableSample g = new SyllableSample();
        
        /**Use the following settings in SyllableSample class 
         * for the first part of data KHSyl1.jpg:
         * int LMargin = 15;//0;//left margin
         * int RMargin = 15;//0;//right margin
         * int UMargin = 10;//3;//upper margin
         * int DMargin = 10;//7;//"down"er margin
         * int firstCon = 0x1780;// Unicode for "Ka"
         * int firstVow = 0x17b6;// Unicode for "a"
         * int lineThikness = 10;//table line thickness, pixels
         * int initX = 682;//x-coordinate of top-left corner, pixels
         * int initY = 562;//y-coordinate of top-left corner, pixels
         * int gap = 172;
         * int BWthreshold2 = 100;//whether to put 1 or 0 to text file.
         */
//        g = new ImageSample("KHsyllablespg1.jpg");
//        g = new SyllableSample("KHSyl1.jpg");
//        g.divideImageAndSaveToTextFile(16,31,"KHSyl", "KHSyllables.txt");
        
        /**Use the following settings in SyllableSample class for 
         * the second part of data KHSyl2.jpg
         * int LMargin = 15;//0;//left margin
         * int RMargin = 15;//0;//right margin
         * int UMargin = 10;//3;//upper margin
         * int DMargin = 10;//7;//"down"er margin
         * int firstCon = 0x1790;// Unicode for "Tha"
         * int firstVow = 0x17b6;// Unicode for "a"
         * int lineThikness = 10;//table line thickness, pixels
         * int initX = 682;//x-coordinate of top-left pixel of the table
         * int initY = 562;//y-coordinate of top-left pixel of the table
         * int gap = 172;
         * int BWthreshold2 = 100;//whether to put 1 or 0 to text file.
         */
        
        g = new SyllableSample("newSamples\\s9b.jpg");
        /*
         * divideImageAndSaveToTextFile(int rows, int cols, String sampleIconName, String textDataFileName)
         * rows = 2* (int)(how many consonants/ 2) 
         * cols = how many vowels
         * sampleIconName = what do we call each syllable image
         * textDataFileName = pixels are converted to text and written here
         */
        
        g.divideImageAndSaveToTextFile(18,31,"sample9B-", "samples9B-.txt");
        
        //rows: 8
        //coloms: 33
        //makeARFFfile("SHORT-KH-TextData");
        ///save samples as text in the same file "textImagetxt"

    }//end of useSyllableSample

    
    
    public static void makeARFFfile(String textFile) throws Exception{
        File inputFile = new File(textFile + ".txt");
        Scanner input = new Scanner(inputFile);
        
        File outputFile = new File (textFile + ".arff");
        PrintWriter pen = new PrintWriter(outputFile);
        
        pen.write("@RELATION KHletters\n\n");
        ArrayList classes = new ArrayList();
        int pixels =0;
        String y;
        int L;
        while(input.hasNext()){
            String line = input.nextLine();
            String [] items = line.split(" ");
            L = items.length;
            if (pixels < L - 1)
                pixels = L - 1;
            y = items[L - 1];
            if(! (classes.contains(y)) ){
                classes.add(y);
            }
        }
        input.close();
        
        //make arff file header:
        
        for(int i = 0 ; i < pixels; i++){
//            pen.write("@ATTRIBUTE pixel" + Integer.toString(i) + "\tINTEGER\n");
            pen.write("@ATTRIBUTE pixel" + Integer.toString(i) + "\t{0,1}\n");
        }
        pen.write("@ATTRIBUTE class\t{");
        for(int i = 0; i < classes.size()-1; i++){
             pen.write( classes.get(i) + ",");
            
        }
        
        pen.write( classes.get(classes.size()-1).toString() );
        pen.write( "}\n\n" );
        
        input = new Scanner(inputFile);
        
        pen.write("@DATA\n");
        String line;
        int t = 0;
        while( input.hasNext() ){
            
            line = input.nextLine();
            String [] items = line.split(" ");
            for (int i = 0; i < items.length - 1; i++){
                pen.write(items[i]);
                pen.write(",");
            }
            pen.write(items[items.length - 1]);
            pen.write("\n");
            System.out.println(t++);
            
        }
        pen.write("%\n");
        pen.write("%\n");
        pen.write("%\n");
        pen.close();
        input.close();
    }
    
    
//    public void convertSamplesToTextFile() throws Exception {
//        convertSamplesToTextFile("sample", 10, 26, "textImage.txt");
//    }//end of convertSamplesToTextFile()
//
//    public void convertSamplesToTextFile(
//            String sampleFileHeader, 
//            int numberOfABCs, 
//            int numberOfLetters, 
//            String dataFileName) throws Exception {
//        String nextSampleFile;
//        GetPixelColor image;
//        File textualImage = new File("textImage.txt");
//        PrintWriter pen = new PrintWriter(textualImage);
//
//        ///for all images named like "sample(.d+)X(.d+)\.jpg" ***
//        for (int i = 0; i < numberOfABCs; i++) {
//            for (int j = 0; j < numberOfLetters; j++) {
//                ///convert image to text and write the text to "textImage.txt"
//                nextSampleFile = sampleFileHeader + String.valueOf(i) + 
//                        "X" + String.valueOf(j) + ".jpg";
//
//                image = new GetPixelColor(nextSampleFile);
//                pen.print(image.returnPixels());
//
//                //convert j to corresponding letter : 0=A 1=B ...
//                ///add the label (letter's UNICODE)
//                pen.println(Character.toString((char) (j + 65)));
//                
//            }//end of for, j    
//        }//end of for, i   
//        pen.close();
//    }//end of convertSamplesToTextFile

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
