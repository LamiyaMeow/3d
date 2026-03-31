import java.awt.*;

public class Cube {
    private final float[][] cubeVertices = {
            {-1, -1, -1, 1}, {1, -1, -1, 1}, {1, 1, -1, 1}, {-1, 1, -1, 1},
            {-1, -1, 1, 1}, {1, -1, 1, 1}, {1, 1, 1, 1}, {-1, 1, 1, 1}
    };

    private final int[][] cubeEdges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0},
            {4, 5}, {5, 6}, {6, 7}, {7, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    private float angle = 0;
    private Camera camera;

    public Cube(Camera camera) {
        this.camera = camera;
    }

    public void update() {
        angle += 0.02;
    }

    public void draw(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        float[][] rotation = Matrix.rotationY(angle);
        float[][] view = camera.getViewMatrix();
        float[][] projection = Matrix.perspective((float) Math.toRadians(90), (float) width / height, 0.1f, 10f);
        float[][] transform = Matrix.multiply(projection, view);

        int[][] screenVertices = new int[8][2];
        boolean[] visible = new boolean[8];

        for (int i = 0; i < cubeVertices.length; i++) {
            float[] transformed = Matrix.multiply(transform, Matrix.multiply(rotation, cubeVertices[i]));

            if (transformed[3] <= 0) {
                visible[i] = false;
                continue;
            }
            visible[i] = true;

            screenVertices[i][0] = (int) ((transformed[0] / transformed[3]) * width / 2 + width / 2);
            screenVertices[i][1] = (int) ((-transformed[1] / transformed[3]) * height / 2 + height / 2);
        }

        for (int[] edge : cubeEdges) {
            int v1 = edge[0], v2 = edge[1];

            if (!visible[v1] || !visible[v2]) continue;

            g2d.drawLine(screenVertices[v1][0], screenVertices[v1][1],
                    screenVertices[v2][0], screenVertices[v2][1]);
        }


        drawWorldAxis(g2d, width, height, transform);
    }

    private void drawWorldAxis(Graphics2D g2d, int width, int height, float[][] transform) {
        int[][] axisScreenCoords = new int[3][2];

        float[][] axisLines = {
                {2, 0, 0, 1},
                {0, 2, 0, 1},
                {0, 0, 2, 1}
        };

        Color[] axisColors = {Color.RED, Color.GREEN, Color.BLUE};

        int[] originScreenCoords = projectPoint(0, 0, 0, transform, width, height);

        for (int i = 0; i < axisLines.length; i++) {
            int[] projected = projectPoint(axisLines[i][0], axisLines[i][1], axisLines[i][2], transform, width, height);

            g2d.setColor(axisColors[i]);
            g2d.drawLine(originScreenCoords[0], originScreenCoords[1], projected[0], projected[1]);
        }
    }

    private int[] projectPoint(float x, float y, float z, float[][] transform, int width, int height) {
        float[] point = {x, y, z, 1};
        float[] transformed = Matrix.multiply(transform, point);

        if (transformed[3] <= 0) return new int[]{width / 2, height / 2};

        int screenX = (int) ((transformed[0] / transformed[3]) * width / 2 + width / 2);
        int screenY = (int) ((-transformed[1] / transformed[3]) * height / 2 + height / 2);

        return new int[]{screenX, screenY};
    }
}
