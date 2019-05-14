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

		String path = "/Users/Justad/Desktop/testFileGraph.txt"; // lagt in den i args[0] med
		String route = "Alpha - Delta";							// lagt in den i args[1] med

		System.out.println("path: "+ args[0] +"\nroute: "+args[1]);
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
		while(!(data = input.nextLine() ).isEmpty()) {
			graph.add(new Node(data, count));
			count++;

		}
		//add adjacent
		if(type.equals("UNDIRECTED")) {

			while(input.hasNextLine()) {

				String line = input.nextLine();
				String [] word = line.split("\t");
				for(int i = 0; i<word.length; i++)
					System.out.println(word[i]);
				// noden vi skall lägga till
				boolean flagMain = true;
				// noden som skall bli tillagd
				boolean flagAdj = true;
				int index = 0;
				int indexMain = 0;
				int indexadj = 0;

				// find this node and the one that should be added
				while(flagMain || flagAdj) {
					if(graph.get(index).getData().equals(word[0])) {
						flagMain = false;
						indexMain = index;
					}
					if(graph.get(index).getData().equals(word[1]))
					{ 
						flagAdj = false;
						indexadj = index;
					}
					if(flagAdj || flagMain) {
						index++;
					}

				}
				
				// adding
				graph.get(indexMain).addAdjecent(graph.get(indexadj), Integer.parseInt(word[2]));

				// cause undirected, ta bort raden nedan blir den directed
				graph.get(indexadj).addAdjecent(graph.get(indexMain), Integer.parseInt(word[2]));


			}
		} else {

			while(input.hasNextLine()) {

				String line = input.nextLine();
				String [] word = line.split("\t");
				// noden vi skall lägga till
				boolean flagMain = true;
				// noden som skall bli tillagd
				boolean flagAdj = true;
				int index = 0;
				int indexMain = 0;
				int indexadj = 0;

				// find this node and the one that should be added
				while(flagMain || flagAdj) {
					if(graph.get(index).getData().equals(word[0])) {
						flagMain = false;
						indexMain = index;
					}
					if(graph.get(index).getData().equals(word[1]))
					{ 
						flagAdj = false;
						indexadj = index;
					}
					if(flagAdj || flagMain) {
						index++;
					}

				}

				// adding
				graph.get(indexMain).addAdjecent(graph.get(indexadj), Integer.parseInt(word[2]));


			}
		}

		for(int i = 0; i < graph.size();i++) {
			System.out.println("\n");
			System.out.println(graph.get(i).getInfo());
		}

	}

	private static void directed() {
		// TODO Auto-generated method stub

	}

}
