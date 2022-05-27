package me.nopox.hcf.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HCTeams
 */
@AllArgsConstructor
public class Coordinate {

    @Getter @Setter int x;
    @Getter @Setter int z;

    @Override
    public String toString() {
        return (x + ", " + z);
    }

}
