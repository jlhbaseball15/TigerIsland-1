import java.awt.*;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Point> pointsInSettlement;

    public Settlement(){
        pointsInSettlement = new ArrayList<>();
    }

    public void addPointToSettlement(Point point){
        pointsInSettlement.add(point);
    }

    public boolean contains(Point point){
        for(Point p : pointsInSettlement){
            if(p.getX() == point.getX() && p.getY() == point.getY()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> getSettlement(){
        return pointsInSettlement;
    }
}
