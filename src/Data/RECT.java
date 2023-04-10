package Data;

public class RECT {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private String tag;
    private String hoverLabel;
    private Frame gHover;


    public RECT(int x1, int x2, int y1, int y2, String tag) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = "";
        gHover = null;
    }
    public RECT(int x1, int x2, int y1, int y2, String tag, String hoverLabel) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = hoverLabel;
        gHover = null;
    }

    public RECT(int x1, int x2, int y1, int y2, String tag, Frame gHover) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = "";
        this.gHover = gHover;
    }

    public Frame getGraphicalHover() {
        return gHover;
    }

    public String getHoverLabel() {
        return hoverLabel;
    }
    public boolean isCollision(int x, int y) {
        boolean collision = false;

        if( x >= x1 && x <= x2) {
            if(y >= y1 && y <= y2) {
                collision = true;
            }
        }
        return collision;
    }

    public boolean isCollision(int x, int width, int y, int height) {
        if(x + width > x1 &&
            x < x2 &&
            y + height > y1 &&
            y < y2
        ) {
            return true;
        } else return false;
    }

    public boolean isClicked(Click click, int buttonComparator) {
        if(click.getButton() != buttonComparator) {
            return false;
        }
        return isCollision(click.getX(), click.getY());
    }

    public String getTag() {
        return tag;
    }

    public String toString() {
        return String.format("x(%d, %d) y(%d, %d)",
                x1,
                x2,
                y1,
                y2);
    }
}
