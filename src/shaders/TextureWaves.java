package shaders;

import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.rlgl.RLGL;
import com.raylib.java.rlgl.shader.Shader;
import com.raylib.java.textures.Texture2D;

import static com.raylib.java.core.Color.RAYWHITE;
import static com.raylib.java.core.Color.WHITE;

public class TextureWaves{

    /*******************************************************************************************
     *
     *   raylib [shaders] example - Texture Waves
     *
     *   NOTE: This example requires raylib OpenGL 3.3 or ES2 versions for shaders support,
     *         OpenGL 1.1 does not support shaders, recompile raylib to OpenGL 3.3 version.
     *
     *   NOTE: Shaders used in this example are #version 330 (OpenGL 3.3), to test this example
     *         on OpenGL ES 2.0 platforms (Android, Raspberry Pi, HTML5), use #version 100 shaders
     *         raylib comes with shaders ready for both versions, check raylib/shaders install folder
     *
     *   This example has been created using raylib-j (Version 0.4)
     *   Ported by CreedVI
     *   https://github.com/creedvi/raylib-j
     *
     *   raylib is licensed under an unmodified zlib/libpng license
     *   Original example written and copyright by Ramon Santamaria (@raysan5)
     *   https://github.com/raysan5
     *
     *   Copyright (c) 2019 Anata (@anatagawa)
     *
     ********************************************************************************************/

    public static void main(String[] args){

        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [shaders] example - texture waves");

        // Load texture texture to apply shaders
        Texture2D texture = rlj.textures.LoadTexture("resources/space.png");

        // Load shader and setup location points and values
        Shader shader = rlj.core.LoadShader(null, "resources/shaders/glsl330/wave.fs");

        int secondsLoc = rCore.GetShaderLocation(shader, "secondes");
        int freqXLoc = rCore.GetShaderLocation(shader, "freqX");
        int freqYLoc = rCore.GetShaderLocation(shader, "freqY");
        int ampXLoc = rCore.GetShaderLocation(shader, "ampX");
        int ampYLoc = rCore.GetShaderLocation(shader, "ampY");
        int speedXLoc = rCore.GetShaderLocation(shader, "speedX");
        int speedYLoc = rCore.GetShaderLocation(shader, "speedY");

        // Shader uniform values that can be updated at any time
        float[] freqX = {25.0f};
        float[] freqY = {25.0f};
        float[] ampX = {5.0f};
        float[] ampY = {5.0f};
        float[] speedX = {8.0f};
        float[] speedY = {8.0f};

        float[] screenSize = { (float) rlj.core.GetScreenWidth(), (float) rlj.core.GetScreenHeight() };
        rCore.SetShaderValue(shader, rCore.GetShaderLocation(shader, "size"), screenSize, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_VEC2);
        rCore.SetShaderValue(shader, freqXLoc, freqX, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
        rCore.SetShaderValue(shader, freqYLoc, freqY, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
        rCore.SetShaderValue(shader, ampXLoc, ampX, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
        rCore.SetShaderValue(shader, ampYLoc, ampY, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
        rCore.SetShaderValue(shader, speedXLoc, speedX, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
        rCore.SetShaderValue(shader, speedYLoc, speedY, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);

        float seconds = 0.0f;

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        // -------------------------------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())    // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            seconds += rlj.core.GetFrameTime();

            rCore.SetShaderValue(shader, secondsLoc, new float[]{seconds}, RLGL.rlShaderUniformDataType.RL_SHADER_UNIFORM_FLOAT);
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(RAYWHITE);

            rlj.core.BeginShaderMode(shader);

            rlj.textures.DrawTexture(texture, 0, 0, WHITE);
            rlj.textures.DrawTexture(texture, texture.width, 0, WHITE);

            rlj.core.EndShaderMode();

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        rlj.core.UnloadShader(shader);         // Unload shader
        rlj.textures.UnloadTexture(texture);       // Unload texture
        //--------------------------------------------------------------------------------------
    }

}
