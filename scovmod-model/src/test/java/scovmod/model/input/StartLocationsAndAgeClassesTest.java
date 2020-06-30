/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import scovmod.model.BadInputDataException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import scovmod.model.input.seeding.AgeClass;

public class StartLocationsAndAgeClassesTest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "startLocationsAndAgeClasses");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("peopleStartLocations.csv");
		StartLocationsAndAgeClasses instance = new StartLocationsAndAgeClasses(testData);

		Map<Integer, Integer> expectedLoc = new HashMap<>();
		expectedLoc.put(28739, 345);
		expectedLoc.put(54362, 564);
		expectedLoc.put(56432, 254);
		expectedLoc.put(77645, 13);

		Map<Integer, AgeClass> expectedAge = new HashMap<>();
		expectedAge.put(28739, AgeClass.YOUNG);
		expectedAge.put(54362, AgeClass.ADULT);
		expectedAge.put(56432, AgeClass.ELDERLY);
		expectedAge.put(77645, AgeClass.YOUNG);

		assertEquals(expectedAge, instance.getPeopleAgeClasses());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new StartLocationsAndAgeClasses(rootPath.resolve("peopleStartLocations_badHeader.csv"));
	}

	@Test(expected = BadInputDataException.class)
	public void exceptionIfRepeatedLocationId() {
		Path repeatedRowFilePath = rootPath.resolve("peopleStartLocations_repeatedRow.csv");
		new StartLocationsAndAgeClasses(repeatedRowFilePath);
	}

}
