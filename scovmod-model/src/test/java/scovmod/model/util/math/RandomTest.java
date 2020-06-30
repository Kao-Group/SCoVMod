package scovmod.model.util.math;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.*;

import java.util.*;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.model.util.TestUtils;

public class RandomTest {

    private static double IDENTICAL = 0.0;
    private static double A_BIT = 1e-5;

    private Random instance;
    @Mock
    private Enumerator enumerator;
    @Mock
    private RandomDataGenerator randomDataGenerator;
    @Mock
    private AliasMethodFactory aliasMethodFactory;

    private Set<Integer> setOfItems;
    private ArrayList<Integer> arrayListOfItems;
    private LinkedList<Integer> linkedListOfItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        setOfItems = new HashSet<>();
        arrayListOfItems = new ArrayList<>();
        linkedListOfItems = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++) {
            setOfItems.add(i);
            arrayListOfItems.add(i);
            linkedListOfItems.add(i);
        }

        instance = new Random(enumerator, randomDataGenerator, aliasMethodFactory);
    }

    @Test
    public void sampleOneUniformly_FromSet() {
        when(enumerator.toArrayList(setOfItems)).thenReturn(arrayListOfItems);
        testUniformSamplingWith(setOfItems);
    }

    @Test
    public void sampleOneUniformly_FromArrayList() {
        testUniformSamplingWith(arrayListOfItems);
        verifyZeroInteractions(enumerator);
    }
    
    @Test
    public void noExceptionIfSampleFromListOfOneItem(){
    	Random instance = Random.buildRandom();
    	
        assertEquals(instance.sampleOne(TestUtils.listOf("OneItem")), "OneItem");
    }

    private void testUniformSamplingWith(Collection<Integer> collection) {
        when(randomDataGenerator.nextInt(0, 9)).thenReturn(0, 3, 3, 4, 7, 9);

        int[] counts = new int[10];
        for (int i = 0; i < 6; i++) {
            counts[instance.sampleOne(collection)]++;
        }

        assertEquals(1, counts[0]);
        assertEquals(0, counts[1]);
        assertEquals(0, counts[2]);
        assertEquals(2, counts[3]);
        assertEquals(1, counts[4]);
        assertEquals(0, counts[5]);
        assertEquals(0, counts[6]);
        assertEquals(1, counts[7]);
        assertEquals(0, counts[8]);
        assertEquals(1, counts[9]);
    }

    @Test
    public void nextBoolean() {
        when(randomDataGenerator.nextUniform(0.0, 1.0)).thenReturn(0.1, 0.2, 0.3, 0.4);

        assertTrue(instance.nextBoolean(0.3));
        assertTrue(instance.nextBoolean(0.3));
        assertFalse(instance.nextBoolean(0.3));
        assertFalse(instance.nextBoolean(0.3));
    }

    @Test
    public void nextBernoulliTrials_FromSet() {
        when(enumerator.toLinkedList(setOfItems)).thenReturn(linkedListOfItems);
        testBernoulliTrialWith(0.5, setOfItems);
    }

    @Test
    public void nextBernoulliTrials_FromLinkedList() {
        testBernoulliTrialWith(0.5, linkedListOfItems);
        verifyZeroInteractions(enumerator);
    }


    private void testBernoulliTrialWith(double probTrue, Collection<Integer> collection) {
        when(randomDataGenerator.nextUniform(0.0, 1.0)).thenReturn(0.8, 0.3, 0.6, 0.4, 0.2, 0.7, 0.1, 0.4, 0.9);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(4);
        expected.add(6);
        expected.add(7);

        assertEquals(expected, instance.nextBernoulliTrials(0.5, collection));
    }

    @Test
    public void sampleWithoutReplacement_FromSet() {
        when(enumerator.toLinkedList(setOfItems)).thenReturn(linkedListOfItems);
        testSamplingWithoutReplacementWith(setOfItems);
    }

    @Test
    public void sampleWithoutReplacement_FromLinkedList() {
        testSamplingWithoutReplacementWith(linkedListOfItems);
        verifyZeroInteractions(enumerator);
    }

    private void testSamplingWithoutReplacementWith(Collection<Integer> collection) {
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	0 1 2 3 4 5 6 7 8 9
        when(randomDataGenerator.nextInt(0, 9)).thenReturn(3);
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	0 1 2 4 5 6 7 8 9
        when(randomDataGenerator.nextInt(0, 8)).thenReturn(2);
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	0 1 4 5 6 7 8 9
        when(randomDataGenerator.nextInt(0, 7)).thenReturn(5);
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	0 1 4 5 6 8 9
        when(randomDataGenerator.nextInt(0, 6)).thenReturn(3);
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	0 1 4 6 8 9
        when(randomDataGenerator.nextInt(0, 5)).thenReturn(0);
        // Position	0 1 2 3 4 5 6 7 8 9
        // Value	1 4 6 8 9

        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(2);
        expected.add(7);
        expected.add(5);
        expected.add(0);

        assertEquals(expected, instance.sampleWithoutReplacement(5, collection));
    }

    @Test
    public void sampleWithoutReplacementDoesntModifyProvidedCollection() {
    	// Note result is thrown away, just looking for mutations of input
        instance.sampleWithoutReplacement(5, linkedListOfItems);

        List<Integer> expected = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            expected.add(i);
        }

        assertEquals(expected, linkedListOfItems);
    }

    @Test
    public void sampleWithoutReplacement_AllItems(){
        assertEquals(linkedListOfItems, Random.buildRandom().sampleWithoutReplacement(linkedListOfItems.size(), linkedListOfItems));
    }

    @Test
    public void sampleWithoutReplacement_NoItems(){
        linkedListOfItems = new LinkedList<>();
        try {
            assertEquals(linkedListOfItems, Random.buildRandom().sampleWithoutReplacement(0, linkedListOfItems));
            fail("Expected exception");
        } catch(UnsupportedOperationException e){};
    }

    @Test
    public void sampleWithoutReplacement_SampleSizeTooBig(){
        try {
            Random.buildRandom().sampleWithoutReplacement(20, linkedListOfItems);
            fail("Expected exception");
        } catch(UnsupportedOperationException e){};
    }

    @Test
    public void enumerateCollection() {
        Collection<Integer> myItems = new HashSet<Integer>();
        LinkedList<Integer> enumerated = new LinkedList<Integer>();

        when(enumerator.toLinkedList(myItems)).thenReturn(enumerated);

        assertSame(enumerated, instance.toLinkedList(myItems));
    }

    @Test
    public void nextExponential() {
        when(randomDataGenerator.nextExponential(0.1)).thenReturn(0.11, 0.12, 0.13);
        when(randomDataGenerator.nextExponential(0.2)).thenReturn(0.21);

        assertEquals(0.11, instance.nextExponential(0.1), IDENTICAL);
        assertEquals(0.21, instance.nextExponential(0.2), IDENTICAL);
        assertEquals(0.12, instance.nextExponential(0.1), IDENTICAL);
        assertEquals(0.13, instance.nextExponential(0.1), IDENTICAL);
    }

    @Test
    public void nextPoissonIsOneOrMore() {
        double rate = 0.1;
        double prob = 1 - Math.exp(-rate);

        when(randomDataGenerator.nextUniform(0.0, 1.0)).thenReturn(prob - A_BIT, prob, prob + A_BIT);

        assertTrue(instance.nextPoissonReturnsOneOrMore(rate));
        assertFalse(instance.nextPoissonReturnsOneOrMore(rate));
        assertFalse(instance.nextPoissonReturnsOneOrMore(rate));
    }

//    @Test
//    public void sampleBetaDistribution(){
//        double alphaValue_Sp = 6.102;
//        double betaValue_Sp = 0.809;
//        BetaDistribution beta_sp = new BetaDistribution(alphaValue_Sp,betaValue_Sp);
//        assertEquals(0.883,beta_sp.getNumericalMean(),A_BIT);
//
//        double alphaValue_Se = 7.618;
//        double betaValue_Se = 0.905;
//        BetaDistribution beta_se = new BetaDistribution(alphaValue_Se,betaValue_Se);
//        assertEquals(0.894,beta_se.getNumericalMean(),A_BIT);
//    }
    
    @Test
    public void exceptionIfAskForBooleanOfNegativeProbability(){
        double negativeProb = -0.1;
        try {
            instance.nextBoolean(negativeProb);
            fail("Expected exception");
        } catch (UnsupportedOperationException e) {}    
    }

    @Test
    public void nextDouble() {
        when(randomDataGenerator.nextUniform(0.0, 1.0)).thenReturn(0.1, 0.2, 0.3);

        assertEquals(instance.nextUnitUniform(), 0.1, IDENTICAL);
        assertEquals(instance.nextUnitUniform(), 0.2, IDENTICAL);
        assertEquals(instance.nextUnitUniform(), 0.3, IDENTICAL);
    }

    @Test
    public void nextUniformInteger() {
        when(randomDataGenerator.nextInt(1, 2)).thenReturn(1);
        when(randomDataGenerator.nextInt(2, 5)).thenReturn(2);
        when(randomDataGenerator.nextInt(5, 10)).thenReturn(3);

        assertEquals(1, instance.nextUniformInteger(1, 2));
        assertEquals(2, instance.nextUniformInteger(2, 5));
        assertEquals(3, instance.nextUniformInteger(5, 10));
    }

    @Test
    public void nextWeightedSample() {
        double[] weights = new double[]{0.01, 0.02, 0.03, 0.04};

        double sum = 0;
        for (double w : weights) {
            sum += w;
        }

        double[] cumulative = new double[weights.length - 1];
        cumulative[0] = weights[0] / sum;
        for (int i = 1; i < weights.length - 1; i++) {
            cumulative[i] = weights[i] / sum + cumulative[i - 1];
        }

        when(randomDataGenerator.nextUniform(0.0, 1.0)).thenReturn(
                cumulative[0] - A_BIT,
                cumulative[0],
                cumulative[0] + A_BIT,
                cumulative[1] - A_BIT,
                cumulative[1],
                cumulative[1] + A_BIT,
                cumulative[2] - A_BIT,
                cumulative[2],
                cumulative[2] + A_BIT,
                1.0
        );

        assertEquals(0, instance.nextWeightedIndex(weights));
        assertEquals(0, instance.nextWeightedIndex(weights));
        assertEquals(1, instance.nextWeightedIndex(weights));

        assertEquals(1, instance.nextWeightedIndex(weights));
        assertEquals(1, instance.nextWeightedIndex(weights));
        assertEquals(2, instance.nextWeightedIndex(weights));

        assertEquals(2, instance.nextWeightedIndex(weights));
        assertEquals(2, instance.nextWeightedIndex(weights));
        assertEquals(3, instance.nextWeightedIndex(weights));

        assertEquals(3, instance.nextWeightedIndex(weights));
    }

    @Test
    public void noWeightedSamplingIfMoreThanFiveItems() {
        //As this would be an inefficient approach
        double[] weights = new double[]{1, 2, 3, 4, 5, 6};
        try {
            instance.nextWeightedIndex(weights);
            fail("Expected exception");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void exceptionIfMultinomialTrialWeightDontAddToOne(){
        Map<String, Double> groupWeights = new TreeMap<>();
        groupWeights.put("Aye", 0.5);
        groupWeights.put("Bee", 0.5);
        groupWeights.put("Sea", 0.1);

        try{
            Random.buildRandom().nextMultinomialTrials(groupWeights, 7);
            fail("Expected exception");
        } catch (IllegalArgumentException e){};
    }

    @Test
    public void multinomialTrials() {
        Map<String, Double> groupWeights = new TreeMap<>();
        groupWeights.put("Aye", 0.4);  // 0
        groupWeights.put("Bee", 0.5);  // 1
        groupWeights.put("Sea", 0.1);  // 2

        AliasMethod mockAlias = mock(AliasMethod.class);
        when(aliasMethodFactory.build()).thenReturn(mockAlias);
        when(mockAlias.next()).thenReturn(0, 1, 1, 0, 2, 1, 0);

        Map<String, Integer> result = instance.nextMultinomialTrials(groupWeights, 7);

        Map<String, Integer> expected = new TreeMap<>();
        expected.put("Aye", 3);
        expected.put("Bee", 3);
        expected.put("Sea", 1);

        assertEquals(expected, result);

        verify(mockAlias).initialise(TestUtils.listOf(0.4, 0.5, 0.1));
    }

    @Test
    public void exceptionShufflingEmptyCollection() {

        List<Integer> numbers = new ArrayList<>();
        try{
            instance.shuffleList(numbers);
            fail("Expected exception");
        } catch (UnsupportedOperationException e){};

    }

    @Test
    public void shufflingListDoesntMutateOriginal() {

        List<Integer> numbers = TestUtils.listOf(1,2,3,4);

        instance.shuffleList(numbers);

        assertEquals(numbers, TestUtils.listOf(1,2,3,4));
    }
}
