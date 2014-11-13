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

    @Override public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        Pose p = new Pose();
        p.setPose(PoseType.THUMB_TO_PINKY);

        return new NanoHTTPD.Response(new Gson().toJson(p));
    }


    public static void main(String[] args) {
        ServerRunner.run(MyoServer.class);
    }
}
