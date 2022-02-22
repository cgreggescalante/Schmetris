import processing.core.PApplet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Schmetris extends PApplet {
    static final int STEP_RATE = 30;

    static final List<Class<? extends Tile>> PIECES = new ArrayList<>() {{
        add(IPiece.class);
        add(OPiece.class);
        add(SPiece.class);
    }};

    Tile.TileValue[][] gridStates;

    Tile tile;

    int timeStatic;

    boolean downPressed;

    public void settings() {
        size(500, 600);
    }

    public void setup() {
        tile = null;

        timeStatic = 0;

        downPressed = false;

        gridStates = new Tile.TileValue[20][10];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                gridStates[i][j] = Tile.TileValue.EMPTY;
            }
        }

        drawFrame();
    }

    public void draw() {
        drawGrid();

        if (tile == null) {
            for (int i = 19; i > -1; i--) {
                while (isRowFull(i)) {
                    for (int j = 0; j < 10; j++) {
                        gridStates[i][j] = Tile.TileValue.EMPTY;
                        for (int k = i; k > 0; k--) {
                            gridStates[k][j] = gridStates[k - 1][j];
                        }
                        gridStates[0][j] = Tile.TileValue.EMPTY;
                    }
                }
            }
            tile = randomPiece();
            tile.addToGrid(gridStates);
        }

        if (downPressed && frameCount % (STEP_RATE / 5) == 0 || frameCount % STEP_RATE == 0) {
            if (tile.canFall(gridStates)) {
                tile.fall(gridStates);
            } else {
                timeStatic++;
                if (timeStatic > 3) {
                    tile = null;
                    timeStatic = 0;
                }
            }
        }
    }

    public void drawFrame() {
        background(200);

        pushStyle();

        noStroke();
        fill(100);

        rect(0, 0, 100, height);
        rect(400, 0, 100, height);

        popStyle();
    }

    public void drawGrid() {
        pushStyle();

        stroke(52);

        for (int i = 0; i <= 10; i++) {
            line(100 + i * 30, 0, 100 + i * 30, height);
        }

        for (int i = 0; i <= 20; i++) {
            line(100, i * 30, 400, i * 30);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                fill(gridStates[j][i].r, gridStates[j][i].g, gridStates[j][i].b);
                rect(100 + i * 30, j * 30, 30, 30);
            }
        }

        popStyle();
    }

    public void keyPressed() {
        if (tile != null) {
            if (keyCode == LEFT && tile.canTranslate(gridStates, -1)) {
                tile.translate(gridStates, -1);
            } else if (keyCode == RIGHT && tile.canTranslate(gridStates, 1)) {
                tile.translate(gridStates, 1);
            } else if (keyCode == DOWN && tile.canRotate(gridStates)) {
                tile.rotate(gridStates);
            }
        }

        if (key == ' ') {
            downPressed = true;
        }
    }

    public void keyReleased() {
        if (key == ' ') {
            downPressed = false;
        }
    }

    public Tile randomPiece() {
        try {
            return PIECES.get(new Random().nextInt(PIECES.size())).getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isRowFull(int row) {
        for (int i = 0; i < 10; i++) {
            if (gridStates[row][i] == Tile.TileValue.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean isRowClear(int row) {
        for (int i = 0; i < 10; i++) {
            if (gridStates[row][i] != Tile.TileValue.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] pArgs = new String[]{"Schmetris"};

        PApplet.runSketch(pArgs, new Schmetris());
    }
}