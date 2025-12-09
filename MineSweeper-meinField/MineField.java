import java.util.Random;

/**
 * Trieda MineField predstavuje hracie pole hry Minesweeper.
 *
 * Uchováva dvojrozmerné pole objektov , ktoré reprezentujú jednotlivé
 * políčka na ploche. Zodpovedá za vytvorenie hracieho poľa, náhodné generovanie mín,
 * výpočet čísiel okolo mín a za logiku odkrývania políčok po kliknutí.
 *
 * Trieda zároveň konvertuje pixely z kliknutia myšou na dlaždicové súradnice (row, column).
 */

public class MineField {

    private static final int TILE_SIZE = Tile.BLOCK_SIZE;

    private int rows;
    private int columns;
    private Tile[][] tiles;

    private int offsetX;
    private int offsetY;

    private int mineCount;
    private boolean initialized;

    /**
     * Konštruktor vytvorí hracie pole daných rozmerov.
     * Míny sa zatiaľ negenerujú – to sa spraví až pri prvom kliknutí.
     *
     * @param rows počet riadkov
     * @param cols počet stĺpcov
     */
    public MineField(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;

        this.offsetX = 50;
        this.offsetY = 100;
        
        if (this.columns == 9 && this.columns == 9) {
            this.mineCount = 10;
        } else if (this.columns == 15 && this.columns == 15) {
            this.mineCount = 34;
        } else if (this.columns == 21 && this.columns == 16) {
            this.mineCount = 60;
        } else {
            this.mineCount = (this.rows * this.columns) / 6;
        }
        
        this.initialized = false;

        this.tiles = new Tile[this.rows][this.columns];

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.tiles[r][c] = new Tile(r, c, this.offsetX, this.offsetY);
            }
        }
    }
    
    /** @return počet riadkov. */
    public int getRows() {
        return this.rows;
    }

    /** @return počet stĺpcov. */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Získa block na daných súradniciach, ak existuje.
     *
     * @param row riadok
     * @param column stĺpec
     * @return tile alebo null, ak súradnice nie sú platné
     */
    public Tile getTile(int row, int column) {
        if (this.isInside(row, column)) {
            return this.tiles[row][column];
        }
        return null;
    }

    /**
     * Kontrola, či daná pixelová pozícia patrí do hracieho poľa.
     *
     * @param x súradnica X
     * @param y súradnica Y
     * @return true, ak je kliknutie vnútri poľa
     */
    public boolean isInsidePixel(int x, int y) {
        return x >= this.offsetX && x < this.offsetX + this.columns * TILE_SIZE
                && y >= this.offsetY && y < this.offsetY + this.rows * TILE_SIZE;
    }

    /**
     * Prevod pixelovej Y súradnice na index riadka.
     */
    public int getRowFromPixel(int y) {
        return (y - this.offsetY) / TILE_SIZE;
    }

    /**
     * Prevod pixelovej X súradnice na index stĺpca.
     */
    public int getColumnFromPixel(int x) {
        return (x - this.offsetX) / TILE_SIZE;
    }

    /**
     * Odkryje políčko na daných súradniciach.
     * Obsahuje kompletnú hernú logiku odkrývania.
     *
     * Prvé kliknutie, generuje sa bezpečné hracie pole,
     * Flajka na políčku neodkryje.
     * Políčko je mína, ukončí hru, ak je číslo pokračuje ďalej, ak prázdne odkryje ďalšie.
     *
     * @param row riadok
     * @param column stĺpec
     * @return true ak je políčko bezpečné, false ak je mína
     */
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

    /**
     * Označí alebo odznačí vlajku na danom políčku.
     */
    public void toggleFlagOnTile(int row, int column) {
        if (this.isInside(row, column)) {
            this.tiles[row][column].toggleFlag();
        }
    }

    /**
     * Odkryje všetky míny – používa sa pri prehre.
     */
    public void revealAllMines() {
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                if (this.tiles[r][c].hasMine()) {
                    this.tiles[r][c].reveal();
                }
            }
        }
    }

    /**
     * Inicializuje hracie pole pri prvom kliknutí.
     * Umiestni míny tak, aby prvé kliknutie aj jeho okolie bolo bezpečné.
     * Vypočíta čísla susedných mín.
     */
    private void initializeField(int safeRow, int safeColumn) {
        this.placeMines(this.mineCount, safeRow, safeColumn);
        this.computeNumbers();
        this.initialized = true;
    }

    /**
     * Náhodne rozmiestni míny po hracom poli.
     */
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

    /**
     * Pre každé políčko, ktoré nie je mínou, spočíta počet mín v jeho okolí.
     */
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

    /**
     * Spočíta, koľko mín sa nachádza v okolí daného políčka.
     */
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

    /**
     * Rekurzívne odkrýva prázdne políčka (adjacentMines == 0)
     * a ich susedov, až kým nenarazí na políčko s číslom.
     */
    private void floodReveal(int row, int column) {
        if (!this.isInside(row, column)) {
            return;
        }

        Tile tile = this.tiles[row][column];

        if (tile.isRevealed() || tile.isFlagged()) {
            return;
        }

        tile.reveal();

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

    /**
     * Kontrola, či súradnice, patria do hracieho poľa.
     */
    private boolean isInside(int row, int column) {
        return row >= 0 && row < this.rows
                && column >= 0 && column < this.columns;
    }
}
