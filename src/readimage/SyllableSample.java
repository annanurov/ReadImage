package readimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 *
 * @author Bayram
 */
public class SyllableSample {

    BufferedImage image;
    int H;
    int W;
    int default_color_threshold = 100;

    public SyllableSample() {
    }

    public SyllableSample(String imageFile) throws IOException {
        this.getImage(imageFile);
        H = image.getHeight();
        W = image.getWidth();
        System.out.println(H + " " + W);
    }//end of constructor

    public void getImage(String imageFile) throws IOException {
//        File file = new File("your_file.jpg");
        File file = new File(imageFile);
        image = ImageIO.read(file);
        //H = image.getHeight();
        //W = image.getWidth();
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

    /**
     * Put all pixel values in one row. Genius!
     */
    public String returnPixels() {

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

    /**
     * Given pixel coord., return an array of thee integers[red, green, blue]
     */
    public int[] getColors(int clr) {

        int[] res = new int[3];
        res[0] = (clr & 0x00ff0000) >> 16;//red
        res[1] = (clr & 0x0000ff00) >> 8;//green
        res[2] = clr & 0x000000ff;//blue
        return res;
    }//end of getColors

    /**
     * Return 1 if pixel is darker than threshold. Return 0 otherwise.
     */
    public int getBW(int clr, int threshold) {
        int res = 0;
        res += (clr & 0x00ff0000) >> 16;//red
        res += (clr & 0x0000ff00) >> 8; //green
        res += clr & 0x000000ff;       //blue
        if (res / 3 > threshold) {
//            System.out.println(clr);
            return 0;
        } else {
            return 1;
        }
    }//end of getBW

    /**
     * Return 1 if pixel is darker than default threshold. Return 0 otherwise.
     */
    public int getBW(int clr) {
        return getBW(clr, default_color_threshold);
    }//end of getBW

    /**
     * Extract each sample. Save to separate file.
     */
    public void divideImage(int rows, int cols, String sampleIconName /*, int rowIndex, int colIndex*/) throws Exception {
        int verticalOffset = 0;
        int horizontalOffset = 4;

        int newH = this.H / rows; //int sylHeight = this.H / (rows - 1);
        int newW = this.W / cols; //int sylWidth = this.W / (cols - 1);

        int LMargin = 6;
        int RMargin = 8;
        int UMargin = 1;
        int DMargin = 1;
        System.out.println(rows + " " + cols);
        System.out.println(this.H / rows + " " + this.W / cols);
        BufferedImage nextImage = new BufferedImage(newW - RMargin - LMargin, newH - DMargin - UMargin, this.image.getType());
        int K;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                for (int i1 = UMargin; i1 < newH - DMargin; i1++) {
                    for (int j1 = LMargin; j1 < newW - RMargin; j1++) {

                        K = this.image.getRGB(j * newW + horizontalOffset * (int) (j / 13) + horizontalOffset * (int) (j / 20) + j1,
                                i * newH + verticalOffset + i1);
                        //if K = very dark, set (j1,i1) to black, otherwise to white:
//                        //normalize color to B/W
//                        if( compareColor(0x000000, K) < 300)
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0x000000);
//                        else
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
//                            nextImage.setRGB(j1 - LMargin, i1 - UMargin, 0xFFFFFF);
                        nextImage.setRGB(j1 - LMargin, i1 - UMargin, K);
//                        int K =  this.image.getRGB(i * sylHeight + i1, j * sylWidth + j1); nextImage.setRGB(i1, j1, K);

                    }//end of for,j1
                }//end of for,i1
                System.out.println("Building image " + i + " X " + j);
                String outputImageName = sampleIconName + Integer.toString(i) + "X" + Integer.toString(j) + ".jpg";
                //savetoFile:
                saveImage(nextImage, outputImageName);

            }//end of for, j
        }//end of for, i
    }//end of divideImage

    public void divideImageAndSaveToTextFile() throws Exception {
        divideImageAndSaveToTextFile(10, 26, "KHSample", "textImage.txt");
    }

    public void divideImageAndSaveToTextFile(int rows, int cols) throws Exception {
        divideImageAndSaveToTextFile(rows, cols, "KHSample", "textImage.txt");

    }

    /**
     * Get each pixel as 1/0 value. Save each sample as a line in a text file.
     */
    public void divideImageAndSaveToTextFile(int rows, int cols, String sampleIconName, String textDataFileName) throws Exception {
        //currently: rows = 16
        //           cols = 31
        //           textDataFileName = "KHSyl"

        /**
         * offset from the edge of paper:
         */
        int verticalOffset = 0;
        int horizontalOffset = 3;

        String separator = " ";

        /**
         * height of a sample:
         */
        int sylHeight = 58;
        /**
         * width of a sample:
         */
        int sylWidth = 88; 

        /**
         * Spare distance between sample rows:
         */
        int gap = 57; 


        /**
         * Am I at the top row?
         */
        boolean topRow = true;
        int extraGap = 0;

        /**
         * Thickness of line in the grid
         */
        int lineThickness = 2;//4;//10;
        /**
         * big row height
         */
//        int bigRowH = gap + 2 * sylHeight + 3 * lineThickness;
        int bigRowH = 182;
       
        int initX = 225;//x-coord of the topleft corner of topleft syllable
        int initY = 137;//y-coord of the topleft corner of topleft syllable
        
        
        /**
         * margins of each cell with a sample
         */
        int LMargin = 5;//left margin
        int RMargin = 5;//right margin
        int UMargin = 3;//upper margin
        int DMargin = 7;//"down"er margin
        
        System.out.println(rows + " " + cols);
        
        //BufferedImage nextImage = new BufferedImage(sylWidth - RMargin - LMargin, sylHeight - DMargin - UMargin, this.image.getType());
        File textData = new File(textDataFileName);
//        File textDataShort = new File( textDataFileName);
//        File textDataNormal = new File("GAPS-NORMAL" + textDataFileName);
        PrintWriter pen = new PrintWriter(textData);
//        PrintWriter penSHORT = new PrintWriter(textDataShort);
//        PrintWriter penNormal = new PrintWriter(textDataNormal);

        /**
         * normal image dimensions:
         */
        int nX = 50;
        int nY = 50;

//        BufferedImage current = new BufferedImage(sylWidth - RMargin, sylHeight - DMargin, this.image.getType());

        int firstCon = 0x1780;// Unicode for "Tha" // use 0x1780 for "Ga"
        int firstVow = 0x17b6;// Unicode for "a"
//        int BWthreshold1 = 700;
        int BWthreshold2 = 100;
        
        int K, nextPixel;
        int xx, yy; //cord's of pixel within each syllable on big image
        
//        for (int i = 0; i < rows; i++) {
        //for each consosnant:
        for (int i = 0; i < rows / 2; i++) {
            //for each vowel:
            for (int j = 0; j < cols; j++) {
                for (int t = 0; t < 2; t++) {
//                    if( firstCon + i * 2 + t > 0x17a1 ) continue;//comment this out for the first part of data
//                if(topRow){
//                    extraGap = gap;
//                } else{
//                    extraGap = gap;
//                }
//                topRow = ! topRow;
                    int i1, j1;//Pixel counters
                    //create an image object:
                    BufferedImage nextImage = new BufferedImage(sylWidth, sylHeight, this.image.getType());
                    //for each pixel horizontally:
                    for (j1 = 0; j1 < sylWidth; j1++) {
                        //for each pixel vertically:
                        for (i1 = 0; i1 < sylHeight; i1++) {
                            try {
//                                yy = i1 + sylHeight * t + (bigRowH + 40) * i + (lineThickness + 10) * t + initY;
                                yy = i1 + i * bigRowH + t *(sylHeight + lineThickness)  + initY ;
                                yy -= (j / 27)*4 ;
//                                xx = j1 + sylWidth * j + (lineThickness + 2) * j + initX;
                                xx = j1 + j * ( sylWidth + lineThickness)  + initX;
                                
                                //adjust x coord to delet vertical border:
                                xx -= j1/5;

                                //if corrdinate is legal:
                                if((j1 > LMargin) && (j1 < sylWidth - RMargin) && (i1 > UMargin /*+ (j/22)*4*/) && (i1 < sylHeight - DMargin /*- (j/26)*4*/)){
                                    //get the pixel at current coordinate:
                                    K = this.image.getRGB(xx, yy /*- (j/6) * lineThickness*/);
                                    nextPixel = getBW(K, BWthreshold2);
                                } else {
                                    //else: put a white spot:
                                    K = 0xFFFFFFFF;
                                    nextPixel = 0;
                                }
                                //save the coordinate to text file:
                                nextImage.setRGB(j1, i1, K);
                                pen.print(nextPixel);
                                pen.print(" ");
                            } catch (Exception e) {
                                System.out.println("Exception caught!" + j + " " + i + " " + j1 + " " + i1);
                                e.printStackTrace();
                                System.exit(0);
                            }
                        }//end of for,j1
                    }//end of for,i1
                    pen.print(String.valueOf(firstCon + i * 2 + t));
                    pen.print(" ");
                    pen.print(String.valueOf(firstVow + j));
                    pen.print("\n");
                    
                    System.out.print("Building image " + i + " X " + j);
                    String outputImageName = sampleIconName + Integer.toString(i * 2 + t + 16) + "X" + Integer.toString(j) + ".jpg";
                    
                    //savetoFile:
                    System.out.println("\t\t saving: " + outputImageName);
                    saveImage(nextImage, outputImageName);
                }//end of for, t
            }//end of for, j
        }//end of for, i
        pen.close();
//        penSHORT.close();
    }//end of divideImageAndSaveToTextFile


    public void normalize(BufferedImage current, int nX, int nY, PrintWriter penNormal) {
        /**
         * Remove all-white rows and columns from the margins. Resize the image
         * to nX by nY pixels.
         */
        boolean whiteCol = true;
        boolean whiteRow = true;
        int counter = 0;
        int max = 0;
        String[] line = new String[current.getHeight()];
        boolean[] lineEmpty = new boolean[current.getHeight()];
        int pixel;
        for (int i = 0; i < current.getHeight(); i++) {
            for (int j = 0; j < current.getWidth(); j++) {
                whiteRow = true;
                counter = 0;
                line[i] = "";
                lineEmpty[i] = true;
                pixel = current.getRGB(j, i);
                if (pixel == 0x000000) {
                    whiteRow = false;
                    if (!whiteRow) {
                        lineEmpty[i] = false;
                        if (pixel == 0x000000) {
                            line[i] = line[i] + "1";
                        } else {
                            line[i] = line[i] + "0";
                        }
                        counter++;
                    }//end of if (!whiteRow)
                }//end of  if (pixel == 0x000000)
            }//edn of for, j
            if (max < counter) {
                max = counter;
            }
//            if(max < line[i].length()) max = line[i].length();

        }//end of for, i
        int margin = current.getWidth() - max;

        for (int i = 0; i < current.getHeight(); i++) {
            for (int j = margin; j < current.getWidth(); j++) {
                if (!lineEmpty[i]) {
                    penNormal.write(line[i].charAt(j));
                    penNormal.write(",");
                }
            }
        }

    }//end of normalize()

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
        int sameCount;
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
}//end of class SyllableSample

