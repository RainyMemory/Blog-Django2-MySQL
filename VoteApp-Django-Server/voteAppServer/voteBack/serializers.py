from voteBack.models import *
from rest_framework import serializers

class userSerializer(serializers.ModelSerializer):
    # fields: use '__all__' to serializer all params, or use ('para1', 'para2') 
    # exclude: mark the params that should not be serialized, use ['para1', 'para2']
    
    class Meta:
        model = userProfile
        fields = '__all__'

class voteSerializer(serializers.ModelSerializer):

    class Meta:
        model = voteProfile
        fields = '__all__'

class resultSerializer(serializers.ModelSerializer):

    class Meta:
       model = userVote
       fields = '__all__'
