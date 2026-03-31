import java.util.Arrays;

public class Matrix {
    public static float[][] identityMatrix() {
        return new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] rotationX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] rotationY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new float[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] rotationZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new float[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] translation(float tx, float ty, float tz) {
        return new float[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
    }

    public static float[][] scaling(float sx, float sy, float sz) {
        return new float[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] perspective(float fov, float aspect, float near, float far) {
        float f = 1.0f / (float) Math.tan(fov / 2);
        return new float[][]{
                {f / aspect, 0, 0, 0},
                {0, f, 0, 0},
                {0, 0, (far + near) / (near - far), (2 * far * near) / (near - far)},
                {0, 0, -1, 0}
        };
    }
    public static float[] multiply(float[][] matrix, float[] vector) {
        if (vector.length != 4) {
            throw new IllegalArgumentException("Vector must have 4 elements");
        }

        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = matrix[i][0] * vector[0] +
                    matrix[i][1] * vector[1] +
                    matrix[i][2] * vector[2] +
                    matrix[i][3] * vector[3];
        }
        return result;
    }

    public static float[][] multiply(float[][] a, float[][] b) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static void printMatrix(float[][] matrix) {
        for (float[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
