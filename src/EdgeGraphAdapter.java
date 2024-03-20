import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    @Override
    public boolean addEdge(Edge e) {

        if(this.g.hasEdge(e.getSrc(), e.getDst())){
            return true;
        }

        if(!this.g.hasNode(e.getSrc())){
            this.g.addNode(e.getSrc());
        }

        if(!this.g.hasNode(e.getDst())){
            this.g.addNode(e.getDst());
        }

        return this.g.addEdge(e.getSrc(), e.getDst());
    }

    @Override
    public boolean hasNode(String n) {

        return this.g.hasNode(n);
    }

    @Override
    public boolean hasEdge(Edge e) {

        return this.g.hasEdge(e.getSrc(), e.getDst());
    }

    @Override
    public boolean removeEdge(Edge e) {

        return this.g.removeEdge(e.getSrc(), e.getDst());
    }

    @Override
    public List<Edge> outEdges(String n) {

        List<Edge> ll1 = new LinkedList<Edge>();

        for(String key : this.g.succ(n)){
            ll1.add(new Edge(n, key));
        }

        return ll1;
    }

    @Override
    public List<Edge> inEdges(String n) {

        List<Edge> ll1 = new LinkedList<Edge>();

        for(String key : this.g.pred(n)){
            ll1.add(new Edge(key, n));
        }

        return ll1;
    }

    @Override
    public List<Edge> edges() {

        List<Edge> ll1 = new LinkedList<Edge>();

        for(String key : this.g.nodes()){
            for (String tempkey : this.g.succ(key)){
                ll1.add(new Edge(key, tempkey));
            }
        }

        return ll1;
    }

    @Override
    public EdgeGraph union(EdgeGraph g) {

        EdgeGraph newGraph = new EdgeGraphAdapter(this.g);

        List<Edge> ll1 = g.edges();

        for(Edge key : ll1){

            newGraph.addEdge(key);
        }

        return newGraph;
    }

    @Override
    public boolean hasPath(List<Edge> e) {

        if(e.isEmpty()){
            return true;
        }

        for(Edge item : e){
            if(!this.hasEdge(item)){
                return false;
            }
        }


        for (int i = 0; i < e.size()-1; i++) {

            if(!e.get(i).getDst().equals(e.get(i + 1).getSrc())){
                throw new BadPath("Path does not exist");
            }
        }

        return true;
    }


}
