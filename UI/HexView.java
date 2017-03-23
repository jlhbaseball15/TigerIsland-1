import java.awt.*;
import java.awt.geom.Point2D;


/**
 * Created by scott on 3/20/17.
 */
public class HexView {

    //Hex class
    private static class HexCoords {
        //axial style
        private int q, r, s;
        public Point2D.Double center;

        private HexCoords(int q_, int r_) {
            q = q_;
            r = r_;
            s = -q_ - r_;
        }

        private HexCoords(int q_, int r_, int s_) {
            q = q_;
            r = r_;
            s = s_;
        }

        public void setCenter(Point2D.Double center0) {
            center = center0;
        }
    }

    //Fractional Hex
    private static class FractionalHexCoords {
        private double q, r, s;
        public Point2D.Double center;

        private FractionalHexCoords(double q_, double r_) {
            q = q_;
            r = r_;
            s = -q_ - -r_;
        }

        public void setCenter(Point2D.Double center0) {
            center = center0;
        }
    }

    //Orientation class
    private static class Orientation {
        private double f0, f1, f2, f3;
        private double b0, b1, b2, b3;
        private double start_angle; // in multiples of 60 degrees

        private Orientation(double f0_, double f1_, double f2_, double f3_,
                            double b0_, double b1_, double b2_, double b3_,
                            double start_angle_) {
            f0 = f0_;
            f1 = f1_;
            f2 = f2_;
            f3 = f3_;
            b0 = b0_;
            b1 = b1_;
            b2 = b2_;
            b3 = b3_;
            start_angle = start_angle_;
        }
    }

    private static class Layout {
        private Orientation orientation;
        private Point2D.Double size;
        private Point2D.Double origin;

        private Layout(Orientation orientation_, Point2D.Double size_, Point2D.Double origin_) {
            orientation = orientation_;
            size = size_;
            origin = origin_;
        }
    }

    //Constants
    public static final Orientation layoutPointy =
            new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0,
                            Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5);

    public static Layout hexLayout;

    public static void setLayout(Point2D.Double size, Point2D.Double origin) {
        hexLayout = new Layout(layoutPointy, size, origin);
    }

    private static Point2D.Double hexToPixel(Layout layout, HexCoords h) {
        Orientation orient = layout.orientation;
        double x = (orient.f0 * h.q + orient.f1 * h.r) * layout.size.x;
        double y = (orient.f2 * h.q + orient.f3 * h.r) * layout.size.y;
        return new Point2D.Double(x + layout.origin.x, y + layout.origin.y);
    }

    public static Point pixelToHex(int x, int y) {
        Orientation orient = hexLayout.orientation;
        Point2D.Double pt = new Point2D.Double((x - hexLayout.origin.x) / hexLayout.size.x,
                         (y - hexLayout.origin.y) / hexLayout.size.y);
        double q = orient.b0 * pt.getX() + orient.b1 * pt.getY();
        double r = orient.b2 * pt.getX() + orient.b3 * pt.getY();
        FractionalHexCoords fractionHex =  new FractionalHexCoords(q, r);
        HexCoords roundedHex = hexRound(fractionHex);
        return new Point(roundedHex.q, roundedHex.r);
    }

    public static void drawCurrentTile(Tile tile, int boxWidth, int boxHeight, Graphics2D g2) {
        Hex[] currentHexes = tile.getHexes();
        int xHex = boxWidth;
        int yHex = boxHeight;
        HexView.fillHex(xHex, yHex, currentHexes[0].getTerrain(), g2);
    }

    public static HexCoords hexRound(FractionalHexCoords hexToRound) {
        int q = (int)Math.round(hexToRound.q);
        int r = (int)Math.round(hexToRound.r);
        int s = (int)Math.round(hexToRound.s);
        double q_diff = Math.abs(q - hexToRound.q);
        double r_diff = Math.abs(r - hexToRound.r);
        double s_diff = Math.abs(s - hexToRound.s);

        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        } else if (r_diff > s_diff) {
            r = -q - s;
        } else {
            s = -q - r;
        }
        return new HexCoords(q, r, s);
    }

    private static Point2D.Double getHexCornerOffset(Layout layout, int cornerNum) {
        Point2D.Double size = layout.size;
        double angle = 2.0 * Math.PI * (layout.orientation.start_angle + cornerNum) / 6;
        return new Point2D.Double(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }

    private static Point2D[] getPolygonCorners(Layout layout, HexCoords h) {
        Point2D[] corners = new Point2D[6];
        Point2D.Double center = hexToPixel(layout, h);
        for (int i = 0; i < 6; i++) {
            Point2D.Double offset = getHexCornerOffset(layout, i);
            corners[i] = new Point2D.Double(center.getX() + offset.getX(),
                                            center.getY() + offset.getY());
        }
        return corners;
    }


    public static HexCoords buildHex(int x0, int y0) {

        HexCoords builtHex = new HexCoords(x0, y0);

        Point2D.Double center = hexToPixel(hexLayout, builtHex);
        builtHex.setCenter(center);
        return builtHex;
    }

    public static Polygon hexToPoly(HexCoords hex) {
        Point2D[] hexCorners = getPolygonCorners(hexLayout, hex);
        int[] cx = new int[6];
        int[] cy = new int[6];
        for (int i = 0; i < 6; i++) {
            cx[i] = (int)hexCorners[i].getX();
            cy[i] = (int)hexCorners[i].getY();
        }
        return new Polygon(cx, cy, 6);
    }

    public static void drawHex(int x0, int y0, Graphics2D g2) {

        HexCoords hex = buildHex(x0, y0);
        Polygon poly = hexToPoly(hex);
        g2.setColor(Color.ORANGE);
        g2.fillPolygon(poly);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(poly);

        //adding numbers to hexes
        int pixelX = (int) hex.center.getX();
        int pixelY = (int) hex.center.getY();
        g2.setColor(Color.BLACK);
        g2.drawString(x0 + ", " + y0, pixelX - 15, pixelY);
    }

    public static void fillHex(int x0, int y0, int hexNumber, Graphics2D g2) {
        char hexValue = (char) hexNumber;
        HexCoords hex = buildHex(x0, y0);
        Polygon poly = hexToPoly(hex);
        int pixelX = (int) hex.center.getX();
        int pixelY = (int) hex.center.getY();

        // J = jungle, R = rocky, L = lake, G = grasslands, V = volcano
        switch(hexValue) {
            case 'J' : g2.setColor(Color.MAGENTA);
                       break;
            case 'R' : g2.setColor(Color.GRAY);
                       break;
            case 'L' : g2.setColor(Color.BLUE);
                       break;
            case 'G' : g2.setColor(Color.GREEN);
                       break;
            case 'V' : g2.setColor(Color.RED);
                       break;
            default : g2.setColor(Color.BLACK);
                           break;
        }
        g2.fillPolygon(poly);
        g2.setColor(new Color(255,100,255));
        g2.drawString(""+hexValue, pixelX, pixelY);
    }
}
