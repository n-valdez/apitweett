package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiData;
import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.bean.ApiSingleResponse;
import com.example.twitterapi.api.bean.twitter.Space;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.example.twitterapi.api.repository.SpaceRepository;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.twitterapi.api.helper.ResponseUtils.API_RESPONSE_BAD_REQUEST;
import static com.example.twitterapi.api.helper.ResponseUtils.DEFAULT_ERROR_RESPONSE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final Command command;
    private final SpaceRepository spaceRepository;

    @Autowired
    public SpaceServiceImpl(Command command, SpaceRepository spaceRepository) {
        this.command = command;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> save(Map<String, String> userMap) throws IOException {
        String world = userMap.getOrDefault("world", "");
        if (world.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        String spacesTwQuery = URLEncoder.encode(String.format("\"%s\"", world), "UTF-8");
        String url = String.format(CommandUrl.SEARCH_SPACES, spacesTwQuery);
        HttpGet spacesTw = new HttpGet(url);
        String spacesTwResp = command.executeHttpMethod(spacesTw);
        JSONObject twSpacesJson;
        try {
            twSpacesJson = new JSONObject(spacesTwResp);
            if (!twSpacesJson.has("data")) {
                return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("No hay Spaces para guardar.")),
                        HttpStatus.SC_NOT_FOUND), NOT_FOUND);
            }
            JSONArray spacesArray = twSpacesJson.getJSONArray("data");
            for (int i = 0; i < spacesArray.length(); i++) {
                Space spaceTw = new Gson().fromJson(spacesArray.getString(i), Space.class);
                spaceRepository.findOneById(spaceTw.getId()).ifPresent(spaceObj -> spaceTw.setId(spaceObj.getId()));
                spaceRepository.save(spaceTw);
            }

            return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("Spaces Guardados.")),
                    HttpStatus.SC_OK), OK);
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<ApiData> apiData = new ArrayList<>(spaceRepository.findAll());
        return new ResponseEntity<>(new ApiResponse(true, apiData, HttpStatus.SC_OK), OK);
    }
}
