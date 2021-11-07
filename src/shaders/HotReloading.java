package shaders;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.rlgl.RLGL;
import com.raylib.java.rlgl.shader.Shader;

import static com.raylib.java.core.input.Keyboard.KEY_A;
import static com.raylib.java.core.input.Mouse.MouseButton.MOUSE_LEFT_BUTTON;
import static com.raylib.java.rlgl.RLGL.ShaderUniformDataType.SHADER_UNIFORM_VEC2;

public class HotReloading{

    /*******************************************************************************************
     *
     *   raylib [shaders] example - Hot reloading
     *
     *   NOTE: This example requires raylib OpenGL 3.3 for shaders support and only #version 330
     *         is currently supported. OpenGL ES 2.0 platforms are not supported at the moment.
     *
     *   This example has been created using raylib 3.0 (www.raylib.com)
     *   raylib is licensed under an unmodified zlib/libpng license (View raylib.h for details)
     *
     *   Copyright (c) 2020 Ramon Santamaria (@raysan5)
     *
     ********************************************************************************************/

    public static void main(String[] args){
        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [shaders] example - hot reloading");

        String fragShaderFileName = "resources/shaders/glsl330/reload.fs";
        long fragShaderFileModTime = rlj.core.GetFileModTime(fragShaderFileName);

        // Load raymarching shader
        // NOTE: Defining 0 (NULL) for vertex shader forces usage of internal default vertex shader
        Shader shader = rlj.core.LoadShader(null, fragShaderFileName);

        // Get shader locations for required uniforms
        int resolutionLoc = rlj.core.GetShaderLocation(shader, "resolution");
        int mouseLoc = rlj.core.GetShaderLocation(shader, "mouse");
        int timeLoc = rlj.core.GetShaderLocation(shader, "time");

        float[] resolution = {(float) screenWidth, (float) screenHeight};
        rlj.core.SetShaderValue(shader, resolutionLoc, resolution, SHADER_UNIFORM_VEC2);

        float totalTime = 0.0f;
        boolean shaderAutoReloading = false;

        rlj.core.SetTargetFPS(60);                       // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())            // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            totalTime += rlj.core.GetFrameTime();
            Vector2 mouse = rlj.core.GetMousePosition();
            float[] mousePos = {mouse.x, mouse.y};

            // Set shader required uniform values
            rlj.core.SetShaderValue(shader, timeLoc, new float[]{totalTime}, RLGL.ShaderUniformDataType.SHADER_UNIFORM_FLOAT);
            rlj.core.SetShaderValue(shader, mouseLoc, mousePos, SHADER_UNIFORM_VEC2);

            // Hot shader reloading
            if (shaderAutoReloading || (rlj.core.IsMouseButtonPressed(MOUSE_LEFT_BUTTON))){
                long currentFragShaderModTime = rlj.core.GetFileModTime(fragShaderFileName);

                // Check if shader file has been modified
                if (currentFragShaderModTime != fragShaderFileModTime){
                    // Try reloading updated shader
                    Shader updatedShader = rlj.core.LoadShader(null, fragShaderFileName);

                    if (updatedShader.getId() != rlj.core.rlgl.rlGetShaderDefault().getId()){      // It was correctly loaded
                        rlj.core.UnloadShader(shader);
                        shader = updatedShader;

                        // Get shader locations for required uniforms
                        resolutionLoc = rlj.core.GetShaderLocation(shader, "resolution");
                        mouseLoc = rlj.core.GetShaderLocation(shader, "mouse");
                        timeLoc = rlj.core.GetShaderLocation(shader, "time");

                        // Reset required uniforms
                        rlj.core.SetShaderValue(shader, resolutionLoc, resolution, SHADER_UNIFORM_VEC2);
                    }

                    fragShaderFileModTime = currentFragShaderModTime;
                }
            }

            if (rlj.core.IsKeyPressed(KEY_A)) shaderAutoReloading = !shaderAutoReloading;
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            // We only draw a white full-screen rectangle, frame is generated in shader
            rlj.core.BeginShaderMode(shader);
            rlj.shapes.DrawRectangle(0, 0, screenWidth, screenHeight, Color.WHITE);
            rlj.core.EndShaderMode();

            rlj.text.DrawText("PRESS [A] to TOGGLE SHADER AUTOLOADING: " + (shaderAutoReloading ? "AUTO" : "MANUAL")
                    , 10, 10, 10, (shaderAutoReloading ? Color.RED : Color.BLACK));
            if (!shaderAutoReloading) rlj.text.DrawText("MOUSE CLICK to SHADER RE-LOADING", 10, 30, 10, Color.BLACK);

            rlj.text.DrawText("Shader last modification: " + fragShaderFileModTime, 10, 430, 10, Color.BLACK);


            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        rlj.core.UnloadShader(shader);           // Unload shader
        //--------------------------------------------------------------------------------------
    }

}
