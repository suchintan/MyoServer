import com.sun.corba.se.spi.orb.DataCollector;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

import java.util.StringTokenizer;

/**
 * Created by suchintan on 2014-11-12.
 */
public class MyoTest {
    public static void main(String[] args) {
        try {
            String property = System.getProperty("java.library.path");
            StringTokenizer parser = new StringTokenizer(property, ";");
            while (parser.hasMoreTokens()) {
                System.err.println(parser.nextToken());
            }
//            Hub hub = new Hub("com.example.hello-myo");
//
//            System.out.println("Attempting to find a Myo...");
//            Myo myo = hub.waitForMyo(10000);
//
//            if (myo == null) {
//                throw new RuntimeException("Unable to find a Myo!");
//            }
//
//            System.out.println("Connected to a Myo armband!");
//            MyoListener dataCollector = new MyoListener();
//            hub.addListener(dataCollector);
//
//            while (true) {
//                hub.run(1000 / 20);
//                System.out.print(dataCollector);
//            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
