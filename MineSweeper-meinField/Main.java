import fri.shapesge.Manager;
import javax.swing.JOptionPane;

public class Main {

    private Manager manager;
    private GUI gui;
    private int counter;
    private String[] options = {"Small", "Medium", "Large"};

    private boolean flagMode;
    private boolean gameOver;

    public Main() {
        int option = JOptionPane.showOptionDialog(
                null,
                "Welcome, choose your field size.",
                "MineField",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                this.options,
                this.options[0]
        );

        if (option == -1) {
            System.exit(0);
        }

        switch (option) {
            case 0:
                this.gui = new GUI(10, 10);
                break;
            case 1:
                this.gui = new GUI(20, 20);
                break;
            case 2:
                this.gui = new GUI(25, 25);
                break;
            default:
                this.gui = new GUI(10, 10);
                break;
        }

        this.flagMode = false;
        this.gameOver = false;

        this.counter = 0;
        this.manager = new Manager();
        this.manager.manageObject(this);
    }

    public void tick() {
        if (this.gameOver) {
            return;
        }

        if (this.counter >= 4) {
            this.gui.incrementTime();
            this.counter = 0;
        }
        this.counter = this.counter + 1;
    }

    public void chooseCoordinates(int x, int y) {
        System.out.println("mouse");
        if (this.gameOver) {
            return;
        }

        MineField field = this.gui.getMineField();

        if (!field.isInsidePixel(x, y)) {
            return; // click outside the field
        }

        int row = field.getRowFromPixel(y);
        int col = field.getColumnFromPixel(x);

        if (this.flagMode) {
            field.toggleFlagOnTile(row, col);
        } else {
            boolean safe = field.revealTile(row, col);

            if (!safe) {
                field.revealAllMines();
                this.gameOver = true;
                JOptionPane.showMessageDialog(null, "Boom! You hit a mine.");
                this.manager.stopManagingObject(this);
            } else {
                this.gui.incrementScore();
            }
        }
    }

    public void activate() {
        System.out.println("asd");
        this.flagMode = !this.flagMode;
    }
}
