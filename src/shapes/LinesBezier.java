package shapes;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.rCore;
import com.raylib.java.core.input.Mouse.MouseButton;
import com.raylib.java.raymath.Vector2;

public class LinesBezier{

    //TODO.txt:Something's fucky wucky

    /*******************************************************************************************
     *
     *   raylib [shapes] example - Cubic-bezier lines
     *
     *   This example has been created using raylib-j (Version 0.4)
     *   Ported by CreedVI
     *   https://github.com/creedvi/raylib-j
     *
     *   raylib is licensed under an unmodified zlib/libpng license
     *   Original example written and copyright by Ramon Santamaria (@raysan5)
     *   https://github.com/raysan5
     *
     ********************************************************************************************/

    public static void main(String[] args){

        // Initialization
        //--------------------------------------------------------------------------------------
        final int screenWidth = 800;
        final int screenHeight = 450;
        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [shapes] example - cubic-bezier lines");
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_MSAA_4X_HINT);

        Vector2 start = new Vector2();
        Vector2 end = new Vector2(screenWidth, screenHeight);

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())    // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsMouseButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) start = rlj.core.GetMousePosition();
            else if (rlj.core.IsMouseButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) end = rlj.core.GetMousePosition();
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            rlj.text.DrawText("USE MOUSE LEFT-RIGHT CLICK to DEFINE LINE START and END POINTS", 15, 20, 20, Color.GRAY);

            rlj.shapes.DrawLineBezier(start, end, 2.0f, Color.RED);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }
    }
}