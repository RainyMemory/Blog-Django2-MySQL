from django.shortcuts import render
from rest_framework import renderers
from rest_framework import viewsets
from rest_framework.decorators import action
from voteBack.models import *
from voteBack.serializers import *
from rest_framework.response import Response
from voteBack.utils import *
from voteBack.const import *
from urllib import parse
import datetime


# Create your views here.
class userViewSet(viewsets.ModelViewSet):
    queryset = userProfile.objects.all()
    serializer_class = userSerializer

    # Rewrite retrieve function, we use email address to find target user
    def retrieve(self, request, pk=None):
        queryset = userProfile.objects.all()
        pk = pk + EMAIL_SUF
        tarUser = None
        for user in queryset:
            if pk == user.userEmail:
                tarUser = user
        if tarUser != None:
            serializer = userSerializer(tarUser)
            return Response(serializer.data)
        return Response(getErrorMsg(LOGIN_ERR))

class voteViewSet(viewsets.ModelViewSet):
    queryset = voteProfile.objects.all()
    serializer_class = voteSerializer

    # Rewrite retrieve function, find vote via voteKey or user id
    def retrieve(self, request, pk=None):
        if len(pk) != 8:
            queryset = voteProfile.objects.filter(starterId=pk)
            serializer = voteSerializer(queryset, many=True)
            return Response(serializer.data)
        else :
            queryset = voteProfile.objects.all()
            tarVote = None
            for vote in queryset:
                if pk == vote.voteKey:
                    tarVote = vote
            if tarVote != None:
                # If the vote is already closed, notice the user.
                if tarVote.endDate < datetime.date.today():
                    return Response(getErrorMsg(VOTE_OUT_DATE))
                serializer = voteSerializer(tarVote)
                return Response(serializer.data)
            return Response(getErrorMsg(VOTE_NOT_FOUND))

class resultViewSet(viewsets.ModelViewSet):
    queryset = userVote.objects.all()
    serializer_class = resultSerializer

    # Rewrite retrieve function, use vote key 
    def retrieve(self, request, pk=None):
        if len(pk) != 8:
            queryset = userVote.objects.filter(userId=pk)
            serializer = resultSerializer(queryset, many=True)
            return Response(serializer.data)
        else:
            queryset = userVote.objects.filter(voteKey=pk)
            userId = request.query_params.get(GET_RES_PARA_USERID)
            # If we got userId as param, we apply it to the search
            if userId != "" and userId != None:
                queryset = queryset.filter(userId=userId)
            if len(queryset) == 0 or queryset is None:
                return Response(getErrorMsg(NO_VOTE_RESULT))
            serializer = resultSerializer(queryset, many=True)
            return Response(serializer.data)