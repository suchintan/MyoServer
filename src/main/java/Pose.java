import com.thalmic.myo.enums.PoseType;

/**
 * Created by suchintan on 2014-11-12.
 */
public class Pose {
    public Pose(){

    }
    private PoseType pose;
    private Direction direction;

    public PoseType getPose() {
        return pose;
    }

    public void setPose(PoseType pose) {
        this.pose = pose;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
