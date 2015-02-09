package event;

/**
 * Created by suchintan on 2015-02-08.
 */
public class PoseEvent extends Event {
    private int myo;
    private String pose;

    public String getPose() {
        return pose;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }

    public int getMyo() {
        return myo;
    }

    public void setMyo(int myo) {
        this.myo = myo;
    }
}
