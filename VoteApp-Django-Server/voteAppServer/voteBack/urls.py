from django.conf.urls import url, include 
from rest_framework import routers
from voteBack import views

# use router to register the app's urls
router = routers.DefaultRouter()
router.register(r'users', views.userViewSet)
router.register(r'votes', views.voteViewSet)
router.register(r'results', views.resultViewSet)

urlpatterns = [
    url(r'^', include(router.urls))
]