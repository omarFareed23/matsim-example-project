package org.matsim.network;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;
public class OSMToMatsim {
    public static void main(String[] args) {
        String osm = "egypt-latest.osm";
        Scenario sc = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        Network net = sc.getNetwork();
        CoordinateTransformation ct = TransformationFactory
                .getCoordinateTransformation(TransformationFactory.WGS84,
                        TransformationFactory.WGS84_UTM35S);
        OsmNetworkReader onr = new OsmNetworkReader(net, ct);
        onr.parse(osm);
        System.out.println("network created");
        new NetworkWriter(net).write("net.xml");
    }
}
