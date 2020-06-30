/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import org.junit.Test;
import scovmod.model.BadInputDataException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class InfectedSeedsTest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "infectedSeeds");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("infectedSeeds.csv");
		InfectedSeeds instance = new InfectedSeeds(testData);

		HashSet<Integer> expectedSeed = new HashSet<>();
		expectedSeed.add(28739);
		expectedSeed.add(54362);
		expectedSeed.add(56432);
		expectedSeed.add(77645);


		assertEquals(expectedSeed, instance.getInfectedSeeds());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new InfectedSeeds(rootPath.resolve("infectedSeeds_badHeader.csv"));
	}

	@Test(expected = BadInputDataException.class)
	public void exceptionIfRepeatedLocationId() {
		Path repeatedRowFilePath = rootPath.resolve("infectedSeeds_repeatedRow.csv");
		new InfectedSeeds(repeatedRowFilePath);
	}

}
