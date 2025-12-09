import fri.shapesge.TextBlock;

/**Trieda Time (Čas) služi na sledovanie a zobrazovanie uplynuleho casu
v hre (pravdepodobne v Minesweeperi, sudiac podla UML diagramu).
Využiva komponent TextBlock z knižnice fri.shapesge na graficke zobrazenie.*/
public class Time {
    private int seconds;
    private TextBlock text;

    /**Konstruktor triedy Time.
    *Nastavuje čas na 0 sekund a vytvori TextBlock na danych suradniciach.
    */

    public Time(int offsetX, int offsetY) {
        this.seconds = 0;// Vytvorenie noveho TextBlocku s počiatočnym textom a umiestnenim
        this.text = new TextBlock("Time: 0", offsetX, offsetY);
        this.text.changeColor("white"); // Zmena farby, ale možno by sa malo volať 'menitFarbu'
        this.text.makeVisible();// Zobrazenie TextBlocku
    }

    /**Resetuje cas hry na nulu.*/
    public void reset() {
        this.seconds = 0;
        this.updateText(); // Okamžite aktualizuje zobrazenie casu
    }

    /**Zvyši aktualny počet sekund o 1 (simuluje "tiknutie" hodin).*/
    public void increment() {
        this.seconds = this.seconds + 1;
        this.updateText(); // Aktualizuje zobrazenie po inkrementacii
    }

    /**
     *Aktualizuje text v TextBlocku, aby zodpovedal aktualnej hodnote v poli 'seconds'.
     */
    public void updateText() {
        this.text.changeText("Time: " + this.seconds);
    }
}