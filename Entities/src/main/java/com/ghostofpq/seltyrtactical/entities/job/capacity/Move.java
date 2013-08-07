package com.ghostofpq.seltyrtactical.entities.job.capacity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Move extends Capacity {

    private static final long serialVersionUID = 768372563773451676L;

    public Move(String name, String description, int price) {
        this.prerequisites = new ArrayList<Capacity>();

        this.name = name;
        this.description = description;

        this.type = CapacityType.MOVE;

        this.price = price;
        this.locked = true;
    }
}
