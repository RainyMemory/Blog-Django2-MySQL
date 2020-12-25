package com.sail.exp.freevoteapp.data.service;

import android.util.Log;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.data.httpapi.ResponseHandler;
import com.sail.exp.freevoteapp.data.model.AppUser;
import com.sail.exp.freevoteapp.data.model.AppVote;
import com.sail.exp.freevoteapp.data.model.UserVote;
import com.sail.exp.freevoteapp.data.util.Const;
import com.sail.exp.freevoteapp.data.util.HttpReqThread;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AutoFillService {

    /**
     * Auto login into the app if the user info in the local database is valid
     * @return if the user info store in the local database is valid
     */
    public static boolean userAutoLogin() {
        ArrayList<AppUser> hisUsers = (ArrayList<AppUser>) AppUser.listAll(AppUser.class);
        AppUser loginUser = null;
        if (null != hisUsers && !hisUsers.isEmpty()) {
            loginUser = hisUsers.get(0);
        }
        if (null == loginUser) {
            return false;
        } else {
            Gson gson  = new Gson();
            ExecutorService executor = Executors.newCachedThreadPool();
            HttpReqThread thread = new HttpReqThread(loginUser.getUserEmail(), Const.GET_TAR_USER);
            Future<String> future = executor.submit(thread);
            executor.shutdown();
            String response = "";
            try {
                Thread.sleep(Const.TR_SLEEP);
                response = future.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (null != response && !response.equals("")) {
                Map paraMap = gson.fromJson(response, Map.class);
                response = ResponseHandler.id2Django(response);
                if (null != paraMap.get(Const.RSP_FLAG) && Const.SERVER_EXP.equals(paraMap.get(Const.RSP_FLAG))) {
                    Log.i(Const.UNEXPECTED_L, (String) paraMap.get(Const.RSP_MSG));
                    return false;
                } else {
                    AppUser retrieveUser = gson.fromJson(response, AppUser.class);
                    System.out.println(retrieveUser);
                    if (loginUser.getUserEmail().equals(retrieveUser.getUserEmail())
                            && loginUser.getPassWord().equals(retrieveUser.getPassWord())) {
                        AppUser.deleteAll(AppUser.class);
                        retrieveUser.save();
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Will return null if not logged in
     * @return the current user id
     */
    public static String getCurrentUserId() {
        ArrayList<AppUser> hisUsers = (ArrayList<AppUser>) AppUser.listAll(AppUser.class);
        if (null == hisUsers || hisUsers.isEmpty()) {
            return null;
        } else {
            return hisUsers.get(0).getDjango();
        }
    }

    /**
     * Will return null if not logged in
     * @return the current user email
     */
    public static String getCurrentUserEmail() {
        ArrayList<AppUser> hisUsers = (ArrayList<AppUser>) AppUser.listAll(AppUser.class);
        if (null == hisUsers || hisUsers.isEmpty()) {
            return null;
        } else {
            return hisUsers.get(0).getUserEmail();
        }
    }

    /**
     * Show all the votes started by the user
     * @return Votes started by the user
     */
    public static ArrayList<AppVote> getVoteList() {
        String currentUser = getCurrentUserId();
        if (null != currentUser) {
            return (ArrayList<AppVote>) AppVote.find(AppVote.class, Const.QRY_ID, currentUser);
        } else {
            return null;
        }
    }

    /**
     * Show all the results submitted by the user.
     * @return The submissions from the user
     */
    public static ArrayList<UserVote> getSubResults() {
        String currentUser = getCurrentUserId();
        if (null != currentUser) {
            return (ArrayList<UserVote>) UserVote.find(UserVote.class, Const.QRY_ID, currentUser);
        } else {
            return null;
        }
    }

}
