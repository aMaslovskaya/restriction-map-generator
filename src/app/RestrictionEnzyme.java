package app;

public class RestrictionEnzyme {
    private String name;
    private String sequence;

    public RestrictionEnzyme(String name, String sequence) {
        this.name = name;
        this.sequence  = sequence;
    }

    public String getName() { return name; }

    public String getSequence() { return sequence; }
}
