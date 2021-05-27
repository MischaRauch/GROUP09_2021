import src.main.java.titan.Vector3dInterface;

public class RocketState {
    private Vector3dInterface p0;
    private Vector3dInterface v0;

    public RocketState(Vector3dInterface p0, Vector3dInterface v0) {
        this.p0 = p0;
        this.v0 = v0;
    }

    public Vector3dInterface getP0() {
        return p0;
    }

    public Vector3dInterface getV0() {
        return v0;
    }

    @Override
    public String toString() {
        return "RocketState{" +
                "p0=" + p0 +
                ", v0=" + v0 +
                '}';
    }
}
