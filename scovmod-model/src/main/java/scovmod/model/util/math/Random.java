package scovmod.model.util.math;

import java.util.*;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;

public class Random {

    private Enumerator enumerator;
    private RandomDataGenerator rDataGenerator;
    private AliasMethodFactory amf;

    public static Random buildRandom() {
        return new Random(
                new Enumerator(),
                new RandomDataGenerator(
                        new MersenneTwister(System.currentTimeMillis() * Thread.currentThread().getId())
                ),
                new AliasMethodFactory()
        );
    }

    Random(Enumerator enumerator, RandomDataGenerator generator, AliasMethodFactory amf) {
        this.enumerator = enumerator;
        this.rDataGenerator = generator;
        this.amf = amf;
    }

    public <E> E sampleOne(Collection<E> items) {
        if (items.size() == 1) {
            return items.iterator().next();
        } else if (items instanceof List) {
            return ((List<E>) items).get(rDataGenerator.nextInt(0, items.size() - 1));
        } else {
            ArrayList<E> arrayList = enumerator.toArrayList(items);
            return arrayList.get(rDataGenerator.nextInt(0, arrayList.size() - 1));
        }
    }

    public boolean nextBoolean(double probTrue) {
        if(probTrue<0) throw new UnsupportedOperationException("Cannot handle negative probabilities");
        return nextUnitUniform() < probTrue;
    }

    public <E> LinkedList<E> toLinkedList(Collection<E> collection) {
        return enumerator.toLinkedList(collection);
    }

    public <E> List<E> sampleWithoutReplacement(int sampleSize, Collection<E> collection) {
        if(collection.size()==0){
            throw new UnsupportedOperationException("Cannot sample from an empty collection");
        }
        if(sampleSize > collection.size()){
            throw new UnsupportedOperationException("Cannot have a sample size larger than collection");
        }
        LinkedList<E> shallowCopyOfItems;
        if (collection instanceof LinkedList) {
            shallowCopyOfItems = new LinkedList<E>(collection);
        } else {
            shallowCopyOfItems = enumerator.toLinkedList(collection);
        }
        if(sampleSize == collection.size()){
            return shallowCopyOfItems;
        }
        int numRemaining = shallowCopyOfItems.size();
        List<E> sampled = new ArrayList<>();

        for (int i = 0; i < sampleSize; i++) {
            sampled.add(
                    shallowCopyOfItems.remove(rDataGenerator.nextInt(0, --numRemaining))
            );
        }
        return sampled;
    }

    public double nextUnitUniform() {
        return rDataGenerator.nextUniform(0.0, 1.0);
    }

    public int nextUniformInteger(int lowerInclusive, int upperInclusive) {
        return rDataGenerator.nextInt(lowerInclusive, upperInclusive);
    }

    public boolean nextPoissonReturnsOneOrMore(double rate) {
        double prob = 1.0 - Math.exp(-rate);
        return nextBoolean(prob);
    }

    public int samplePoisson(double rate) {
        //TODO is there a 'static' way to use it?
        PoissonDistribution distribution = new PoissonDistribution(rate);
        return distribution.sample();
    }

    public double sampleBeta(double alpha, double beta) {
        BetaDistribution distribution = new BetaDistribution(alpha,beta);
        return distribution.sample();
    }

    public double nextExponential(double d) {
        return rDataGenerator.nextExponential(d);
    }

    public int nextWeightedIndex(double[] weights) {
        if (weights.length > 5) {
            throw new UnsupportedOperationException("Don't use this method for more than 5 weights, it's too inefficient");
        }

        double total = 0;
        for (double w : weights) {
            total += w;
        }
        double[] cumulative = new double[weights.length - 1];
        cumulative[0] = weights[0] / total;
        for (int i = 1; i < cumulative.length; i++) {
            cumulative[i] = cumulative[i - 1] + weights[i] / total;
        }

        double r = nextUnitUniform();
        int idx = 0;
        while (idx < cumulative.length && cumulative[idx] < r) {
            idx++;
        }
        return idx;
    }

    public <E> List<E> nextBernoulliTrials(double probTrue,  Collection<E> collection) {
        LinkedList<E> shallowCopyOfItems;
        if (collection instanceof LinkedList) {
            shallowCopyOfItems = new LinkedList<E>(collection);
        } else {
            shallowCopyOfItems = enumerator.toLinkedList(collection);
        }
        int numItems = shallowCopyOfItems.size();
        List<E> sampled = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            if (this.nextBoolean(probTrue)) {
                sampled.add(shallowCopyOfItems.get(i));
            }
        }
        return sampled;
    }

    public <E> Map<E, Integer> nextMultinomialTrials(Map<E, Double> itemWeights, int numTrials) {
        AliasMethod am = amf.build();
        ArrayList<Double> weights = new ArrayList<>();
        ArrayList<E> items = new ArrayList<>();

        for (Map.Entry<E, Double> entry : itemWeights.entrySet()) {
            weights.add(entry.getValue());
            items.add(entry.getKey());
        }
        am.initialise(weights);

        Map<E, Integer> result = new TreeMap<>();
        for (int i = 0; i < numTrials; i++) {
            E item = items.get(am.next());
            if (!result.containsKey(item)) result.put(item, 0);
            result.put(item,result.get(item) + 1);
        }
        return result;
    }

    public  <E> List<E> shuffleList(List<E> items) {
        if(items.size()==0){ //TODO might delete this in future?
            throw new UnsupportedOperationException("Cannot shuffle an empty list");
        }
        ArrayList<E> shallowCopyOfItems = new ArrayList<>(items);
        Collections.shuffle(shallowCopyOfItems);
        return shallowCopyOfItems;
    }
}
