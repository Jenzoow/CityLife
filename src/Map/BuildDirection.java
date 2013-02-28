package Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jens
 * Date: 28/02/13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public enum BuildDirection {
    DOWN(true, true), LEFT(false, false), UP(true, false), RIGHT(false, true);

    private boolean vertical;
    private boolean rightDown;

    private BuildDirection(boolean vertical, boolean rightDown) {
        this.vertical = vertical;
        this.rightDown = rightDown;
    }


}
