/**
 * Created by Colette on 3/15/2017.
 */
public class Deckdesigner {
    public Hex[][] Deckoftiles = new Hex[3][48];
    public int startofrangeoffirstterraintype;
    public void tilecreator() {
        for (int i = 0; i < 48; i++)
            Deckoftiles[2][i]=new Hex('V');
        for (int i = 0; i < 12; i++)
            Deckoftiles[0][i] =new Hex('A');
        startofrangeoffirstterraintype=0;
        secondvaluesetup();
        for (int i = 12; i < 24; i++)
            Deckoftiles[0][i] = new Hex('B');
        startofrangeoffirstterraintype=12;
        secondvaluesetup();
        for (int i = 24; i < 36; i++)
            Deckoftiles[0][i] = new Hex('C');
        startofrangeoffirstterraintype=24;
        secondvaluesetup();
        for (int i = 36; i < 48; i++)
            Deckoftiles[0][i] =new Hex('S');
        startofrangeoffirstterraintype=36;
        secondvaluesetup();
    }
    /* For the most part the deck is always set up in the same way where it sets up the 12 tiles that begin with with first
    * type (like A) then it sets up three of the second value in secondvaluesetup
    * so the deck will always be in the order of AAV AAV AAV ABV ABV ABV ACV ACV.... and so on*/
    private void secondvaluesetup(){
        for(int i=0; i<2; i++){
            Deckoftiles[1][startofrangeoffirstterraintype]=new Hex('A');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[1][startofrangeoffirstterraintype]=new Hex('B');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[1][startofrangeoffirstterraintype]=new Hex('C');
            startofrangeoffirstterraintype++;
        }
        for(int i=0; i<2; i++){
            Deckoftiles[1][startofrangeoffirstterraintype]=new Hex('D');
            startofrangeoffirstterraintype++;
        }
    }

 }
