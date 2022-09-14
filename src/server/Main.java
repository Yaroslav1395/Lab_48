package server;

import service.CandidatesDataModel;
import service.FileService;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        try {
            new VoteMachine("localhost", 9879).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
