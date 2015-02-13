import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import event.Event;
import event.EventDeserializer;
import event.OrientationEvent;
import event.PoseEvent;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by suchintan on 2015-02-08.
 */
public class WebSocketTest extends WebSocketClient{
    private static List<OrientationEvent.Orientation> orientationList = new ArrayList<OrientationEvent.Orientation>();

    public WebSocketTest( URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    public WebSocketTest( URI serverURI ) {
        super( serverURI );
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        System.out.println( "opened connection" );
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
        String m = message.substring(message.indexOf("event") + 7,message.length() - 1);

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Event.class, new EventDeserializer());
        Gson gson = gb.create();

        String out = message;
        try {
            Event e = gson.fromJson(m, Event.class);

            out = gson.toJson(e);
            if(e instanceof OrientationEvent){

                OrientationEvent.Orientation o = ((OrientationEvent) e).getOrientation();
//            orientationList.add(o);

                out = "";
            }

            if(e instanceof PoseEvent){
//                out = ((PoseEvent) e).getPoseType().toString();
            }

            MyoServer.update(e);
        }catch(Exception e){

        }


        if(!out.equals("")){
            System.out.println(out);
        }
    }

//    @Override
    public void onFragment( Framedata fragment ) {
        System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public static void main( String[] args ) throws URISyntaxException , InterruptedException{
        WebSocketTest c = new WebSocketTest( new URI( "ws://127.0.0.1:10138/myo/3" ), new Draft_10() ); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
        Thread.sleep(1000);
        c.send("[\"command\", {\"command\": \"set_locking_policy\", \"type\": \"none\"}]");
//        Thread.sleep(10000);
//        c.close();

//        Chart.draw(orientationList);
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    public void send( String text ) {
        getConnection().send(text);
    }
}
