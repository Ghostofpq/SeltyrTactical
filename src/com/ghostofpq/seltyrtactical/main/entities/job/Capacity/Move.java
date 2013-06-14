package com.ghostofpq.seltyrtactical.main.entities.job.capacity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

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
