package com.ghostofpq.seltyrtactical.main.entities.job.capacity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Capacity implements Serializable {

    private static final long serialVersionUID = 6338786566341517746L;

    protected String name;
    protected String description;
    protected CapacityType type;

    protected int price;
    protected boolean locked;

    protected List<Capacity> prerequisites;

    public boolean isAvailable() {
        if (prerequisites.isEmpty()) {
            return true;
        } else {
            for (Capacity prerequisite : prerequisites) {
                if (prerequisite.isLocked()) {
                    return false;
                }
            }
            return true;
        }
    }

    public void addPrerequisites(Capacity prerequisite) {
        prerequisites.add(prerequisite);
    }
}
