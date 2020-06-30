/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules;

import org.junit.Before;
import org.junit.Test;
import scovmod.model.BadInputDataException;
import scovmod.model.output.HealthBoardLookup;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class HealthBoardLookupTest {

    private Path rootPath = Paths.get("src", "test", "resources", "inputData");
    HealthBoardLookup instance;

    @Before
    public void setup() {
        Path testData = rootPath.resolve("hblookup.csv");
        instance = new HealthBoardLookup(testData);
    }

    @Test
    public void getHBOfIZ() {
        int IZ_1 = 168;
        int expectedHB = 17;
        assertEquals(expectedHB, instance.getHealthBoardFromIZ(IZ_1));
    }

    @Test
    public void getHBOfAnotherIZ() {
        int IZ_2 = 330;
        int expectedHB = 24;
        assertEquals(expectedHB, instance.getHealthBoardFromIZ(IZ_2));
    }

    @Test(expected = BadInputDataException.class)
    public void badHeader() {
        new HealthBoardLookup(rootPath.resolve("hblookup_badheader.csv"));
    }

}
