/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.util.math;

import scovmod.model.util.TestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Test;

public class AliasMethodTest {
 
    @Test
    public void exceptionWhenInitialiseMoreThanOnce(){
        List<Double> probs = new ArrayList<>();
        probs.add(0.5); probs.add(0.5);
        
        AliasMethod am1 = new AliasMethod();
        am1.initialise(probs);
        
        try{
            am1.initialise(probs);
            fail("exception expected");
        } catch (UnsupportedOperationException e){}
    }
    
    @Test
    public void exceptionWhenProbabilityVectorDoesNotAddToOne(){
        AliasMethod am1 = new AliasMethod();   
        List<Double> justOverOne = TestUtils.listOf(0.5,0.3,0.2, 0.00000001);
        try{
            am1.initialise(justOverOne);
            fail("exception expected");
        } catch (IllegalArgumentException e){}
        
        AliasMethod am2 = new AliasMethod();  
        List<Double> justUnderOne = TestUtils.listOf(0.5,0.3,0.199999999);
        try{
            am1.initialise(justUnderOne);
            fail("exception expected");
        } catch (IllegalArgumentException e){}
    }        
}
