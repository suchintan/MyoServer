package event;

import com.thalmic.myo.enums.PoseType;

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

    public PoseType getPoseType(){
        for(PoseType p : PoseType.values()){
            if(p.toString().toLowerCase().equals(pose)){
                return p;
            }
        }
        return PoseType.UNKNOWN;
    }

    public int getMyo() {
        return myo;
    }

    public void setMyo(int myo) {
        this.myo = myo;
    }
}
