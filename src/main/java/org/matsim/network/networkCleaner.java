package org.matsim.network;


import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.run.NetworkCleaner;

public class networkCleaner {
    public static void main(String[] args) {
        String inputNetworkFile = "net.xml";
        String outputNetworkFile = "network_cleaned.xml";

//        Network network = NetworkUtils.readNetwork(inputNetworkFile);
        new NetworkCleaner().run(inputNetworkFile, outputNetworkFile);
//        NetworkUtils.writeNetwork(network, outputNetworkFile);
    }
}
