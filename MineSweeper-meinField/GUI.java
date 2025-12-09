public class GUI {

    private MineField mineField;
    private Score score;
    private Time time;
    private int rows;
    private int cols;

    public GUI(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.mineField = new MineField(rows, cols);
        this.score = new Score(50, 70);
        this.time = new Time(50, 50);
    }

    public void incrementTime() {
        this.time.increment();
    }

    public void incrementScore() {
        this.score.addScore();
    }

    public MineField getMineField() {
        return this.mineField;
    }
}
