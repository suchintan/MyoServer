import com.thalmic.myo.enums.PoseType;

/**
 * Created by suchintan on 2014-11-12.
 */
public class JSON {
    private Action action;
    private PoseType gesture;

    public JSON(){

    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public PoseType getPose() {
        return gesture;
    }

    public void setPose(PoseType gesture) {
        this.gesture = gesture;
    }
}
