package text;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class FormatText{

    /*******************************************************************************************
     *
     *   raylib [text] example - Text formatting
     *
     *   This example has been created using raylib 1.1 (www.raylib.com)
     *   raylib is licensed under an unmodified zlib/libpng license (View raylib.h for details)
     *
     *   Copyright (c) 2014 Ramon Santamaria (@raysan5)
     *
     ********************************************************************************************/

    public static void main(String[] args){

        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [text] example - text formatting");

        int score = 100020;
        int hiscore = 200450;
        int lives = 5;

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose()) {   // Detect window close button or ESC key

            // Update
            //----------------------------------------------------------------------------------
            // TODO.txt: Update your variables here
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            rlj.text.DrawText(rlj.text.TextFormat("Score: %08d", score), 200, 80, 20, Color.RED);

            rlj.text.DrawText(rlj.text.TextFormat("HiScore: %08d", hiscore), 200, 120, 20, Color.GREEN);

            rlj.text.DrawText(rlj.text.TextFormat("Lives: %02d", lives), 200, 160, 40, Color.BLUE);

            rlj.text.DrawText(rlj.text.TextFormat("Elapsed Time: %02.02f ms", rlj.core.GetFrameTime()*1000), 200, 220, 20, Color.BLACK);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------

    }

}
