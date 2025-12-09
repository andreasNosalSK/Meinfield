import fri.shapesge.TextBlock;

/**
 * Trieda Time predstavuje jednoduchý časovač.
 */
public class Time {
    private int seconds;
    private TextBlock text;

    /**
     * Konštruktor triedy Time.
     *
     * Inicializuje časovač s hodnotou 0 s bielou farbou a vytvorí textový objekt
     * na zadanej pozícii.
     *
     * @param offsetX pozícia textu v osi X
     * @param offsetY pozícia textu v osi Y
     */
    public Time(int offsetX, int offsetY) {
        this.seconds = 0;
        this.text = new TextBlock("Time: 0", offsetX, offsetY);
        this.text.changeColor("white");
        this.text.makeVisible();
    }

    /**
     * Resetuje časovač na 0 sekúnd.
     */
    public void reset() {
        this.seconds = 0;
        this.updateText();
    }

    /**
     * Zvýši počet o 1 sekundu.
     */
    public void increment() {
        this.seconds = this.seconds + 1;
        this.updateText();
    }

    /**
     * Aktualizuje text, aby zobrazoval aktuálny čas.
     */
    public void updateText() {
        this.text.changeText("Time: " + this.seconds);
    }
}