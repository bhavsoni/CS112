package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node item1 = poly1;
		Node item2 = poly2;
		Node result = new Node(0,0,null);
		
		
		//returns 0 if both poly are null or if one of them are null returns the other
		if(item1 == null && item2 == null){
			return result;
		} 
		else if(item1 == null){
			return item2;
		}
		else if(item2 == null){
			return item1;
		}
		
		//remove initial zero
		result = result.next;
		
		//adds coefficients of similar degree terms or puts terms in ascending order
		while(item1!=null && item2!= null){
			if(item1.term.degree == item2.term.degree){
				float coeffSum = item1.term.coeff + item2.term.coeff;
				if(coeffSum != 0){
					result = new Node(coeffSum, item1.term.degree, result);
				}
				item1 = item1.next;
				item2 = item2.next;
			}
			
			else if(item1.term.degree > item2.term.degree){
				result = new Node(item2.term.coeff, item2.term.degree, result);
				item2 = item2.next;
			}
			
			else if (item1.term.degree < item2.term.degree){
				result = new Node(item1.term.coeff, item1.term.degree, result);
				item1 = item1.next;
			}
		}
		
		//when the first poly is longer than the second one keep adding terms to the result
		while(item1 != null){
			result = new Node(item1.term.coeff, item1.term.degree, result);
			item1 = item1.next;
		}

		//when the second poly is longer than the first one keep adding terms to the result
		while(item2 != null){
			result = new Node(item2.term.coeff, item2.term.degree, result);
			item2 = item2.next;
		}
		
		//uses the reverse method to put poly into descending order
		return reverseLList(result);
	}
	
	private static Node reverseLList(Node poly){
		Node item = poly;
		Node result = new Node(0,0,null);
		result = result.next;
		while(item != null){
			if(item.term.coeff != 0){
				result = new Node(item.term.coeff, item.term.degree, result);
				item = item.next;
			}else{
				item = item.next;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node item1 = poly1;
		Node item2 = poly2; 
		Node result = new Node(0,0,null);
		Node tempResult = new Node(0,0,null);
		tempResult = tempResult.next;
		
		if(item1 == null || item2 == null){
			return result;
		}
		
		while(item1 != null){
			while(item2 != null){
				tempResult = new Node( (item1.term.coeff * item2.term.coeff), (item1.term.degree + item2.term.degree), tempResult);
				item2 = item2.next;
			}
			
			//order the temporary poly in descending order to be added to the result
			tempResult = reverseLList(tempResult);
			
			result = add(tempResult, result);
			item2 = poly2;
			item1 = item1.next;
			tempResult = new Node(0,0,null);
			tempResult = tempResult.next;
		
		}
		return result;
	}
		
	//32.0x^9 + 16.0x^8 + -16.0x^7 + -20.0x^6 + 52.0x^5 + 38.0x^4 + -6.0x^3 + -6.0x^2 + 9.0x + 27.0
	
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node item = poly;
		float result = 0;
		
		while(item != null){
			double itemResult = item.term.coeff* (Math.pow(x, item.term.degree));
			result += (float)itemResult;
			item = item.next;
		}
		
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
