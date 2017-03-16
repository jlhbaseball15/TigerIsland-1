/**
 * Created by Colette on 3/15/2017.
 */
public class Deckdesigner {
    int numberofterrainspertile=3;
    int numberoftotaltiles = 48;
    private Hex[][] Deckoftiles;
    private int startofrangeoffirstterraintype;

    public Deckdesigner() {
        Deckoftiles = new Hex[numberoftotaltiles][numberofterrainspertile];
        tilecreator();
    }

    private void tilecreator() {
        //    Deckoftiles= Deckcreator();
        for (int i = 0; i < 48; i++)
            Deckoftiles[i][2]=new Hex('V');
        for (int i = 0; i < 12; i++)
            Deckoftiles[i][0] =new Hex('A');
        startofrangeoffirstterraintype=0;
        secondvaluesetup();
        for (int i = 12; i < 24; i++)
            Deckoftiles[i][0] = new Hex('B');
        startofrangeoffirstterraintype=12;
        secondvaluesetup();
        for (int i = 24; i < 36; i++)
            Deckoftiles[i][0] = new Hex('C');
        startofrangeoffirstterraintype=24;
        secondvaluesetup();
        for (int i = 36; i < 48; i++)
            Deckoftiles[i][0] =new Hex('D');
        startofrangeoffirstterraintype=36;
        secondvaluesetup();
    }
    /* For the most part the deck is always set up in the same way where it sets up the 12 tiles that begin with with first
    * type (like A) then it sets up three of the second value in secondvaluesetup
    * so the deck will always be in the order of AAV AAV AAV ABV ABV ABV ACV ACV.... and so on*/
    private void secondvaluesetup(){
        for(int i=0; i<2; i++){
            Deckoftiles[startofrangeoffirstterraintype][1]=new Hex('A');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[startofrangeoffirstterraintype][1]=new Hex('B');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[startofrangeoffirstterraintype][1]=new Hex('C');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[startofrangeoffirstterraintype][1]=new Hex('D');
            startofrangeoffirstterraintype++;
        }
    }

    public Hex[][] getTile() {
        return Deckoftiles;
    }

}