package model;

public class PhysicsCalculator {

    public static final double K = 8.99E9;
    public static final double COULOMB_CONSTANT = K;
    public static final double PICO_TO_COULOMB = 1E-12;
    public static final double CENTIMETER_TO_METER = 1E-2;
    public static final double SQUARE_CHARGE_FACTOR = Math.sqrt(2) - 4;

    public record WorkResult(
            double chargePc,
            double sideCm,
            double chargeCoulomb,
            double sideMeter,
            double workJoule
    ) {
    }

    public double picoToCoulomb(double qPc) {
        return qPc * PICO_TO_COULOMB;
    }

    public double cmToMeter(double aCm) {
        return aCm * CENTIMETER_TO_METER;
    }

    public WorkResult calculateFromUserUnits(double chargePc, double sideCm) {
        double chargeCoulomb = picoToCoulomb(chargePc);
        double sideMeter = cmToMeter(sideCm);
        double workJoule = calculateWork(chargePc, sideCm);
        return new WorkResult(chargePc, sideCm, chargeCoulomb, sideMeter, workJoule);
    }

    public double calculateWork(double qPc, double aCm) {
        double chargeCoulomb = picoToCoulomb(qPc);
        double sideMeter = cmToMeter(aCm);

        if (sideMeter <= 0) {
            throw new IllegalArgumentException("O lado a precisa ser maior que zero.");
        }

        return (K * chargeCoulomb * chargeCoulomb / sideMeter) * SQUARE_CHARGE_FACTOR;
    }
}
