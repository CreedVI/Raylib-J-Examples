package shaders;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.Core;
import com.raylib.java.core.camera.Camera;
import com.raylib.java.core.camera.Camera3D;
import com.raylib.java.raymath.Vector3;
import com.raylib.java.rlgl.shader.Shader;
import com.raylib.java.textures.Texture2D;
import com.raylib.java.utils.rLights;
import com.raylib.java.utils.rLights.*;

import static com.raylib.java.Config.ConfigFlag.FLAG_MSAA_4X_HINT;
import static com.raylib.java.core.camera.Camera.CameraMode.CAMERA_ORBITAL;
import static com.raylib.java.core.camera.Camera.CameraProjection.CAMERA_PERSPECTIVE;
import static com.raylib.java.core.input.Keyboard.*;
import static com.raylib.java.rlgl.RLGL.ShaderLocationIndex.SHADER_LOC_MATRIX_MODEL;
import static com.raylib.java.rlgl.RLGL.ShaderLocationIndex.SHADER_LOC_VECTOR_VIEW;
import static com.raylib.java.rlgl.RLGL.ShaderUniformDataType.SHADER_UNIFORM_VEC3;
import static com.raylib.java.rlgl.RLGL.ShaderUniformDataType.SHADER_UNIFORM_VEC4;
import static com.raylib.java.utils.rLights.LIGHT_POINT;
import static com.raylib.java.utils.rLights.MAX_LIGHTS;

public class BasicLighting{

    /*******************************************************************************************
     *
     *   raylib [shaders] example - basic lighting
     *
     *   NOTE: This example requires raylib OpenGL 3.3 or ES2 versions for shaders support,
     *         OpenGL 1.1 does not support shaders, recompile raylib to OpenGL 3.3 version.
     *
     *   NOTE: Shaders used in this example are #version 330 (OpenGL 3.3).
     *
     *   This example has been created using raylib 2.5 (www.raylib.com)
     *   raylib is licensed under an unmodified zlib/libpng license (View raylib.h for details)
     *
     *   Example contributed by Chris Camacho (@codifies) and reviewed by Ramon Santamaria (@raysan5)
     *
     *   Chris Camacho (@codifies -  http://bedroomcoders.co.uk/) notes:
     *
     *   This is based on the PBR lighting example, but greatly simplified to aid learning...
     *   actually there is very little of the PBR example left!
     *   When I first looked at the bewildering complexity of the PBR example I feared
     *   I would never understand how I could do simple lighting with raylib however its
     *   a testement to the authors of raylib (including rlights.h) that the example
     *   came together fairly quickly.
     *
     *   Copyright (c) 2019 Chris Camacho (@codifies) and Ramon Santamaria (@raysan5)
     *
     ********************************************************************************************/

    //TODO.txt: Models

    public static void main(String[] args){
        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;
        Raylib rlj = new Raylib();

        Core.SetConfigFlags(FLAG_MSAA_4X_HINT);  // Enable Multi Sampling Anti Aliasing 4x (if available)
        rlj.core.InitWindow(screenWidth, screenHeight, "raylib [shaders] example - basic lighting");

        // Define the camera to look into our 3d world
        Camera3D camera = new Camera3D();
        camera.position = new Vector3(2.0f, 2.0f, 6.0f);    // Camera position
        camera.target = new Vector3(0.0f, 0.5f, 0.0f);      // Camera looking at point
        camera.up = new Vector3(0.0f, 1.0f, 0.0f);          // Camera up vector (rotation towards target)
        camera.fovy = 45.0f;                                        // Camera field-of-view Y
        camera.projection = CAMERA_PERSPECTIVE;                     // Camera mode type

        /* Load models
        Model modelA = LoadModelFromMesh(GenMeshTorus(0.4f, 1.0f, 16, 32));
        Model modelB = LoadModelFromMesh(GenMeshCube(1.0f, 1.0f, 1.0f));
        Model modelC = LoadModelFromMesh(GenMeshSphere(0.5f, 32, 32));
        */

        // Load models texture
        Texture2D texture = rlj.textures.LoadTexture("resources/texel_checker.png");

        /* Assign texture to default model material
        modelA.materials[0].maps[MATERIAL_MAP_DIFFUSE].texture = texture;
        modelB.materials[0].maps[MATERIAL_MAP_DIFFUSE].texture = texture;
        modelC.materials[0].maps[MATERIAL_MAP_DIFFUSE].texture = texture;
        */
        Shader shader = rlj.core.LoadShader("resources/shaders/glsl330/base_lighting.vs",
                                   "resources/shaders/glsl330/lighting.fs");

        // Get some shader loactions
        shader.locs[SHADER_LOC_MATRIX_MODEL] = rlj.core.GetShaderLocation(shader, "matModel");
        shader.locs[SHADER_LOC_VECTOR_VIEW] = rlj.core.GetShaderLocation(shader, "viewPos");

        // ambient light level
        int ambientLoc = rlj.core.GetShaderLocation(shader, "ambient");
        rlj.core.SetShaderValue(shader, ambientLoc, new float[]{0.2f, 0.2f, 0.2f, 1.0f}, SHADER_UNIFORM_VEC4);

        float angle = 6.282f;

        /* All models use the same shader
        modelA.materials[0].shader = shader;
        modelB.materials[0].shader = shader;
        modelC.materials[0].shader = shader;
        */

        // Using 4 point lights, white, red, green and blue
        Light[] lights = new Light[MAX_LIGHTS];
        lights[0] = rLights.CreateLight(LIGHT_POINT, new Vector3(4, 2, 4), new Vector3(), Color.WHITE, shader);
        lights[1] = rLights.CreateLight(LIGHT_POINT, new Vector3(4, 2, 4), new Vector3(), Color.RED, shader);
        lights[2] = rLights.CreateLight(LIGHT_POINT, new Vector3(0, 4, 2), new Vector3(), Color.GREEN, shader);
        lights[3] = rLights.CreateLight(LIGHT_POINT, new Vector3(0, 4, 2), new Vector3(), Color.BLUE, shader);

        Camera.SetCameraMode(camera, CAMERA_ORBITAL);  // Set an orbital camera mode

        rlj.core.SetTargetFPS(60);                       // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())            // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsKeyPressed(KEY_W)) { lights[0].enabled = !lights[0].enabled; }
            if (rlj.core.IsKeyPressed(KEY_R)) { lights[1].enabled = !lights[1].enabled; }
            if (rlj.core.IsKeyPressed(KEY_G)) { lights[2].enabled = !lights[2].enabled; }
            if (rlj.core.IsKeyPressed(KEY_B)) { lights[3].enabled = !lights[3].enabled; }

            Camera.UpdateCamera(camera);              // Update camera

            // Make the lights do differing orbits
            angle -= 0.02f;
            lights[0].position.x = (float) (Math.cos(angle)*4.0f);
            lights[0].position.z = (float) (Math.sin(angle)*4.0f);
            lights[1].position.x = (float) (Math.cos(-angle*0.6f)*4.0f);
            lights[1].position.z = (float) (Math.sin(-angle*0.6f)*4.0f);
            lights[2].position.y = (float) (Math.cos(angle*0.2f)*4.0f);
            lights[2].position.z = (float) (Math.sin(angle*0.2f)*4.0f);
            lights[3].position.y = (float) (Math.cos(-angle*0.35f)*4.0f);
            lights[3].position.z = (float) (Math.sin(-angle*0.35f)*4.0f);

            rLights.UpdateLightValues(shader, lights[0]);
            rLights.UpdateLightValues(shader, lights[1]);
            rLights.UpdateLightValues(shader, lights[2]);
            rLights.UpdateLightValues(shader, lights[3]);

            /* Rotate the torus
            modelA.transform = MatrixMultiply(modelA.transform, MatrixRotateX(-0.025f));
            modelA.transform = MatrixMultiply(modelA.transform, MatrixRotateZ(0.012f));
            */

            // Update the light shader with the camera view position
            float[] cameraPos = new float[]{ camera.position.x, camera.position.y, camera.position.z };
            rlj.core.SetShaderValue(shader, shader.locs[SHADER_LOC_VECTOR_VIEW], cameraPos, SHADER_UNIFORM_VEC3);
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            rlj.core.BeginMode3D(camera);

            /* Draw the three models
            rlj.models.DrawModel(modelA, new Vector3(), 1.0f, Color.WHITE);
            rlj.models.DrawModel(modelB, new Vector3(-1.6f,0.0f,0.0f), 1.0f, Color.WHITE);
            rlj.models.DrawModel(modelC, new Vector3(1.6f,0.0f,0.0f), 1.0f, Color.WHITE);
            */

            // Draw markers to show where the lights are
            if (lights[0].enabled) { rlj.models.DrawSphereEx(lights[0].position, 0.2f, 8, 8, Color.WHITE); }
            if (lights[1].enabled) { rlj.models.DrawSphereEx(lights[1].position, 0.2f, 8, 8, Color.RED); }
            if (lights[2].enabled) { rlj.models.DrawSphereEx(lights[2].position, 0.2f, 8, 8, Color.GREEN); }
            if (lights[3].enabled) { rlj.models.DrawSphereEx(lights[3].position, 0.2f, 8, 8, Color.BLUE); }

            rlj.models.DrawGrid(10, 1.0f);

            rlj.core.EndMode3D();

            rlj.text.DrawFPS(10, 10);

            rlj.text.DrawText("Use keys RGBW to toggle lights", 10, 30, 20, Color.DARKGRAY);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        /*
        rlj.models.UnloadModel(modelA);        // Unload the modelA
        rlj.models.UnloadModel(modelB);        // Unload the modelB
        rlj.models.UnloadModel(modelC);        // Unload the modelC
         */

        rlj.textures.UnloadTexture(texture);     // Unload the texture
        rlj.core.UnloadShader(shader);       // Unload shader
        //--------------------------------------------------------------------------------------
    }
}
