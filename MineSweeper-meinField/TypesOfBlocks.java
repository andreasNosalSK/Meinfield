public enum TypesOfBlocks {
    NUMBER_1("pics\\minesweeper_1.jpg"),
    NUMBER_2("pics\\minesweeper_2.jpg"),
    NUMBER_3("pics\\minesweeper_3.jpg"),
    NUMBER_4("pics\\minesweeper_4.jpg"),
    NUMBER_5("pics\\minesweeper_5.jpg"),
    NUMBER_6("pics\\minesweeper_6.jpg"),
    NUMBER_7("pics\\minesweeper_7.jpg"),
    NUMBER_8("pics\\minesweeper_8.jpg"),
    EMPTY("pics\\minesweeper_empty.jpg"),
    FLAG("pics\\minesweeper_flag.jpg"),
    FLAG_TOGGLE("pics\\minesweeper_flag_toggled.jpg"),
    BLOCK("pics\\minesweeper_block.jpg"),
    MINE("pics\\minesweeper_mine.jpg");
    
    private String typeOfPicture;
    
    private TypesOfBlocks(String typeOfPicture) {
        this.typeOfPicture = typeOfPicture;
    }
    
    public String getPathOfPicture() {
        return this.typeOfPicture;
    }
}
