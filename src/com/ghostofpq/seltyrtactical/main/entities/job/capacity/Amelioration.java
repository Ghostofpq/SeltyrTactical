package com.ghostofpq.seltyrtactical.main.entities.job.capacity;

import lombok.Getter;
import lombok.Setter;

import com.ghostofpq.seltyrtactical.main.entities.characteristics.PrimaryCharacteristics;
import java.util.ArrayList;

@Getter
@Setter
public class Amelioration extends Capacity {

    private static final long serialVersionUID = -7036564291741868266L;

    private PrimaryCharacteristics caracteristics;

    public Amelioration(String name, String description,
                        PrimaryCharacteristics caracteristics, int price) {
        this.prerequisites = new ArrayList<Capacity>();

        this.name = name;
        this.description = description;

        this.type = CapacityType.AMELIORATION;

        this.price = price;
        this.locked = true;

        this.caracteristics = caracteristics;
    }

}
