#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os

import paramiko
'''
ssh工具列类
'''


class RemoteTool(object):
    """工具类"""

    def __init__(self, keypath):
        # 记录操作日志
        paramiko.util.log_to_file("paramiko.log")
        if keypath is not None:
            self.key = paramiko.RSAKey.from_private_key_file(keypath)

    # 上传单个文件
    def upload_one_file(self, sftp, from_path, to_path):
        '''
            port: 端口 int型
        '''
        print(from_path + "==>" + to_path)
        if (os.path.isdir(from_path)):
            return
        sftp.put(from_path, to_path, callback=putback)
        sftp.chmod(to_path, 0o755)

    # 上传多个文件
    def upload_more_file(self, sftp, from_path, to_path):
        if not os.path.isdir(from_path):
            print("the path must be a dir")
            return
        files = os.listdir(from_path)
        for file in files:
            f_path = from_path + "/" + file
            t_path = to_path + "/" + file
            if os.path.isdir(f_path):
                self.create_dir_if_not_exits(sftp, t_path)
                self.upload_more_file(sftp, f_path, t_path)
            self.upload_one_file(sftp, f_path, t_path)

    # 连接到服务器 返回当前session
    def connect_server(self, host, port, username, password):
        ssh_client = paramiko.SSHClient()
        # 信任主机
        ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        if self.key is None:
            ssh_client.connect(
                hostname=host,
                port=port,
                username=username,
                password=password,
                timeout=60)
        else:
            print('ssh key connect')
            ssh_client.connect(
                hostname=host,
                port=port,
                username=username,
                password=paramiko,
                pkey=self.key,
                timeout=60)

        return ssh_client

    # 连接到文件服务器
    def connect_file_server(self, host, port, username, password):
        transport = paramiko.Transport(host, port)
        if self.key is None:
            transport.connect(username=username, password=password)
        else:
            print('sftp key connect')
            transport.connect(
                username=username, password=password, pkey=self.key)
        sftp = paramiko.SFTPClient.from_transport(transport)
        return transport, sftp

    def exec_command(self, ssh_client, command, show_line=100):
        '''
        执行ssh命令, 并作输出
        Args:
            ssh_client: ssh连接对象
            command: 需要执行的命令
            show_line: 读取的行数
        Returns:
            返回数据行数
        Raises:
            无异常
        '''
        total_line = 0
        print("命令： " + command, end="\n")
        _, stdout, _ = ssh_client.exec_command(command)
        for line in stdout:
            print(line, end='')
            total_line += 1
        return total_line >= show_line

    def del_remote_dir(self, ssh_client, path):
        '''
        删除远程服务器上的文件
        '''
        self.exec_command(ssh_client, "rm -rf " + path)

    def create_dir_if_not_exits(self, sftp, path):
        try:
            sftp.stat(path)
        except IOError:
            try:
                sftp.mkdir(path, 0o755)
            except Exception as e:
                print(e)


def putback(put_size, file_size):
    '''
    上传进度回调
    Args:
        put_size: 上传了的文件大小
        file_size: 文件总大小
    '''
    progress = put_size / file_size * 100
    print('上传进度: {:0.3f}'.format(progress) + '%', end='\r', flush=True)
