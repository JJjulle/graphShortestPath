/**
 * 
 */
package shortestPathGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Justad
 *
 */
public class ShortetstPathMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Node> graph = new ArrayList<Node>();
		PriorityQueue minHeap = new PriorityQueue();
		System.out.println("path: " + args[0] + "\nroute: " + args[1]);

		// get start and end data of node
		String[] info = args[1].split("-");
		String start = info[0];
		String end = info[1];
		int startIndex = -1;
		int endIndex = -1;

		String path = "/Users/Justad/Desktop/testFileGraph.txt"; // lagt in den i args[0] med
		String route = "Alpha - Delta"; // lagt in den i args[1] med

		File inDataFile = new File(args[0]);
		Scanner input = null;
		try {
			input = new Scanner(inDataFile);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		String type = input.nextLine();
		System.out.println(type);

		int count = 0;
		String data = null;
		// create nodes
		while (!(data = input.nextLine()).isEmpty()) {
			graph.add(new Node(data, count));
			graph.get(count).setId(count);
			count++;

		}
		// add adjacent
		if (type.equals("UNDIRECTED")) {

			while (input.hasNextLine()) {

				String line = input.nextLine();
				String[] word = line.split("\t");
				for (int i = 0; i < word.length; i++)
					System.out.println(word[i]);
				// noden vi skall lägga till
				boolean flagMain = true;
				// noden som skall bli tillagd
				boolean flagAdj = true;
				int index = 0;
				int indexMain = 0;
				int indexadj = 0;

				// find this node and the one that should be added
				while (flagMain || flagAdj) {
					if (graph.get(index).getData().equals(word[0])) {
						flagMain = false;
						indexMain = index;
					}
					if (graph.get(index).getData().equals(word[1])) {
						flagAdj = false;
						indexadj = index;
					}
					if (flagAdj || flagMain) {
						index++;
					}

				}

				// adding
				graph.get(indexMain).addAdjecent(graph.get(indexadj), Integer.parseInt(word[2]));

				// cause undirected, ta bort raden nedan blir den directed
				graph.get(indexadj).addAdjecent(graph.get(indexMain), Integer.parseInt(word[2]));

			}
		} else {

			while (input.hasNextLine()) {

				String line = input.nextLine();
				String[] word = line.split("\t");
				// noden vi skall lägga till
				boolean flagMain = true;
				// noden som skall bli tillagd
				boolean flagAdj = true;
				int index = 0;
				int indexMain = 0;
				int indexadj = 0;

				// find this node and the one that should be added
				while (flagMain || flagAdj) {
					if (graph.get(index).getData().equals(word[0])) {
						flagMain = false;
						indexMain = index;
					}
					if (graph.get(index).getData().equals(word[1])) {
						flagAdj = false;
						indexadj = index;
					}
					if (flagAdj || flagMain) {
						index++;
					}

				}

				// adding
				graph.get(indexMain).addAdjecent(graph.get(indexadj), Integer.parseInt(word[2]));

			}
		}

		for (Node k : graph) {
			k.setDist(10000);
//			// test av heap
//			int ran = (int) (Math.random()*100);
//			k.setDist(ran);
//			minHeap.enqueue(k);

			k.setHandled(false);
			if (k.getData().equalsIgnoreCase(start)) {
				k.setDist(0);
				startIndex = k.getId();
			}
			if (k.getData().equalsIgnoreCase(end))
				endIndex = k.getId();

		}

//		System.out.println(start + " - " + end);
//		for(int i = 0; i < graph.size();i++) {
//			System.out.println("\n");
//			System.out.println(graph.get(i).getInfo());
//
//		}

		// algon
		Node startNode = graph.get(startIndex);
		Node endNode = graph.get(endIndex);

		for (int i = 0; i < startNode.getAdjacent().size(); i++) {

			Node current = startNode.getAdjacent().get(i);
			int currentCost = startNode.getCosts().get(i);

			current.setDist(currentCost + startNode.getDist());
			current.setPrevNode(startNode);
			graph.get(startNode.getId()).setHandled(true);

			minHeap.enqueue(current);

		}
//		displayHeap(minHeap);

		while (!minHeap.isEmpty()) {
			Node current = minHeap.peek();
			System.out.println(current.getData());
			minHeap.dequeue();
			if (!current.isHandled()) {
				current.setHandled(true);
				for (int i = 0; i < current.getAdjacent().size(); i++) {
					Node adjc = current.getAdjacent().get(i);
					int cost = current.getCosts().get(i);

					if (!adjc.isHandled()) {
						adjc.setDist(current.getDist() + cost);
						adjc.setPrevNode(current);
						minHeap.enqueue(adjc);
					}
				}
			}

		}

		displayHeap(minHeap);
		displayGraph(graph);
		System.out.println(printShortestPath(startNode, endNode));

	}

	public static String printShortestPath(Node start, Node current) {
		if (current.getData().equals(start.getData()))
			return start.getData();
		else {
			return printShortestPath(start, current.getPrevNode()) + " -> " + current.getData();
		}
	}

	private static void directed() {
		// TODO Auto-generated method stub

	}

	private static void displayHeap(PriorityQueue minHeap) {
		PriorityQueue copy = new PriorityQueue();
		copy = minHeap;
		System.out.println("-------------------------------------------------");
		while (!copy.isEmpty()) {
			Node k = copy.peek();
			System.out.print("\n" + k.getData() + "dist: " + k.getDist());
			copy.dequeue();
		}
	}

	private static void displayGraph(ArrayList<Node> graph) {
		System.out.println("-------------------------------------------------");
		for (int i = 0; i < graph.size(); i++) {
			System.out.println("\n");
			System.out.println(graph.get(i).getInfo());

		}
	}

}
