package com.netophonix.core.controller;

import com.netophonix.core.exception.ForbiddenException;
import com.netophonix.core.scrapper.SagaScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    @Value("${com.netophonix.core.adminpassword}")
    private String adminPassword;

    @Autowired
    private SagaScrapper sagaScrapper;

    @Autowired
    private TaskExecutor taskExecutor;

    @RequestMapping(value = "/api/sync/sagas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, params = {"password"})
    public void syncSagas(@RequestParam String password) {
        if(adminPassword.equals(password)) {
            taskExecutor.execute(() -> sagaScrapper.scrap());
        } else {
            throw new ForbiddenException();
        }
    }

}
