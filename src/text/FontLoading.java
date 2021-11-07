package text;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.text.Font;

import static com.raylib.java.core.input.Keyboard.KEY_SPACE;

public class FontLoading{

    /*******************************************************************************************
     *
     *   raylib [text] example - Font loading
     *
     *   raylib can load fonts from multiple file formats:
     *
     *     - TTF/OTF > Sprite font atlas is generated on loading, user can configure
     *                 some of the generation parameters (size, characters to include)
     *     - BMFonts > Angel code font fileformat, sprite font image must be provided
     *                 together with the .fnt file, font generation cna not be configured
     *     - XNA Spritefont > Sprite font image, following XNA Spritefont conventions,
     *                 Characters in image must follow some spacing and order rules
     *
     *   This example has been created using raylib 2.6 (www.raylib.com)
     *   raylib is licensed under an unmodified zlib/libpng license (View raylib.h for details)
     *
     *   Copyright (c) 2016-2019 Ramon Santamaria (@raysan5)
     *
     ********************************************************************************************/

    public static void main(String[] args){

        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [text] example - font loading");

        // Define characters to draw
        // NOTE: raylib supports UTF-8 encoding, following list is actually codified as UTF8 internally
        String msg = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHI\nJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmn\nopqrstuvwxyz" +
            "{|}~¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓ\nÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷\nøùúûüýþÿ";

        // NOTE: Textures/Fonts MUST be loaded after Window initialization (OpenGL context is required)

        // BMFont (AngelCode) : Font data and image atlas have been generated using external program
        Font fontBm = rlj.text.LoadFont("resources/pixantiqua.fnt");

        // TTF font : Font data and atlas are generated directly from TTF
        // NOTE: We define a font base size of 32 pixels tall and up-to 250 characters
        Font fontTtf = rlj.text.LoadFontEx("resources/pixantiqua.ttf", 32, null, 250);

        boolean useTtf = false;

        rlj.core.SetTargetFPS(60);               // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose()) {   // Detect window close button or ESC key
            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsKeyDown(KEY_SPACE)){
                useTtf = true;
            }
            else{
                useTtf = false;
            }
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(Color.RAYWHITE);

            rlj.text.DrawText("Hold SPACE to use TTF generated font", 20, 20, 20, Color.LIGHTGRAY);

            if (!useTtf)
            {
                rlj.text.DrawTextEx(fontBm, msg, new Vector2(20.0f, 100.0f), (float)fontBm.baseSize, 2, Color.MAROON);
                rlj.text.DrawText("Using BMFont (Angelcode) imported", 20, rlj.core.GetScreenHeight() - 30, 20, Color.GRAY);
            }
            else
            {
                rlj.text.DrawTextEx(fontTtf, msg, new Vector2(20.0f, 100.0f), (float)fontTtf.baseSize, 2, Color.LIME);
                rlj.text.DrawText("Using TTF font generated", 20, rlj.core.GetScreenHeight() - 30, 20, Color.GRAY);
            }

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        rlj.text.UnloadFont(fontBm);     // AngelCode Font unloading
        rlj.text.UnloadFont(fontTtf);    // TTF Font unloading
        //--------------------------------------------------------------------------------------

    }

}
