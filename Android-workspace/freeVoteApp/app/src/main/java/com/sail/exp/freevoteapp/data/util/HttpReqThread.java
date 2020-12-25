package com.sail.exp.freevoteapp.data.util;

import com.sail.exp.freevoteapp.data.httpapi.RequestGenerator;

import java.util.concurrent.Callable;

public class HttpReqThread implements Callable<String> {

    // Store the params append after the urls as the pk value, during some POST methods this may be null
    private String param;
    // Mark the target function
    private String tarFunc;
    // The Json param list
    private String jsonParam;

    public HttpReqThread(String param, String tarFunc) {
        this.param = param;
        this.tarFunc = tarFunc;
    }

    public HttpReqThread(String param, String tarFunc, String jsonParam) {
        this.param = param;
        this.tarFunc = tarFunc;
        this.jsonParam = jsonParam;
    }

    public void setJsonParam(String jsonParam) {
        this.jsonParam = jsonParam;
    }

    @Override
    public String call() throws Exception {
        String responseJson = "";
        if (this.tarFunc.equals(Const.GET_TAR_USER)) {
            responseJson = RequestGenerator.getTargetUser(this.param);
        } else if (this.tarFunc.equals(Const.PAT_TAR_USER)) {
            responseJson = RequestGenerator.patchTargetUser(this.param, this.jsonParam);
        } else if (this.tarFunc.equals(Const.REG_NEW_USER)) {
            responseJson = RequestGenerator.createNewUser(this.jsonParam);
        } else if (this.tarFunc.equals(Const.CRE_NEW_VOTE)) {
            responseJson = RequestGenerator.createNewVote(this.jsonParam);
        } else if (this.tarFunc.equals(Const.MOD_TAR_VOTE)) {
            responseJson = RequestGenerator.patchVote(this.param, this.jsonParam);
        } else if (this.tarFunc.equals(Const.GET_TAR_VOTE)) {
            responseJson = RequestGenerator.getTarVote(this.param);
        } else if (this.tarFunc.equals(Const.SUB_NEW_RES)) {
            responseJson = RequestGenerator.submitVote(this.jsonParam);
        } else if (this.tarFunc.equals(Const.GET_VOTE_RES)) {
            responseJson = RequestGenerator.checkVoteResult(this.param, this.jsonParam);
        }
        Thread.sleep(Const.TR_SLEEP);
        return responseJson;
    }
}
