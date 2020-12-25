package com.sail.exp.freevoteapp.data.util;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class HttpReqThreadTest {

    // Set up the thread executor
    ExecutorService executor;
    // Init the callable thread
    HttpReqThread thread;

    String param = "114514@yyut.com";

    Gson gson = new Gson();

    @Before
    public void SetUpTest() {
        executor = Executors.newCachedThreadPool();
    }

    @Test
    public void testUser() throws ExecutionException, InterruptedException {
        thread = new HttpReqThread(param, Const.GET_TAR_USER);
        Future<String> future = executor.submit(thread);
        executor.shutdown();
        String response = future.get();
        Map<String, String> responseMap = gson.fromJson(response, Map.class);
        assertTrue(responseMap.get("userName").equals("yyut"));
    }

}