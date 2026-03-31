# 3D Software Renderer (Java)

## Description

This project is a simple **3D renderer written in Java using Swing**, without any external graphics libraries (like OpenGL or DirectX).

It demonstrates how 3D graphics work internally, including:

* Camera transformations
* Perspective projection
* Matrix math
* Rendering 3D objects on a 2D screen

The program renders:

* A rotating cube
* A generated sphere
* A 3D model loaded from an `.obj` file

---

## Features

* 3D camera movement (WASD + QE)
* Camera rotation using arrow keys
* Perspective projection
* Custom matrix math implementation
* OBJ model loading
* Real-time rendering (FPS counter)
* Basic face rendering (filled triangles)

---

##  Controls

| Key   | Action                  |
| ----- | ----------------------- |
| W / S | Move forward / backward |
| A / D | Move left / right       |
| Q / E | Move up / down          |
| ↑ / ↓ | Look up / down          |
| ← / → | Look left / right       |

---

## Technologies Used

* Java
* Java Swing (Graphics2D)
* Custom math (no external libraries)

---

## Project Structure

```

Main.java          # Entry point
Window.java        # Main window and render loop

Camera.java        # Camera movement and view matrix
Matrix.java        # Matrix math operations

Cube.java          # Cube rendering
Sphere.java        # Sphere generation and rendering
Model.java         # 3D model rendering
OBJLoader.java     # OBJ file loader
```

---

## How to Run

1. Open the project in any Java IDE:

    * IntelliJ IDEA
    * Eclipse
    * NetBeans

2. Make sure you have Java installed (JDK 8+)

3. Place your `.obj` file in the project root:

   ```
   12221_Cat_v1_l3.obj
   ```

4. Run:

```
Main.java
```

---

##  Supported Model Format

The project supports basic `.obj` files:

* Vertices (`v`)
* Faces (`f`)

 Note:

* Only triangular faces are supported
* No textures or lighting

---

##  How It Works

1. Model vertices are transformed using:

    * Rotation
    * View matrix (camera)
    * Projection matrix

2. 3D coordinates are converted to 2D screen space using:

   ```
   x_screen = (x / w) * width/2 + width/2
   y_screen = (-y / w) * height/2 + height/2
   ```

3. Faces are drawn using `Graphics2D`

---

##  Limitations

* No Z-buffer (depth sorting may be incorrect)
* No lighting or shading
* No textures
* Basic clipping

---

##  Author

* LamiyaMeow

---

## License

This project is created for educational purposes.
