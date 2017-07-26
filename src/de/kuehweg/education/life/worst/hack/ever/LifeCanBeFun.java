package de.kuehweg.education.life.worst.hack.ever;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

/**
 * @author Michael Kühweg
 */
public class LifeCanBeFun {

	public static final String GENERATION_TITLE = "generation ";

	private static final int RANDOM_FILL_INITIAL_SHARE = 4;

	private final int[][] cells;

	public LifeCanBeFun(final int width, final int height) {
		cells = new int[width][height];
	}

	public void createRandomLife() {
		final Random random = new Random();
		applyToAllCells(
				(x, y) -> cells[x][y] = random.nextInt(RANDOM_FILL_INITIAL_SHARE + 1) / RANDOM_FILL_INITIAL_SHARE);
	}

	public void evolve() {
		evaluateNeighbourhoodOfCurrentGenerationForAllCells();
		evolveAllCellsToNextGeneration();
	}

	private void evaluateNeighbourhoodOfCurrentGenerationForAllCells() {
		applyToAllCells((x, y) -> mooreNeighbourhood(x, y));
	}

	private void evolveAllCellsToNextGeneration() {
		applyToAllCells((x, y) -> cells[x][y] = (2 + cells[x][y] % 2 << 2 >> cells[x][y] / 10) % 2);
	}

	private void applyToAllCells(final BiConsumer<Integer, Integer> perCell) {
		applyToAllCellsWithAfterRowTreatment(perCell, (y) -> {
		});
	}

	private void applyToAllCellsWithAfterRowTreatment(final BiConsumer<Integer, Integer> perCell,
			final IntConsumer afterRow) {
		for (int y = 0; y < cells[0].length; y++) {
			for (int x = 0; x < cells.length; x++) {
				perCell.accept(x, y);
			}
			afterRow.accept(y);
		}
	}

	private void mooreNeighbourhood(final int x, final int y) {
		final int stateChange = cells[x][y] % 2 * 10;
		for (int dy = -1; dy <= 1; dy++) {
			for (int dx = -1; dx <= 1; dx++) {
				try {
					cells[x + dx][y + dy] += stateChange;
				} catch (final IndexOutOfBoundsException ex) {
				}
			}
		}
		cells[x][y] -= stateChange;
	}

	private void liveThroughGenerations(final int generations) {
		for (int generation = 0; generation < generations; generation++) {
			System.out.println(GENERATION_TITLE + generation);
			System.out.println(this);
			evolve();
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		applyToAllCellsWithAfterRowTreatment((x, y) -> {
			builder.append(".*".substring(cells[x][y] % 2, cells[x][y] % 2 + 1));
		}, (y) -> {
			builder.append("\n");
		});
		return builder.toString();
	}

	public static void main(final String[] args) {
		final Integer width = Integer.valueOf(args[0]);
		final Integer height = Integer.valueOf(args[1]);
		final Integer generations = Integer.valueOf(args[2]);
		final LifeCanBeFun life = new LifeCanBeFun(width, height);
		life.createRandomLife();
		life.liveThroughGenerations(generations);
	}
}
