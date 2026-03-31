    import java.awt.*;
    import java.util.List;

    public class Model {
        private Camera camera;
        private List<float[]> vertices;
        private List<int[]> faces;

        public Model(Camera camera, String filePath) {
            this.camera = camera;
            if (OBJLoader.loadOBJ(filePath)) {
                this.vertices = OBJLoader.vertices;
                this.faces = OBJLoader.faces;
                flipModel(true, false, false);
            } else {
                System.out.println("Помилка завантаження моделі!");
            }
        }

        public void flipModel(boolean flipX, boolean flipY, boolean flipZ) {
            for (float[] vertex : vertices) {
                if (flipX) vertex[0] = -vertex[0];
                if (flipY) vertex[1] = -vertex[1];
                if (flipZ) vertex[2] = -vertex[2];
            }
        }


        public void rotateModel(float angleX, float angleY, float angleZ) {
            double radX = Math.toRadians(angleX);
            double radY = Math.toRadians(angleY);
            double radZ = Math.toRadians(angleZ);

            for (float[] vertex : vertices) {
                float x = vertex[0], y = vertex[1], z = vertex[2];


                float newY = (float) (y * Math.cos(radX) - z * Math.sin(radX));
                float newZ = (float) (y * Math.sin(radX) + z * Math.cos(radX));
                y = newY;
                z = newZ;


                float newX = (float) (x * Math.cos(radY) + z * Math.sin(radY));
                newZ = (float) (-x * Math.sin(radY) + z * Math.cos(radY));
                x = newX;
                z = newZ;


                newX = (float) (x * Math.cos(radZ) - y * Math.sin(radZ));
                newY = (float) (x * Math.sin(radZ) + y * Math.cos(radZ));
                x = newX;
                y = newY;


                vertex[0] = x;
                vertex[1] = y;
                vertex[2] = z;
            }
        }

        public void draw(Graphics g, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);

            float[][] view = camera.getViewMatrix();
            float[][] projection = Matrix.perspective((float) Math.toRadians(90), (float) width / height, 0.1f, 10f);
            float[][] transform = Matrix.multiply(projection, view);


            int[][] screenVertices = new int[vertices.size()][2];
            boolean[] visible = new boolean[vertices.size()];

            for (int i = 0; i < vertices.size(); i++) {
                float[] worldPos = {vertices.get(i)[0], vertices.get(i)[1], vertices.get(i)[2], 1};
                float[] transformed = Matrix.multiply(transform, worldPos);

                if (transformed[3] <= 0) {
                    visible[i] = false;
                    continue;
                }
                visible[i] = true;

                screenVertices[i][0] = (int) ((transformed[0] / transformed[3]) * width / 2 + width / 2);
                screenVertices[i][1] = (int) ((-transformed[1] / transformed[3]) * height / 2 + height / 2);
            }


            g2d.setColor(new Color(0, 255, 0, 150));

            for (int[] face : faces) {
                int v1 = face[0], v2 = face[1], v3 = face[2];

                if (!visible[v1] || !visible[v2] || !visible[v3]) continue;

                int[] xPoints = {screenVertices[v1][0], screenVertices[v2][0], screenVertices[v3][0]};
                int[] yPoints = {screenVertices[v1][1], screenVertices[v2][1], screenVertices[v3][1]};

                g2d.fillPolygon(xPoints, yPoints, 3);
            }
        }
    }
