
package oop.ex6.main;

import java.util.HashMap;
import java.util.Vector;

import oop.ex6.types.Type;


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
		
		// created a new SymbolTableList.
		this.symbolTableList = new Vector<HashMap<String, Type>>();

		// Add the global depth hashmap to the SymbolTableList.
		this.symbolTableList.add(Parser.GLOBAL_DEPTH,new HashMap<String, Type>());
	}
	
	/**Add a new Type variable to the the SymbolTableList at the given depth index.
	 * 
	 * @param name the name of the variable Type to be added.
	 * @param type the variable Type to be added.
	 * @param depth the depth at which the variable Type was declared, and thus the hashmap in the vector to 
	 * which it is to be added.
	 * @return the Type previously associated with that name in the hashmap, null if no Type previously
	 * associated.
	 */
	protected Type addTypeToALevel(String name, Type type, int depth){
		return this.symbolTableList.elementAt(depth).put(name, type);
	}
	
	/**Search the SymbolTableList at the given depth for a type with the given name.
	 * 
	 * @param name the name to be searched.
	 * @param depth the depth (vector idx) at which to search.
	 * @return the Type found with the given name, or null if none found.
	 */
	public Type search(String name, int depth){
		return this.symbolTableList.elementAt(depth).get(name);
	}

	/**
	 * Add another hashmap to the SymbolTableList, thus increasing its depth (level).
	 */
	protected void increaseLevel(){
		this.symbolTableList.add(new HashMap<String, Type>());
	}
	
	/**Add a new hashmap to the SymbolTableList at the given depth (vector idx), thus increasing its depth 
	 * (level).
	 * @param depth
	 */
	protected void increaseLevel(int depth){
		this.symbolTableList.add(depth, new HashMap<String, Type>());
	}
	
	/**Remove the hashmap at the given depth (vector idx) of the SymbolTableList.
	 * 
	 * @param depth the depth from which the hashmap should be deleted.
	 */
	protected void decreaseLevel(int depth){
		this.symbolTableList.remove(depth);
	}
	
	/**
	 * @return the size of the SymbolTableList.
	 */
	protected int size(){
		return this.symbolTableList.size();
	}

	/**Find the hashmap at the given depth (vector idx).
	 * 
	 * @param depth the depth at which to find the hashmap.
	 * @return the hashmap at the given depth.
	 */
	protected  HashMap<String, Type> findLevel(int depth){
		return this.symbolTableList.elementAt(depth);
	}
}
