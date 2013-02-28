package Buildings.House;

import Map.BuildDirection;
import Map.Ground;

/**
 * Created with IntelliJ IDEA.
 * User: Jens
 * Date: 28/02/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class House {
    private Ground[][] buildingPlan;

    public House(BuildDirection buildDirection){
        buildingPlan = initHousePlan();

    }



    public House(){
        this(BuildDirection.RIGHT);
    }

    private Ground[][] initHousePlan() {
        Ground[][] buildingPlan = new Ground[2][2];
        buildingPlan[0][0] = new HouseA1();
        buildingPlan[0][1] = new HouseA2();
        buildingPlan[1][0] = new HouseB1();
        buildingPlan[1][1] = new HouseB2();

        return buildingPlan;
    }

    public Ground[][] getHousePlan() {
        return buildingPlan;
    }
}
