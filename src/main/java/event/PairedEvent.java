package event;

/**
 * Created by suchintan on 2015-02-08.
 */
public class PairedEvent extends Event{
    private int myo;
    private int[] version;

    public int getMyo() {
        return myo;
    }

    public void setMyo(int myo) {
        this.myo = myo;
    }

    public int[] getVersion() {
        return version;
    }

    public void setVersion(int[] version) {
        this.version = version;
    }
}
