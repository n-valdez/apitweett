package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiData;
import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.bean.ApiSingleResponse;
import com.example.twitterapi.api.bean.twitter.List;
import com.example.twitterapi.api.bean.twitter.User;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.example.twitterapi.api.repository.ListRepository;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static com.example.twitterapi.api.helper.ResponseUtils.API_RESPONSE_BAD_REQUEST;
import static com.example.twitterapi.api.helper.ResponseUtils.DEFAULT_ERROR_RESPONSE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
public class ListServiceImpl implements ListService {

    private final Command command;
    private final UserService userService;
    private final ListRepository listRepository;

    @Autowired
    public ListServiceImpl(Command command, UserService userService, ListRepository listRepository) {
        this.command = command;
        this.userService = userService;
        this.listRepository = listRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> saveByUsername(Map<String, String> userMap) throws IOException {

        String user = userMap.getOrDefault("user", "");
        if (user.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        String url = String.format(CommandUrl.SEARCH_USERS, user);
        HttpGet usersTw = new HttpGet(url);
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson;
        try {
            twUserJson = new JSONObject(userTwResp);
            ResponseEntity<ApiResponse> checkUserErrors = userService.checkUserErrors(twUserJson, user);
            if (checkUserErrors != null) {
                return checkUserErrors;
            }
            User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);
            String urlLists = String.format(CommandUrl.LISTS_BY_USER_ID, userTw.getId());
            HttpGet listsTw = new HttpGet(urlLists);
            String listsTwResp = command.executeHttpMethod(listsTw);
            JSONObject listsTwJson = new JSONObject(listsTwResp);
            if (!listsTwJson.has("data")) {
                return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("No hay Listas para guardar.")),
                        HttpStatus.SC_NOT_FOUND), NOT_FOUND);
            }
            JSONArray spacesArray = listsTwJson.getJSONArray("data");
            for (int i = 0; i < spacesArray.length(); i++) {
                List listTw = new Gson().fromJson(spacesArray.getString(i), List.class);
                listRepository.findOneById(listTw.getId()).ifPresent(listObj -> listTw.setId(listObj.getId()));
                listRepository.save(listTw);
            }

            return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("Listas Guardadas.")),
                    HttpStatus.SC_OK), OK);
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        java.util.List<ApiData> apiData = new ArrayList<>(listRepository.findAll());
        return new ResponseEntity<>(new ApiResponse(true, apiData, HttpStatus.SC_OK), OK);
    }
}
