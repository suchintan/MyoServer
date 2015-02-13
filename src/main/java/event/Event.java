package event;

/**
 * Created by suchintan on 2015-02-08.
 */
public class Event {
    private long timestamp;
    private String type;
    private int myo;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMyo() {
        return myo;
    }

    public void setMyo(int myo) {
        this.myo = myo;
    }
}
