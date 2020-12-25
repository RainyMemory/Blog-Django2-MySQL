import datetime
import random
import string

def getErrorMsg(msg):
    errorMsg = {
        'ResponseCode':'696', 
        'ResponseMessage':msg
    }
    return errorMsg

# usa current time as seed to generate different strings
def generateVoteKey():
    random.seed(datetime.datetime.now().timestamp())
    voteKey = ''.join(random.sample(string.ascii_letters + string.digits, 8))
    return voteKey