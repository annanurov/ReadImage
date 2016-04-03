
package readimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 *
 * @author Bayram
 */
public class sampleSet {
    
    String directoryName;
    File directory;
//    String outputFileName;
    
    public sampleSet(String dir){
        this.directoryName = dir;
        this.directory = new File(this.directoryName);
//        this.outputFileName
    }
    
    /**gives defaults to convertSamplesToText(int colorThreshold, int pixelThreshold, String textFileName, int batchSize)*/
    public  void convertSamplesToText(){
        convertSamplesToText(600, 80, "sample1A", 10);
    }
        
    /**gives defaults except dir name to convertSamplesToText(int colorThreshold, int pixelThreshold, String textFileName, int batchSize)*/
    public  void convertSamplesToText(String textFileName){
        convertSamplesToText(600, 80, textFileName, 10);
    }
    
/**    Default values: colorThreshold = 600, pixelThreshold = 5, textFileName = "sample1A", batchSize = 10*/
    public  void convertSamplesToText(int colorThreshold, int pixelThreshold, String textFileName, int batchSize){
    /* given: this.directory_name, color threshold, pixel threshold, text file name, batch size
     * get list of all samples in the directory
     * open file for writing
     * for each sample:
     * --for each batch of pixels:
     * ----if sum > threshold
     * ------write "1 "
     * ----else
     * ------write "0 "
     * --write letter codes
     * --write new line
     * close file
     */
        int color= 0 ;
        int currentBatchSum = 0;
        int sum = 0;
        File[] listOfFiles = directory.listFiles();
        File outputDataFile = new File(textFileName + "t" + pixelThreshold);
        String cons, vow; 
                int batchNumber = 0;
        int vowelNameStartIndex =0;
        int fileNameLength = 0;
        try{
            PrintWriter pen = new PrintWriter(outputDataFile);
            for (File file : listOfFiles){
                if ( ! file.getName().endsWith(".jpg"))
                    continue;
                BufferedImage image = ImageIO.read(file);
                int H = image.getHeight();
                int W = image.getWidth();
                for(int x = 0 ; x <= W - batchSize; x += batchSize){
                    for(int y = 0 ; y <= H - batchSize; y += batchSize){
                        currentBatchSum = 0;
                        for(int i = 0 ; i < batchSize; i++){
                            for(int j = 0 ; j < batchSize; j++){
                                color = image.getRGB(x + i, y + j);
                                sum = (color & 0xFF) + (color & 0xFF00) + (color & 0xFF0000);
                                if (sum > colorThreshold)
                                    currentBatchSum++;
                                
                            }
                        }
//                        System.out.print(batchNumber++);
//                        System.out.print(" ");
                        if (currentBatchSum > pixelThreshold)
                            pen.write("1 ");
                        else
                            pen.write("0 ");
                    }
                    currentBatchSum = 0;
                }
                
//                    String cons = "";
//                file.getName().substring(9, file.getName().length()-3)
//                            sample1B-16X0

//                String[] parts = file.getName().split("X");
////                System.out.println(parts[0] + " " + parts[1] + "uuuuuuuuuuuu");
//                String [] p2 = parts[0].split("-");
//                if(p2.length > 0)
//                    System.out.print (p2[1] + "CCC");
////                String [] p3 = parts[1].split(".");
////                if(p3.length > 0)
////                    System.out.println(p3[1]);

                cons = file.getName().substring(9, file.getName().indexOf("X"));
//                cons = p2[1];
//                cons = parts[0].split("-")[1];
//                System.out.print(file.getName().substring(9, file.getName().indexOf("X")) + " ");
                System.out.print(cons + " ");
//                    pen.write(" ");
                vowelNameStartIndex = file.getName().indexOf("X");
                fileNameLength = file.getName().length();
                
                vow = file.getName().substring( vowelNameStartIndex + 1, fileNameLength - 4); 
                System.out.print(vow + " ");
                
                if(file.getName().contains("B"))
                    cons = Integer.toString(Integer.valueOf(cons) + 16);
//                pen.write(Integer.valueOf(cons));
                pen.write(cons);
                pen.write(" ");
//                pen.write(Integer.valueOf(vow));
                pen.write(vow);
                pen.write(" \n");
                System.out.print(file.getName() + "\n");
//                batchNumber = 0;
            }//end of for, file
            
            pen.close();
            
        }//end of try
        catch (Exception ex){
            ex.printStackTrace();
        }//end of catch
    
    }//end of convertSamplesToText(int threshold, String textFileName)
    
    public String imageToText(BufferedImage image){
        String result = "";
        
        int height = image.getHeight();
        int width = image.getWidth();
        
        return result;
    
    }
    
}//end of class sampleSet
