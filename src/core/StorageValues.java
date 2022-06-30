package core;

import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.core.input.Keyboard;

import java.io.IOException;

import static com.raylib.java.core.Color.*;
import static com.raylib.java.core.input.Keyboard.KEY_ENTER;
import static com.raylib.java.core.input.Keyboard.KEY_SPACE;
import static core.StorageValues.StorageData.STORAGE_POSITION_HISCORE;
import static core.StorageValues.StorageData.STORAGE_POSITION_SCORE;

public class StorageValues{

    static class StorageData{
        final static int
            STORAGE_POSITION_SCORE = 0,
            STORAGE_POSITION_HISCORE = 1;
    }

    public static void main(String[] args){
        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib-j [core] example - storage save/load values");

        int score = 0;
        int hiscore = 0;
        int framesCounter = 0;

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())    // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsKeyPressed(Keyboard.KEY_R)){
                score = rCore.GetRandomValue(1000, 2000);
                hiscore = rCore.GetRandomValue(2000, 4000);
            }

            if (rlj.core.IsKeyPressed(KEY_ENTER)){
                try{
                    rlj.core.SaveStorageValue(STORAGE_POSITION_SCORE, score);
                    rlj.core.SaveStorageValue(STORAGE_POSITION_HISCORE, hiscore);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if (rlj.core.IsKeyPressed(KEY_SPACE)){
                // NOTE: If requested position could not be found, value 0 is returned
                try{
                    score = rlj.core.LoadStorageValue(STORAGE_POSITION_SCORE);
                    hiscore = rlj.core.LoadStorageValue(STORAGE_POSITION_HISCORE);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            framesCounter++;
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(RAYWHITE);

            rlj.text.DrawText("SCORE: " + score, 280, 130, 40, MAROON);
            rlj.text.DrawText("HI-SCORE: " + hiscore, 210, 200, 50, BLACK);

            rlj.text.DrawText("frames: " + framesCounter, 10, 10, 20, LIME);

            rlj.text.DrawText("Press R to generate random numbers", 220, 40, 20, LIGHTGRAY);
            rlj.text.DrawText("Press ENTER to SAVE values", 250, 310, 20, LIGHTGRAY);
            rlj.text.DrawText("Press SPACE to LOAD values", 252, 350, 20, LIGHTGRAY);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        //TODO.txt: Unload values here
        //--------------------------------------------------------------------------------------

    }

}
