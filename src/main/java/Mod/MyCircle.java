package Mod;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MyCircle  extends Circle implements PhysicalBody {
    private Vec2D velocityVector;
    private Vec2D accelerationVector;

    private double velocityScalar;
    private double accelerationScalar;

    {
        velocityVector = new Vec2D();
        accelerationVector = new Vec2D();

        velocityScalar = 0;
        accelerationScalar = 0;
    }

    public MyCircle() {
        super();
    }
    public MyCircle(double var1) {
        super(var1);
    }
    public MyCircle(double var1, Paint var3) {
        super(var1, var3);
    }
    public MyCircle(double var1, double var3, double var5) {
        super(var1, var3, var5);
    }
    public MyCircle(double var1, double var3, double var5, Paint var7) {
        super(var1, var3, var5, var7);
    }

    @Override
    public void setVelocityVector(Vec2D velocityVector) {
        if (velocityVector == null) {
            throw new NullPointerException("The Vec2D elements passed to the function \"setVelocityVector\" of \"class MyCircle\" are null");
        }

        this.velocityVector = new Vec2D(velocityVector);
    }

    @Override
    public void setAccelerationVector(Vec2D accelerationVector) {
        if (accelerationVector == null) {
            throw new NullPointerException("The Vec2D elements passed to the function \"setAccelerationVector\" of \"class MyCircle\" are null");
        }

        this.accelerationVector = new Vec2D(accelerationVector);
    }

    @Override
    public Vec2D getVelocityVector() {
        return velocityVector;
    }

    @Override
    public Vec2D getAccelerationVector() {
        return accelerationVector;
    }

    public Vec2D getCenter() {
        return new Vec2D(this.getCenterX(), this.getCenterY());
    }

    public void normalizationVelocity() {
        velocityVector.normalization();
        velocityScalar /= velocityVector.modulus();
    }

    @Override
    public void setVelocityScalar(double velocityScalar) {
        this.velocityScalar = velocityScalar;
    }

    @Override
    public void setAccelerationScalar(double accelerationScalar) {
        this.accelerationScalar = accelerationScalar;
    }

    @Override
    public double getVelocityScalar() {
        return velocityScalar;
    }

    @Override
    public double getAccelerationScalar() {
        return accelerationScalar;
    }

}
