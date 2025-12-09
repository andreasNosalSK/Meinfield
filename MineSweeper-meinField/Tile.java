import fri.shapesge.Image;


/**
 * Trieda Tile reprezentuje jedno políčko/block na hracom poli hry Minesweeper.
 *
 * Každé políčko uchováva:
 * svoju pozíciu (row, column),
 * informáciu, či obsahuje mínu,
 * či je odhalené,
 * či je označené vlajkou,
 * počet susedných mín (0–8),
 * obrázok, ktorý sa má aktuálne zobraziť na hernej ploche.
 *
 * Trieda je zodpovedná za:
 * - zmenu vzhľadu dlaždice podľa stavu,
 * - prepínanie vlajky,
 * - odhaľovanie políčka,
 */
public class Tile {

    // Definivanie velkosti bloku.
    public static final int BLOCK_SIZE = 16;

    private int row;
    private int column;

    private boolean hasMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    private Image image;

    /**
     * Nové políčko na zadanej mriežkovej pozícii.
     *
     * Na začiatku je políčko:
     * neodhalené,
     * bez míny,
     * bez vlajky,
     * s hodnotou susedných mín = 0.
     *
     * Zobrazí sa základný obrázok zakrytého políčka (BLOCK).
     *
     * @param row riadok v hracej mriežke
     * @param column stĺpec v hracej mriežke
     * @param offsetX posun hracieho poľa v osi X
     * @param offsetY posun hracieho poľa v osi Y
     */
    public Tile(int row, int column, int offsetX, int offsetY) {
        this.row = row;
        this.column = column;

        this.hasMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;

        this.image = new Image(TypesOfBlocks.BLOCK.getPathOfPicture());
        int x = offsetX + column * BLOCK_SIZE;
        int y = offsetY + row * BLOCK_SIZE;
        this.image.changePosition(x, y);
        this.image.makeVisible();
    }

    /**
     * Nastaví políčko tak, aby obsahovalo mínu.
     */
    public void placeMine() {
        this.hasMine = true;
    }

    /**
     * Zvýši počet susedných mín o 1.
     */
    public void incrementAdjacentMines() {
        this.adjacentMines = this.adjacentMines + 1;
    }

    /** @return true, ak políčko obsahuje mínu. */
    public boolean hasMine() {
        return this.hasMine;
    }

    /** @return true, ak je políčko už odhalené. */
    public boolean isRevealed() {
        return this.isRevealed;
    }

    /** @return true, ak je políčko označené vlajkou. */
    public boolean isFlagged() {
        return this.isFlagged;
    }

    /** @return počet susedných mín (0–8). */
    public int getAdjacentMines() {
        return this.adjacentMines;
    }

    /**
     * Prepne stav vlajky na políčku.
     */
    public void toggleFlag() {
        if (this.isRevealed) {
            return;
        }

        this.isFlagged = !this.isFlagged;
        if (this.isFlagged) {
            this.image.changeImage(TypesOfBlocks.FLAG.getPathOfPicture());
        } else {
            this.image.changeImage(TypesOfBlocks.BLOCK.getPathOfPicture());
        }
    }

    /**
     * Odhalí políčko a zmení jeho vzhľad podľa stavu.
     *
     *
     *  Ak je už je odhalene alebo vlajka, nestane sa nič.
     *  Inač zobrazí podľa očakávaní.
     * - ak má susedné míny → zobrazí príslušné číselo.
     */
    public void reveal() {
        if (this.isRevealed || this.isFlagged) {
            return;
        }

        this.isRevealed = true;

        if (this.hasMine) {
            this.image.changeImage(TypesOfBlocks.MINE.getPathOfPicture());
        } else if (this.adjacentMines == 0) {
            this.image.changeImage(TypesOfBlocks.EMPTY.getPathOfPicture());
        } else {
            String path = this.getNumberImagePath();
            this.image.changeImage(path);
        }
    }

    /**
     * @return cesta k obrázku.
     */
    private String getNumberImagePath() {
        switch (this.adjacentMines) {
            case 1:
                return TypesOfBlocks.NUMBER_1.getPathOfPicture();
            case 2:
                return TypesOfBlocks.NUMBER_2.getPathOfPicture();
            case 3:
                return TypesOfBlocks.NUMBER_3.getPathOfPicture();
            case 4:
                return TypesOfBlocks.NUMBER_4.getPathOfPicture();
            case 5:
                return TypesOfBlocks.NUMBER_5.getPathOfPicture();
            case 6:
                return TypesOfBlocks.NUMBER_6.getPathOfPicture();
            case 7:
                return TypesOfBlocks.NUMBER_7.getPathOfPicture();
            case 8:
                return TypesOfBlocks.NUMBER_8.getPathOfPicture();
            default:
                return TypesOfBlocks.EMPTY.getPathOfPicture();
        }
    }
}
