package text;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.text.Font;
import com.raylib.java.textures.Textures;

import static com.raylib.java.core.Color.*;
import static com.raylib.java.core.input.Keyboard.KEY_SPACE;
import static com.raylib.java.core.input.Mouse.MouseButton.MOUSE_LEFT_BUTTON;

public class RectangleBounds{

    /*******************************************************************************************
     *
     *   raylib [text] example - Draw text inside a rectangle
     *
     *   This example has been created using raylib 2.3 (www.raylib.com)
     *   raylib is licensed under an unmodified zlib/libpng license (View raylib.h for details)
     *
     *   Example contributed by Vlad Adrian (@demizdor) and reviewed by Ramon Santamaria (@raysan5)
     *
     *   Copyright (c) 2018 Vlad Adrian (@demizdor) and Ramon Santamaria (@raysan5)
     *
     ********************************************************************************************/

    public static void main(String[] args){

        // Initialization
        //--------------------------------------------------------------------------------------
        int screenWidth = 800;
        int screenHeight = 450;

        Raylib rlj = new Raylib(screenWidth, screenHeight, "raylib [text] example - draw text inside a rectangle");

        String text = "Text cannot escape\tthis container\t...word wrap also works when active so here's a long text for " +
            "testing.\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Nec ullamcorper sit amet risus nullam eget felis eget.";

        boolean resizing = false;
        boolean wordWrap = true;

        Rectangle container = new Rectangle(25.0f, 25.0f, screenWidth - 50.0f, screenHeight - 250.0f);
        Rectangle resizer = new Rectangle(container.x + container.width - 17, container.y + container.height - 17, 14, 14);

        // Minimum width and heigh for the container rectangle
        float minWidth = 60;
        float minHeight = 60;
        float maxWidth = screenWidth - 50.0f;
        float maxHeight = screenHeight - 160.0f;

        Vector2 lastMouse = new Vector2(); // Stores last mouse coordinates
        Color borderColor = MAROON;         // Container border color
        Font font = rlj.text.GetFontDefault();       // Get default system font

        rlj.core.SetTargetFPS(60);                   // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!rlj.core.WindowShouldClose()){        // Detect window close button or ESC key

            // Update
            //----------------------------------------------------------------------------------
            if (rlj.core.IsKeyPressed(KEY_SPACE)) wordWrap = !wordWrap;

            Vector2 mouse = rlj.core.GetMousePosition();

            // Check if the mouse is inside the container and toggle border color
            if (rlj.shapes.CheckCollisionPointRec(mouse, container)) borderColor = Textures.Fade(MAROON, 0.4f);
            else if (!resizing) borderColor = MAROON;

            // Container resizing logic
            if (resizing)
            {
                if (rlj.core.IsMouseButtonReleased(MOUSE_LEFT_BUTTON)) resizing = false;

                float width = container.width + (mouse.x - lastMouse.x);
                container.width = (width > minWidth)? ((width < maxWidth)? width : maxWidth) : minWidth;

                float height = container.height + (mouse.y - lastMouse.y);
                container.height = (height > minHeight)? ((height < maxHeight)? height : maxHeight) : minHeight;
            }
            else
            {
                // Check if we're resizing
                if (rlj.core.IsMouseButtonDown(MOUSE_LEFT_BUTTON) && rlj.shapes.CheckCollisionPointRec(mouse, resizer)) resizing = true;
            }

            // Move resizer rectangle properly
            resizer.x = container.x + container.width - 17;
            resizer.y = container.y + container.height - 17;

            lastMouse = mouse; // Update mouse
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            rlj.core.BeginDrawing();

            rlj.core.ClearBackground(RAYWHITE);

            rlj.shapes.DrawRectangleLinesEx(container, 3, borderColor); // Draw container border

            // Draw text in container (add some padding)
            rlj.text.DrawTextRec(font, text, new Rectangle(container.x + 4, container.y + 4, container.width - 4, container.height - 4), 20.0f, 2.0f, wordWrap, GRAY);

            rlj.shapes.DrawRectangleRec(resizer, borderColor);         // Draw the resize box

            // Draw bottom info
            rlj.shapes.DrawRectangle(0, screenHeight - 54, screenWidth, 54, GRAY);
            rlj.shapes.DrawRectangleRec(new Rectangle(382.0f, screenHeight - 34.0f, 12.0f, 12.0f ), MAROON);

            rlj.text.DrawText("Word Wrap: ", 313, screenHeight-115, 20, BLACK);
            if (wordWrap) rlj.text.DrawText("ON", 447, screenHeight - 115, 20, RED);
            else rlj.text.DrawText("OFF", 447, screenHeight - 115, 20, BLACK);

            rlj.text.DrawText("Press [SPACE] to toggle word wrap", 218, screenHeight - 86, 20, GRAY);

            rlj.text.DrawText("Click hold & drag the    to resize the container", 155, screenHeight - 38, 20, RAYWHITE);

            rlj.core.EndDrawing();
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------
    }

}
