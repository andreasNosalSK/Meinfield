import fri.shapesge.TextBlock;

/**
 * Trieda Score zodpovedá za uchovávanie a zobrazovanie aktuálneho skóre hráča.
 */
public class Score {

    private int value;
    private TextBlock text;

    /**
     * Inicializuje skóre na hodnotu 0 a vytvorí textové pole na určených súradniciach.
     * Text je nastavený na bielu farbu a je okamžite zobrazený.
     *
     * @param offsetX x-ová pozícia textu skóre na obrazovke
     * @param offsetY y-ová pozícia textu skóre na obrazovke
     */
    public Score(int offsetX, int offsetY) {
        this.value = 0;
        this.text = new TextBlock("Score: 0", offsetX, offsetY);
        this.text.changeColor("white");
        this.text.makeVisible();
    }

    /**
     * Zvýši aktuálne skóre o 1 a následne aktualizuje zobrazovaný text.
     */
    public void addScore() {
        this.value = this.value + 1;
        this.updateText();
    }

    /**
     * Resetuje skóre na hodnotu 0 a aktualizuje text na obrazovke.
     */
    public void reset() {
        this.value = 0;
        this.updateText();
    }

    /**
     * Aktualizuje text v {@link TextBlock}, aby odrážal aktuálnu hodnotu skóre.
     */
    public void updateText() {
        this.text.changeText("Score: " + this.value);
    }
}
