import java.util.*;
import java.io.*;
/**
 * Write a description of class DirectedGraph here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DirectedGraph<E>{
    Map<E, DirectedGraphNode<E>> list;
    public static final int INFINITY = Integer.MAX_VALUE;

    public DirectedGraph(){
        list = new HashMap<E, DirectedGraphNode<E>>();
    }

    public boolean isEmpty(){
        if(list.size() == 0){
            return true;
        }
        return false;
    }

    private void clearAll(){
        for(DirectedGraphNode<E> node : list.values()){
            node.reset();
        }
    }

    private void printPath(DirectedGraphNode<E> end){
        //int x = 0;
        if(end.prev != null){
            printPath(end.prev);
            System.out.print(" to ");
        }
        System.out.println(end.key);
    }

    public void printPath(E end){
        DirectedGraphNode<E> temp = list.get(end);
        E key = null;
        //int x = 0;
        if(temp == null){
            throw new NoSuchElementException();
        }
        else if(temp.dist == INFINITY){
            System.out.println(temp.key+" is unreachable");
        }
        else{
            System.out.print("(Total Distance is: " + temp.dist + ") ");
            printPath(temp);
            System.out.println();
        }
    }

    private DirectedGraphNode<E> getNode(E key){
        DirectedGraphNode<E> temp = this.list.get(key);
        return temp;
    }

    public boolean addNode(E e){
        DirectedGraphNode<E> temp = null;
        if(list.get(e) == null){
            temp = new DirectedGraphNode<E>(e);
            this.list.put(e,temp);
            return true;
        }
        return false;
    }

    public boolean addEdge(E key1, E key2, int w){
        DirectedGraphNode<E> temp = getNode(key1);
        DirectedGraphNode<E> temp2 = getNode(key2);
        DirectedGraphEdge<E> edge = temp.getEdge(temp2);
        if(temp != null && edge != null){
            edge.weight = w;
            return true;
        }
        else if(temp != null && this.list.get(key2) != null){
            temp.outgoing.add(new DirectedGraphEdge(temp2, w));
            return true;
        }
        return false;
    }

    public ArrayList<DirectedGraphNode> getNeighbors(E key){
        ArrayList<DirectedGraphNode> reachable = new ArrayList<DirectedGraphNode>();
        DirectedGraphNode<E> temp = this.list.get(key);
        for(DirectedGraphEdge e: temp.outgoing){
            DirectedGraphNode<E> neighbor = (DirectedGraphNode)e.end;
            reachable.add(neighbor);
        }
        return reachable;
    }

    public void dijkstra(E key1){
        PriorityQueue<Path> q = new PriorityQueue<Path>();
        DirectedGraphNode<E> start = this.list.get(key1);
        if(start == null){
            throw new NoSuchElementException("Key not found");
        }
        clearAll();
        q.add(new Path(start, 0));
        start.dist = 0;

        int nodesSeen = 0;
        while(!q.isEmpty() && nodesSeen < list.size()){
            Path vrec = q.remove();
            DirectedGraphNode<E> temp = vrec.end;
            if(temp.scratch !=0){
                continue;
            }
            temp.scratch = 1;
            nodesSeen++;
            for(DirectedGraphEdge<E> e: temp.outgoing){
                DirectedGraphNode<E> node = e.end;
                int w = e.weight;
                if(w < 0){
                    throw new GraphException("Graph has negative edges");
                }
                if(node.dist > temp.dist + w){
                    node.dist = temp.dist+w;
                    node.prev = temp;
                    q.add(new Path(node, node.dist));
                }

            }

        } 
    }

    public class DirectedGraphEdge<E>{
        DirectedGraphNode<E> end;
        int weight;
        public DirectedGraphEdge(DirectedGraphNode<E> end, int weight){
            this.end = end;
            this.weight = weight;
        }

    }

    public class Path implements Comparable<Path>{
        DirectedGraphNode<E> end;
        int weight;
        public Path(DirectedGraphNode<E> end, int c){
            this.end = end;
            this.weight = c;
        }

        public int compareTo(Path p){
            int otherWeight = p.weight;
            return this.weight < otherWeight ? -1 : this.weight > otherWeight ? 1 : 0;
        }
    }

    public class DirectedGraphNode<E>{
        E key;
        ArrayList<DirectedGraphEdge> outgoing;
        int dist;
        DirectedGraphNode<E> prev;
        int scratch;
        public DirectedGraphNode(E e){
            this.key = e;
            this.outgoing = new ArrayList<DirectedGraphEdge>();
            reset();
        }

        public void reset(){
            this.dist = DirectedGraph.INFINITY;
            this.prev = null;
            this.scratch = 0;
        }

        public E neighbor(){
            if(this.outgoing.isEmpty() == false){
                int min = this.outgoing.get(0).weight;
                DirectedGraphNode<E> neighbor = this.outgoing.get(0).end;
                for(int i = 0; i<this.outgoing.size(); i++){
                    if(outgoing.get(i).weight < min){
                        min = this.outgoing.get(i).weight;
                        neighbor = this.outgoing.get(i).end;
                    }
                }
                return neighbor.key;
            }
            else{
                return null;
            }
        }

        public DirectedGraphEdge<E> getEdge(DirectedGraphNode<E> node){
            if(this.outgoing.isEmpty() == false){
                for(DirectedGraphEdge e: this.outgoing){
                    if(e.end.equals(node)){
                        return e;
                    }
                }
            }
            return null;
        }

    }
}

class GraphException extends RuntimeException{
    public GraphException(String name){
        super(name);
    }
}
