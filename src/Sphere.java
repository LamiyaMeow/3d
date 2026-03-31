import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sphere {
    private List<float[]> vertices;
    private List<int[]> triangles;
    private Camera camera;
    private float x, y, z, radius;

    public Sphere(Camera camera, float x, float y, float z, float radius, int latSteps, int lonSteps) {
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.vertices = new ArrayList<>();
        this.triangles = new ArrayList<>();

        generateSphere(latSteps, lonSteps);
    }

    public void draw(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        float[][] view = camera.getViewMatrix();
        float[][] projection = Matrix.perspective((float) Math.toRadians(90), (float) width / height, 0.1f, 10f);
        float[][] transform = Matrix.multiply(projection, view);

        int[][] screenVertices = new int[vertices.size()][2];
        boolean[] visible = new boolean[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            float[] worldPos = {vertices.get(i)[0] + x, vertices.get(i)[1] + y, vertices.get(i)[2] + z, 1};
            float[] transformed = Matrix.multiply(transform, worldPos);

            if (transformed[3] <= 0) {
                visible[i] = false;
                continue;
            }
            visible[i] = true;

            screenVertices[i][0] = (int) ((transformed[0] / transformed[3]) * width / 2 + width / 2);
            screenVertices[i][1] = (int) ((-transformed[1] / transformed[3]) * height / 2 + height / 2);
        }

        g2d.setColor(new Color(0, 0, 255, 150));

        for (int[] triangle : triangles) {
            int v1 = triangle[0], v2 = triangle[1], v3 = triangle[2];

            if (!visible[v1] || !visible[v2] || !visible[v3]) continue;

            int[] xPoints = {screenVertices[v1][0], screenVertices[v2][0], screenVertices[v3][0]};
            int[] yPoints = {screenVertices[v1][1], screenVertices[v2][1], screenVertices[v3][1]};

            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }

    private void generateSphere(int latSteps, int lonSteps) {

        for (int lat = 0; lat <= latSteps; lat++) {
            float theta = (float) (Math.PI * lat / latSteps);
            float sinTheta = (float) Math.sin(theta);
            float cosTheta = (float) Math.cos(theta);

            for (int lon = 0; lon <= lonSteps; lon++) {
                float phi = (float) (2 * Math.PI * lon / lonSteps);
                float sinPhi = (float) Math.sin(phi);
                float cosPhi = (float) Math.cos(phi);

                float x = radius * cosPhi * sinTheta;
                float y = radius * cosTheta;
                float z = radius * sinPhi * sinTheta;

                vertices.add(new float[]{x, y, z, 1});
            }
        }


        for (int lat = 0; lat < latSteps; lat++) {
            for (int lon = 0; lon < lonSteps; lon++) {
                int v1 = lat * (lonSteps + 1) + lon;
                int v2 = v1 + lonSteps + 1;
                int v3 = v1 + 1;
                int v4 = v2 + 1;

                if (lat != latSteps - 1) {
                    triangles.add(new int[]{v1, v2, v3});
                    triangles.add(new int[]{v3, v2, v4});
                }
            }
        }
    }
}
