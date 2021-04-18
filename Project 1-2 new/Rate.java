import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class Rate implements RateInterface {

    private final Vector3dInterface[] rates = new Vector3dInterface[11];

    public Rate(Vector3dInterface[] rates) {
        System.arraycopy(rates, 0, this.rates, 0, rates.length);
    }

    public Vector3dInterface[] getRates() {
        return rates;
    }
}
