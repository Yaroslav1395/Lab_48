package service;

public class CandidateService {
    private CandidatesDataModel candidatesDataModel = new FileService().readJson("candidates.json");


    public Candidate getCandidate(int id){
        return candidatesDataModel.getCandidates().stream().filter(e -> e.getId() == id).findFirst().orElseThrow();
    }

    public CandidatesDataModel plusVote(int id){
        for(int i = 0; i < candidatesDataModel.getCandidates().size(); i++){
            if(candidatesDataModel.getCandidates().get(i).getId() == id){
                    candidatesDataModel.getCandidates().get(i).setVotes(1);
            }
        }
        percentage();
        return candidatesDataModel;
    }

    public CandidatesDataModel getCandidatesDataModel() {
        return candidatesDataModel;
    }

    public void setCandidateDataModel(CandidatesDataModel candidateDataModel) {
        this.candidatesDataModel = candidateDataModel;
    }

    public void percentage(){
        double count = 0;
        for(int i = 0; i < candidatesDataModel.getCandidates().size(); i++){
            count += candidatesDataModel.getCandidates().get(i).getVotes();
        }
        for(int i = 0; i < candidatesDataModel.getCandidates().size(); i++){
            candidatesDataModel.getCandidates().get(i).setPercent(
                    candidatesDataModel.getCandidates().get(i).getVotes() * 100 / count);
        }
    }
}
