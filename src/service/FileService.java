package service;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class FileService {
    public void makeAndSave(String fileName, CandidatesDataModel candidate) {
        var filePath = Path.of("data", fileName);
        var json = new Gson().toJson(candidate);
        try {
            Files.writeString(filePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeAndSaveUsers(String fileName, UsersDataModel users) {
        var filePath = Path.of("data", fileName);
        var json = new Gson().toJson(users);
        try {
            Files.writeString(filePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CandidatesDataModel readJson(String fileName){
        CandidatesDataModel candidates = new CandidatesDataModel();
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            candidates = gson.fromJson(Files.readString(filePath), CandidatesDataModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public UsersDataModel readJsonUser(String fileName){
        UsersDataModel users = null;
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            users = gson.fromJson(Files.readString(filePath), UsersDataModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
