package scovmod.model.util;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

@SuppressWarnings("varargs")
public class TestUtils {
    @SafeVarargs
    public static <E> Iterator<E> iteratorOf(E... items) {
        return Arrays.asList(items).iterator();
    }

    @SafeVarargs
    public static <E> List<E> listOf(E... items) {
        return Arrays.asList(items);
    }

    @SafeVarargs
    public static <E> LinkedHashSet<E> setOf(E... items) {
        LinkedHashSet<E> s = new LinkedHashSet<>(Arrays.asList(items));
        return s;
    }

    public static IntSet intSetOf(Integer... ints) {
        return new IntOpenHashSet(Arrays.asList(ints));
    }

    public static LongSet longSetOf(Long... longs) {
        return new LongOpenHashSet(Arrays.asList(longs));
    }
}
