package com.company;

class NetworkController {

    Network firstNetwork;
    Network secondNetwork;
    int networkConclusion;
    int totalRight;
    int totalWrong;
    int desiredOutput;
    boolean changedWeights;

    public NetworkController() {

        this.firstNetwork = new Network(2,5,2,0.3);
        this.secondNetwork = new Network(2,5,2,0.3);
        this.totalRight = 0;
        this.totalWrong = 0;
        this.changedWeights = true;
    }

    int getNetworkConclusion() {
        return this.networkConclusion;
    }

    int runFirstNetwork(double[] inputs, int idealAction){
        this.networkConclusion = firstNetwork.predictAction(inputs);

        if(changedWeights == true)
            firstNetwork.printNetwork();

        if(this.networkConclusion == idealAction)
            totalRight += 1;
        else {
            totalWrong += 1;

            firstNetwork.adjustNetwork(inputs, idealAction);
            if(changedWeights == true) {
                firstNetwork.printNetwork();
                changedWeights = false;
            }
        }
        return -1;
    }
}
