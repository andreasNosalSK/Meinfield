import fri.shapesge.TextBlock;
/**
 *Trieda Score poskytuje zobrazovanie aktuálneho skóre pomocou objektu TextBlock
 */
public class Score {

    private int value;
    private TextBlock text;
    /**
     * konštruktor pre objekt Score
     * inicializuje hodnotu skóre na 0 a zobrazí textové pole na daných suradniciach
     */ 
        
    public Score(int offsetX, int offsetY) {
        this.value = 0;
        this.text = new TextBlock("Score: 0", offsetX, offsetY);
        this.text.changeColor("black");
        this.text.makeVisible();
    }
    //Zvyšuje aktuálne skóre o 1 a aktualizuje text
    public void addScore() {
        this.value = this.value + 1;
        this.updateText();
    }
    //Nastaví (resetuje skóre) na 0 a aktualizuje text
    public void reset() {
        this.value = 0;
        this.updateText();
    }
    //Aktualizuje text aby zobrazoval aktuálnu hodnotu skóre
    public void updateText() {
        this.text.changeText("Score: " + this.value);
    }
}
