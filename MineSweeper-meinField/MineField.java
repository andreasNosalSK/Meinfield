import java.util.Random;

public class MineField {

    private static final int TILE_SIZE = Tile.BLOCK_SIZE;

    private int rows;
    private int columns;
    private Tile[][] tiles;

    // layout
    private int offsetX;
    private int offsetY;

    private int mineCount;
    private boolean initialized;

    public MineField(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;

        this.offsetX = 50;
        this.offsetY = 100;
        this.mineCount = (rows * cols) / 6;
        this.initialized = false;

        this.tiles = new Tile[this.rows][this.columns];

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.tiles[r][c] = new Tile(r, c, this.offsetX, this.offsetY);
            }
        }
    }
    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Tile getTile(int row, int column) {
        if (this.isInside(row, column)) {
            return this.tiles[row][column];
        }
        return null;
    }

    public boolean isInsidePixel(int x, int y) {
        return x >= this.offsetX && x < this.offsetX + this.columns * TILE_SIZE
                && y >= this.offsetY && y < this.offsetY + this.rows * TILE_SIZE;
    }

    public int getRowFromPixel(int y) {
        return (y - this.offsetY) / TILE_SIZE;
    }

    public int getColumnFromPixel(int x) {
        return (x - this.offsetX) / TILE_SIZE;
    }

    public boolean revealTile(int row, int column) {
        if (!this.isInside(row, column)) {
            return true;
        }

        if (!this.initialized) {
            this.initializeField(row, column);
        }

        Tile tile = this.tiles[row][column];

        if (tile.isRevealed() || tile.isFlagged()) {
            return true;
        }

        if (tile.hasMine()) {
            tile.reveal();
            return false;
        }

        if (tile.getAdjacentMines() == 0) {
            this.floodReveal(row, column);
        } else {
            tile.reveal();
        }

        return true;
    }

    public void toggleFlagOnTile(int row, int column) {
        if (this.isInside(row, column)) {
            this.tiles[row][column].toggleFlag();
        }
    }

    public void revealAllMines() {
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                if (this.tiles[r][c].hasMine()) {
                    this.tiles[r][c].reveal();
                }
            }
        }
    }

    private void initializeField(int safeRow, int safeColumn) {
        this.placeMines(this.mineCount, safeRow, safeColumn);
        this.computeNumbers();
        this.initialized = true;
    }

    private void placeMines(int mineCount, int safeRow, int safeColumn) {
        Random random = new Random();
        int placed = 0;

        while (placed < mineCount) {
            int r = random.nextInt(this.rows);
            int c = random.nextInt(this.columns);

            if (Math.abs(r - safeRow) <= 1 && Math.abs(c - safeColumn) <= 1) {
                continue;
            }

            if (!this.tiles[r][c].hasMine()) {
                this.tiles[r][c].placeMine();
                placed = placed + 1;
            }
        }
    }

    private void computeNumbers() {
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                if (!this.tiles[r][c].hasMine()) {
                    int count = this.countMinesAround(r, c);
                    for (int i = 0; i < count; i++) {
                        this.tiles[r][c].incrementAdjacentMines();
                    }
                }
            }
        }
    }

    private int countMinesAround(int row, int column) {
        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                int rr = row + dr;
                int cc = column + dc;
                if (this.isInside(rr, cc) && this.tiles[rr][cc].hasMine()) {
                    count = count + 1;
                }
            }
        }

        return count;
    }

    private void floodReveal(int row, int column) {
        if (!this.isInside(row, column)) {
            return;
        }

        Tile tile = this.tiles[row][column];

        if (tile.isRevealed() || tile.isFlagged()) {
            return;
        }

        tile.reveal();

        // stop when we reach a number
        if (tile.getAdjacentMines() > 0) {
            return;
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                this.floodReveal(row + dr, column + dc);
            }
        }
    }

    private boolean isInside(int row, int column) {
        return row >= 0 && row < this.rows
                && column >= 0 && column < this.columns;
    }
}
