/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readimage;

/**
 *
 * @author Bayram
 */
import java.io.*;
//import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
//import java.util.ArrayList;

public class GetPixelColor {

    BufferedImage image;
    int H;
    int W;

    public GetPixelColor(String imageFile) throws IOException {
        this.getImage(imageFile);
        H = image.getHeight();
        W = image.getWidth();
        System.out.println(H + " " + W);
    }

    public void getImage(String imageFile) throws IOException {
//        File file = new File("your_file.jpg");
        File file = new File(imageFile);
        image = ImageIO.read(file);
    }

    public void getPixels() {
        // Getting pixel color by position x and y 
        H = image.getHeight();
        W = image.getWidth();

        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                int clr = image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
//                if (red != 255 && green != 255 && blue !=255){
                if (red < 100 && green < 100 && blue < 100) {
                    System.out.print("R = " + red);
                    System.out.print("\tG = " + green);
                    System.out.print("\tB = " + blue);
                    System.out.print("\tat\tX: " + x);
                    System.out.println("\tY: " + y);
                }//end of if 
            }//end of for, y
        }//end of for, x
        System.out.println("W: " + W + "H: " + H);
    }//end of getPixels /////taken from http://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages

    public String returnPixels() {
        // Getting pixel color by position x and y 
        H = image.getHeight();
        W = image.getWidth();
        String res = "";
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                int clr = image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
//                if (red != 255 && green != 255 && blue !=255){
//                if (red < 100 && green < 100 && blue < 100) {
//                    System.out.print("R = " + red);
//                    System.out.print("\tG = " + green);
//                    System.out.print("\tB = " + blue);
//                    System.out.print("\tat\tX: " + x);
//                    System.out.println("\tY: " + y);
//                }//end of if 
                res = res + String.valueOf(red) + " " + String.valueOf(green) + " " + String.valueOf(blue) + " ";
            }//end of for, y
        }//end of for, x
//        System.out.println("W: " + W + "H: " + H);
        return res;
    }

    public int[] getColors(int clr) {
        int[] res = new int[3];
        res[0] = (clr & 0x00ff0000) >> 16;//red
        res[1] = (clr & 0x0000ff00) >> 8;//green
        res[2] = clr & 0x000000ff;//blue
        return res;
    }//end of getColors

    public void divideImage(int rows, int cols/*, int rowIndex, int colIndex*/) throws Exception {
        int verticalOffset = 0;
        int horizontalOffset = 3;

        int newH = this.H / rows - 1; //int newH = this.H / (rows - 1);
        int newW = this.W / cols - 1; //int newW = this.W / (cols - 1);

        int LMargin = 3;
        int RMargin = 7;
        int UMargin = 3;
        int DMargin = 7;
        System.out.println(rows + " " + cols);
        BufferedImage nextImage = new BufferedImage(newW - RMargin - LMargin, newH - DMargin - UMargin, this.image.getType());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                for (int i1 = UMargin; i1 < newH - DMargin; i1++) {
                    for (int j1 = LMargin; j1 < newW - RMargin; j1++) {

                        int K = this.image.getRGB(j * newW + horizontalOffset + j1, i * newH + verticalOffset + i1);
                        //if K = very dark, set (j1,i1) to black, otherwise to white:
//                        //normalize color to B/W
//                        if( compareColor(0x000000, K) < 300)
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0x000000);
//                        else
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
                        nextImage.setRGB(j1 - LMargin, i1 - UMargin, K);
//                        int K =  this.image.getRGB(i * newH + i1, j * newW + j1); nextImage.setRGB(i1, j1, K);

                    }//end of for,j1
                }//end of for,i1
                System.out.println("Building image " + i + " X " + j);
                String outputImageName = "sample" + Integer.toString(i) + "X" + Integer.toString(j) + ".jpg";
                //savetoFile:
                saveImage(nextImage, outputImageName);

            }//end of for, j
        }//end of for, i
    }//end of divideImage

    public void divideImageAndSaveToTextFile() throws Exception {
        divideImageAndSaveToTextFile(10, 26, "textImage.txt");
    }

    public void divideImageAndSaveToTextFile(int rows, int cols) throws Exception{
        divideImageAndSaveToTextFile( rows,  cols, "textImage.txt") ; 
    
    }
    public void divideImageAndSaveToTextFile(int rows, int cols/*, int rowIndex, int colIndex*/, String textDataFileName) throws Exception {
        int verticalOffset = 0;
        int horizontalOffset = 3;

        int newH = this.H / rows - 1; //int newH = this.H / (rows - 1);
        int newW = this.W / cols - 1; //int newW = this.W / (cols - 1);

        int LMargin = 3;
        int RMargin = 7;
        int UMargin = 3;
        int DMargin = 7;
        System.out.println(rows + " " + cols);
        //BufferedImage nextImage = new BufferedImage(newW - RMargin - LMargin, newH - DMargin - UMargin, this.image.getType());

        File textData = new File(textDataFileName);
        PrintWriter pen = new PrintWriter(textData);
        int firstLetter = 65;// Unicode for "A"

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                for (int i1 = UMargin; i1 < newH - DMargin; i1++) {
                    for (int j1 = LMargin; j1 < newW - RMargin; j1++) {

                        int K = this.image.getRGB(j * newW + horizontalOffset + j1, i * newH + verticalOffset + i1);
//                        int L = this.image.getRGB(i, i1, i1, i1, ints, i1, i1)
                        //if K = very dark, set (j1,i1) to black, otherwise to white:
//                        //normalize color to B/W
//                        if( compareColor(0x000000, K) < 300)
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0x000000);
//                        else
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
                        //nextImage.setRGB(j1 - LMargin, i1 - UMargin, K);
                        int red = (K & 0x00ff0000) >> 16;
                        int green = (K & 0x0000ff00) >> 8;
                        int blue = K & 0x000000ff;
                        pen.print(String.valueOf(red) + " ");
                        pen.print(String.valueOf(green) + " ");
                        pen.print(String.valueOf(blue) + " ");

//                        int K =  this.image.getRGB(i * newH + i1, j * newW + j1); nextImage.setRGB(i1, j1, K);

                    }//end of for,j1

                }//end of for,i1

                pen.println(Character.toString((char) (j + firstLetter)));

                System.out.println("Building image " + i + " X " + j);
                //String outputImageName = "sample" + Integer.toString(i) + "X" + Integer.toString(j) + ".jpg";
                //savetoFile:
                //saveImage(nextImage, outputImageName);

            }//end of for, j
        }//end of for, i
        pen.close();
    }//end of divideImage

    public void saveImage(BufferedImage img, String fileName) throws Exception {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(img, "jpg", outputfile);
        }//end of try 
        catch (IOException e) {
            System.out.printf(e.toString());
        }//end of catch
    }//end of saveImage

    public void removeLines(BufferedImage nextImage) {
        int h = nextImage.getHeight();
        int w = nextImage.getWidth();
        int sameCount = 0; //"assigned value never used"???
        for (int i = 0; i < h; i++) {
            int entryColor = nextImage.getRGB(1, i);
            if (compareColor(0xFFFFFF, entryColor) < 0x30) {
                continue;
            }
            sameCount = 0;
            for (int j = 0; j < w; j++) {
                if (compareColor(nextImage.getRGB(j, i), entryColor) < 5) {
                    sameCount++;
                }

            }//end of for, j
            if (w - sameCount < 5) //repaint line:
            {
                for (int j = 0; j < w; j++) {
                    nextImage.setRGB(j, i, 0x000000);
                }//end of for, j
            }
        }//end of for, i
    }//end of removeLines

    public int compareColor(int C1, int C2) {

        int a1 = (0xff000000 & C1) >>> 24;
        int r1 = (0x00ff0000 & C1) >> 16;
        int g1 = (0x0000ff00 & C1) >> 8;
        int b1 = (0x000000ff & C1);

        int a2 = (0xff000000 & C2) >>> 24;
        int r2 = (0x00ff0000 & C2) >> 16;
        int g2 = (0x0000ff00 & C2) >> 8;
        int b2 = (0x000000ff & C2);

        return /*Math.abs(a1-a2) +*/ Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }//end of compareColor

    public void convertSamplesToTextFile() throws Exception {
        convertSamplesToTextFile("sample", 10, 26, "textImage.txt");
    }//end of convertSamplesToTextFile()

    public void convertSamplesToTextFile(String sampleFileHeader, int numberOfABCs, int numberOfLetters, String dataFileName) throws Exception {
        String nextSampleFile;
        GetPixelColor imageX;
        File textualImage = new File("textImage.txt");
        PrintWriter pen = new PrintWriter(textualImage);

        ///for all images named like "sample(.d+)X(.d+)\.jpg"
        for (int i = 0; i < numberOfABCs; i++) {
            for (int j = 0; j < numberOfLetters; j++) {
                ///convert image to text and write the text to "textImage.txt"
                nextSampleFile = sampleFileHeader + String.valueOf(i) + "X" + String.valueOf(j) + ".jpg";

                imageX = new GetPixelColor(nextSampleFile);
                pen.print(imageX.returnPixels());

                //convert j to corresponding letter : 0=A 1=B ...
                ///add the label (letter's UNICODE)
                pen.println(Character.toString((char) (j + 65)));

            }//end of for, j    
        }//end of for, i   
        pen.close();
    }//end of convertSamplesToTextFile
}//end of class GetPixelColor.java

