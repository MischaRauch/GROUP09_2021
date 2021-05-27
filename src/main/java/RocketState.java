import src.main.java.titan.Vector3dInterface;

/**
 * Class which represents the state of the rocket at a specific point in time.
 */
public class RocketState {
    private Vector3dInterface p;   // coordinates
    private Vector3dInterface v;   // velocity

    public RocketState(Vector3dInterface p, Vector3dInterface v) {
        this.p = p;
        this.v = v;
    }

    public Vector3dInterface getP0() {
        return p;
    }

    public Vector3dInterface getV0() {
        return v;
    }

    @Override
    public String toString() {
        return "RocketState{" +
                "p=" + p +
                ", v=" + v +
                '}';
    }
}
