/**
 * 
 */
package shortestPathGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

		File inDataFile = new File(args[0]);
		Scanner input = null;
		try {
			input = new Scanner(inDataFile);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		String type = input.nextLine();

		int count = 0;
		String data = null;
		// create nodes
		while (!(data = input.nextLine()).isEmpty()) {
			graph.add(new Node(data, count));
			graph.get(count).setId(count);
			count++;

		}
		// add adjacent


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
			if (type.equals("UNDIRECTED")) {
				// cause undirected, ta bort raden nedan blir den directed
				graph.get(indexadj).addAdjecent(graph.get(indexMain), Integer.parseInt(word[2]));
			}
		}


		for (Node k : graph) {
			k.setDist(Integer.MAX_VALUE);
			k.setHandled(false);
			if (k.getData().equalsIgnoreCase(start)) {
				k.setDist(0);
				startIndex = k.getId();
			}
			if (k.getData().equalsIgnoreCase(end))
				endIndex = k.getId();
		}

		Node startNode = graph.get(startIndex);
		Node endNode = graph.get(endIndex);

		//Uppdaterar distans och lägger in startnodens grannar i en min heap
		for (int i = 0; i < startNode.getAdjacent().size(); i++) {

			Node current = copyNode(startNode.getAdjacent().get(i));
			int currentCost = startNode.getCosts().get(i);

			current.setDist(currentCost + startNode.getDist());
			current.setPrevNode(startNode);
			
			minHeap.enqueue(current);

		}
		startNode.setHandled(true);


		while (!minHeap.isEmpty() && !graph.get(endIndex).isHandled()) {
			Node current = copyNode(minHeap.peek());

			Node realNode = graph.get(current.getId());
			minHeap.dequeue();

			if (!realNode.isHandled()) {
				realNode.setDist(current.getDist());
				realNode.setPrevNode(current.getPrevNode());
				realNode.setHandled(true);
				for (int i = 0; i < current.getAdjacent().size(); i++) {
					Node realAdjc = graph.get(current.getAdjacent().get(i).getId());
					int cost = current.getCosts().get(i);

					if (!realAdjc.isHandled()) {
						Node adjc = copyNode(realAdjc);
						adjc.setDist(current.getDist() + cost);
						adjc.setPrevNode(current);
						minHeap.enqueue(adjc);
					}
				}
			}
		}

		System.out.println(printShortestPath(startNode, endNode));
		writeAwnser(startNode, endNode);
		

	}

	private static void writeAwnser(Node startNode, Node endNode){
		FileWriter myfile2;
		try {
			myfile2 = new FileWriter ("Anwser.txt", false);
			PrintWriter printData = new PrintWriter(myfile2);
			
			printData.println("0");
			printData.println(endNode.getDist());
			printData.print(printShortestPath(startNode, endNode));
			
			printData.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private static Node copyNode(Node original) {
		Node copy = new Node();
		copy.setAdjacent(original.getAdjacent());
		copy.setCosts(original.getCosts());
		copy.setData(original.getData());
		copy.setDist(original.getDist());
		copy.setHandled(original.isHandled());
		copy.setId(original.getId());
		copy.setPrevNode(original.getPrevNode());

		return copy;

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
