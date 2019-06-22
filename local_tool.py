#!/usr/bin/env python3
# -*- coding: utf-8 -*-

'''
本地执行命令工具
'''

import subprocess


class LocalTool(object):
    def __init__(self):
        pass

    def package_java(self):
        if self.run_command("gradle clean") != 0:
            print("clean error")
            return False
        if self.run_command("gradle build -x test") != 0:
            print("build error")
            return False
        return True

    def run_command(self, commons):
        '''
        commons 是字符串(命令)数组
        '''
        print(commons)
        retcode = subprocess.call(commons, shell=True)
        print(retcode)
        return retcode
