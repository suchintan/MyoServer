package event;

/**
 * Created by suchintan on 2015-02-08.
 */
public class OrientationEvent extends Event {
    private double[] accelerometer;
    private double[] gyroscope;
    private Orientation orientation;

    public double[] getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(double[] accelerometer) {
        this.accelerometer = accelerometer;
    }

    public double[] getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(double[] gyroscope) {
        this.gyroscope = gyroscope;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setOrientation(double w, double x, double y, double z) {
        Orientation orientation = new Orientation();
        orientation.setW(w);
        orientation.setX(x);
        orientation.setY(y);
        orientation.setZ(z);
        this.orientation = orientation;
    }

    public class Orientation{
        private double w;
        private double x;
        private double y;
        private double z;

        public double getW() {
            return w;
        }

        public void setW(double w) {
            this.w = w;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public double getRoll(){
            return Math.atan2(2 * (y * z + w * x), w * w - x * x - y * y + z * z);
        }

        public double getPitch(){
            return Math.asin(-2*(x*z - w*y));
        }

        public double getYaw(){
            return Math.atan2(2*(x*y + w*z), w*w + x*x - y*y - z*z);
        }
        @Override
        public String toString() {
            return w + "," + x + "," + y + "," + z;
        }

        public String getYawPitchRoll(){
            return getYaw() + "," + getPitch() + "," + getRoll();
        }
    }
}
