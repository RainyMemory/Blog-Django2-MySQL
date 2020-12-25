package com.sail.exp.freevoteapp.data.httpapi;

import android.util.Log;

import com.google.gson.Gson;
import com.sail.exp.freevoteapp.data.util.Const;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestGenerator {

    /**
     * Set up the connection for the target url using target method
     * @param url define the target url
     * @param requestType mark the method type: GET, POST, PATCH, PUT, DELETE
     * @return the fully initialed connection
     */
    private static HttpURLConnection getRequestHeader(String url, String requestType) {
        try {
            // Define the target url that we want to have access to.
            URL tarUrl = new URL(url.trim());
            HttpURLConnection connection = (HttpURLConnection) tarUrl.openConnection();
            connection.setRequestMethod(requestType);
            connection.setConnectTimeout(Const.TIMEOUT);
            connection.setReadTimeout(Const.TIMEOUT);
            // Check if we are using GET method.
            if (requestType.equals(Const.GET)) {
                return connection;
            } else {
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty(Const.CHARSET, Const.ENCODE);
                connection.setRequestProperty(Const.CONT_TYPE, Const.MSG_FORMAT);
                return connection;
            }
        } catch (Exception e) {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    private static String readResponse(InputStream reader) throws UnsupportedEncodingException {
        BufferedReader loader = new BufferedReader(new InputStreamReader(reader, Const.ENCODE));
        StringBuilder response = new StringBuilder();
        String line;
        // Read all the responses sent back from the server. Should be a Json string.
        try {
            while ((line = loader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            Log.i(Const.IO_EXP_L, Const.ERR_MESSAGE + e.getMessage());
        } finally {
            try {
                loader.close();
            } catch (Exception e) {
                Log.i(Const.IO_EXP_L, Const.ERR_MESSAGE + e.getMessage());
            }
        }
        return response.toString();
    }

    /**
     * GET_TAR_USER
     * This method is designed to handle the user login activity
     * @param email as pk
     * @return Json string describes the user
     */
    public static String getTargetUser(String email) {
        String response = "";
        String requestType = Const.GET;
        String url = Const.URL_PRE + Const.USER_URL + email.substring(0, email.indexOf('.'));
        // Get the connection set up properly.
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Connect to the target url.
                connection.connect();
                InputStream receiveReader = connection.getInputStream();
                // Read out the response Json string.
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * PAT_TAR_USER
     * Define how user change his/her profile
     * @param url define which user should be adjusted
     * @param jsonParam use PATCH method, contents the partial info of the user that need to be undated
     * @return Json string describes the updated user profile
     */
    public static String patchTargetUser(String url, String jsonParam) {
        String response = "";
        url = Const.URL_PRE + Const.USER_URL + url + "/";
//        jsonParam = "{\n" +
//                "    \"userName\": \"summer\",\n" +
//                "    \"userPhone\": \"34356677\",\n" +
//                "    \"userEmail\": \"8785673@8mail.com\"\n" +
//                "}";
        String requestType = Const.PATCH;
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Configure the set up for the jsonParam.
                connection.setRequestProperty(Const.CONT_LEN, String.valueOf(jsonParam.length()));
                connection.connect();

                // Put the jsonParam into the request.
                DataOutputStream dataLoader = new DataOutputStream(connection.getOutputStream());
                dataLoader.writeBytes(jsonParam);
                dataLoader.flush();
                dataLoader.close();

                InputStream receiveReader = connection.getInputStream();
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * REG_NEW_USER
     * User registration
     * @param jsonParam describe the user's base information
     * @return a Json string describes the user
     */
    public static String createNewUser(String jsonParam) {
        String response = "";
        String url = Const.URL_PRE + Const.USER_URL;
//        jsonParam = "{" +
//                "        \"userName\": \"NewUser_007\",\n" +
//                "        \"passWord\": \"23445678\",\n" +
//                "        \"userEmail\": \"878567333@Tmails.com\",\n" +
//                "        \"userPhone\": \"34356672709\",\n" +
//                "        \"birth\": \"1989-04-22\",\n" +
//                "        \"education\": \"master\",\n" +
//                "        \"regDate\": \"2020-04-08\"" +
//                "}";
        String requestType = Const.POST;
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Configure the set up for the jsonParam.
                connection.setRequestProperty(Const.CONT_LEN, String.valueOf(jsonParam.length()));
                connection.connect();

                // Put the jsonParam into the request.
                DataOutputStream dataLoader = new DataOutputStream(connection.getOutputStream());
                dataLoader.writeBytes(jsonParam);
                dataLoader.flush();
                dataLoader.close();

                InputStream receiveReader = connection.getInputStream();
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * CRE_NEW_VOTE
     * Create a new vote, the start date will be init as the create date.
     * @param jsonParam describe the details of the vote
     * @return the Json string describes the vote
     */
    public static String createNewVote(String jsonParam) {
        String response = "";
        String url = Const.URL_PRE + Const.VOTE_URL;
        String requestType = Const.POST;
//        jsonParam = "{\n" +
//                "    \"starterId\": \"3\",\n" +
//                "    \"starterEmail\": \"8785673@8mail.com\",\n" +
//                "    \"endDate\": \"2020-05-30\",\n" +
//                "    \"contentJson\": \"[{\\\"Question\\\":\\\"Test question 1\\\",\\\"Options\\\":{\\\"dafdsaf\\\",\\\"dafdsafd\\\",\\\"dafdasgewqr\\\"}}]\"\n" +
//                "}";
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Configure the set up for the jsonParam.
                connection.setRequestProperty(Const.CONT_LEN, String.valueOf(jsonParam.length()));
                connection.connect();

                // Put the jsonParam into the request.
                DataOutputStream dataLoader = new DataOutputStream(connection.getOutputStream());
                dataLoader.writeBytes(jsonParam);
                dataLoader.flush();
                dataLoader.close();

                InputStream receiveReader = connection.getInputStream();
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * MOD_TAR_VOTE
     * Modify or stop a vote
     * @param voteKey mark the target vote that need to be updated
     * @param jsonParam the partial information that need to be changed
     * @return a Json string describes the current vote
     */
    public static String patchVote(String voteKey, String jsonParam) {
        String response = "";
        String url = Const.URL_PRE + Const.VOTE_URL + "Ekbd20GTsz08";
//        jsonParam = "{\n" +
//                "    \"starterId\": \"3\",\n" +
//                "    \"starterEmail\": \"8785673@8mail.com\",\n" +
//                "    \"endDate\": \"2020-09-30\",\n" +
//                "    \"contentJson\": \"[{\\\"Question\\\":\\\"Test question 1\\\",\\\"Options\\\":{\\\"dafdsaf\\\",\\\"dafdsafd\\\",\\\"dafa???Dafdsa\\\"}}]\"\n" +
//                "}";
        String requestType = Const.PATCH;
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Configure the set up for the jsonParam.
                connection.setRequestProperty(Const.CONT_LEN, String.valueOf(jsonParam.length()));
                connection.connect();

                // Put the jsonParam into the request.
                DataOutputStream dataLoader = new DataOutputStream(connection.getOutputStream());
                dataLoader.writeBytes(jsonParam);
                dataLoader.flush();
                dataLoader.close();

                InputStream receiveReader = connection.getInputStream();
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * GET_TAR_VOTE
     * Search for the target vote.
     * @param voteKey mark the target vote that should be retrieved
     * @return a Json string describes the current vote
     */
    public static String getTarVote(String voteKey) {
        String response = "";
        String url = Const.URL_PRE + Const.VOTE_URL + voteKey;
        String requestType = Const.GET;
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Connect to the target url.
                connection.connect();
                InputStream receiveReader = connection.getInputStream();
                // Read out the response Json string.
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * SUB_NEW_RES
     * Submit the vote result to the server, same user can only submit once.
     * @param jsonParam the info of the result
     * @return a Json string describes the result
     */
    public static String submitVote(String jsonParam) {
        String response = "";
        String url = Const.URL_PRE + Const.RES_URL;
        String requestType = Const.POST;
        HttpURLConnection connection = getRequestHeader(url, requestType);
//        jsonParam = "{\n" +
//                "    \"userId\": \"2\",\n" +
//                "    \"userEmail\": \"8780556@qwr.com\",\n" +
//                "    \"voteId\": \"2\",\n" +
//                "    \"voteKey\": \"ZtmkCWlV\",\n" +
//                "    \"voteRes\": \"[\\\"Choices\\\":{\\\"dafdsaf\\\",\\\"dafdsafd\\\",\\\"dafdasgewqr\\\"}]\"\n" +
//                "}";
        if (null != connection) {
            try {
                // Configure the set up for the jsonParam.
                connection.setRequestProperty(Const.CONT_LEN, String.valueOf(jsonParam.length()));
                connection.connect();

                // Put the jsonParam into the request.
                DataOutputStream dataLoader = new DataOutputStream(connection.getOutputStream());
                dataLoader.writeBytes(jsonParam);
                dataLoader.flush();
                dataLoader.close();

                InputStream receiveReader = connection.getInputStream();
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

    /**
     * GET_VOTE_RES
     * This is a GET method and the jsonParam will not be send to the backend directly
     * All the params in the json string will be put into the query_params of the GET method
     * This function returns all the valid results for the query.
     * @param voteKey mark the target vote whose results will be retrieved
     * @param jsonParam describes the params that need to be put in the GET query_params
     * @return a Json array string contents all the results that satisfy the query conditions
     */
    public static String checkVoteResult(String voteKey, String jsonParam) {
        String response = "";
        String url = Const.URL_PRE + Const.RES_URL + voteKey + "/";
        String requestType = Const.GET;
//        jsonParam = "{\n" +
//                "    \"userId\": \"2\"\n" +
//                "}";
        Gson gson = new Gson();
        Map paramMap = gson.fromJson(jsonParam, Map.class);
        if (null != paramMap && !paramMap.isEmpty()) {
            url += "?";
            for (Object key : paramMap.keySet()) {
                // Set the param to the connection
                url += key.toString() + "=" + paramMap.get(key);
            }
        }
        HttpURLConnection connection = getRequestHeader(url, requestType);
        if (null != connection) {
            try {
                // Connect to the target url.
                connection.connect();
                InputStream receiveReader = connection.getInputStream();
                // Read out the response Json string.
                response = readResponse(receiveReader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return response;
        } else {
            Log.i(Const.UNEXPECTED_L, Const.HTTP_ERROR);
            return null;
        }
    }

}
