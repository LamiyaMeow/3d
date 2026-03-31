import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JPanel {
    private Cube cube;
    private Sphere sphere;
    private Camera camera;
    private int frames = 0;
    private Timer fpsTimer;
    private Model model;

    public Window() {
        JFrame frame = new JFrame("3D Scene");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        camera = new Camera(new float[]{0, 0, 5}, new float[]{0, 0, 0}, new float[]{0, 1, 0});
        cube = new Cube(camera);
        sphere = new Sphere(camera, 2, 0, 0, 1, 20, 20);
        model = new Model(camera, "12221_Cat_v1_l3.obj");
        model.rotateModel(270, 90, 0);


        fpsTimer = new Timer(1000, e -> {
            frame.setTitle("3D Scene - FPS: " + frames);
            frames = 0;
        });
        fpsTimer.start();


        Timer renderTimer = new Timer(16, e -> {
            cube.update();
            repaint();
            frames++;
        });
        renderTimer.start();


        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleCameraMovement(e.getKeyCode());
            }
        });
    }

    private void handleCameraMovement(int keyCode) {
        float speed = 0.2f;
        float angleSpeed = 5f;

        switch (keyCode) {
            case KeyEvent.VK_W -> camera.moveForward(speed);
            case KeyEvent.VK_S -> camera.moveBackward(speed);
            case KeyEvent.VK_A -> camera.moveLeft(speed);
            case KeyEvent.VK_D -> camera.moveRight(speed);
            case KeyEvent.VK_Q -> camera.moveUp(speed);
            case KeyEvent.VK_E -> camera.moveDown(speed);


            case KeyEvent.VK_LEFT -> camera.rotateYaw(-angleSpeed);
            case KeyEvent.VK_RIGHT -> camera.rotateYaw(angleSpeed);
            case KeyEvent.VK_UP -> camera.rotatePitch(-angleSpeed);
            case KeyEvent.VK_DOWN -> camera.rotatePitch(angleSpeed);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cube.draw(g, getWidth(), getHeight());
        sphere.draw(g, getWidth(), getHeight());
        model.draw(g, getWidth(), getHeight());
    }
}
