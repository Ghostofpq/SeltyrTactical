package java.com.ghostofpq.seltyrtactical.main.entities.race;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 14/06/13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public enum RaceType {
    ELVE("Elve"), DWARF("Dwarf"), HUMAN("Human");

    private final String propertyName;

    RaceType(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
