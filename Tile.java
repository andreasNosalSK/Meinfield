import fri.shapesge.Image;


//Reprezentuje jedno políčko na hracom poli.

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

    //Vytvorí nové poličko na danej pozícii.

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

    //Nastaví políčko tak, aby obsahovalo mínu.
    public void placeMine() {
        this.hasMine = true;
    }

    //Zvýši počet susedných min o 1.
    public void incrementAdjacentMines() {
        this.adjacentMines = this.adjacentMines + 1;
    }

    //Skontroluje, či políčko obsahuje mínu.
    public boolean hasMine() {
        return this.hasMine;
    }

    //Skontroluje, či je políčko odhalené.
    public boolean isRevealed() {
        return this.isRevealed;
    }

    //Skontroluje či je políčko označené vlajkou.
    public boolean isFlagged() {
        return this.isFlagged;
    }

    //Vráti počet susedných mín (0-8).
    public int getAdjacentMines() {
        return this.adjacentMines;
    }

    /**
     * Prepne stav vlajky (označí/odznačí políčko).
     * Ak je políčko odhalené, neurobí nič.
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
     * Odhalí políčko, zmení jeho obrázok podľa obsahu (mína, číslo, prázdne).
     * Ak je políčko už odhalené alebo označené, neurobí nič.
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
