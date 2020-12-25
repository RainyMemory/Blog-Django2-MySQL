'''
    Store the forms
    Check if the figures are valid
'''

from django import forms


class BlogForm(forms.Form):
    title = forms.CharField()
    author = forms.CharField()
    subscribe = forms.CharField(required=False)
    pic_no = forms.IntegerField()
    context = forms.CharField()


class UserForm(forms.Form):
    loginUser = forms.CharField(max_length=100)
    loginPwd = forms.CharField(max_length=100)


class RegisterForm(forms.Form):
    nickname = forms.CharField(max_length=20)
    birth = forms.DateField()
    phone = forms.CharField()
    email = forms.EmailField()
    status = forms.BooleanField()
    info = forms.CharField()
    user_name = forms.CharField(max_length=12)
    password = forms.CharField(max_length=20)


class Recharge(forms.Form):
    charge = forms.CharField()
