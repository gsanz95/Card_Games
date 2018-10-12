package com.company;

class NetworkController {

    Network[] groupedNetworks;
    int networkConclusion;
    int totalRight;
    int totalWrong;
    boolean changedWeights;

    public NetworkController() {

        this.groupedNetworks = new Network[2];
        this.groupedNetworks[0] = new Network(2, 5, 2, 0.3);
        this.groupedNetworks[1] = new Network(2, 5, 2, 0.3);
        this.totalRight = 0;
        this.totalWrong = 0;
        this.changedWeights = true;
    }

    void runNetwork(double[] inputs, int idealAction, int networkNumber) {
        this.networkConclusion = groupedNetworks[networkNumber].predictAction(inputs);

        if (changedWeights == true)
            groupedNetworks[networkNumber].printNetwork(networkNumber);

        if (this.networkConclusion == idealAction)
            totalRight += 1;
        else {
            totalWrong += 1;

            groupedNetworks[networkNumber].adjustNetwork(inputs, idealAction);
            if (changedWeights == true) {
                groupedNetworks[networkNumber].printNetwork(networkNumber);
                changedWeights = false;
            }
        }
    }
}
