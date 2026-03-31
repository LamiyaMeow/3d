public class Camera {
    private float[] position;
    private float[] target;
    private float[] up;
    private float yaw = 0;
    private float pitch = 0;

    public Camera(float[] position, float[] target, float[] up) {
        this.position = position;
        this.target = target;
        this.up = up;
    }

    public float[][] getViewMatrix() {
        float[] z = normalize(subtract(position, target));
        float[] x = normalize(cross(up, z));
        float[] y = cross(z, x);

        return new float[][]{
                {x[0], x[1], x[2], -dot(x, position)},
                {y[0], y[1], y[2], -dot(y, position)},
                {z[0], z[1], z[2], -dot(z, position)},
                {0, 0, 0, 1}
        };
    }

    public void moveForward(float speed) {
        float[] direction = normalize(subtract(target, position));
        position = add(position, scale(direction, speed));
        target = add(target, scale(direction, speed));
    }

    public void moveBackward(float speed) {
        float[] direction = normalize(subtract(target, position));
        position = subtract(position, scale(direction, speed));
        target = subtract(target, scale(direction, speed));
    }

    public void moveLeft(float speed) {
        float[] right = normalize(cross(up, subtract(target, position)));
        position = subtract(position, scale(right, speed));
        target = subtract(target, scale(right, speed));
    }

    public void moveRight(float speed) {
        float[] right = normalize(cross(up, subtract(target, position)));
        position = add(position, scale(right, speed));
        target = add(target, scale(right, speed));
    }

    public void moveUp(float speed) {
        position = add(position, scale(up, speed));
        target = add(target, scale(up, speed));
    }

    public void moveDown(float speed) {
        position = subtract(position, scale(up, speed));
        target = subtract(target, scale(up, speed));
    }

    public void rotateYaw(float angle) {
        yaw += Math.toRadians(angle);
        updateTarget();
    }

    public void rotatePitch(float angle) {
        pitch += Math.toRadians(angle);
        pitch = (float) Math.max(-Math.PI / 2, Math.min(Math.PI / 2, pitch));
        updateTarget();
    }

    private void updateTarget() {
        float x = (float) (Math.cos(yaw) * Math.cos(pitch));
        float y = (float) Math.sin(pitch);
        float z = (float) (Math.sin(yaw) * Math.cos(pitch));

        float[] direction = {x, y, z};
        target = add(position, direction);
    }

    private float[] subtract(float[] a, float[] b) {
        return new float[]{a[0] - b[0], a[1] - b[1], a[2] - b[2]};
    }

    private float[] add(float[] a, float[] b) {
        return new float[]{a[0] + b[0], a[1] + b[1], a[2] + b[2]};
    }

    private float[] scale(float[] v, float scalar) {
        return new float[]{v[0] * scalar, v[1] * scalar, v[2] * scalar};
    }

    private float[] normalize(float[] v) {
        float length = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        return new float[]{v[0] / length, v[1] / length, v[2] / length};
    }

    private float[] cross(float[] a, float[] b) {
        return new float[]{
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        };
    }

    private float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }
}
