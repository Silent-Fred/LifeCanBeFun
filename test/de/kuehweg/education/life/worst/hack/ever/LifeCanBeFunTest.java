package de.kuehweg.education.life.worst.hack.ever;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael Kühweg
 */
public class LifeCanBeFunTest {

	private static final int GENERATIONS = 5;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static final int LENGTH_BREAK_AFTER_TITLE = 1;
	private static final int LENGTH_BREAK_AFTER_ROW = 1;
	// generation numbers for this test are assumed to be in the one digit range
	private static final int LENGTH_GENERATION_NUMBER = 1;
	private static final int LENGTH_BREAK_AFTER_GENERATION = 1;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Test
	public void greatCoverageButUselessTest() {
		LifeCanBeFun.main(new String[] { String.valueOf(WIDTH), String.valueOf(HEIGHT), String.valueOf(GENERATIONS) });
		final int expectedLength = (LifeCanBeFun.GENERATION_TITLE.length() + LENGTH_GENERATION_NUMBER //
				+ LENGTH_BREAK_AFTER_TITLE //
				+ (WIDTH + LENGTH_BREAK_AFTER_ROW) * HEIGHT //
				+ LENGTH_BREAK_AFTER_GENERATION //
		) * GENERATIONS;
		assertEquals(expectedLength, outContent.toString().length());
	}

}
