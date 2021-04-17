import titan.*;

public class ODESolver implements ODESolverInterface {

    private final StateInterface[] states;

    private Vector3dInterface sunC = new Vector3d(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06);
    private Vector3dInterface mercuryC = new Vector3d(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09);
    private Vector3dInterface venusC = new Vector3d(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09);
    private Vector3dInterface earthC = new Vector3d(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06);
    private Vector3dInterface moonC = new Vector3d(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07);
    private Vector3dInterface marsC = new Vector3d(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09);
    private Vector3dInterface jupiterC = new Vector3d(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08);
    private Vector3dInterface saturnC = new Vector3d(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09);
    private Vector3dInterface titanC = new Vector3d(6.332873118527889e+11, -1.357175556995868e+12, -2.134637041453660e+09);
    private Vector3dInterface uranusC = new Vector3d(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10);
    private Vector3dInterface neptuneC = new Vector3d(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10);

    public Vector3dInterface[] coordinates = {sunC, mercuryC, venusC, earthC, moonC, marsC, jupiterC, saturnC, titanC, uranusC, neptuneC};

    private final Vector3dInterface sunV = new Vector3d(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01);
    private final Vector3dInterface mercuryV = new Vector3d(3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03);
    private final Vector3dInterface venusV = new Vector3d(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04);
    private final Vector3dInterface earthV = new Vector3d(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
    private final Vector3dInterface moonV = new Vector3d(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01);
    private final Vector3dInterface marsV = new Vector3d(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02);
    private final Vector3dInterface jupiterV = new Vector3d(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02);
    private final Vector3dInterface saturnV = new Vector3d(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02);
    private final Vector3dInterface titanV = new Vector3d(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02);
    private final Vector3dInterface uranusV = new Vector3d(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01);
    private final Vector3dInterface neptuneV = new Vector3d(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02);

    public Vector3dInterface[] velocities = {sunV, mercuryV, venusV, earthV, moonV, marsV, jupiterV, saturnV, titanV, uranusV, neptuneV};

    public ODESolver(double dt) {
        StateInterface y0 = new State(coordinates, velocities);
        ODEFunctionInterface f = new Rate();

        states = solve(f, y0, 31536000, dt); //31536000
    }

    public StateInterface[] getStates() {
        return states;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        StateInterface[] states = new StateInterface[(int) ((tf/h)+1)];

        int t = 0;

        states[0] = y0;

        for(int i = 1; i < states.length; i++) {
            t += h;
            states[i] = step(f, t, states[i-1], h);
        }

        return states;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        f.call(t, y);
        return y.addMul(h, (RateInterface) f);
    }

}
