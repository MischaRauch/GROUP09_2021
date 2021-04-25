public class RunCode {

    private static final double stepSize = 600;

    public static void main(String[] args) {
        ODESolver solver = new ODESolver(stepSize); // 6000
        ProbeSimulator probeSimulator = new ProbeSimulator(solver.getStates(), stepSize);

        Frame frame = new Frame(solver.getStates(), probeSimulator.getCoordinatesProbe());
    }

}
