from django.shortcuts import render, get_list_or_404, get_object_or_404, render_to_response
from django.http import HttpResponse, HttpResponseRedirect
from django.urls import reverse
from django.core import serializers
from django.db.models import Q
from . import models, forms
from .forms import UserForm, RegisterForm
import json


# Create your views here.

def index(request):
    return HttpResponseRedirect('/MetalFighter/queryInfo')


def platform(request):
    tid = request.POST['test']
    target = models.Contact.objects.get(id=tid)
    print(target)
    tarList = []
    tarList.append(target)
    info = {
        'answerPhone': tarList
    }
    return render(request, 'MetalFighter/SQLSHOW.html', info)


def login(request):
    # 如果是登录功能
    if request.method == 'POST':
        # uf = UserForm(request)
        # 根据值确认表单是否有错
        # if uf.is_valid():
        #     username = uf.cleaned_data['loginUser']
        #     password = uf.cleaned_data['loginPwd']
        username = request.POST['loginUser']
        password = request.POST['loginPwd']
        # return HttpResponse('GSS')
        user = models.User.objects.filter(user_name=username, password=password)
        users = serializers.serialize('json', user)
        if len(users) != 2:
            # 10 将users 传递到前端请求
            request.session['username'] = users
            request.session['has'] = 1
            # 11 设置该session的有效时长
            request.session.set_expiry(6000)
            return HttpResponseRedirect('/MetalFighter/queryInfo')
        else:
            return render(request, 'MetalFighter/login.html', {

            })
    else:
        return render(request, 'MetalFighter/login.html', {

        })


def logout(request):
    try:
        del request.session['username']
        request.session['has'] = 0
    except KeyError:
        pass
    return HttpResponseRedirect('/MetalFighter/queryInfo')


def recharge(request):
    if request.method == 'POST':
        # user = models.User.objects.filter(user_name='ppp', password='ppp')
        # users = serializers.serialize('json', user)
        # request.session['username'] = users
        ss = request.session['username']
        if ss is not None and ss != '':
            sss = ss.strip('[')
            sss = sss.strip(']')
            str = json.loads(sss)
            # print(str['fields']['nickname'])
            charge = int(request.POST['charge'])
            print(charge)
            print(str['fields']['balance'] + charge)
            models.User.objects.filter(user_name=str['fields']['user_name']).update(
                balance=(str['fields']['balance'] + charge), role=True)
            return HttpResponseRedirect('/MetalFighter/queryInfo')
        else:
            return render(request, 'MetalFighter/login.html', {

            })
    else:
        print('傻逼')
        return render(request, 'MetalFighter/member.html', {
            'username':'none'
        })


def register(request):
    # 如果是注册功能
    if request.method == 'POST':
        nickname = request.POST['nickname']
        birth = request.POST['birth']
        phone = request.POST['phone']
        email = request.POST['email']
        info = request.POST['info']
        user_name = request.POST['user_name']
        password = request.POST['password']
        print(user_name, password)
        user = models.User.objects.filter(user_name=user_name)
        user1 = models.User.objects.filter(nickname=nickname)
        user2 = models.User.objects.filter(phone=phone)
        print(len(user))
        if len(user) == 0 and len(user1) == 0 and len(user2) == 0:
            # 9 重定向到index.html
            create = models.User.objects.create(nickname=nickname, birth=birth, phone=phone, email=email, status=0,
                                                info=info, user_name=user_name, password=password, role=0, balance=0)
            print(type(create), create)
            return HttpResponseRedirect('/MetalFighter/queryInfo')
        else:
            return render(request, 'MetalFighter/register.html', {

            })
    else:
        return render(request, 'MetalFighter/register.html', {

        })


def show_passage(request, question_id):
    pid = question_id
    targetPassage = models.Passage.objects.get(id=pid)
    print(targetPassage.context)
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
        username = str['fields']['user_name']
    except KeyError:
        username = "none"

    backInfo = {
        'title': targetPassage.title,
        'author': targetPassage.author,
        'time': targetPassage.time,
        'subscribe': targetPassage.subscribe,
        'context': targetPassage.context,
        'username': username
    }
    return render(request, 'MetalFighter/article.html', backInfo)


def is_vip(nickname):
    user = models.User.objects.get(nickname=nickname)
    if user is not None and user.role is True:
        return True
    else:
        return False


def sort_cmp(passage):
    if is_vip(passage.author):
        return -1
    else:
        return 1


def query_passage(request):
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
        username = str['fields']['user_name']
    except KeyError:
        username = "none"
    if request.method == 'POST':
        queryInfo = request.POST['queryInfo']
        passageList = models.Passage.objects.filter(Q(title__icontains=queryInfo) | Q(author__icontains=queryInfo) |
                                                    Q(subscribe__icontains=queryInfo) | Q(context__icontains=queryInfo))
        passageList = list(passageList)
        passageList.sort(key=lambda passage: sort_cmp(passage))
        backInfo = {
            'passages': passageList,
            'username': username,
        }
        # return render(request, 'MetalFighter/234.html', backInfo)
        return HttpResponse('Good')
    else:
        passageList = models.Passage.objects.all()
        passageList = list(passageList)
        passageList.sort(key=lambda passage: sort_cmp(passage))
        backInfo = {
            'passages': passageList,

            'username': username,
        }
        return render(request, 'MetalFighter/main.html', backInfo)


def own_passage(request):
    # user = models.User.objects.filter(user_name='ppp', password='ppp')
    # users = serializers.serialize('json', user)
    # request.session['username'] = users
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
        author = str['fields']['nickname']
        username = str['fields']['user_name']
    except KeyError:
        username = "none"
        author = ''
    if author is not '':
        passageList = models.Passage.objects.filter(author=author)
        passageList = list(passageList)
        for pas in passageList:
            print(pas.context)
        backInfo = {
            'passages': passageList,
            'username': username,
        }
        return render(request, 'MetalFighter/myblog.html', backInfo)
    else:
        return HttpResponseRedirect('/MetalFighter/queryInfo')


def write_blog(request):
    if request.method == 'POST':
        # user = models.User.objects.filter(user_name='ppp', password='ppp')
        # users = serializers.serialize('json', user)
        # request.session['username'] = users
        try:
            ss = request.session['username']
            sss = ss.strip('[')
            sss = sss.strip(']')
            str = json.loads(sss)
            author = str['fields']['nickname']
            print (str['fields']['nickname'])
            if author is not '':
                blog = forms.BlogForm(request.POST)
                if request.POST['context'] is not '':
                    subscr = ''
                    contex = request.POST['context']
                    if len(contex) < 30:
                        subscr = contex
                    else:
                        subscr = contex[0:30]
                models.Passage.objects.create(title=request.POST['title'], author=author,
                                              subscribe=subscr,
                                              pic_no=2, context=request.POST['context'])
                return HttpResponseRedirect('/MetalFighter/authorWork')
        except KeyError:
            return HttpResponseRedirect('/MetalFighter/queryInfo')
    else:
        return render(request, 'MetalFighter/write.html', {
        })



def pay_30(request):
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
    except KeyError:
        return HttpResponseRedirect('/MetalFighter/queryInfo')

    return render(request, 'MetalFighter/pay30.html', {

    })


def pay_90(request):
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
    except KeyError:
        return HttpResponseRedirect('/MetalFighter/queryInfo')

    return render(request, 'MetalFighter/pay70.html', {

    })


def pay_180(request):
    try:
        ss = request.session['username']
        sss = ss.strip('[')
        sss = sss.strip(']')
        str = json.loads(sss)
    except KeyError:
        return HttpResponseRedirect('/MetalFighter/queryInfo')

    return render(request, 'MetalFighter/pay200.html', {

    })
