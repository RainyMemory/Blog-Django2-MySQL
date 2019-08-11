'''
    本项目下的url与path信息。

'''

from django.urls import path
from django.conf.urls import url
from . import views

app_name = "MetalFighter"

urlpatterns = [
    path('login/', views.login, name='login'),
    path('quit/', views.logout, name='quit'),
    path('register/', views.register, name='register'),
    path('logout/', views.logout, name='logout'),
    path('queryInfo/', views.query_passage, name='query'),
    path('charge/', views.recharge, name='charge'),
    path('<int:question_id>/passageDetail/', views.show_passage, name='pass_detail'),
    path('authorWork/', views.own_passage, name='authorWork'),
    path('pushBlog/', views.write_blog, name='writeBlog'),
    path('', views.index, name="index"),
    path('platform/', views.platform, name="platform"),
    path('py30', views.pay_30, name="p30"),
    path('py90', views.pay_90, name="p90"),
    path('py180', views.pay_180, name="p180"),
]
