package com.v2java.fs.router;

import com.v2java.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("router")
public class RouteController {

    @Autowired
    RouteService routeService;

    @PostMapping("token")
    public Response token(StorageRequest request){
        String token = routeService.getToken(request);
        return Response.success(token);
    }
}
