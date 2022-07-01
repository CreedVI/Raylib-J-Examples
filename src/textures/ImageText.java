package textures;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.text.Font;
import com.raylib.java.text.rText;
import com.raylib.java.textures.Image;
import com.raylib.java.textures.Texture2D;
import com.raylib.java.textures.rTextures;

import static com.raylib.java.core.input.Keyboard.KEY_SPACE;

public class ImageText{

    /*******************************************************************************************
     *
     *   raylib-j [textures] example - Image text drawing
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

    //TODO.txt: Custom Font loading

    public static void main(String[] args){
        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj  = new Raylib(screenWidth, screenHeight, "raylib-j [texture] example - image text drawing");

        Image parrots = rTextures.LoadImage("resources/parrots.png"); // Load image in CPU memory (RAM)

        // TTF Font loading with custom generation parameters
        Font font = rlj.text.LoadFontEx("resources/KAISG.ttf", 64, null, 0);

        // Draw over image using custom font
        rlj.textures.ImageDrawTextEx(parrots, font, "[Parrots font drawing]", new Vector2(20.0f, 20.0f),
                (float)font.baseSize, 0.0f, Color.RED);

        // Image converted to texture, uploaded to GPU memory (VRAM)
        Texture2D texture = rTextures.LoadTextureFromImage(parrots);

        // Once image has been converted to texture and uploaded to VRAM, it can be unloaded from RAM
        rTextures.UnloadImage(parrots);

        Vector2 position = new Vector2(screenWidth/2.0f - texture.width/2.0f, screenHeight/2.0f - texture.height/2.0f - 20);

        boolean showFont;

        rlj.core.SetTargetFPS(60);
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose())    // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            showFont = rCore.IsKeyDown(KEY_SPACE);
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            if (!showFont)
            {
                // Draw texture with text already drawn inside
                rlj.textures.DrawTextureV(texture, position, Color.WHITE);

                // Draw text directly using sprite font
                rlj.text.DrawTextEx(font, "[Parrots font drawing]", new Vector2(position.x + 20, position.y + 20 + 280),
                                    (float)font.baseSize, 0.0f, Color.WHITE);
            }
            else{
                rlj.textures.DrawTexture(font.getTexture(), screenWidth/2 - font.getTexture().width/2, 50, Color.BLACK);
            }

            rlj.text.DrawText("PRESS SPACE to SEE USED SPRITEFONT ", 290, 420, 10, Color.DARKGRAY);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        rlj.textures.UnloadTexture(texture);     // Texture unloading

        rText.UnloadFont(font);           // Unload custom spritefont
        //--------------------------------------------------------------------------------------
    }

}
