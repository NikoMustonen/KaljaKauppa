package fi.tamk.beerbros.kaljakauppa.components;

public class Link {
    
    private String href;
    private String rel;
    
    public Link() {}
    
    public Link(String rel) {
        this(rel, rel);
    }
    
    public Link(String rel, String href) {
        this.rel = rel;
        setHref(href);
    }

    public String getHref() {
        return "http://localhost:8080" + href;
    }

    public final void setHref(String href) {
        String newHref = href.trim();
        if(newHref.charAt(0) != '/')newHref = "/" + newHref;
        this.href = newHref;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
