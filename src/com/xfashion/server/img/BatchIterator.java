package com.xfashion.server.img;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BatchIterator<T> {

	private List<T> collection;
	private int batchSize;
	private Iterator<T> iterator;
	
	public BatchIterator(Collection<T> collection, int batchSize) {
		this.collection = new ArrayList<T>(collection);
		this.batchSize = batchSize;
		this.iterator = this.collection.iterator();
	}
	
	public Collection<T> next() {
		ArrayList<T> batch = new ArrayList<T>(batchSize);
		int cnt = 0;
		while (iterator.hasNext() && cnt < batchSize) {
			batch.add(iterator.next());
			cnt++;
		}
		if (cnt == 0) {
			return null;
		}
		return batch;
	}

}
