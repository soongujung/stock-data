import paramiko
from connections.connection_info import busan_toc_gw
# import connections.connection_info

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect(hostname=busan_toc_gw['hostname'],
            username=busan_toc_gw['username'],
            password=busan_toc_gw['password'],
            port=busan_toc_gw['port'])

print("==================")
print("connection success ...")
print("==================")


print("\n\n")
print("==================")
print("check whether messaging process alive .... ")
print("==================")

"""
UID     PID     PPID    C   STIME   TTY     CMD
root    1       0       0   2018    ?       /usr/local/java/bin/java Xms512M Xmx1G ...... xxx.jar start
"""
stdin, stdout, stderr = ssh.exec_command('ps -ef | grep activemq')
for line in stdout.read().splitlines():
    print(line)

print("\n\n")
print("==================")
print("check whether messaging port is listening.... (inbound)")
print("==================")

"""
COMMAND     PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
"""
stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61614 | grep LISTEN')
for line in stdout.read().splitlines():
    print(line)


print("\n\n")
print("==================")
print("check whether messaging port outbound alive.... (outbound)")
print("==================")

"""
COMMAND     PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
"""
stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61614')
for line in stdout.read().splitlines():
    print(line)

ssh.close()
