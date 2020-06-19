import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DirectedGraphTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DirectedGraphTest{
    @Test
    public void testAddNode(){
        DirectedGraph<String> graph = new DirectedGraph<String>();
        assert graph.isEmpty() == true;
        graph.addNode("a");
        assert graph.list.get("a") != null;
        graph.addNode("b");
        assert graph.list.get("b") != null;
        assert graph.list.get("c") == null;
    }

    @Test
    public void testAddEdge(){
        DirectedGraph<String> graph = new DirectedGraph<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        assert graph.addEdge("a", "b",1) == true;
        assert graph.addEdge("a", "c",3) == true;
        assert graph.addEdge("b", "c",2) == true;
        assert graph.addEdge("a", "b",12) == true;
        assert graph.list.get("a").outgoing.get(0).weight == 12;
        assert graph.addEdge("a", "d",4) == false;
    }

    @Test
    public void testgetNeighbors(){
        DirectedGraph<String> graph = new DirectedGraph<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");
        graph.addEdge("a", "b",1);
        graph.addEdge("a", "c",3);
        graph.addEdge("b", "c",2);
        graph.addEdge("a", "d",4);
        String x = "";
        ArrayList<DirectedGraph.DirectedGraphNode> arr = graph.getNeighbors("a");
        for(int i = 0; i<arr.size(); i++){
            x += arr.get(i).key;
        }
        assert x.equals("bcd");

        x="";
        arr = graph.getNeighbors("b");
        for(int i = 0; i<arr.size(); i++){
            x += arr.get(i).key;
        }
        assert x.equals("c");

        x="";
        arr = graph.getNeighbors("c");
        for(int i = 0; i<arr.size(); i++){
            x += arr.get(i).key;
        }
        assert x.equals("");

        x="";
        arr = graph.getNeighbors("d");
        for(int i = 0; i<arr.size(); i++){
            x += arr.get(i).key;
        }
        assert x.equals("");
    }

    @Test
    public void testNeighbor(){
        DirectedGraph<String> graph = new DirectedGraph<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");
        graph.addEdge("a", "b",1);
        graph.addEdge("a", "c",3);
        graph.addEdge("b", "c",2);
        graph.addEdge("a", "d",4);
        assert graph.list.get("a").neighbor().equals("b");
        assert graph.list.get("b").neighbor().equals("c");
        assert graph.list.get("c").neighbor() == null;
        graph.addEdge("b","d",1);
        assert graph.list.get("b").neighbor().equals("d");
        assert graph.list.get("d").neighbor() == null;
    }

    @Test
    public void testGetEdge(){
        DirectedGraph<String> graph = new DirectedGraph<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");
        graph.addEdge("a", "b",1);
        graph.addEdge("a", "c",3);
        graph.addEdge("b", "c",2);
        graph.addEdge("a", "d",4);
        assert graph.list.get("a").getEdge(graph.list.get("b")).end.key.equals("b");
        assert graph.list.get("a").getEdge(graph.list.get("b")).weight == 1;
        assert graph.list.get("a").getEdge(graph.list.get("c")).end.key.equals("c");
        assert graph.list.get("a").getEdge(graph.list.get("c")).weight == 3;
        assert graph.list.get("a").getEdge(graph.list.get("d")).end.key.equals("d");
        assert graph.list.get("a").getEdge(graph.list.get("d")).weight == 4;
        assert graph.list.get("b").getEdge(graph.list.get("c")).end.key.equals("c");
        assert graph.list.get("b").getEdge(graph.list.get("c")).weight == 2;
        assert graph.list.get("c").getEdge(graph.list.get("d")) == null;
    }

}
