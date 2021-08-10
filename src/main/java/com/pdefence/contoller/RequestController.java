package com.pdefence.contoller;

import com.google.api.client.util.DateTime;
import com.google.gson.JsonObject;
import com.pdefence.entity.Request;
import com.pdefence.entity.User;
import com.pdefence.service.RequestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://project-defence.vercel.app"}, allowCredentials = "true")
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping("/save")
    public void saveRequest(@RequestBody HashMap map) throws ParseException {

        Map data = (Map) map.get("data");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(data.get("date").toString());

        Request request = new Request();
        request.setType(data.get("type").toString());
        request.setDate(date);
        request.setCreatedBy(map.get("email").toString());
        request.setHour((Integer.parseInt(data.get("hour").toString())));
        requestService.saveRequest(request);
    }

    @PostMapping("/getByDate")
    public List<Request> getAllRequestsByDate(@RequestBody String date) throws ParseException, ExecutionException, InterruptedException, JSONException {
        JSONObject obj = new JSONObject(date);
        String jsonDate = obj.getString("date");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date ndate = df.parse(jsonDate);
        return requestService.getRequestByDate(ndate);
    }

    @PostMapping("/getByEmail")
    public List<Request> getAllRequestsByEmail(@RequestBody String email) throws ParseException, ExecutionException, InterruptedException, JSONException {
        return requestService.getRequestByEmail(email);
    }

    @GetMapping("/all")
    public List<Request> getAllRequests() throws ExecutionException, InterruptedException {
        return requestService.getAllRequests();
    }

}