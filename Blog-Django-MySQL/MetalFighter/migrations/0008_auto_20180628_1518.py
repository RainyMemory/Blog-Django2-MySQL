# Generated by Django 2.0.6 on 2018-06-28 15:18

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('MetalFighter', '0007_auto_20180628_1341'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='info',
            field=models.TextField(blank=True, verbose_name='个人说明'),
        ),
    ]
