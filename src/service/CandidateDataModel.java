package service;

public class CandidateDataModel {
    private Candidate candidate;

    public CandidateDataModel(){};

    public CandidateDataModel(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
