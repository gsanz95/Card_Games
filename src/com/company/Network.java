package com.company;

import java.util.Random;

public class Network {
    private int numberOfInputs;
    private int numberOfHidden;
    private int numberOfOutputs;

    private double learningRate;
    private double confidence;

    private double[] biasBottom;
    private double[] biasTop;
    private double[][] weightBottom;
    private double[][] weightTop;

    private double[] outputValues;
    private double[] hiddenValues;

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

    private double getRandom() {
        Random randomGenerator = new Random();
        double randomNumber = (double)randomGenerator.nextInt(200);
        return (randomNumber/100) - 1;
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
                nodeSum += this.weightTop[i][j] * this.hiddenValues[i];

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

    void printNetwork(int networkNumber) {
        StringBuilder outputText = new StringBuilder();
        outputText.append(String.format("\n==================== Network %d ==========================\n\n", networkNumber));
        outputText.append(String.format("Input=%d Hidden=%d Output=%d",numberOfInputs, numberOfHidden, numberOfOutputs));

        outputText.append("\n\nWeights:");
        for (int i = 0; i < this.numberOfInputs; i++) {
            outputText.append(String.format("\nInputs -> Hidden[%d:%d] =", i, this.numberOfHidden));
            for (int j = 0; j < this.numberOfHidden; j++)
                outputText.append(" ").append(this.weightBottom[i][j]);
        }

        for (int i = 0; i < this.numberOfHidden; i++) {
            outputText.append(String.format("\nHidden -> Output[%d:%d] =", i, this.numberOfOutputs));
            for (int j = 0; j < this.numberOfOutputs; j++)
                outputText.append(" ").append(this.weightTop[i][j]);
        }

        outputText.append(String.format("\n\nBias:\nHidden[0:%d] =", this.numberOfHidden));
        for(int i = 0; i < this.numberOfHidden; i++) {
            outputText.append(" ").append(this.biasBottom[i]);
        }

        outputText.append(String.format("\nOutput[0:%d] =", this.numberOfOutputs));
        for(int i = 0; i < this.numberOfOutputs; i++)
            outputText.append(" ").append(this.biasTop[i]);

        outputText.append(String.format("\nConfidence: %.3f",this.confidence));
        System.out.println(outputText.toString());
    }
}
