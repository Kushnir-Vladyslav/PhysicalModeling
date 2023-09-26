package Mod;

public class Vec2D {
    double x = 0;
    double y = 0;

    Vec2D() {

    }
    Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Vec2D(Vec2D other) {
        if (other == null) {
            throw new NullPointerException("The Vec2D elements passed to the constructor of \"class Vec2D\" are null");
        }

        this.x = other.x;
        this.y = other.y;
    }

    public void add (Vec2D other) {
        if (other == null) {
            throw new NullPointerException("The Vec2D elements passed to the function \"add\" of \"class Vec2D\" are null");
        }

        this.x += other.x;
        this.y += other.y;
    }
    public void sub (Vec2D other) {
        if (other == null) {
            throw new NullPointerException("The Vec2D elements passed to the function \"sub\" of \"class Vec2D\" are null");
        }

        this.x -= other.x;
        this.y -= other.y;
    }
    public void mul (double scal) {
        this.x *= scal;
        this.y *= scal;
    }

    public void converseX() {
        this.x *= -1;
    }

    public void converseY() {
        this.y *= -1;
    }

    public double distance(Vec2D other) {
        if (other == null) {
            throw new NullPointerException("The Vec2D elements passed to the function \"distance\" of \"class Vec2D\" are null");
        }

        return Math.sqrt(
                Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)
        );
    }

    public double modulus() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2D normalized() {
        Vec2D vec2D = new Vec2D(this);
        vec2D.normalization();
        return vec2D;
    }

    public void normalization() {
            this.x /= modulus();
            this.y /= modulus();

    }

    public static double distance(Vec2D first, Vec2D second) {
        if (first == null || second == null) {
            throw new NullPointerException("One or two Vec2D elements passed to the function \"distance\" of \"class Vec2D\" are null");
        }

        return Math.sqrt(
                Math.pow(first.x - second.x, 2) +
                Math.pow(first.y - second.y, 2)
        );
    }

    public static Vec2D newNorVec(Vec2D first, Vec2D second) {
        if (first == null || second == null) {
            throw new NullPointerException("One or two Vec2D elements passed to the constructor of \"class Vec2D\" are null");
        }

        Vec2D vec2D = new Vec2D(first.x - second.x, first.y - second.y );
        vec2D.normalization();

        return vec2D;
    }

}
