package server;

import com.sun.net.httpserver.HttpExchange;
import service.*;

import java.io.IOException;
import java.util.Map;
import java.util.Random;


public class VoteMachine extends BasicServer {
    private final CandidateService candidateService = new CandidateService();
    private final UserService userService = new UserService();
    private final FileService fileService = new FileService();
    protected VoteMachine(String host, int port) throws IOException {
        super(host, port);
        registerGet("", this::candidatesHandler);
        registerPost("/vote", this::voteHandler);
        registerGet("/votes", this::votesHandler);
        registerGet("/login", this::loginGetHandler);
        registerPost("/login", this::loginPostHandler);
    }

    private void voteHandler(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        for (int i = 0; i < userService.getUsersDataModel().getUsers().size(); i++){
            if(!userService.getUsersDataModel().getUsers().get(i).getVoted() &&
                    userService.getUsersDataModel().getUsers().get(i).getId() == Integer.parseInt(cookies.get("id"))){
                String row = getBody(exchange);
                Map<String, String> parsed = Utils.parseUrlEncoded(row, "&");
                int id = Integer.parseInt(parsed.get("candidateId"));
                Candidate candidate = candidateService.getCandidate(id);
                CandidatesDataModel candidatesDataModel = candidateService.plusVote(id);
                userService.getUsersDataModel().getUsers().get(i).setVoted(true);
                fileService.makeAndSave("candidates.json", candidatesDataModel);
                renderTemplate(exchange, "thankyou.html", getCandidateDataModel(candidate));
            }
            else {
                renderTemplate(exchange, "votes.html", candidateService.getCandidatesDataModel());
            }
        }
    }

    private void votesHandler(HttpExchange exchange) {

        renderTemplate(exchange, "votes.html", candidateService.getCandidatesDataModel());
    }

    private void candidatesHandler(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String cookiesValue = cookies.getOrDefault("id", "0");
        if(userService.checkCookieId(cookiesValue) && !cookiesValue.equals("0")){
            renderTemplate(exchange, "candidates.html", candidateService.getCandidatesDataModel());
        }
        else {
            redirect303(exchange, "login.html");
        }
    }

    private void loginPostHandler(HttpExchange exchange) {
        String cookieString = getCookies(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String cookiesValue = cookies.getOrDefault("id", "0");
        String row = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(row, "&");
        if(userService.checkCookieId(cookiesValue) && !cookiesValue.equals("0")){
            renderTemplate(exchange, "candidates.html", candidateService.getCandidatesDataModel());
        }
        else {
            Random rnd = new Random();
            int userId = rnd.nextInt(9000) + 1000;
            for(int i = 0; i < userService.getUsersDataModel().getUsers().size(); i++){
                if(userService.getUsersDataModel().getUsers().get(i).getPassword().equals(parsed.get("password"))){
                    Cookie userCook = Cookie.make("id", userId);
                    userCook.setMaxAge(120);
                    userCook.setHttpOnly(true);
                    setCookie(exchange, userCook);
                    userService.getUsersDataModel().getUsers().get(i).setId(userId);
                    fileService.makeAndSaveUsers("users.json", userService.getUsersDataModel());
                    renderTemplate(exchange, "candidates.html", candidateService.getCandidatesDataModel());
                }
            }
        }
    }

    private void loginGetHandler(HttpExchange exchange) {
        renderTemplate(exchange, "candidates.html", candidateService.getCandidatesDataModel());
    }

    private CandidateDataModel getCandidateDataModel(Candidate candidate){
        return new CandidateDataModel(candidate);
    }
}
