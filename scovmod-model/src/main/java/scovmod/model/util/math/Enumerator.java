package scovmod.model.util.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Enumerator {
	public <E> LinkedList<E> toLinkedList(Collection<E> items) {
		return new LinkedList<E>(items);
	}

	public <E> ArrayList<E> toArrayList(Collection<E> items) {
		return new ArrayList<E>(items);
	}
}
