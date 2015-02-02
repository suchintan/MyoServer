import com.google.gson.Gson;
import com.thalmic.myo.enums.PoseType;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;

import java.util.Map;

/**
 * Created by suchintan on 2014-11-12.
 */
public class MyoServer extends NanoHTTPD{
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
        if(c >= 5){
            j.setPose(PoseType.THUMB_TO_PINKY);
            j.setAction(Action.Start);
        }
        if(c >= 10){
            j.setPose(PoseType.REST);
            j.setAction(Action.Cruise);
        }
        if(c >= 15){
            j.setPose(PoseType.THUMB_TO_PINKY);
            j.setAction(Action.Lock);
        }
        if(c >= 20){
            j.setPose(PoseType.THUMB_TO_PINKY);
            j.setAction(Action.Unlock);
        }
        if(c >= 25){
            j.setPose(PoseType.WAVE_OUT);
            j.setAction(Action.Right);
        }
        if(c >= 30){
            j.setPose(PoseType.FINGERS_SPREAD);
            j.setAction(Action.Stop);
        }
        if(c >= 35){
            j.setPose(PoseType.WAVE_OUT);
            j.setAction(Action.Shutdown);
        }
        if(c>=50){
            c = 0;
        }
        Response r = new NanoHTTPD.Response(new Gson().toJson(j));
        r.addHeader("Access-Control-Allow-Origin", "*");
        return r;
    }


    public static void main(String[] args) {
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
