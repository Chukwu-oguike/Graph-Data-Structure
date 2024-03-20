import java.util.*;


public class ListGraph implements Graph {

    private HashMap<String, LinkedList<String>> nodes;

    public ListGraph(){

        this.nodes = new HashMap<String, LinkedList<String>>();
    }

    @Override
    public boolean addNode(String n){

        if (this.nodes.containsKey(n)){

            return false;

        } else {

            this.nodes.put(n, new LinkedList<String>());
            return true;
        }

    }

    @Override
    public boolean addEdge(String n1, String n2){

        if (this.nodes.containsKey(n1) && nodes.containsKey(n2)){
            this.nodes.get(n1).add(n2);
            return true;
        } else {
            if (!this.nodes.containsKey(n1)){
                throw new NoSuchElementException(n1 + " doesn't exist in graph");
            }
            if (!this.nodes.containsKey(n2)){
                throw new NoSuchElementException(n2 + " doesn't exist in graph");
            }

        }

        return false;

    }

    @Override
    public boolean hasNode(String n){

        if (this.nodes.containsKey(n)){
            return true;
        } else{
            return false;
        }

    }

    @Override
    public boolean hasEdge(String n1, String n2) {

        if (this.hasNode(n1)) {

            if (this.nodes.get(n1).contains(n2)) {

                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public boolean removeNode(String n){

        // may not have node
        if (this.hasNode(n)){

            // remove connections from node
            this.nodes.get(n).clear();
            this.nodes.remove(n);

            // remove connections to node
            for (String key : this.nodes.keySet()) {

                if(this.nodes.get(key).contains(n)){

                    this.nodes.get(key).remove(n);
                }

            }



            return true;

        } else {
            return false;
        }


    }

    @Override
    public boolean removeEdge(String n1, String n2){

        //check if nodes
        if (this.hasNode(n1)) {
            if(this.nodes.get(n1).contains(n2)){
                this.nodes.get(n1).remove(n2);
                return true;
            } else {
                throw new NoSuchElementException("edge doesn't exist in graph");
            }
        } else{
            throw new NoSuchElementException("edge doesn't exist in graph");
        }
    }

    @Override
    public List<String> nodes(){

        List<String> ll1 = new ArrayList<String>();

        for (String key : this.nodes.keySet()){
            ll1.add(key); //what happens when list is empty
        }

        return ll1;

    }

    @Override
    public List<String> succ(String n){

        List<String> ll1 = new ArrayList<String>();

        if (this.hasNode(n)){
            for (String temp : this.nodes.get(n)){
                ll1.add(temp); //what happens when list is empty

            }

        } else {
            throw new NoSuchElementException(n + " doesn't exist in graph");
        }
        return ll1;
    }

    @Override
    public List<String> pred(String n){

        if (this.hasNode(n)){
            List<String> ll1 = new ArrayList<String>();

            for (String key : this.nodes.keySet()){
                if (this.nodes.get(key).contains(n)){
                    ll1.add(key);
                }
                //what happens when list is empty
            }

            return ll1;

        } else {
            throw new NoSuchElementException(n + " doesn't exist in graph");
        }

    }

    @Override
    public Graph union(Graph g){

        //go through each node in new graph, check if exist, if so check for succ and predd, add nodes and edges
        ListGraph newGraph = new ListGraph();

        // copy current graph
        for (Map.Entry<String, LinkedList<String>> entry : this.nodes.entrySet()){
            newGraph.nodes.put(entry.getKey(), new LinkedList<String>(entry.getValue()));
        }

        for (String key : g.nodes()){
            if(newGraph.hasNode(key)){
                for (String tempKey : g.succ(key)){
                    if(!newGraph.nodes.get(key).contains(tempKey)){
                        newGraph.nodes.get(key).add(tempKey);
                    }
                }


            } else{ // if not exist add node, add succ and predd, add nodes and edges
                newGraph.addNode(key);
                for (String newKey : g.succ(key)){
                    newGraph.addEdge(key, newKey);
                    if(!newGraph.hasNode(newKey)){
                        newGraph.addNode(newKey);
                    }
                }

            }

        }
        // if not exist add node, add succ and predd, add nodes and edges
        return newGraph;

    }

    @Override
    public Graph subGraph(Set<String> nodes){

        ListGraph newGraph = new ListGraph();

        for (String value : nodes) {
            if(this.hasNode(value)) {
                newGraph.addNode(value);
            }
        }

        for (String key : newGraph.nodes()) {
            for(String newKey : newGraph.nodes()){
                if(this.hasEdge(key, newKey)){
                    newGraph.addEdge(key, newKey);
                }
            }
        }
        // Merge new graph with current graph
        return newGraph;
    }

    @Override
    public boolean connected(String n1, String n2){
        if(!hasNode(n1))
            throw new NoSuchElementException(n1 + " doesn't exist in graph");

        if(!hasNode(n2))
            throw new NoSuchElementException(n2 + " doesn't exist in graph");

        if (n1 == n2)
            return true;

        // Mark all the vertices as not visited
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

        for (String key : this.nodes.keySet()){
            visited.put(key, false);
        }

        // Create a queue for BFS
        LinkedList<String> bfsQueue = new LinkedList<String>();

        // Mark the current node as visited and enqueue it
        visited.put(n1, true);
        bfsQueue.add(n1);


        while (bfsQueue.size() != 0) {

            // Dequeue a vertex from queue and print it
            String currentNode = bfsQueue.peek();
            bfsQueue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it visited
            // and enqueue it
            for (String key : this.nodes.get(currentNode)) {
                // If this adjacent node is the destination node, then
                // return true
                if (key == n2) {
                    return true;
                }

                // Else, continue to do BFS
                if (visited.get(key) == false)
                {
                    visited.put(key, true);
                    bfsQueue.add(key);
                }
            }
        }

        // if n2 not found
        return false;

    }



}

