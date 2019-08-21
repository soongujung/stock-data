import paramiko
from connections.connection_info import busan_toc_gw
# import connections.connection_info

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect(hostname=busan_toc_gw['hostname'],
            username=busan_toc_gw['username'],
            password=busan_toc_gw['password'],
            port=busan_toc_gw['port'])

stdin, stdout, stderr = ssh.exec_command('ls -al')

for line in stdout.read().splitlines():
    print(line)

ssh.close()
