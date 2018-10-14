package com.company;

class NetworkController {

    Network[] groupedNetworks;
    int networkConclusion;
    double totalRight;
    double totalWrong;
    boolean changedWeights;

    public NetworkController() {

        this.groupedNetworks = new Network[2];
        this.groupedNetworks[0] = new Network(2, 5, 2, 0.1);
        this.groupedNetworks[1] = new Network(2, 5, 2, 0.1);
        this.totalRight = 0;
        this.totalWrong = 0;
        this.changedWeights = true;
    }

    void runNetwork(double[] inputs, int idealAction, int networkNumber) {
        this.networkConclusion = this.groupedNetworks[networkNumber].predictAction(inputs);
        System.out.println("\nConclusion: " + this.networkConclusion + " idealAction: "+ idealAction +"\n");

        if (this.changedWeights == true) {
            this.groupedNetworks[networkNumber].printNetwork(networkNumber);
            this.changedWeights = false;
        }

        if (this.networkConclusion == idealAction)
            this.totalRight += 1;
        else {
            this.totalWrong += 1;

            this.groupedNetworks[networkNumber].adjustNetwork(inputs, idealAction);
            this.groupedNetworks[networkNumber].printNetwork(-1);
            }

        double total = this.totalRight + this.totalWrong;
        System.out.println(String.format("\nRight: %.2f Wrong: %.2f\n",this.totalRight/total, this.totalWrong/total));
    }
}
