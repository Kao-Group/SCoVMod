/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.util;

import java.util.Iterator;

public class FlatteningIterator<T> implements Iterator<T> {

	private Iterator<Iterator<T>> outerIterator;
	private Iterator<T> currentInnerIterator = null;

	public FlatteningIterator(Iterator<Iterator<T>> grandIt) {
		this.outerIterator = grandIt;
	}

	@Override
	public boolean hasNext() {
		if (currentInnerIterator == null) {
			if (outerIterator.hasNext()) {
				currentInnerIterator = outerIterator.next();
			} else {
				return false;
			}
		}

		while (!currentInnerIterator.hasNext()
				&& outerIterator.hasNext()) {
			currentInnerIterator = outerIterator.next();
		}

		return currentInnerIterator.hasNext();
	}

	@Override
	public T next() {
		if (currentInnerIterator == null || !currentInnerIterator.hasNext()) {
			currentInnerIterator = outerIterator.next();
		}
		return currentInnerIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
