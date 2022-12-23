import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Main {
    public static void main(String[] args) {
        glfwInit();
        // Create a new window
        long window = GLFW.glfwCreateWindow(800, 600, "Image drawer", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        // Create an OpenGL context
        GL.createCapabilities();

        // Load the image using STB
        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];
        ByteBuffer data = stbi_load("/home/shivc/Downloads/background.png", width, height, channels, 0);

        // Create an OpenGL texture
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width[0], height[0], 0, GL11.GL_RGBA , GL11.GL_UNSIGNED_BYTE, data);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        while(!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glEnable(GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(-1, -1);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(1, -1);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(1, 1);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(-1, 1);
            GL11.glEnd();
            glDisable(GL_TEXTURE_2D);

            glfwSwapBuffers(window);
        }

        // Clean up resources
        stbi_image_free(data);
        GLFW.glfwDestroyWindow(window);
    }
}