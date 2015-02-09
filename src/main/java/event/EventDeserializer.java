package event;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by suchintan on 2015-02-08.
 */
public class EventDeserializer implements JsonDeserializer<Event> {
    @Override
    public Event deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Event e = new Event();

        JsonObject jo = jsonElement.getAsJsonObject();
        String eventType = jo.get("type").getAsString();
        long timestamp = Long.parseLong(jo.get("timestamp").getAsString());

        if(eventType.equals("orientation")){
            OrientationEvent oe = new OrientationEvent();

            JsonArray a = jo.get("accelerometer").getAsJsonArray();
            double[] accelerometer = deserializeAsDoubleArray(a);

            a = jo.get("gyroscope").getAsJsonArray();
            double[] gyroscope = deserializeAsDoubleArray(a);

            JsonObject o = jo.getAsJsonObject("orientation");
            double w = o.get("w").getAsDouble();
            double x = o.get("x").getAsDouble();
            double y = o.get("y").getAsDouble();
            double z = o.get("z").getAsDouble();

            oe.setAccelerometer(accelerometer);
            oe.setGyroscope(gyroscope);
            oe.setOrientation(w,x,y,z);

            e = oe;
        }

        if(eventType.equals("pose")){
            PoseEvent pe = new PoseEvent();

            pe.setMyo(jo.get("myo").getAsInt());
            pe.setPose(jo.get("pose").getAsString());
            e = pe;
        }

        e.setType(eventType);
        e.setTimestamp(timestamp);

        return e;
    }

    private double[] deserializeAsDoubleArray(JsonArray a) {
        double[] accelerometer = new double[a.size()];

        for(int c = 0; c < a.size(); c++){
            accelerometer[c] = a.get(c).getAsDouble();
        }

        return accelerometer;
    }
}
