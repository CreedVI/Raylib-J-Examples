package core;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.rCore;
import com.raylib.java.textures.rTextures;

public class DropFiles{

    /*******************************************************************************************
     *
     *   raylib-j [core] example - Windows drop files
     *
     *   This example only works on platforms that support drag & drop (Windows, Linux, OSX, Html5?)
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
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib-j [core] example - drop files");

        int count = 0;
        String[] droppedFiles = new String[8];

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose()){    // Detect window close button or ESC key
            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsFileDropped()){
                droppedFiles = rlj.core.GetDroppedFiles();
                count = rlj.core.GetDroppedFilesCount();
            }
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            if (count == 0){
                rlj.text.DrawText("Drop your files to this window!", 100, 40, 20, Color.DARKGRAY);
            }
            else{
                rlj.text.DrawText("Dropped files:", 100, 40, 20, Color.DARKGRAY);

                for (int i = 0; i < count; i++){
                    if (i % 2 == 0){
                        rlj.shapes.DrawRectangle(0, 85 + 40 * i, screenWidth, 40, rTextures.Fade(Color.LIGHTGRAY, 0.5f));
                    }
                    else{
                        rlj.shapes.DrawRectangle(0, 85 + 40 * i, screenWidth, 40, rTextures.Fade(Color.LIGHTGRAY, 0.3f));
                    }

                    rlj.text.DrawText(droppedFiles[i], 120, 100 + 40 * i, 10, Color.GRAY);
                }

                rlj.text.DrawText("Drop new files...", 100, 110 + 40 * count, 20, Color.DARKGRAY);
            }

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        rCore.ClearDroppedFiles();    // Clear internal buffers
        //--------------------------------------------------------------------------------------

    }

}
