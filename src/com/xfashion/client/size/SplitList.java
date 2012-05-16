package com.xfashion.client.size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SplitList<E> implements List<E> {

	protected List<E> first;
	protected List<E> second;

	public SplitList() {
		first = new ArrayList<E>();
		second = new ArrayList<E>();
	}

	public SplitList(Collection<E> c) {
		this();
		int mid = (c.size() + 1) / 2;
		Iterator<E> iterator = c.iterator();
		for (int i = 0; i < mid; i++) {
			first.add(iterator.next());
		}
		while (iterator.hasNext()) {
			second.add(iterator.next());
		}
	}

	@Override
	public boolean add(E e) {
		second.add(e);
		balanceLists();
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < first.size()) {
			first.add(index, element);
		} else {
			second.add(index - first.size(), element);
		}
		balanceLists();
	}

	private void balanceLists() {
		while (first.size() > second.size() + 1) {
			second.add(0, first.remove(first.size() - 1));
		}
		while (second.size() > first.size()) {
			first.add(second.remove(0));
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		second.addAll(c);
		balanceLists();
		return c.size() > 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < first.size()) {
			first.addAll(index, c);
		} else {
			second.addAll(index - first.size(), c);
		}
		balanceLists();
		return c.size() > 0;
	}

	@Override
	public void clear() {
		first.clear();
		second.clear();
	}

	@Override
	public boolean contains(Object o) {
		return first.contains(o) || second.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		List<?> temp = new ArrayList<Object>(c);
		temp.removeAll(first);
		temp.removeAll(second);
		return temp.size() == 0;
	}

	@Override
	public E get(int index) {
		if (index < first.size()) {
			return first.get(index);
		} else {
			return second.get(index - first.size());
		}
	}

	@Override
	public int indexOf(Object o) {
		int index = first.indexOf(o);
		if (index == -1) {
			index = second.indexOf(o);
			if (index != -1) {
				index += first.size();
			}
		}
		return index;
	}

	@Override
	public boolean isEmpty() {
		return first.isEmpty() && second.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = second.lastIndexOf(o);
		if (index == -1) {
			index = first.indexOf(o);
		} else {
			index += first.size();
		}
		return index;
	}

	@Override
	public ListIterator<E> listIterator() {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		boolean removed = first.remove(o);
		if (!removed) {
			removed = second.remove(o);
		}
		balanceLists();
		return removed;
	}

	@Override
	public E remove(int index) {
		E item = null;
		if (index < first.size()) {
			item = first.remove(index);
		} else {
			item = second.remove(index - first.size());
		}
		balanceLists();
		return item;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean removed = false;
		removed |= first.removeAll(c);
		removed |= second.removeAll(c);
		balanceLists();
		return removed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean removed = false;
		removed |= first.retainAll(c);
		removed |= second.retainAll(c);
		balanceLists();
		return removed;
	}

	@Override
	public E set(int index, E element) {
		E item = null;
		if (index < first.size()) {
			item = first.set(index, element);
		} else {
			item = second.set(index - first.size(), element);
		}
		return item;
	}

	@Override
	public int size() {
		return first.size() + second.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		List<E> temp = new ArrayList<E>(first);
		temp.addAll(second);
		return temp.toArray(a);
	}

	public List<E> getFirst() {
		return first;
	}

	public List<E> getSecond() {
		return second;
	}

}
