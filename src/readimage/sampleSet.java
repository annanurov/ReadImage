package readimage;

import java.awt.Graphics2D;
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

    public sampleSet(String dir) {
        this.directoryName = dir;
        this.directory = new File(this.directoryName);
//        this.outputFileName
    }

    /**
     * gives defaults to convertSamplesToText(int colorThreshold, int
     * pixelThreshold, String textFileName, int batchSize)
     */
//    public  void convertSamplesToText(){
//        convertSamplesToText(600, 80, "sample1A", 10);
//    }
    /**
     * gives defaults except dir name to convertSamplesToText(int
     * colorThreshold, int pixelThreshold, String textFileName, int batchSize)
     */
    public void convertSamplesToText(String textFileName) {
        convertSamplesToText(600, 98, textFileName, 1);
    }

    /**
     * Default values: colorThreshold = 600, pixelThreshold = 5, textFileName =
     * "sample1A", batchSize = 10
     */
    public void convertSamplesToText(int colorThreshold, int pixelThreshold, String textFileName, int batchSize) {
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
        int color = 0;
        int currentBatchSum = 0;
        int sum = 0;
        File[] listOfFiles = directory.listFiles();
        File outputDataFile = new File(textFileName + "t" + pixelThreshold);
        String cons, vow;
        int batchNumber = 0;
        int vowelNameStartIndex = 0;
        int fileNameLength = 0;
        try {
            PrintWriter pen = new PrintWriter(outputDataFile);
            for (File file : listOfFiles) {
                if (!file.getName().endsWith(".jpg")) {
                    continue;
                }
                BufferedImage image = ImageIO.read(file);
                int H = image.getHeight();
                int W = image.getWidth();
                for (int x = 0; x <= W - batchSize; x += batchSize) {
                    for (int y = 0; y <= H - batchSize; y += batchSize) {
                        currentBatchSum = 0;
                        for (int i = 0; i < batchSize; i++) {
                            for (int j = 0; j < batchSize; j++) {
                                color = image.getRGB(x + i, y + j);
                                sum = (color & 0xFF) + ((color & 0xFF00) >> 8) + ((color & 0xFF0000) >> 16);

                                if (sum > colorThreshold) {
                                    currentBatchSum++;
                                }
//                                else
//                                    System.out.print(" sum " + sum + " ");
                            }
                        }
//                        System.out.print(batchNumber++);
//                        System.out.print(" ");
                        if (currentBatchSum > pixelThreshold) {
                            pen.write("1 ");
                        } else {
                            pen.write("0 ");
                        }
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

//                cons = file.getName().substring(9, file.getName().indexOf("X"));
                cons = file.getName().substring(9, file.getName().length()-4);
//                cons = p2[1];
//                cons = parts[0].split("-")[1];
//                System.out.print(file.getName().substring(9, file.getName().indexOf("X")) + " ");
                System.out.print(cons + " ");
//                    pen.write(" ");
//                vowelNameStartIndex = file.getName().indexOf("X");
//                fileNameLength = file.getName().length();

//                vow = file.getName().substring(vowelNameStartIndex + 1, fileNameLength - 4);
//                System.out.print(vow + " ");

                if (file.getName().contains("B")) {
                    cons = Integer.toString(Integer.valueOf(cons) + 16);
                }
//                pen.write(Integer.valueOf(cons));
                pen.write(cons);
//                pen.write(" ");
//                pen.write(Integer.valueOf(vow));
//                pen.write(vow);
                pen.write(" \n");
                System.out.print(file.getName() + "\n");
//                batchNumber = 0;
            }//end of for, file

            pen.close();

        }//end of try
        catch (Exception ex) {
            ex.printStackTrace();
        }//end of catch

    }//end of convertSamplesToText(int threshold, String textFileName)

    public String imageToText(BufferedImage image) {
        String result = "";

        int height = image.getHeight();
        int width = image.getWidth();

        return result;

    }

    public void cutOffMargins(String noMarginExtension, int colorTreshold) {
        File[] listOfFiles = directory.listFiles();
        int pixel;

        for (File file : listOfFiles) {
            if (file.getName().startsWith(noMarginExtension)) {
                continue;
            }
            System.out.print(file.getName());
            System.out.print(" ");
            try {
                boolean rowEmpty = true;
                boolean colEmpty = true;

                boolean foundTopMargin = false;
                boolean foundLeftMargin = false;
                int TMargin = 0;
                int BMargin = 0;
                int RMargin = 0;
                int LMargin = 0;
                BufferedImage image = ImageIO.read(file);
                int H = image.getHeight();
                int W = image.getWidth();

//find top and bottom margins:

                for (int i = 0; i < H; i++) {
                    for (int j = 0; j < W; j++) {
                        pixel = image.getRGB(j, i);
                        if ((pixel & 0xFF) + ((pixel & 0xFF00) >> 8) + ((pixel & 0xFF0000) >> 16) < colorTreshold) {
                            rowEmpty = false;
                            foundTopMargin = true;
                        }
                    }//end of for, j
                    if (rowEmpty) {
//                        System.out.println("E ");
                        if (!foundTopMargin) {
                            TMargin++;
                        } else {
                            break;
                        }
                    }
                    BMargin++;
                    rowEmpty = true;
                }//end of for, i (rows)

///find riht and left margins:
                for (int j = 0; j < W; j++) {
                    for (int i = 0; i < H; i++) {
                        pixel = image.getRGB(j, i);
                        if ((pixel & 0xFF) + ((pixel & 0xFF00) >> 8) + ((pixel & 0xFF0000) >> 16) < colorTreshold) {
                            colEmpty = false;
                            foundLeftMargin = true;
                        }//end of if
                    }//end of for, i(rows)
                    if (colEmpty) {
                        if (!foundLeftMargin) {
                            LMargin++;
                        } else {
                            break;
                        }
                    }
                    RMargin++;
                    colEmpty = true;
                }//end of for, j (cols)

                //save cropped image to new file

                System.out.println(W + " " + RMargin + " " + LMargin);// + " " + ( H - TMargin - LMargin));
                System.out.println(H + " " + TMargin + " " + BMargin);// + " " + ( H - TMargin - LMargin));
//                System.out.println((W - RMargin - LMargin ) + " " + ( H - TMargin - LMargin));
//                System.out.println((W - RMargin - LMargin ) + " " + ( H - TMargin - LMargin));
//                BufferedImage nextImage = new BufferedImage(W - RMargin - LMargin, H - TMargin - LMargin, image.getType());
                BufferedImage nextImage = new BufferedImage(RMargin - LMargin, BMargin - TMargin, image.getType());

                for (int y = TMargin; y < BMargin; y++) {
                    for (int x = LMargin; x < RMargin; x++) {
                        pixel = image.getRGB(x, y);
                        nextImage.setRGB(x - LMargin, y - TMargin, pixel);
                    }
                }
                //saveImage(nextImage, outputImageName);
                File outputfile = new File(noMarginExtension + file.getName());
                ImageIO.write(nextImage, "jpg", outputfile);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }//end of for, file

    }//end of cutOffMargins

    public void resizeAll(String prefixToRemove) {
        File[] files = directory.listFiles();
        int minW = 10000;
        int minH = 10000;
        for (File f : files) {
            System.out.println(f.getName());
            if (!f.getName().startsWith(prefixToRemove))
                continue;
            try {
                BufferedImage image = ImageIO.read(f);
                if (minW > image.getWidth()) {
                    minW = image.getWidth();
                }
                if (minH > image.getHeight()) {
                    minH = image.getHeight();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }//end of for, file
        for (File f : files) {
            if(! f.getName().startsWith(prefixToRemove))
                continue;
            try {
                            System.out.println(f.getName());

                BufferedImage image = ImageIO.read(f);
                BufferedImage newImage = resize(image, minW, minH);
                String oldname = f.getName();
                int L = oldname.length();
                File outputfile = new File("resized" +
                        f.getName().substring(prefixToRemove.length()-1, L));
                ImageIO.write(newImage, "jpg", outputfile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }//end of for, file

    }//end of resizeAll

    private BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage res = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = res.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null); 
        g.dispose();
        return res;
    }
}//end of class sampleSet
