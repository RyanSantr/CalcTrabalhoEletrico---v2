package view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class ChargeSquare3DPane extends StackPane {

    private static final double SIZE = 390;
    private static final double HALF_SIDE = 108;
    private static final Color POSITIVE = Color.web("#ff365f");
    private static final Color NEGATIVE = Color.web("#2dd4ff");
    private static final Color WAVE = Color.web("#facc15");

    public ChargeSquare3DPane() {
        getStyleClass().add("visual-pane");
        setMinSize(SIZE, SIZE);
        setPrefSize(SIZE, SIZE);
        setMaxSize(SIZE, SIZE);
        setAlignment(Pos.CENTER);

        getChildren().add(createScene());
        getChildren().add(createLegend());
    }

    private SubScene createScene() {
        Point3D topLeft = new Point3D(-HALF_SIDE, 0, -HALF_SIDE);
        Point3D topRight = new Point3D(HALF_SIDE, 0, -HALF_SIDE);
        Point3D bottomLeft = new Point3D(-HALF_SIDE, 0, HALF_SIDE);
        Point3D bottomRight = new Point3D(HALF_SIDE, 0, HALF_SIDE);

        Group waveLayer = new Group();
        List<LinearWave> waves = new ArrayList<>();
        List<Sphere> halos = new ArrayList<>();

        Group chargeSystem = new Group(
                grid(),
                squareEdge(topLeft, topRight),
                squareEdge(topRight, bottomRight),
                squareEdge(bottomLeft, bottomRight),
                squareEdge(topLeft, bottomLeft),
                interactionLine(topLeft, bottomRight, Color.web("#ff4d6d", 0.28)),
                interactionLine(topRight, bottomLeft, Color.web("#2dd4ff", 0.28)),
                waveLayer,
                charge(topLeft, true, halos),
                charge(topRight, false, halos),
                charge(bottomLeft, false, halos),
                charge(bottomRight, true, halos)
        );

        addLinearWaves(waveLayer, waves, topLeft, topRight, 0.00);
        addLinearWaves(waveLayer, waves, topLeft, bottomLeft, 0.18);
        addLinearWaves(waveLayer, waves, bottomRight, topRight, 0.36);
        addLinearWaves(waveLayer, waves, bottomRight, bottomLeft, 0.54);
        addLinearWaves(waveLayer, waves, topLeft, bottomRight, 0.72);
        addLinearWaves(waveLayer, waves, bottomRight, topLeft, 0.90);

        Group world = new Group(chargeSystem);
        Rotate xRotate = new Rotate(-24, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(30, Rotate.Y_AXIS);
        Rotate zRotate = new Rotate(6, Rotate.Z_AXIS);
        world.getTransforms().addAll(xRotate, yRotate, zRotate);

        AmbientLight ambientLight = new AmbientLight(Color.color(0.45, 0.50, 0.58));
        PointLight redLight = pointLight(Color.web("#ff4d6d"), -210, -210, -260);
        PointLight blueLight = pointLight(Color.web("#2dd4ff"), 220, -160, 180);
        PointLight whiteLight = pointLight(Color.WHITE, 0, -330, -430);

        Group root = new Group(world, ambientLight, redLight, blueLight, whiteLight);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(2500);
        camera.setTranslateZ(-760);

        SubScene subScene = new SubScene(root, SIZE, SIZE, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.web("#050816"));
        subScene.setCamera(camera);
        enableMouseControl(subScene, xRotate, yRotate, camera);

        RotateTransition rotation = new RotateTransition(Duration.seconds(22), chargeSystem);
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setFromAngle(0);
        rotation.setToAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();

        startWaveAnimation(waves, halos);

        return subScene;
    }

    private Group charge(Point3D position, boolean positive, List<Sphere> halos) {
        Color coreColor = positive ? POSITIVE : NEGATIVE;
        Color haloColor = positive ? Color.web("#ff4d6d", 0.24) : Color.web("#2dd4ff", 0.24);

        Sphere halo = new Sphere(34);
        halo.setMaterial(material(haloColor, Color.WHITE, 18));

        Sphere core = new Sphere(22);
        core.setMaterial(material(coreColor, Color.WHITE, 34));

        Sphere highlight = new Sphere(6);
        highlight.setMaterial(material(Color.rgb(255, 255, 255, 0.72), Color.WHITE, 22));
        highlight.getTransforms().add(new Translate(-8, -12, -13));

        Group group = new Group(halo, core, highlight);
        group.getTransforms().add(new Translate(position.getX(), position.getY(), position.getZ()));
        halos.add(halo);
        return group;
    }

    private Group grid() {
        Group group = new Group();
        Color gridColor = Color.web("#67e8f9", 0.18);

        for (int offset = -160; offset <= 160; offset += 40) {
            group.getChildren().add(edge(new Point3D(offset, 32, -160), new Point3D(offset, 32, 160), gridColor, 0.55));
            group.getChildren().add(edge(new Point3D(-160, 32, offset), new Point3D(160, 32, offset), gridColor, 0.55));
        }

        return group;
    }

    private Cylinder squareEdge(Point3D start, Point3D end) {
        return edge(start, end, Color.web("#2dd4ff", 0.86), 4.6);
    }

    private Cylinder interactionLine(Point3D start, Point3D end, Color color) {
        return edge(start, end, color, 1.6);
    }

    private void addLinearWaves(Group layer, List<LinearWave> waves, Point3D start, Point3D end, double phaseOffset) {
        for (int i = 0; i < 4; i++) {
            LinearWave wave = new LinearWave(start, end, phaseOffset + i * 0.22, 0.62 + i * 0.04);
            waves.add(wave);
            layer.getChildren().add(wave.node());
        }
    }

    private void startWaveAnimation(List<LinearWave> waves, List<Sphere> halos) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double seconds = now / 1_000_000_000.0;

                for (LinearWave wave : waves) {
                    wave.update(seconds);
                }

                for (int i = 0; i < halos.size(); i++) {
                    double scale = 1.0 + Math.sin(seconds * 3.2 + i * 0.8) * 0.09;
                    Sphere halo = halos.get(i);
                    halo.setScaleX(scale);
                    halo.setScaleY(scale);
                    halo.setScaleZ(scale);
                }
            }
        };
        timer.start();
    }

    private Cylinder edge(Point3D start, Point3D end, Color color, double radius) {
        Point3D direction = end.subtract(start);
        Point3D midpoint = start.midpoint(end);

        Cylinder cylinder = new Cylinder(radius, direction.magnitude());
        cylinder.setMaterial(material(color, Color.WHITE, 24));
        cylinder.getTransforms().add(new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()));
        cylinder.getTransforms().add(rotationToDirection(direction));
        return cylinder;
    }

    private Rotate rotationToDirection(Point3D direction) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D normalized = direction.normalize();
        Point3D rotationAxis = yAxis.crossProduct(normalized);

        if (rotationAxis.magnitude() < 0.0001) {
            return new Rotate(normalized.getY() < 0 ? 180 : 0, Rotate.X_AXIS);
        }

        double angle = Math.toDegrees(Math.acos(clamp(yAxis.dotProduct(normalized), -1, 1)));
        return new Rotate(angle, rotationAxis);
    }

    private PhongMaterial material(Color diffuse, Color specular, double specularPower) {
        PhongMaterial material = new PhongMaterial(diffuse);
        material.setSpecularColor(specular);
        material.setSpecularPower(specularPower);
        return material;
    }

    private PointLight pointLight(Color color, double x, double y, double z) {
        PointLight light = new PointLight(color);
        light.getTransforms().add(new Translate(x, y, z));
        return light;
    }

    private void enableMouseControl(SubScene scene, Rotate xRotate, Rotate yRotate, PerspectiveCamera camera) {
        double[] anchor = new double[4];

        scene.setOnMousePressed(event -> {
            anchor[0] = event.getSceneX();
            anchor[1] = event.getSceneY();
            anchor[2] = xRotate.getAngle();
            anchor[3] = yRotate.getAngle();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - anchor[0];
            double deltaY = event.getSceneY() - anchor[1];
            xRotate.setAngle(clamp(anchor[2] - deltaY * 0.35, -68, 35));
            yRotate.setAngle(anchor[3] + deltaX * 0.35);
        });

        scene.setOnScroll(event -> camera.setTranslateZ(clamp(camera.getTranslateZ() + event.getDeltaY() * 0.45, -980, -460)));
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Label createLegend() {
        Label legend = new Label("Ondas lineares: pulsos eletricos percorrem as ligacoes");
        legend.getStyleClass().add("visual-caption");
        StackPane.setAlignment(legend, Pos.BOTTOM_CENTER);
        return legend;
    }

    private class LinearWave {
        private final Group node;
        private final Point3D start;
        private final Point3D end;
        private final double phase;
        private final double speed;

        LinearWave(Point3D start, Point3D end, double phase, double speed) {
            this.start = start;
            this.end = end;
            this.phase = phase;
            this.speed = speed;

            Sphere head = new Sphere(5.5);
            head.setMaterial(material(WAVE, Color.WHITE, 38));

            Sphere halo = new Sphere(10);
            halo.setMaterial(material(Color.web("#facc15", 0.18), Color.WHITE, 18));

            Cylinder streak = new Cylinder(1.8, 30);
            streak.setMaterial(material(Color.web("#facc15", 0.54), Color.WHITE, 30));
            streak.getTransforms().add(rotationToDirection(end.subtract(start)));

            node = new Group(halo, streak, head);
        }

        Group node() {
            return node;
        }

        void update(double seconds) {
            double progress = (seconds * speed + phase) % 1.0;
            Point3D position = start.interpolate(end, progress);
            double pulse = Math.sin(progress * Math.PI);

            node.setTranslateX(position.getX());
            node.setTranslateY(position.getY() - 6);
            node.setTranslateZ(position.getZ());
            node.setScaleX(0.75 + pulse * 0.38);
            node.setScaleY(node.getScaleX());
            node.setScaleZ(node.getScaleX());
            node.setOpacity(0.30 + pulse * 0.70);
        }
    }
}
