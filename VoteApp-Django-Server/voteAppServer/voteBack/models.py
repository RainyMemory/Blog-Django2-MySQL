from django.db import models
import datetime
from voteBack.utils import *

# Create your models here.

# Stroe user data 
# userName: the nickname of the usser
# userEmail: use to identify the specific user, use as account name
# gender, birth, phone and education level can be adjusted by user after login into the app
class userProfile(models.Model):
    userName = models.CharField(max_length=30, unique=False, null=False, default='NewUser')
    passWord = models.CharField(max_length=50, null=False)
    userEmail = models.EmailField(unique=True, blank=True, default='')
    userPhone = models.CharField(max_length=13, unique=True, blank=True, default='')
    gender = models.NullBooleanField(null=True, blank=True)
    birth = models.DateField(default=datetime.date.today)
    education = models.CharField(max_length=50, blank=True, default='')
    regDate = models.DateField(auto_now_add=True)
    
# Store the info of the vote
# starterId & starterEmail: mark the user who starts the vote 
# voteKey: every vote has a unique voteKey to identify the vote and user can take part in the vote through the voteKey 
# contentJson: a json object that stores the question and options of the vote 
class voteProfile(models.Model):
    starterId = models.CharField(max_length=10, unique=False, null=False)
    starterEmail = models.EmailField(unique=False, blank=True, default='', null=False)
    startDate = models.DateField(auto_now_add=True)
    endDate = models.DateField()
    voteKey = models.CharField(max_length=30, blank=True, null=False, default=generateVoteKey())
    contentJson = models.CharField(max_length=3000, blank=True, default='')

# Store the voting result from a user
# userId & userEmail: record which user gives this result 
# voteId & voteKey: label this record belongs to which vote 
# votRes: a json object that stores the answers towards each questions in the vote 
class userVote(models.Model):
    userId = models.CharField(max_length=10, unique=False, null=False)
    userEmail = models.EmailField(unique=False, blank=True, default='', null=False)
    voteId = models.CharField(max_length=10, unique=False, null=False)
    voteKey = models.CharField(max_length=30, blank=True, null=False)
    resDate = models.DateField(auto_now_add=True)
    voteRes = models.CharField(max_length=3000, blank=True, default='')
    class Meta:
        unique_together = ['userEmail', 'voteKey']