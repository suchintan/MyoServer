import com.thalmic.myo.enums.PoseType;

/**
 * Created by suchintan on 2014-11-12.
 */
public class Pose {
    public Pose(){

    }
    private PoseType pose;

    public PoseType getPose() {
        return pose;
    }

    public void setPose(PoseType pose) {
        this.pose = pose;
    }
}
