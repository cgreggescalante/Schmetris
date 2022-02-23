public class IPiece implements Tile {
    private int x, y;

    private Rotation rotation;

    public IPiece() {
        x = 3;
        y = 0;

        rotation = Rotation.HORIZONTAL;
    }

    @Override
    public void addToGrid(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            for (int i = 0; i < 4; i++) {
                grid[y][x + i] = TileValue.I_PIECE;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                grid[y + i][x] = TileValue.I_PIECE;
            }
        }
    }

    @Override
    public boolean canRotate(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            if (y > 0 && y < 18) {
                for (int i = -1; i < 3; i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (grid[y + i][x + 2] != TileValue.EMPTY) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            if (x > 1 && x < 9) {
                for (int i = -2; i < 2; i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (grid[y + 1][x + i] != TileValue.EMPTY) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void rotate(TileValue[][] grid) {
        if (rotation == Rotation.HORIZONTAL) {
            for (int i = 0; i < 4; i++) {
                grid[y][x + i] = TileValue.EMPTY;
            }
            x += 2;
            y -= 1;
            rotation = Rotation.VERTICAL;
        } else {
            for (int i = 0; i < 4; i++) {
                grid[y + i][x] = TileValue.EMPTY;
            }
            x -= 2;
            y += 1;
            rotation = Rotation.HORIZONTAL;
        }
        addToGrid(grid);
    }

    @Override
    public boolean canFall(TileValue[][] grid) {
        if (rotation == Rotation.VERTICAL) {
            if (y < grid.length - 4) {
                return grid[y + 4][x] == TileValue.EMPTY;
            }
        } else {
            if (y < grid.length - 1) {
                for (int i = 0; i < 4; i++) {
                    if (grid[y + 1][x + i] != TileValue.EMPTY) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void fall(TileValue[][] grid) {
        if (rotation == Rotation.VERTICAL) {
            grid[y][x] = TileValue.EMPTY;
            grid[y + 4][x] = TileValue.I_PIECE;
        } else {
            for (int i = 0; i < 4; i++) {
                grid[y][x + i] = TileValue.EMPTY;
                grid[y + 1][x + i] = TileValue.I_PIECE;
            }
        }

        y++;
    }

    @Override
    public boolean canTranslate(TileValue[][] grid, int direction) {
        if (rotation == Rotation.HORIZONTAL) {
            if (direction < 0) {
                return x > 0 && grid[y][x - 1] == TileValue.EMPTY;
            } else {
                return x < 6 && grid[y][x + 4] == TileValue.EMPTY;
            }
        } else {
            if (direction < 0) {
                if (x > 0) {
                    for (int i = 0; i < 4; i++) {
                        if (grid[y + i][x - 1] != TileValue.EMPTY) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (x < 9) {
                    for (int i = 0; i < 4; i++) {
                        if (grid[y + i][x + 1] != TileValue.EMPTY) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void translate(TileValue[][] grid, int direction) {
        if (rotation == Rotation.HORIZONTAL) {
            if (direction < 0) {
                grid[y][x + 3] = TileValue.EMPTY;
                grid[y][x - 1] = TileValue.I_PIECE;
            } else {
                grid[y][x] = TileValue.EMPTY;
                grid[y][x + 4] = TileValue.I_PIECE;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                grid[y + i][x] = TileValue.EMPTY;
                grid[y + i][x + direction] = TileValue.I_PIECE;
            }
        }

        x += direction;
    }
}
