package com.ghostofpq.seltyrtactical.main.entities.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ghostofpq.seltyrtactical.main.entities.job.capacity.Amelioration;
import com.ghostofpq.seltyrtactical.main.entities.job.capacity.Move;
import com.ghostofpq.seltyrtactical.main.entities.job.capacity.Capacity;
import com.ghostofpq.seltyrtactical.main.entities.characteristics.PrimaryCharacteristics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Job implements Serializable {

	private static final long serialVersionUID = 7613901055857944135L;
	private String name;
	private String description;

	private List<Capacity> skillTree;

	private List<Move> unlockedMoves;
	private List<Amelioration> unlockedAmeliorations;

	private int jobPoints;
	private int cumulatedJobPoints;

	public Job(String name, String description) {
		this.name = name;
		this.description = description;

		this.jobPoints = 0;
		this.cumulatedJobPoints = 0;

		this.unlockedMoves = new ArrayList<Move>();
		this.unlockedAmeliorations = new ArrayList<Amelioration>();
	}

	public void gainJobPoints(int jobPoints) {
		this.jobPoints += jobPoints;
	}

	public boolean canUnlockCapacity(Capacity capacity) {
		if (capacity.isAvailable() && this.jobPoints >= capacity.getPrice()
				&& !capacity.isLocked()) {
			return true;
		}
		return false;
	}

	public void unlockCapacity(Capacity capacity) {
		if (canUnlockCapacity(capacity)) {
			this.jobPoints -= capacity.getPrice();
			capacity.setLocked(false);

			switch (capacity.getType()) {
			case AMELIORATION:
				unlockedAmeliorations.add((Amelioration) capacity);
				break;
			case MOVE:
				unlockedMoves.add((Move) capacity);
				break;
			}
		}
	}

	public PrimaryCharacteristics getAggregatedCaracteristics() {
		PrimaryCharacteristics result = new PrimaryCharacteristics(0, 0, 0, 0,
				0, 0);

		for (Amelioration amelioration : unlockedAmeliorations) {
			result.plus(amelioration.getCaracteristics());
		}

		return result;
	}
}
