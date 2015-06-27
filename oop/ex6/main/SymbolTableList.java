
package oop.ex6.main;

import java.util.HashMap;
import java.util.Vector;

import oop.ex6.types.Type;
//TODO shana add comments :))


/**This is the class for the  SybolTableList, a data structure that holds our variables.
 * 
 * @author Shana M, Joshsvoss
 */
public class SymbolTableList{

	// A vector of hashmaps holding our variables.
	private Vector<HashMap<String, Type>> symbolTableList;
	
	
	/**
	 * The constructor for creating a new SymbolTableList.
	 */
	public SymbolTableList(){

		this.symbolTableList = new Vector<HashMap<String, Type>>();
		this.symbolTableList.add(Parser.GLOBAL_DEPTH,new HashMap<String, Type>());
	}
	
	protected Type addTypeToALevel(String name, Type type, int depth){
		return this.symbolTableList.elementAt(depth).put(name, type);
	}
	
	public Type search(String name, int depth){
		return this.symbolTableList.elementAt(depth).get(name);
	}
	
	protected void increaseLevel(){
		this.symbolTableList.add(new HashMap<String, Type>());
	}
	
	protected void increaseLevel(int depth){
		this.symbolTableList.add(depth, new HashMap<String, Type>());
	}
	
	protected void decreaseLevel(int depth){
		this.symbolTableList.remove(depth);
	}
	
	protected int size(){
		return this.symbolTableList.size();
	}

	public  HashMap<String, Type> findLevel(int depth){
		return this.symbolTableList.elementAt(depth);
	}
}
