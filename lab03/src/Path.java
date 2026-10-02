/** A class that represents a path via pursuit curves. */
public class Path {

    // TODO
    public Point curr = new Point();
    public Point next = new Point();


    public Path(double x, double y) {
        curr.setX(x);
        next.setX(x);
        curr.setY(y);
        next.setY(y);
    }

    public double getCurrX() {
        return curr.getX();
    }

    public double getCurrY() {
        return curr.getY();
    }

    public double getNextX() {
        return next.getX();
    }

    public double getNextY() {
        return next.getY();
    }

    public Point getCurrentPoint() {
        return curr;
    }

    public void setCurrentPoint(Point point) {
        double xposi = point.getX();
        double yposi = point.getY();
        curr.setX(xposi);
        curr.setY(yposi);
    }

    public void iterate(double dx, double dy) {
        double xnext = next.getX() + dx;
        double ynext = next.getY() + dy;
        curr.setX(next.getX());
        curr.setY(next.getY());
        next.setX(xnext);
        next.setY(ynext);
    }

}
