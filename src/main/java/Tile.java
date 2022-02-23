public interface Tile {
    enum Rotation {
        HORIZONTAL, VERTICAL
    }

    enum TileValue {
        EMPTY(0),
        I_PIECE(1, 0, 241, 241),
        O_PIECE(2, 214, 214, 0),
        J_PIECE(3),
        L_PIECE(4),
        T_PIECE(5),
        S_PIECE(6, 0, 220, 0),
        Z_PIECE(7, 230, 0, 0);

        public final int value, r, g, b;

        TileValue(int value) {
            this.value = value;
            this.r = 100;
            this.g = 100;
            this.b = 100;
        }

        TileValue(int value, int r, int g, int b) {
            this.value = value;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    void addToGrid(TileValue[][] grid);

    boolean canRotate(TileValue[][] grid);

    void rotate(TileValue[][] grid);

    boolean canFall(TileValue[][] grid);

    void fall(TileValue[][] grid);

    boolean canTranslate(TileValue[][] grid, int direction);

    void translate(TileValue[][] grid, int direction);
}
