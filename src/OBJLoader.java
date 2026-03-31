    import java.io.*;
    import java.util.*;

    public class OBJLoader {
        public static List<float[]> vertices = new ArrayList<>();
        public static List<int[]> faces = new ArrayList<>();

        public static boolean loadOBJ(String filePath) {
            vertices.clear();
            faces.clear();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\s+");

                    if (parts.length < 4) continue;


                    if (parts[0].equals("v")) {
                        float x = Float.parseFloat(parts[1]);
                        float y = Float.parseFloat(parts[2]);
                        float z = Float.parseFloat(parts[3]);
                        vertices.add(new float[]{x, y, z, 1});
                    }


                    else if (parts[0].equals("f")) {
                        int v1 = Integer.parseInt(parts[1].split("/")[0]) - 1;
                        int v2 = Integer.parseInt(parts[2].split("/")[0]) - 1;
                        int v3 = Integer.parseInt(parts[3].split("/")[0]) - 1;
                        faces.add(new int[]{v1, v2, v3});
                    }
                }
            } catch (IOException e) {
                System.err.println("OBJ: " + e.getMessage());
                return false;
            }

            return true;
        }
    }
