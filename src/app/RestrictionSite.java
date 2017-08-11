package app;

import app.RestrictionEnzyme;

public class RestrictionSite {

    private Integer nucleotideNumber;
    private RestrictionEnzyme restrictionEnzyme;

    public RestrictionSite(int nucleotideNumber, RestrictionEnzyme restrictionEnzyme) {
        this.nucleotideNumber = nucleotideNumber;
        this.restrictionEnzyme = restrictionEnzyme;
    }

    public int getNucleotideNumber() {
        return nucleotideNumber;
    }

    public RestrictionEnzyme getRestrictionEnzyme() {
        return restrictionEnzyme;
    }
}
