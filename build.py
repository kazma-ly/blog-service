#!/usr/bin/env python3
# -*- coding: UTF-8 -*-
'''
上传SpringBoot项目,并运行的脚本
'''

from remote_tool import RemoteTool
from local_tool import LocalTool
import requests
import os
import sys

LOCAL_SSH_KEY_PATH = r''
LOCAL_FILE_PATH = r'build/libs/blog-service-4.0.0.jar'
SERVER_PATH = r''
SERVER_FILE_PATH = SERVER_PATH + '/blog.jar'
EXEC_SERVER_COMMAND = r'nohup java -jar ' + SERVER_FILE_PATH + r' --spring.profiles.active=pro >/dev/null 2>&1 &'


def main():
    env = os.environ
    host = env.get('SERVER_DOMAIN')
    port = 22
    username = env.get('SERVER_USERNAME')
    password = None  # env.get('SERVER_PASSWORD')

    argv = sys.argv

    if '-h' in argv:
        print(
            'SKIP_BUILD: 跳过构建\nSKIP_SHUT_DOWN: 跳过停止SpringBoot项目\nSKIP_UPLOAD_JAR: 跳过上传jar'
        )
        return

    tool = RemoteTool(LOCAL_SSH_KEY_PATH)
    if 'SKIP_BUILD' not in argv:
        localTool = LocalTool()
        isOk = localTool.package_java()
        if not isOk:
            print("请检查java打包")
            return

    # 连接到服务器 返回当前session
    ssh_client = tool.connect_server(host, port, username, password)
    if 'SKIP_SHUT_DOWN' not in argv:
        tool.exec_command(ssh_client, 'ps aux|grep java')
        confirm = input("需要杀掉进程吗：")
        if confirm is 'yes':
            pid = input("请输入：")
            tool.exec_command(ssh_client, 'kill -9 ' + pid)

    if 'SKIP_UPLOAD_JAR' not in argv:
        transport, sftp = tool.connect_file_server(host, port, username,
                                                   password)
        # 检查远程是否有这个目录
        tool.create_dir_if_not_exits(sftp, SERVER_PATH)
        tool.upload_one_file(sftp, LOCAL_FILE_PATH, SERVER_FILE_PATH)
        transport.close()

    tool.exec_command(ssh_client, EXEC_SERVER_COMMAND)
    ssh_client.close()


if __name__ == '__main__':
    main()
