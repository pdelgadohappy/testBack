package com.happymoney.webcrawler.controller;

import com.happymoney.webcrawler.controller.requests.UserDto;
import com.happymoney.webcrawler.model.ResponseWithMetadata;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class for handling requests that involve the Apply project
 */
@Controller
public class SystemController {

    @PostMapping("/system")
    @ApiOperation(value = "DESCRIPTION TEST", response = ResponseWithMetadata.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = "{\"createdAt\": \"2021-04-04T04:35:00.524082200Z\",\"status\": \"success\",\"result\": \"email\"}"))),
            @ApiResponse(code = 404, message = "Demand does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<ResponseWithMetadata> updateSystem(@RequestBody UserDto userDto){
        ResponseWithMetadata response = new ResponseWithMetadata("success", userDto.getUser());
        return new ResponseEntity(response, HttpStatus.CREATED);
        //return (ResponseEntity<String>) new ResponseEntity("Work in progress", HttpStatus.OK);
    }

    @GetMapping("/system")
    public ResponseEntity<String> getSystem(){
        return (ResponseEntity<String>) new ResponseEntity("Work in progress", HttpStatus.OK);
    }

}
