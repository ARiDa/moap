/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobilitymining;

/**
 *
 * @author igobrilhante
 */
public enum CardinalDirection {

    EAST, NORTH_EAST,NORTH, NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST;

    @Override
    public String toString() {
        if (this == NORTH) {
            return "NORTH";
        }
        if (this == NORTH_WEST) {
            return "NORTH-WEST";
        }
        if (this == NORTH_EAST) {
            return "NORTH-EAST";
        }
        if (this == SOUTH) {
            return "SOUTH";
        }
        if (this == SOUTH_EAST) {
            return "SOUTH-EAST";
        }
        if (this == SOUTH_WEST) {
            return "SOUTH-WEST";
        }
        if (this == EAST) {
            return "EAST";
        }
        if (this == WEST) {
            return "WEST";
        }
        return null;
    }

//    public Integer toInteger() {
//        if (this == NORTH) {
//            return 0;
//        }
//        if (this == NORTH_WEST) {
//            return 1;
//        }
//        if (this == NORTH_EAST) {
//            return 2;
//        }
//        if (this == SOUTH) {
//            return 3;
//        }
//        if (this == SOUTH_EAST) {
//            return 4;
//        }
//        if (this == SOUTH_WEST) {
//            return 5;
//        }
//        if (this == EAST) {
//            return 6;
//        }
//        if (this == WEST) {
//            return 7;
//        }
//        return null;
//    }
}
