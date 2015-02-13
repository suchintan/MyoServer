import com.google.gson.Gson;
import com.thalmic.myo.enums.PoseType;
import event.Event;
import event.OrientationEvent;
import event.PoseEvent;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;
import org.java_websocket.drafts.Draft_10;

import java.net.URI;
import java.util.Map;

/**
 * Created by suchintan on 2014-11-12.
 */
public class MyoServer extends NanoHTTPD{
    private static PoseEvent pose1 = null;
    private static OrientationEvent orientation1 = null;

    private static OrientationEvent orientation2 = null;
    private static PoseEvent pose2 = null;

    private static Action currentAction = null;
    private static PoseType currentState = null;

    public static void update(Event e){
        if(e instanceof PoseEvent){
            PoseEvent pose = (PoseEvent) e;

            if(pose.getMyo() == 1){
                pose1 = pose;
            }

            if(pose.getMyo() == 2){
                pose2 = pose;
            }

            if(pose.getMyo() == 1){
                if(currentState == null || currentAction == Action.Shutdown){
                    currentAction = Action.Shutdown;
                    if(pose.getPoseType() == PoseType.DOUBLE_TAP){
                        currentAction = Action.Start;
                    }
                }
                if(currentState != null && currentAction != Action.Shutdown){
                    if(pose.getPoseType() == PoseType.DOUBLE_TAP && currentAction != Action.Cruise){
                        currentAction = Action.Cruise;
                    }
                    if(pose.getPoseType() == PoseType.DOUBLE_TAP && currentAction == Action.Cruise){
                        currentAction = Action.Unlock;
                    }
                    if(currentAction != Action.Cruise){
                        if(pose.getPoseType() == PoseType.FINGERS_SPREAD){
                            currentAction = Action.Cruise;
                        }
                    }


                }
            }

            if(pose.getMyo() == 2){
                if(currentState != null && currentAction != Action.Shutdown){
                    if(currentAction != Action.Cruise){
                        if(pose.getPoseType() == PoseType.WAVE_OUT){
                            currentAction = Action.Right;
                        }
                        if(pose.getPoseType() == PoseType.WAVE_IN){
                            currentAction = Action.Left;
                        }
                    }
                }
            }



            currentState = pose.getPoseType();

            System.out.println(currentState);
        }

        if(e instanceof OrientationEvent){
            OrientationEvent orientation = (OrientationEvent) e;

            if(orientation.getMyo() == 1){

                if(currentState == PoseType.FINGERS_SPREAD) {
                    if ((orientation.getOrientation().getPitch() - orientation1.getOrientation().getPitch()) < 0) {
                        currentAction = Action.Stop;
                    }
                }
                if(currentState == PoseType.WAVE_OUT) {
                    if((orientation.getOrientation().getYaw() - orientation1.getOrientation().getYaw()) > 0){
                        currentAction = Action.Shutdown;
                    }
                }

                orientation1 = orientation;
            }

            if(orientation.getMyo() == 2){
                orientation2 = orientation;
            }
        }
    }
    public MyoServer() {
        super(8080);
    }

    public static int c;


    @Override public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        JSON j = new JSON();
        //start with this
        j.setPose(PoseType.UNKNOWN);
        j.setAction(Action.Shutdown);

        //after 5 seconds change pose
//        c = 40;
        if(c >= 5){
            j.setPose(PoseType.DOUBLE_TAP);
            j.setAction(Action.Start);
        }
        if(c >= 10){
            j.setPose(PoseType.REST);
            j.setAction(Action.Cruise);
        }
        if(c >= 15){
            j.setPose(PoseType.DOUBLE_TAP);
            j.setAction(Action.Lock);
        }
        if(c >= 20){
            j.setPose(PoseType.DOUBLE_TAP);
            j.setAction(Action.Unlock);
        }
        if(c >= 25){
            j.setPose(PoseType.WAVE_IN);
            j.setAction(Action.Right);
        }
        if(c >= 30){
            j.setPose(PoseType.FINGERS_SPREAD);
            j.setAction(Action.Stop);
        }
        if(c >= 35){
            j.setPose(PoseType.WAVE_OUT);
            j.setAction(Action.Left);
        }
        if(c >= 40){
            j.setPose(PoseType.WAVE_OUT);
            j.setAction(Action.Shutdown);
        }
        if(c>=45){
            c = 0;
        }

        Response r = new NanoHTTPD.Response(new Gson().toJson(j));
        r.addHeader("Access-Control-Allow-Origin", "*");
        return r;
    }


    public static void main(String[] args) throws Exception{
        //Start reading the Websocket

//        WebSocketTest ws = new WebSocketTest( new URI( "ws://127.0.0.1:10138/myo/3" ), new Draft_10() );
//        ws.connect();
//        Thread.sleep(500);
//        ws.send("[\"command\", {\"command\": \"set_locking_policy\", \"type\": \"none\"}]");

        //Start the local server
        c = 0;
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c++;
                    System.out.println(c);
                }
            }
        })).start();
        ServerRunner.run(MyoServer.class);
    }
}
