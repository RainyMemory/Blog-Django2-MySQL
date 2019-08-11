from django.db import models


# Create your models here.

class Contact(models.Model):
    name = models.CharField(max_length=200, verbose_name='测试名')
    age = models.IntegerField(default=0, verbose_name='测试数')
    email = models.EmailField(verbose_name='测试邮箱')

    def __str__(self):
        return str(self.name) + "\t" + str(self.age) + "\t" + str(self.email)


class User(models.Model):
    nickname = models.CharField(max_length=20, blank=False, unique=True, verbose_name='昵称')
    birth = models.DateField(verbose_name='出生日期')
    phone = models.CharField(max_length=15, verbose_name='电话号码')
    email = models.EmailField(verbose_name='电子邮箱')
    status = models.BooleanField(verbose_name='账户状态')
    info = models.TextField(blank=True, verbose_name='个人说明')
    user_name = models.CharField(max_length=12, verbose_name='用户名')
    password = models.CharField(max_length=20, verbose_name='密码')
    role = models.BooleanField(verbose_name='VIP状态')
    balance = models.BigIntegerField(default=0, blank=False, verbose_name='VIP剩余时间')


class Passage(models.Model):
    title = models.CharField(max_length=40, blank=False, verbose_name='标题')
    author = models.CharField(max_length=20, verbose_name='作者')
    subscribe = models.CharField(max_length=60, blank=True, verbose_name='描述')
    time = models.DateField(auto_now=True, verbose_name='发表时间')
    pic_no = models.IntegerField(default=1, verbose_name='图片编号')
    context = models.TextField(verbose_name='文章内容')
