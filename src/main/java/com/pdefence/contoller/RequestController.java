package com.pdefence.contoller;

import com.pdefence.entity.Request;
import com.pdefence.entity.enums.Status;
import com.pdefence.service.RequestService;
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
        date.setDate(date.getDate()+1);

        Request request = new Request();
        request.setType(data.get("type").toString());
        request.setDate(date);
        request.setCreatedBy(map.get("email").toString());
        request.setHour((Integer.parseInt(data.get("hour").toString())));
        request.setStatus(Status.ACTIVE);
        request.setDescription(data.get("description").toString());
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

    @PostMapping("/getById")
    public Request getRequestById(@RequestBody String id) throws ExecutionException, InterruptedException, JSONException {
        JSONObject obj = new JSONObject(id);
        String jsonDate = obj.getString("id");
        return requestService.getRequestById(jsonDate);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody String id) throws ExecutionException, InterruptedException {
        requestService.setStatusToRequest(id, Status.CANCELLED);
    }

    @PostMapping("/process")
    public void process(@RequestBody String id) throws ExecutionException, InterruptedException {
        requestService.setStatusToRequest(id, Status.DONE);
    }

    @PostMapping("/activate")
    public void activate(@RequestBody String id) throws ExecutionException, InterruptedException {
        requestService.setStatusToRequest(id, Status.ACTIVE);
    }

    @PostMapping("/getByEmail")
    public List<Request> getAllRequestsByEmail(@RequestBody String email) throws ExecutionException, InterruptedException {
        return requestService.getRequestByEmail(email);
    }

    @PostMapping("/nextPage")
    public List<Request> nextPage(@RequestBody Request last) throws ExecutionException, InterruptedException {
        return requestService.nextPage(last, false);
    }

    @PostMapping("/prevPage")
    public List<Request> prevPage(@RequestBody Request first) throws ExecutionException, InterruptedException {
        return requestService.prevPage(first, false);
    }
    @PostMapping("/archNextPage")
    public List<Request> archNextPage(@RequestBody Request last) throws ExecutionException, InterruptedException {
        return requestService.nextPage(last, true);
    }

    @PostMapping("/archPrevPage")
    public List<Request> archPrevPage(@RequestBody Request first) throws ExecutionException, InterruptedException {
        return requestService.prevPage(first, true);
    }

    @GetMapping("/all")
    public List<Request> getAllRequests() throws ExecutionException, InterruptedException {
        return requestService.getAllRequests();
    }

    @GetMapping("/archived")
    public List<Request> getArchivedRequests() throws ExecutionException, InterruptedException {
        return requestService.getArchivedRequests();
    }

    @GetMapping("statusCount")
    public List<Integer> getStatusCount() throws ExecutionException, InterruptedException {
        return requestService.getStatusCount();
    }

}