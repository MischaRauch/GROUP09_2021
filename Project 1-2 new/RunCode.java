public class RunCode {

    private static double a = 2;
    private static double b = 5;

    private static final double[] list = {a, b};

    public static void main(String[] args) {
        ODESolver solver = new ODESolver(6000);

        Frame frame = new Frame(solver.getStates());
    }

}
