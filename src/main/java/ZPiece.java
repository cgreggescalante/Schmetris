public class ZPiece implements Tile {
    private Rotation rotation;

    private int x, y;

    public ZPiece() {
        x = 6;
        y = 0;

        rotation = Rotation.HORIZONTAL;
    }

    @Override
    public void addToGrid(TileValue[][] grid) {
        grid[y][x] = TileValue.Z_PIECE;
        grid[y + 1][x] = TileValue.Z_PIECE;
        grid[y + 1][x + 1] = TileValue.Z_PIECE;
        grid[y][x - 1] = TileValue.Z_PIECE;
    }

    @Override
    public boolean canRotate(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            if (y > 0) {
                return grid[y - 1][x + 1] == TileValue.EMPTY && grid[y][x + 1] == TileValue.EMPTY;
            }
        } else {
            if (x > 0) {
                return grid[y][x - 1] == TileValue.EMPTY && grid[y + 1][x + 1] == TileValue.EMPTY;
            }
        }

        return false;
    }

    @Override
    public void rotate(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            grid[y][x + 1] = TileValue.Z_PIECE;
            grid[y - 1][x + 1] = TileValue.Z_PIECE;

            grid[y][x - 1] = TileValue.EMPTY;
            grid[y + 1][x + 1] = TileValue.EMPTY;

            rotation = Rotation.VERTICAL;
        } else {
            grid[y][x - 1] = TileValue.Z_PIECE;
            grid[y + 1][x + 1] = TileValue.Z_PIECE;

            grid[y - 1][x + 1] = TileValue.EMPTY;
            grid[y][x + 1] = TileValue.EMPTY;

            rotation = Rotation.HORIZONTAL;
        }
    }

    @Override
    public boolean canFall(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            if (y < 18) {
                return grid[y + 2][x] == TileValue.EMPTY && grid[y + 2][x + 1] == TileValue.EMPTY && grid[y + 1][x - 1] == TileValue.EMPTY;
            }
        } else {
            if (y < 18) {
                return grid[y + 2][x] == TileValue.EMPTY && grid[y + 1][x + 1] == TileValue.EMPTY;
            }
        }

        return false;
    }

    @Override
    public void fall(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            grid[y][x] = TileValue.EMPTY;
            grid[y][x - 1] = TileValue.EMPTY;
            grid[y + 1][x + 1] = TileValue.EMPTY;

            grid[y + 1][x - 1] = TileValue.Z_PIECE;
            grid[y + 2][x] = TileValue.Z_PIECE;
            grid[y + 2][x + 1] = TileValue.Z_PIECE;
        } else {
            grid[y - 1][x + 1] = TileValue.EMPTY;
            grid[y][x] = TileValue.EMPTY;

            grid[y + 1][x + 1] = TileValue.Z_PIECE;
            grid[y + 2][x] = TileValue.Z_PIECE;
        }

        y++;
    }

    @Override
    public boolean canTranslate(TileValue[][] grid, int direction) {
        if (rotation == Rotation.HORIZONTAL) {
            if (direction < 0) {
                if (x > 1) {
                    return grid[y][x - 2] == TileValue.EMPTY && grid[y + 1][x - 1] == TileValue.EMPTY;
                }
            } else {
                if (x < 8) {
                    return grid[y + 1][x + 2] == TileValue.EMPTY && grid[y][x + 1] == TileValue.EMPTY;
                }
            }
        } else {
            if (direction < 0) {
                if (x > 0) {
                    return grid[y - 1][x] == TileValue.EMPTY && grid[y][x - 1] == TileValue.EMPTY && grid[y + 1][x - 1] == TileValue.EMPTY;
                }
            } else {
                if (x < 8) {
                    return grid[y - 1][x + 2] == TileValue.EMPTY && grid[y][x + 2] == TileValue.EMPTY && grid[y + 1][x + 1] == TileValue.EMPTY;
                }
            }
        }

        return false;
    }

    @Override
    public void translate(TileValue[][] grid, int direction) {
        if (rotation == Rotation.HORIZONTAL) {
            if (direction < 0) {
                grid[y][x - 2] = TileValue.Z_PIECE;
                grid[y + 1][x - 1] = TileValue.Z_PIECE;

                grid[y + 1][x + 1] = TileValue.EMPTY;
                grid[y][x] = TileValue.EMPTY;
            } else {
                grid[y][x - 1] = TileValue.EMPTY;
                grid[y + 1][x] = TileValue.EMPTY;

                grid[y][x + 1] = TileValue.Z_PIECE;
                grid[y + 1][x + 2] = TileValue.Z_PIECE;
            }
        } else {
            if (direction < 0) {
                grid[y - 1][x] = TileValue.Z_PIECE;
                grid[y][x - 1] = TileValue.Z_PIECE;
                grid[y + 1][x - 1] = TileValue.Z_PIECE;

                grid[y - 1][x + 1] = TileValue.EMPTY;
                grid[y][x + 1] = TileValue.EMPTY;
                grid[y + 1][x] = TileValue.EMPTY;
            } else {
                grid[y - 1][x + 1] = TileValue.EMPTY;
                grid[y][x] = TileValue.EMPTY;
                grid[y + 1][x] = TileValue.EMPTY;

                grid[y - 1][x + 2] = TileValue.Z_PIECE;
                grid[y][x + 2] = TileValue.Z_PIECE;
                grid[y + 1][x + 1] = TileValue.Z_PIECE;
            }
        }

        x += direction;
    }
}
