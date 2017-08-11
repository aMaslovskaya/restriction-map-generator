package app;

import java.util.ArrayList;

public class DNA {

    private int nucleotidesCount;
    private ArrayList<RestrictionSite> restrictionSites = new ArrayList<RestrictionSite>();
    private ArrayList<Character> nucleotides = new ArrayList<>();

    public DNA (ArrayList<Character> nucleotides) {
        this.nucleotides.addAll(nucleotides);
        this.nucleotidesCount = nucleotides.size();
    }

    public int getNucleotidesCount() {
        return nucleotidesCount;
    }

    public ArrayList<RestrictionSite> getRestrictionSites() {
        return restrictionSites;
    }

    public void addRestrictionSite(int nucleotideNumber, RestrictionEnzyme restrictionEnzyme) {
        this.restrictionSites.add(new RestrictionSite(nucleotideNumber, restrictionEnzyme));
    }

    public ArrayList<Character> getNucleotides() {
        return nucleotides;
    }
}
