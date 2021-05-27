import src.main.java.titan.RateInterface;
import src.main.java.titan.Vector3dInterface;

/**
 * Class which represents the rate-of-change of the system.
 */
public class Rate implements RateInterface {

    // Stores the rate-of-change for each object.
    private final Vector3dInterface[] rates;

    /**
     * Constructor, initialises the rates
     * @param rates the rates to set
     */
    public Rate(Vector3dInterface[] rates) {
        this.rates = new Vector3dInterface[rates.length];
        System.arraycopy(rates, 0, this.rates, 0, rates.length);
    }

    /**
     * {@return the rates-of-change stored inside this}
     */
    public Vector3dInterface[] getRates() {
        return rates;
    }

    /**
     * Add another rate-of-change vector multiplied by a scalar to the rate-of-change stored inside this
     * @param scalar    the scalar to multiply the other vector with
     * @param other     the other vector to add once it has been multiplied by the scalar
     * @return result of the addition/multiplication
     */
    public Rate addMul(double scalar, Rate other) {
        Vector3dInterface[] addRates = new Vector3dInterface[rates.length];
        Vector3dInterface[] otherRates = other.getRates();
        for(int i = 0; i < addRates.length; i ++) {
            addRates[i] = this.rates[i].add(otherRates[i].mul(scalar));
        }
        return new Rate(addRates);
    }

    /**
     * Multiplies the rates-of-change inside this by a scalar
     * @param scalar the scalar to multiply by
     * @return result of the multiplication
     */
    public Rate mul(double scalar) {
        Vector3dInterface[] mulRates = new Vector3dInterface[rates.length];
        for(int i = 0; i < mulRates.length; i ++) {
            mulRates[i] = this.rates[i].mul(scalar);
        }
        return new Rate(mulRates);
    }
}
