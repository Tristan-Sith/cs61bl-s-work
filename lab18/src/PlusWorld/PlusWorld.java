package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    private static final int s = 10;

    /** If true, main draws a tessellation of pluses instead of a single plus.
     *  (Tessellating is an optional exercise; a single plus is the required task.) */
    private static final boolean TESSELLATE = false;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(3 * s, 3 * s);

        // initialize tiles
        TETile[][] world = new TETile[3 * s][3 * s];
        for (int x = 0; x < 3 * s; x += 1) {
            for (int y = 0; y < 3 * s; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        if (TESSELLATE) {
            addTesselation(world, s, Tileset.WALL);
        } else {
            // a plus of size s has a 3s by 3s bounding box, so this one fills the window
            addPlus(world, 0, 0, s, Tileset.WALL);
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }

    /** Fills the size by size square whose lower left corner is (x, y) with tile.
     *  Parts of the square that fall outside of the world are simply not drawn. */
    public static void addSquare(TETile[][] world, int x, int y, int size, TETile tile) {
        for (int i = x; i < x + size; i += 1) {
            for (int j = y; j < y + size; j += 1) {
                if (inBounds(world, i, j)) {
                    world[i][j] = tile;
                }
            }
        }
    }

    /** Adds a plus of the given size whose lower left corner is (x, y). A plus is a
     *  center square of size by size tiles, plus a square of the same size directly
     *  above, below, to the left, and to the right of it, so a plus spans a
     *  3 * size by 3 * size bounding box. */
    public static void addPlus(TETile[][] world, int x, int y, int size, TETile tile) {
        addSquare(world, x, y + size, size, tile);              // left leg
        addSquare(world, x + size, y, size, tile);              // bottom leg
        addSquare(world, x + size, y + size, size, tile);       // center
        addSquare(world, x + size, y + 2 * size, size, tile);   // top leg
        addSquare(world, x + 2 * size, y + size, size, tile);   // right leg
    }

    /** Tiles the entire world with non-overlapping pluses of the given size. Thinking
     *  of the world as a grid of size by size blocks, a plus sits on one block and
     *  covers its four orthogonal neighbors as well. Placing pluses on every block
     *  (i, j) with (i + 2 * j) % 5 == 0 covers each block of the world exactly once.
     *  Each plus gets a slightly different color so that the tiling is visible. */
    public static void addTesselation(TETile[][] world, int size, TETile tile) {
        Random rand = new Random(2873123);
        int maxI = world.length / size;
        int maxJ = world[0].length / size;
        for (int i = -1; i <= maxI; i += 1) {
            for (int j = -1; j <= maxJ; j += 1) {
                if ((i + 2 * j) % 5 == 0) {
                    TETile plusTile = TETile.colorVariant(tile, 40, 40, 40, rand);
                    addPlus(world, i * size - size, j * size - size, size, plusTile);
                }
            }
        }
    }

    /** Returns true if (x, y) is a valid position in the world. */
    private static boolean inBounds(TETile[][] world, int x, int y) {
        return x >= 0 && x < world.length && y >= 0 && y < world[0].length;
    }
}
