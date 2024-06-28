package org.matsim.population;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordUtils;
import org.matsim.core.utils.misc.Counter;
import org.matsim.core.utils.misc.Time;

public class createPopulation {
    private static final String PLAN_ATTRIBUTE = "planAttribute";
    private static final String AGE_ATTRIBUTE = "age";
    private static final String NAME_HOME = "h";
    private static final String NAME_WORK = "w";

    private static final double[] xCoords = {
            -1493764.3431479705, -1468394.566686042, -1465039.400495907, -1470165.026144574, -1482728.2851640356,
            -1480567.4310918627, -1480553.2840301471, -1469815.4195463336, -1481033.138887201, -1481012.4275131326,
            -1465099.962276627, -1465028.2975945564, -1465113.0289144872, -1464972.8295166595, -1465136.5093057158,
            -1465079.9115037227, -1465025.3384655742, -1448636.4487915263, -1463491.8603441264, -1444215.6006933681,
            -1466007.8955674865, -1461433.05114404, -1465992.523831747, -1465800.480045178, -1465745.4132322688,
            -1457492.1636804747, -1469849.8390867428, -1472714.8940021312, -1472478.1564498914, -1472471.4039101817,
            -1480675.9781742948, -1455859.6844422047, -1456305.8417438774, -1455948.878304639, -1456701.9368624962,
            -1469193.0036511777, -1469181.3110885175, -1469323.0162492094, -1468648.6354892245, -1468794.3445215432
    };

    private static final double[] yCoords = {
            1.6215957981755067E7, 1.6198821913574602E7, 1.618435539249132E7, 1.619703403906882E7, 1.6190180065727444E7,
            1.6185336276648026E7, 1.618539556414079E7, 1.6194530584451774E7, 1.61861928229485E7, 1.618620875627996E7,
            1.6215216857302323E7, 1.6215319320232846E7, 1.6215336857167207E7, 1.621527828813443E7, 1.621531065585628E7,
            1.6215263231340919E7, 1.6215222799954085E7, 1.620094128341934E7, 1.6172671280462377E7, 1.6196665789930653E7,
            1.6201580046985187E7, 1.620265130555466E7, 1.6181661934081454E7, 1.618162634436243E7, 1.6181628354840267E7,
            1.6179231111306734E7, 1.6223315328471556E7, 1.6194896589770671E7, 1.6195875400623817E7, 1.6195954324838623E7,
            1.6196123958037991E7, 1.6168461551089846E7, 1.6168177313559886E7, 1.6207666124371339E7, 1.620682669812604E7,
            1.6194844670412872E7, 1.6194838542110186E7, 1.6194858028611999E7, 1.6195215888560822E7, 1.619505520488902E7
    };

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        run(args);
    }

    public static void run(String[] args) {
        MatsimRandom.reset(100); // seed

        int personsCount = Integer.parseInt(args[0]);
        String file = args[1];
        System.out.println("file: " + file);
        Counter counter = new Counter("person_");
        Scenario sc = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        Population population = sc.getPopulation();
        PopulationFactory factory = population.getFactory();

        for (int i = 0; i < personsCount; i++) {
            Person person = factory.createPerson(Id.createPersonId(i));
            Plan plan = factory.createPlan();
            plan.getAttributes().putAttribute(PLAN_ATTRIBUTE, i);
            plan.getAttributes().putAttribute(AGE_ATTRIBUTE, MatsimRandom.getRandom().nextInt(80));

            // Random home coordinates
            int homeIndex = MatsimRandom.getRandom().nextInt(xCoords.length);
            double homeX = xCoords[homeIndex];
            double homeY = yCoords[homeIndex];
            Coord homeCoord = CoordUtils.createCoord(homeX, homeY);
            Activity home = factory.createActivityFromCoord(NAME_HOME, homeCoord);
            home.setEndTime(Time.parseTime("06:30:00") + MatsimRandom.getRandom().nextDouble() * Time.parseTime("01:00:00"));
            plan.addActivity(home);

            // Random work coordinates
            int workIndex = MatsimRandom.getRandom().nextInt(xCoords.length);
            double workX = xCoords[workIndex];
            double workY = yCoords[workIndex];
            Coord workCoord = CoordUtils.createCoord(workX, workY);
            Activity work = factory.createActivityFromCoord(NAME_WORK, workCoord);
            work.setEndTime(Time.parseTime("17:00:00") + MatsimRandom.getRandom().nextDouble() * Time.parseTime("01:00:00"));
            Leg leg = factory.createLeg("car");
            plan.addLeg(leg);
            plan.addActivity(work);
            Leg leg2 = factory.createLeg("car");
            plan.addLeg(leg2);

            // Return home
            Activity home2 = factory.createActivityFromCoord(NAME_HOME, homeCoord);
            home2.setEndTime(Time.parseTime("22:00:00") + MatsimRandom.getRandom().nextDouble() * Time.parseTime("01:00:00"));
            plan.addActivity(home2);

            person.addPlan(plan);
            population.addPerson(person);
            counter.incCounter();
        }
        counter.printCounter();
        new PopulationWriter(population).write(file);
    }
}
