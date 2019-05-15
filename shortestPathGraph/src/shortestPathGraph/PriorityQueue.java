package shortestPathGraph;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PriorityQueue {
	private ArrayList<Node> list;
	private int size;

	public PriorityQueue() {
		list = new ArrayList<Node>();
		size = 0;
	}

	public PriorityQueue(Node element) {
		list = new ArrayList<Node>();
		list.add(element);
		size = 1;
	}

	public void enqueue(Node element) {

		if (this.size == 0) {
			list.add(element);
			this.size++;
		} else {
			this.size++;
			boolean flag = true;
			list.add(element);

			int index = size - 1;
			while (flag) {
				int parent = (index - 1) / 2;
				if (this.list.get(parent).getDist().compareTo(element.getDist()) <= 0 || index == 0) {
					flag = false;

				} else {
					Node temp = list.get(parent);
					list.remove(index);
					list.add(index, temp);
					list.remove(parent);
					list.add(parent, element);
					index = parent;

				}

			}

		}

	}

	public void dequeue() {

		if (this.size == 0) {
			throw new NoSuchElementException();
		} else {
			boolean flag = true;
			int index = 0;
			int last = this.size - 1;
			Node val = list.get(last);
			list.remove(0);
			list.add(0, val);
			list.remove(last);
			last--;

			while (flag) {
				if (last >= (index * 2 + 2)) {
					Node lChild = list.get(2 * index + 1);
					Node rChild = list.get(2 * index + 2);
					Node min;
					int minIndex;

					if (lChild.getDist().compareTo(rChild.getDist()) >= 1) {
						min = rChild;
						minIndex = (2 * index + 2);
					} else {
						min = lChild;
						minIndex = (2 * index + 1);
					}

					if (val.getDist().compareTo(min.getDist()) >= 1) {
						swap(index, minIndex);
						index = minIndex;
					} else {
						flag = false;
					}

				} else if (last == index * 2 + 1) {
					Node leftChild = list.get(2 * index + 1);
					int minIndex = (2 * index) + 1;

					if (val.getDist().compareTo(leftChild.getDist()) >= 1) {
						swap(index, minIndex);
						flag = false;
					} else {
						flag = false;
					}

				} else {
					flag = false;
				}

			}

			this.size--;
		}

	}

	public Node peek() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		else
			return this.list.get(0);
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return (this.size == 0);
	}

	private void swap(int i, int k) {
		Node temp = this.list.get(i);
		this.list.remove(i);
		this.list.add(i, this.list.get(k - 1));
		this.list.remove(k);
		this.list.add(k, temp);
	}
}
