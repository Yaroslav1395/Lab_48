package service;

import java.util.List;

public class CandidatesDataModel {
    private List<Candidate> candidates;

    public CandidatesDataModel(){};

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
