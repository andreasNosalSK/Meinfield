import fri.shapesge.Manager;
import javax.swing.JOptionPane;

/**
 * Trieda Main predstavuje hlavnú riadiacu triedu celej hry Minesweeper.
 *
 * Zodpovednosti triedy Main:
 * - Zobraziť úvodný dialóg a nechať používateľa vybrať veľkosť hracieho poľa.
 * - Vytvoriť objekt GUI, ktorý obsahuje MineField, Score a Time.
 * - Spravovať hernú logiku: odkrývanie políčok, označovanie vlajkami, výhra/prehra.
 * - Spravovať časovač pomocou periodickej metódy tick().
 * - Poskytovať reakcie na kliknutia myšou a aktiváciu vlajkovacieho módu.
 */

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

    /**
     * Metóda tick() je volaná Managerom každých 0.25 sekundy.
     *
     * Úloha:
     * - Po štyroch tikoch (1 sekunda) zvýši čas pomocou gui.incrementTime().
     * - Ak je hra ukončená, nič sa nevykoná.
     */
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

    /**
     * Spracovanie kliknutia myšou – Manager zavolá túto metódu pri výbere súradníc.
     *
     * Postup:
     * Overí sa, či kliknutie patrí do hracieho poľa.
     * Prevedú sa pixely na indexy riadka a stĺpca.
     * Podľa režimu (flagMode) sa buď, odryje políčko alebo nastaví vlajka
     * Ak hráč klikol na mínu, všetky míny sa odkryjú a Manager sa zastaví.
     *
     * @param x pozícia kliknutia na osi X v pixeloch
     * @param y pozícia kliknutia na osi Y v pixeloch
     */
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

    /**
     * Prepína režim označovania vlajkami.
     * Ak je flagMode true, kliknutie označí vlajku.
     *
     * Túto metódu volá Manager pri stlačení klávesy Enter.
     */
    public void activate() {
        System.out.println("asd");
        this.flagMode = !this.flagMode;
    }
}
