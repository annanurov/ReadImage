
package readimage;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;
/**
 *
 * @author Bayram
 */
public class NeuralNet {
    int hiddenLayers ;
    int inputs;
    ArrayList<ArrayList<Perceptron>>  nodes;
    
    public NeuralNet(){}
    public NeuralNet(int inputs){
        this.inputs = inputs;
    }
    
    
    public NeuralNet(int features, int[] hiddenLayerSizes){
        this.hiddenLayers = hiddenLayerSizes.length;
        ArrayList<Perceptron> layer  = new ArrayList<Perceptron>();
        
        /**
         * add first hidden layer
         */
        if(hiddenLayerSizes.length > 0){
            for(int i = 0; i < hiddenLayerSizes[0]; i++){
                    layer.add(new Perceptron(features));
            }
            nodes.add(layer);
        }
        /**
         * add other hidden layers
         */
        if(hiddenLayerSizes.length > 1){
            for(int layerIndex = 1; layerIndex < hiddenLayerSizes.length; layerIndex++){
                layer  = new ArrayList<Perceptron>();

                for(int i = 0; i < hiddenLayerSizes[layerIndex]; i++){
                    layer.add(new Perceptron(hiddenLayerSizes[layerIndex-1]));
                }
                nodes.add(layer);
            }
        }
        /**
         * add output node
         */
        ArrayList<Perceptron> lastlayer = new ArrayList<Perceptron>();
        lastlayer.add(new Perceptron(hiddenLayerSizes[hiddenLayerSizes.length-1]));
        nodes.add(lastlayer);
    }//end of constructor NeuralNet(int features, int[] hiddenLayerSizes)
    
    
    public void initiateWeights() {
        for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).size(); j++){
                nodes.get(i).get(j).initiateWeights();
            }
        }
    }
    public void initiateWeights(boolean isRandom) {
    for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).size(); j++){
                nodes.get(i).get(j).initiateWeights(isRandom);
            }
        }
    }
    public void initiateWeights(double initialWeight, boolean isRandom) {
    for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.get(i).size(); j++){
                nodes.get(i).get(j).initiateWeights(initialWeight, isRandom);
            }
        }
    }
    
    public void file2data(String dataFileName) throws Exception {}

    public void file2data(
            String dataFileName, 
            boolean isTraining, 
            boolean hasHeader) 
            throws Exception {
        Perceptron p;
        ArrayList<Perceptron> firstRow = nodes.get(0);
            for(int i = 0; i < firstRow.size(); i++){
                p = firstRow.get(i);
                p.file2data(dataFileName);
                firstRow.set(i, p);
            }
            nodes.set(0, firstRow);
        }

    
    public void getY(){}
    public void getErrors(){}
    public void updateWeights(){}
    public void saveWeights(){}
    
    public void train(){}
    public void train(int iterations){}
    public void test(){}
    
}//end of call neuralnet
