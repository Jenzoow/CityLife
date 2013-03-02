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

    public BuildDirection getOposite(BuildDirection buildDirection){
        BuildDirection oposite;
        if (buildDirection == BuildDirection.UP){
            oposite = BuildDirection.DOWN;
        } else if (buildDirection == BuildDirection.DOWN){
            oposite = BuildDirection.UP;
        } else       if (buildDirection == BuildDirection.LEFT){
            oposite = BuildDirection.RIGHT;
        } else    if (buildDirection == BuildDirection.RIGHT){
            oposite = BuildDirection.LEFT;
        } else {
            oposite = null;
        }
        return oposite;
    }


}
