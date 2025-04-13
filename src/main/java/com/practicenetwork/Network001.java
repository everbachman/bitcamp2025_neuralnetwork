package com.practicenetwork;
import java.util.Random;

public class Network001 {
    // [number of layers (numHidden + 1)][dest neuron][source neuron]
    private double[][][] w;
    // [layer (numHidden + 1)][num neurons]
    private double[][] b;
    // [layer (numHidden + 1)][num neurons] neuron values, last layer stores outputs
    private double[][] a;
    private double[][] da;

    // 0.1 (standard)
    private double learningRate;


    public Network001(int numInputs, int numOutputs, int numHidden, int hiddenSize, double learningRate) {
        this.learningRate = learningRate;

        // Initialize weights and biases
        w = new double[numHidden + 1][][];
        b = new double[numHidden + 1][];
        a = new double[numHidden + 2][];
        da = new double[numHidden + 1][];

        for (int i = 0; i <= numHidden; i++) {
            int inputSize = (i == 0) ? numInputs : hiddenSize;
            int outputSize = (i == numHidden) ? numOutputs : hiddenSize;

            w[i] = new double[outputSize][inputSize];
            b[i] = new double[outputSize];
            da[i] = new double[outputSize];
            a[i] = new double[inputSize];
            if (i == numHidden) a[i + 1] = new double[numOutputs];

            initializeWeights(w[i]);
        }
    }

    private void initializeWeights(double[][] weights) {
        Random r = new Random();
        // Number of input neurons
        int n_in = weights[0].length; 
        // Number of output neurons
        int n_out = weights.length;  
        double limit = Math.sqrt(6.0 / (n_in + n_out)); // Xavier initialization range

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                // Xavier initialization: Uniform distribution
                weights[i][j] = -limit + 2 * limit * r.nextDouble();
            }
        }
    }

    public double[] run(double[] input) {
        
        for (int i = 0; i < a[0].length; i++) {
            a[0][i] = input[i];
        }
        // iterate through each layer of neurons
        for (int i = 1; i < a.length; i++) {

            // iterates through each neuron in the current layer
            for (int j = 0; j < a[i].length; j++) {

                // sums the product of the previous layer of neurons with their weights and adds the bias
                // then puts it all through the activation function (in this case sigmoid)
                double z = 0;

                for (int n = 0; n < a[i - 1].length; n++) {
                    z += a[i - 1][n] * w[i - 1][j][n];
                }
                z += b[i - 1][j];

                double zz = aFunc(z);
                da[i - 1][j] = zz * (1 - zz);
                a[i][j] = zz;
            }
        }

        // returns the final layer of neurons which is the output
        return a[a.length - 1];
    }

    private double aFunc(double x) {
        return 1 / (1 + Math.exp(0 - x));
    }


    public void train(double[][] inputs, double[][] y) {

        // Initialize wUpdates to accumulate weight updates
        double[][][] wUpdates = new double[w.length][][];
        for (int i = 0; i < w.length; i++) {
            wUpdates[i] = new double[w[i].length][];
            for (int j = 0; j < w[i].length; j++) {
                wUpdates[i][j] = new double[w[i][j].length];
            }
        }

        // create array that will store the temporary variables
        double[][] e_z = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            e_z[i] = new double[a[i].length];
        }
        double[][] bias = new double[b.length][];
        for (int i = 0; i < b.length; i++) {
            bias[i] = new double[b[i].length];
        }

        // iterates through each set of training data in the mini batch
        for (int m = 0; m < inputs.length; m++) {
            run(inputs[m]);

            // calculating the change in total error with respect to each weight
            double[][][] wGradient = new double[w.length][][];
            for (int i = 0; i < w.length; i++) {
                wGradient[i] = new double[w[i].length][];
                for (int j = 0; j < w[i].length; j++) {
                    wGradient[i][j] = new double[w[i][j].length];
                }
            }

            // fills gradient
            fillGradient(e_z, wGradient, y[m]);
            
            // Accumulate weight updates in wUpdates
            for (int l = 0; l < w.length; l++) {
                for (int d = 0; d < w[l].length; d++) {
                    for (int s = 0; s < w[l][d].length; s++) {
                        wUpdates[l][d][s] += -learningRate * wGradient[l][d][s];
                    }
                }
            }

            // Accumulate bias updates
            for (int l = 0; l < b.length; l++) {
                for (int d = 0; d < b[l].length; d++) {
                    bias[l][d] += -learningRate * e_z[l + 1][d];
                }
            }

        }

        // Average the weight updates
        for (int l = 0; l < w.length; l++) {
            for (int d = 0; d < w[l].length; d++) {
                for (int s = 0; s < w[l][d].length; s++) {
                    wUpdates[l][d][s] /= inputs.length;
                }
            }
        }

        // Apply the averaged updates to the weights
        for (int l = 0; l < w.length; l++) {
            for (int d = 0; d < w[l].length; d++) {
                for (int s = 0; s < w[l][d].length; s++) {
                    w[l][d][s] += wUpdates[l][d][s];
                }
            }
        }

        // Average the bias updates
        for (int l = 0; l < b.length; l++) {
            for (int d = 0; d < b[l].length; d++) {
                bias[l][d] /= inputs.length;
            }
        }

        // Apply the averaged updates to the biases
        for (int l = 0; l < b.length; l++) {
            for (int d = 0; d < b[l].length; d++) {
                b[l][d] += bias[l][d];
            }
        }
    }

    private void fillGradient(double[][] e_z, double[][][] wGradient, double[] y) {
        // derivative of overall error with respect to each weight
        // for first layer
        for (int d =  0; d < w[w.length - 1].length; d++) {
            for (int s = 0; s < w[w.length - 1][d].length; s++) {

                // stores the first layer of product of overall error with respect to z (o/a * a/z)
                e_z[e_z.length - 1][d] = (a[a.length - 1][d] - y[d]) * da[da.length - 1][d];
                wGradient[w.length - 1][d][s] = e_z[e_z.length - 1][d] * a[w.length - 1][s];
            }
        }

        // for other layers
        for (int l = w.length - 2; l >= 0; l--) {
            for (int d =  0; d < w[l].length; d++) {
                for (int s = 0; s < w[l][d].length; s++) {
                    double e_a = 0;

                    // itterates through the previous layer's --- and multiplies each by the value of the edge
                    // conncecting that element to the current one
                    for (int nd = 0; nd < a[l + 2].length; nd++) {
                        e_a += e_z[l + 1][nd] * w[l + 1][nd][s];
                    }

                    // ^^ all good
                    
                    //asdfasdfasdf
                    e_z[l][s] = e_a * da[l][s];
                    double e_w = e_z[l][s] * a[l][s];
                    wGradient[l][d][s] = e_w;
                }
            }
        }

    }

}