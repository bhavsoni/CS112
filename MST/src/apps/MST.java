package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		
		 PartialTreeList ptl = new PartialTreeList();
		 
	        for (Vertex vert : graph.vertices) {
	            
	        	PartialTree partt = new PartialTree(vert);
	            Vertex.Neighbor neigh = vert.neighbors;
	           
	            while (neigh != null) {
	                PartialTree.Arc ptarc = new PartialTree.Arc(vert, neigh.vertex, neigh.weight);
	                partt.getArcs().insert(ptarc);
	                neigh = neigh.next;
	            }
	            ptl.append(partt);
	        }
	       
	        return ptl;
	        
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */

		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){
			
			PartialTree pt = ptlist.remove();
			
			if(pt ==null){
				break;
			}
			
			MinHeap<PartialTree.Arc> heap = pt.getArcs();
			PartialTree.Arc ptarc= heap.deleteMin();
			
			Vertex vert = ptarc.v2;
			
			Vertex v1 = pt.getRoot();
			Vertex v2 = vert.getRoot(); 
			
			while(!heap.isEmpty() && v1.equals(v2) ){
				ptarc = heap.deleteMin();
				vert = ptarc.v2;
			}
			
			PartialTree pt2 = ptlist.removeTreeContaining(vert);
			pt.merge(pt2);
			ptlist.append(pt);
			result.add(ptarc);
		}
		
		return result;
	}
}
