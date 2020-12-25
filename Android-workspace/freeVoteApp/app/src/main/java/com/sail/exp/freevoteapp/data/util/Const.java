package com.sail.exp.freevoteapp.data.util;

public class Const {

    // Http methods
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    // Time limitation
    public static final int TIMEOUT = 3000;
    public static final int TR_SLEEP = 500;

    // Json message handler
    public static final String ENCODE = "UTF-8";
    public static final String CHARSET = "Charset";
    public static final String CONT_TYPE = "Content-Type";
    public static final String MSG_FORMAT = "application/json";
    public static final String CONT_LEN = "Content-Length";
    public static final String SERVER_EXP = "696";
    public static final String RSP_FLAG = "ResponseCode";
    public static final String RSP_MSG = "ResponseMessage";
    public static final String ID_KEY = "id";
    public static final String DJANGO_KEY = "django";
    public static final String VISITOR_ID = "114514";
    public static final String VISITOR_EMAIL = "114514@XBZ.com";

    // Sugar queries
    public static final String QRY_ID = "django = ?";

    // Android logger labels & messages
    public static final String UNEXPECTED_L = "Unexpected";
    public static final String IO_EXP_L = "IO Exception";
    public static final String HTTP_ERROR = "HttpURLConnection Error";
    public static final String ERR_MESSAGE = "readResponse: ";
    public static final String LOG_FAIL = "Login failed, please try again.";
    public static final String ERR_VOTEKEY = "The vote is closed or now invalid, please try again.";
    public static final String MUL_USERVOTE = "You have already finish the vote.";
    public static final String REG_FAIL = "Please check if the info is valid.";

    // URLs
    public static final String URL_PRE = "http://10.0.2.2:8082/";
    public static final String USER_URL = "users/";
    public static final String VOTE_URL = "votes/";
    public static final String RES_URL = "results/";

    // Methods
    public static final String GET_TAR_USER = "getTargetUser";
    public static final String PAT_TAR_USER = "patchTargetUser";
    public static final String REG_NEW_USER = "registerNewUser";
    public static final String CRE_NEW_VOTE = "createNewVote";
    public static final String MOD_TAR_VOTE = "modifyTargetVote";
    public static final String GET_TAR_VOTE = "getTargetVote";
    public static final String SUB_NEW_RES = "submitNewResult";
    public static final String GET_VOTE_RES = "getVoteResults";
//    public static final String
//    public static final String

    // Intent put extra labels
    public static final String RES_STR = "responseStr";

}
