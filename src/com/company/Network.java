package com.company;

import java.util.Random;

public class Network {
    int numberOfInputs;
    int numberOfHidden;
    int numberOfOutputs;

    double learningRate;
    double confidence;

    double[] biasBottom;
    double[] biasTop;
    double[][] weightBottom;
    double[][] weightTop;

    double[] outputValues;
    double[] hiddenValues;


    public Network(int numberOfInputs, int numberOfHidden, int numberOfOutputs, double learningRate) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfHidden = numberOfHidden;
        this.numberOfOutputs = numberOfOutputs;
        this.learningRate = learningRate;

        this.weightBottom = new double[numberOfInputs][numberOfHidden];
        for (int i = 0; i < numberOfInputs; i++)
            for (int j = 0; j < numberOfHidden; j++)
                this.weightBottom[i][j] = getRandom();

        this.weightTop = new double[numberOfHidden][numberOfOutputs];
        for (int i = 0; i < numberOfHidden; i++)
            for (int j = 0; j < numberOfOutputs; j++)
                this.weightTop[i][j] = getRandom();

        this.biasBottom = new double[numberOfHidden];
        for(int i = 0; i < numberOfHidden; i++)
            this.biasBottom[i] = getRandom();

        this.biasTop = new double[numberOfOutputs];
        for(int i = 0; i < numberOfOutputs; i++)
            this.biasTop[i] = getRandom();

        this.hiddenValues = new double[numberOfHidden];
        this.outputValues = new double[numberOfOutputs];
    }

    double getRandom() {
        Random randomGenerator = new Random();
        return (double)(randomGenerator.nextInt(2) - 1);
    }

    int predictAction(double[] inputs) {
        double nodeSum;
        double nextHighestSum;

        for(int j = 0; j < this.numberOfHidden; j++){
            nodeSum = 0.0;

            for(int i = 0; i < this.numberOfInputs; i++)
                nodeSum += this.weightBottom[i][j] * inputs[i];

            nodeSum += this.biasBottom[j];
            this.hiddenValues[j] = 1.0/ (1.0 + Math.exp(-nodeSum));
        }

        for(int j = 0; j < this.numberOfOutputs; j++){
            nodeSum = 0.0;

            for(int i = 0; i < this.numberOfHidden; i++)
                nodeSum += this.weightTop[i][j] * inputs[i];

            nodeSum += this.biasTop[j];
            this.outputValues[j] = 1.0/ (1.0 + Math.exp(-nodeSum));
        }

        int activationPosition = 0;
        for(int i = 0; i < this.numberOfOutputs; i++)
            if(i == 0 || this.outputValues[i] > this.outputValues[activationPosition])
                activationPosition = i;

        nextHighestSum = -1.0;
        for(int i = 0; i < this.numberOfOutputs; i++)
            if(i != activationPosition)
                if(nextHighestSum < 0.0 || this.outputValues[i] > nextHighestSum)
                    nextHighestSum = this.outputValues[i];

        this.confidence = this.outputValues[activationPosition] - nextHighestSum;

        return activationPosition;
    }

    void adjustNetwork(double[] inputs, int idealAction){
        double nodeSum;
        double delta;

        for(int j = 0; j < this.numberOfOutputs; j++) {
            nodeSum = (j == idealAction) ? 1.0 : 0.0;
            nodeSum -= this.outputValues[j];

            delta = nodeSum * this.outputValues[j] * (1 - this.outputValues[j]);

            for(int i = 0; i < this.numberOfHidden; i++)
                this.weightTop[i][j] += this.learningRate * delta * this.hiddenValues[i];

            this.biasTop[j] += this.learningRate * delta;

            for(int i = 0; i < this.numberOfInputs; i++)
                this.weightBottom[i][j] += this.learningRate * delta * inputs[i];

            this.biasBottom[j] += this.learningRate * delta;
        }
    }
}
