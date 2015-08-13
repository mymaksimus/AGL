package de.skysoldier.abstractgl2.mklmbversion.lib.render.geom.collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.lwjgl.util.vector.Vector2f;

public class AGLQuadTree {
	
	public static final int MAX_DEPTH = 4;
	public static final int MIN_ITEMS = 2;
	public static final int MAX_ITEMS = 10;
	
	private int depth;
	private int objectCount;
	private Vector2f minCorner;
	private Vector2f maxCorner;
	private Vector2f center;
	private AGLQuadTree parent;
	private ArrayList<AGLQuadTree> children;
	private ArrayList<AGLCollisionInstance> collisionInstanceList;
	
	public AGLQuadTree(Vector2f minCorner, Vector2f maxCorner){
		this(0, minCorner, maxCorner, null); 
	}
	
	public AGLQuadTree(int depth, Vector2f minCorner, Vector2f maxCorner, AGLQuadTree parent){
		this.minCorner = minCorner;
		this.maxCorner = maxCorner;
		this.center = new Vector2f(minCorner.x + 0.5f * (maxCorner.x - minCorner.x), minCorner.y + 0.5f * (maxCorner.y - minCorner.y));
		this.depth = depth;
		this.parent = parent;
		collisionInstanceList = new ArrayList<>();
	}
	
	public AGLQuadTree(int depth, float minx, float miny, float maxx, float maxy, AGLQuadTree parent){
		this(depth, new Vector2f(minx, miny), new Vector2f(maxx, maxy), parent);
	}
	
	private int getQuadrandIndex(AGLCollisionInstance collisionInstance){
		AGLAabb aabb = collisionInstance.getCollideable().getAabb();
		int quadrandPositionX = aabb.getMinCorner().x > center.x ? 1 : (aabb.getMaxCorner().x < center.x ? 0 : -1); //1: right | 0: left
		int quadrandPositionY = aabb.getMinCorner().y > center.y ? 1 : (aabb.getMaxCorner().y < center.y ? 0 : -1); //1: top | 0: bottom
		return quadrandPositionX < 0 || quadrandPositionY < 0 ? -1 : 2 * quadrandPositionX + quadrandPositionY;
	}
	
	public void add(AGLCollisionInstance CollisionInstance){
		objectCount++;
		if(objectCount > MAX_ITEMS && !hasChildren() && depth < MAX_DEPTH) createChildren();
		int quadrandIndex = -1;
		if(hasChildren() && (quadrandIndex = getQuadrandIndex(CollisionInstance)) >= 0){
			children.get(quadrandIndex).add(CollisionInstance);
		}
		else collisionInstanceList.add(CollisionInstance);
	
	}
	
	public void remove(AGLCollisionInstance CollisionInstance){
		objectCount--;
		if(objectCount < MIN_ITEMS && hasChildren()) releaseChildren();
		int quadrandIndex = -1;
		if(hasChildren() && (quadrandIndex = getQuadrandIndex(CollisionInstance)) >= 0){
			children.get(quadrandIndex).remove(CollisionInstance);
		}
		else collisionInstanceList.remove(CollisionInstance);
	}
	
	public boolean hasChildren(){
		return children != null;
	}
	
	private void createChildren(){
		children = new ArrayList<>();
		children.add(new AGLQuadTree(depth + 1, minCorner.x, minCorner.y, center.x, center.y, this)); //bottom left
		children.add(new AGLQuadTree(depth + 1, minCorner.x, center.y, center.x, maxCorner.y, this)); //top left
		children.add(new AGLQuadTree(depth + 1, center.x, minCorner.y, maxCorner.x, center.y, this)); //bottom right
		children.add(new AGLQuadTree(depth + 1, center.x, center.y, maxCorner.x, maxCorner.y, this)); //top right
		ArrayList<AGLCollisionInstance> collisionInstanceCopys = new ArrayList<>();
		collisionInstanceCopys.addAll(collisionInstanceList);
		collisionInstanceList.clear();
		objectCount -= collisionInstanceCopys.size();
		Iterator<AGLCollisionInstance> iterator = collisionInstanceCopys.iterator();
		while(iterator.hasNext()){
			add(iterator.next());
//			iterator.remove();
		}
	}
	
	private void releaseChildren(){
		collisionInstanceList.addAll(collectCollisionInstances(false));
		children = null;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(tabString(depth, "[QT depth: " + depth + ", num: " + objectCount + "] " + collisionInstanceList));
		builder.append("\n");
		if(hasChildren()){
			for(AGLQuadTree qt : children) builder.append(qt);
		}
		return builder.toString();
	}
	
	public Vector2f getMinCorner(){
		return minCorner;
	}
	
	public Vector2f getMaxCorner(){
		return maxCorner;
	}
	
	public static String tabString(int depth, String appendix){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) builder.append("  ");
		if(appendix != null) builder.append(appendix);
		return builder.toString();
	}
	
	public AGLQuadTree getParent(){
		return parent;
	}
	
	private ArrayList<AGLCollisionInstance> getPossibleCollisions(ArrayList<AGLCollisionInstance> collisions){
		ArrayList<AGLCollisionInstance> subList = collectCollisionInstances(false);
		ListIterator<AGLCollisionInstance> iterator1 = collisionInstanceList.listIterator();
		while(iterator1.hasNext()){
			AGLCollisionInstance collisionInstance1 = iterator1.next();
			ListIterator<AGLCollisionInstance> iterator2 = collisionInstanceList.listIterator(iterator1.nextIndex());
			while(iterator2.hasNext()){
				collisions.add(collisionInstance1);
				collisions.add(iterator2.next());
			}
			for(AGLCollisionInstance collisionInstance2 : subList){
				collisions.add(collisionInstance1);
				collisions.add(collisionInstance2);
			}
		}
		if(hasChildren()){
			for(AGLQuadTree tree : children){
				tree.getPossibleCollisions(collisions);
			}
		}
		return collisions;
	}
	
	public ArrayList<AGLCollisionInstance> getPossibleCollisions(){
		ArrayList<AGLCollisionInstance> collisions = new ArrayList<>();
		getPossibleCollisions(collisions);
		return collisions;
	}
	
	private ArrayList<AGLCollisionInstance> collectCollisionInstances(boolean addSelf){
		ArrayList<AGLCollisionInstance> collisionInstances = new ArrayList<>();
		collectCollisionInstances(addSelf, collisionInstances);
		return collisionInstances;
	}
	
	private void collectCollisionInstances(boolean addSelf, ArrayList<AGLCollisionInstance> collisionInstances){
		if(addSelf) collisionInstances.addAll(collisionInstanceList);
		if(hasChildren()){
			for(AGLQuadTree tree : children){
				tree.collectCollisionInstances(true, collisionInstances);
			}
		}
	}
	
//	public static void main(String[] args){
//		QuadTree tree = new QuadTree(new Vector2f(0, 0), new Vector2f(10, 10));
//		
//		AABB aabb1 = new AABB(new Vector2f(1, 1), new Vector2f(2, 2));
//		AABB aabb2 = new AABB(new Vector2f(6, 1), new Vector2f(7, 2));
//		AABB aabb3 = new AABB(new Vector2f(1, 6), new Vector2f(2, 7));
//		AABB aabb4 = new AABB(new Vector2f(6, 6), new Vector2f(7, 7));
//		
//		System.out.println(tree);
//	}
}
