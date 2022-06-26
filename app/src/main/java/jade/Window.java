package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null)
            Window.window = new Window();

        return Window.window;
    }

    public void run() {
        System.out.println("Hello World" + Version.getVersion());

        init();
        loop();

        // Free the memory after exit
        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();

    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initalize GLFW");

        // Config GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);

        // Create the window
        glfwWindow = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (glfwWindow == MemoryUtil.NULL)
            throw new IllegalStateException("Failed to create glfw wind");

        // Event listenr
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make window visible
        GLFW.glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    public void loop() {
        while (!GLFW.glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            GLFW.glfwPollEvents();

            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.6f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GLFW.glfwSwapBuffers(glfwWindow);
        }
    }
}
