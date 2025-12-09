/**
 * Trieda GUI predstavuje jednoduchú vrstvu používateľského rozhrania.
 * Uchováva a spravuje hlavné komponenty hry – hracie pole (MineField),
 * skóre (Score) a časovač (Time).
 *
 */
public class GUI {

    private MineField mineField;
    private Score score;
    private Time time;
    private int rows;
    private int cols;

    /**
     * Konštruktor vytvorí GUI pre hru s danými rozmermi hracieho poľa.
     * Vytvára objekty MineField, Score a Time, ktoré sa následne zobrazia
     * pomocou ShapesGE.
     *
     * @param rows počet riadkov hracieho poľa
     * @param cols počet stĺpcov hracieho poľa
     */
    public GUI(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.mineField = new MineField(rows, cols);
        this.score = new Score(50, 70);
        this.time = new Time(50, 50);
    }

    /**
     * Zvýši čas o jednu sekundu.
     * Volá sa z triedy Main, ktorá synchronizuje tiky z Manazera.
     */
    public void incrementTime() {
        this.time.increment();
    }

    /**
     * Zvýši skóre o jednotku a aktualizuje jeho zobrazenie.
     * Metódu volá trieda Main pri úspešnom odhalení políčka.
     */
    public void incrementScore() {
        this.score.addScore();
    }

    /**
     * Getter, ktorý poskytuje prístup k objektu MineField.
     *
     * @return objekt MineField pre aktuálnu hru
     */
    public MineField getMineField() {
        return this.mineField;
    }
}
