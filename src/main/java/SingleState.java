import src.main.java.titan.Vector3dInterface;

public class SingleState {
    private Vector3dInterface coordinates;
    private Vector3dInterface velocities;
    private double time;

    public SingleState(Vector3dInterface coordinates, Vector3dInterface velocities, double time) {
        this.coordinates = coordinates;
        this.velocities = velocities;
        this.time = time;
    }

    public Vector3dInterface getCoordinates() {
        return coordinates;
    }

    public Vector3dInterface getVelocity() {
        return velocities;
    }

    public double getTime() {
        return time;
    }
}
