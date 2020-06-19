import java.util.*;
import java.io.*;
/**
 * Write a description of class GraphExperiment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GraphExperiment{
    String file;
    DirectedGraph<String> graph;
    public GraphExperiment(String file){
        this.file = file;
        this.graph = new DirectedGraph<String>();
    }

    public void run(){
        Scanner s1 = null;
        Scanner s2 = null;
        String line = "";
        try{
            s1 = new Scanner(new FileReader(this.file));
            int count = 1;
            String [] nodes = null;
            while(s1.hasNextLine()){
                line = s1.nextLine();
                if(count == 1){
                    nodes = line.split("[^A-Za-z]+");
                    for(int i = 0; i<nodes.length; i++){
                        this.graph.addNode(nodes[i]);
                    }
                    count++;
                }
                else{
                    s2 = new Scanner(line);
                    while(s2.hasNext()){
                        String start = s2.next();
                        String end = s2.next();
                        int weight = s2.nextInt();
                        this.graph.addEdge(start, end, weight);
                    }
                    count++;
                }
            }
            this.graph.dijkstra(nodes[0]);
            for(String s: nodes){
                this.graph.printPath(s);
            }

        }
        catch(Exception e){
            System.err.println(e);
            System.out.println("Error");
        }
    }
}

