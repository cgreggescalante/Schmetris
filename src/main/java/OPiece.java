public class OPiece implements Tile {
    private int x, y;

    public OPiece() {
        x = 5;
        y = 0;
    }

    @Override
    public void addToGrid(TileValue[][] grid) {
        grid[y][x] = TileValue.O_PIECE;
        grid[y][x + 1] = TileValue.O_PIECE;
        grid[y + 1][x] = TileValue.O_PIECE;
        grid[y + 1][x + 1] = TileValue.O_PIECE;
    }

    @Override
    public boolean canRotate(TileValue[][] grid) {
        return false;
    }

    @Override
    public void rotate(TileValue[][] grid) {

    }

    @Override
    public boolean canFall(TileValue[][] grid) {
        if (y < 18) {
            return grid[y + 2][x] == TileValue.EMPTY && grid[y + 2][x + 1] == TileValue.EMPTY;
        }

        return false;
    }

    @Override
    public void fall(TileValue[][] grid) {
        grid[y][x] = TileValue.EMPTY;
        grid[y][x + 1] = TileValue.EMPTY;

        grid[y + 2][x] = TileValue.O_PIECE;
        grid[y + 2][x + 1] = TileValue.O_PIECE;

        y++;
    }

    @Override
    public boolean canTranslate(TileValue[][] grid, int direction) {
        if (direction < 0) {
            if (x > 0) {
                return grid[y][x - 1] == TileValue.EMPTY && grid[y + 1][x - 1] == TileValue.EMPTY;
            }
        } else {
            if (x < 8) {
                return grid[y][x + 2] == TileValue.EMPTY && grid[y + 1][x + 2] == TileValue.EMPTY;
            }
        }
        return false;
    }

    @Override
    public void translate(TileValue[][] grid, int direction) {
        if (direction < 0) {
            grid[y][x - 1] = TileValue.O_PIECE;
            grid[y + 1][x - 1] = TileValue.O_PIECE;
            grid[y][x + 1] = TileValue.EMPTY;
            grid[y + 1][x + 1] = TileValue.EMPTY;
        } else {
            grid[y][x + 2] = TileValue.O_PIECE;
            grid[y + 1][x + 2] = TileValue.O_PIECE;
            grid[y][x] = TileValue.EMPTY;
            grid[y + 1][x] = TileValue.EMPTY;
        }

        x += direction;
    }
}
