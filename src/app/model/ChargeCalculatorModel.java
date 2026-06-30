package app.model;

import java.util.ArrayList;
import java.util.List;

public class ChargeCalculatorModel {

    private static final double K = 8.9875E9;
    private static final double TEST_CHARGE = 1E-9;

    public record ChargeInput(double q1, double q2, double q3, double q4, double sideMeters) {
    }

    public record ForceComponent(String label, double fx, double fy) {
        public double magnitude() {
            return Math.hypot(fx, fy);
        }

        public double angleDegrees() {
            return Math.toDegrees(Math.atan2(fy, fx));
        }
    }

    public record ForceResult(double fx, double fy, double magnitude, double angleDegrees, String direction,
                              List<ForceComponent> components) {
    }

    public ForceResult calculateAtCenter(ChargeInput input) {
        if (input.sideMeters <= 0 || !Double.isFinite(input.sideMeters)) {
            throw new IllegalArgumentException("Side length must be greater than zero.");
        }

        double half = input.sideMeters / 2.0;
        List<ForceComponent> components = new ArrayList<>();
        components.add(component("F1", input.q1, -half, half));
        components.add(component("F2", input.q2, half, half));
        components.add(component("F3", input.q3, -half, -half));
        components.add(component("F4", input.q4, half, -half));

        double fx = components.stream().mapToDouble(ForceComponent::fx).sum();
        double fy = components.stream().mapToDouble(ForceComponent::fy).sum();
        double magnitude = Math.hypot(fx, fy);
        double angle = Math.toDegrees(Math.atan2(fy, fx));

        return new ForceResult(fx, fy, magnitude, angle, direction(angle), List.copyOf(components));
    }

    private ForceComponent component(String label, double charge, double sourceX, double sourceY) {
        double distance = Math.hypot(sourceX, sourceY);
        double centerVectorX = -sourceX;
        double centerVectorY = -sourceY;
        double forceScale = K * TEST_CHARGE * charge / Math.pow(distance, 3);
        return new ForceComponent(label, forceScale * centerVectorX, forceScale * centerVectorY);
    }

    private String direction(double angle) {
        double normalized = (angle + 360.0) % 360.0;
        if (normalized >= 337.5 || normalized < 22.5) return "E";
        if (normalized < 67.5) return "NE";
        if (normalized < 112.5) return "N";
        if (normalized < 157.5) return "NW";
        if (normalized < 202.5) return "W";
        if (normalized < 247.5) return "SW";
        if (normalized < 292.5) return "S";
        return "SE";
    }
}
