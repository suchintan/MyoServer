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
        j.setPose(PoseType.THUMB_TO_PINKY);
        j.setAction(Action.Unlock);

        //after 100 seconds change pose
        if(c == 100){
            j.setPose(PoseType.FIST);
            j.setAction(Action.Right);
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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c++;
            }
        })).start();
        ServerRunner.run(MyoServer.class);
    }
}
