package fi.tamk.beerbros.kaljakauppa.components;

import java.util.ArrayList;
import java.util.Arrays;

public class Links {
    
    private ArrayList<Link> links;
    
    public Links() {
        this.links = new ArrayList<>();
    }
    
    public Links(ArrayList<Link> links) {
        this();
        this.links = links;
    }
    
    public Links(Link... links) {
        this();
        this.links.addAll(Arrays.asList(links));
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
    
    public void addLink(Link link) {
        this.links.add(link);
    }
    
    public void addLinks(Link... links) {
        this.links.addAll(Arrays.asList(links));
    }
}
