package Mod;

public interface PhysicalBody {
    public void setVelocityVector(Vec2D velocityVector);
    public void setAccelerationVector(Vec2D accelerationVector);
    public void setVelocityScalar(double velocityScalar);
    public void setAccelerationScalar(double accelerationScalar);

    public Vec2D getVelocityVector();
    public Vec2D getAccelerationVector();
    public double getVelocityScalar();
    public double getAccelerationScalar();
}
