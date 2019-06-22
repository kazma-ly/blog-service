#!/usr/bin/env python3
# -*- coding: UTF-8 -*-
'''
上传SpringBoot项目,并运行的脚本
'''

from remote_tool import RemoteTool
from local_tool import LocalTool
import requests
import os


def main():
    env = os.environ
    host = env.get('SERVER_DOMAIN')
    port = 22
    username = env.get('SERVER_USERNAME')
    password = None  # env.get('SERVER_PASSWORD')

    tool = RemoteTool(r'D:/asw-key.pem')
    # 打包java项目
    localTool = LocalTool()
    isOk = localTool.package_java()
    if not isOk:
        print("请检查java打包")
        return

    # 优雅停止SpringBoot老项目
    try:
        requests.post("http://" + host + ":1331/actuator/shutdown")
    except Exception as e:  # 有可能服务已经关掉了
        print(e)

    transport, sftp = tool.connect_file_server(host, port, username, password)
    # 检查远程是否有这个目录
    tool.create_dir_if_not_exits(sftp, '/home/ubuntu/blog/server')
    tool.upload_one_file(sftp, './build/libs/blog-service-4.0.0.jar', '/home/ubuntu/blog/server/blog.jar')

    # 连接到服务器 返回当前session
    ssh_client = tool.connect_server(host, port, username, password)
    # 发送命令
    tool.exec_command(ssh_client, 'java -jar /home/ubuntu/blog/server/blog.jar --spring.profiles.active=pro')

    # 释放资源
    transport.close()
    ssh_client.close()


if __name__ == '__main__':
    main()
