import java.awt.*;

/**
 * Created by dontf on 4/3/2017.
 */
public class Message {
    boolean isGameOver = false;

    private String gid = "";
    private String pid = "";
    private double time = 0;
    private int move = 0;

    private Tile tile = new Tile('V', 'V');
    private Point tilePoint = new Point(0, 0);
    private int orientation = 0;

    private BuildOptions build = BuildOptions.NOOP;
    private Point buildPoint = new Point(0, 0);
    private char terrain = 'V';

    public boolean getIsGameOver() { return isGameOver; }

    public void setIsGameOver(boolean isOver) { isGameOver = isOver; }

    public String getGID() {
        return gid;
    }

    public void setGID(String gid) {
        this.gid = gid;
    }

    public String getPID() { return pid; }

    public void setPID(String pid) { this.pid = pid; }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Point getTilePoint() {
        return tilePoint;
    }

    public void setTilePoint(Point tilePoint) {
        this.tilePoint = tilePoint;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public BuildOptions getBuild() {
        return build;
    }

    public void setBuild(BuildOptions build) {
        this.build = build;
    }

    public Point getBuildPoint() {
        return buildPoint;
    }

    public void setBuildPoint(Point buildPoint) {
        this.buildPoint = buildPoint;
    }

    public char getTerrain() {
        return terrain;
    }

    public void setTerrain(char terrain) {
        this.terrain = terrain;
    }

}
