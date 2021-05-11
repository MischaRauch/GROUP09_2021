
import src.main.java.titan.RateInterface;
import src.main.java.titan.Vector3dInterface;

public class Rate implements RateInterface {

    private final Vector3dInterface[] rates;

    public Rate(Vector3dInterface[] rates) {
        this.rates = new Vector3dInterface[rates.length];
        System.arraycopy(rates, 0, this.rates, 0, rates.length);
    }

    public Vector3dInterface[] getRates() {
        return rates;
    }

    public Rate addMul(double scalar, Rate other) {
        Vector3dInterface[] addRates = new Vector3dInterface[rates.length];
        Vector3dInterface[] otherRates = other.getRates();
        for(int i = 0; i < addRates.length; i ++) {
            addRates[i] = this.rates[i].add(otherRates[i].mul(scalar));
        }
        return new Rate(addRates);
    }

    public Rate mul(double scalar) {
        Vector3dInterface[] mulRates = new Vector3dInterface[rates.length];
        for(int i = 0; i < mulRates.length; i ++) {
            mulRates[i] = this.rates[i].mul(scalar);
        }
        return new Rate(mulRates);
    }
}