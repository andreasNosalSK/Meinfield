import fri.shapesge.Image;

public class Tile {

    public static final int BLOCK_SIZE = 16;

    private int row;
    private int column;

    private boolean hasMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    private Image image;

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

    public void placeMine() {
        this.hasMine = true;
    }

    public void incrementAdjacentMines() {
        this.adjacentMines = this.adjacentMines + 1;
    }

    public boolean hasMine() {
        return this.hasMine;
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public int getAdjacentMines() {
        return this.adjacentMines;
    }

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
