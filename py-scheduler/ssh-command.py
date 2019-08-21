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
count = 0
stdin, stdout, stderr = ssh.exec_command('ps -ef | grep activemq')
for line in stdout.read().splitlines():
    line = line.decode('utf-8')
    if line.find('ps -ef') >= 0 or line.find('grep') >= 0:
        continue
    count += 1
    print(line)

if count > 0:
    print("activemq process is alive")

print("\n\n")
print("==================")
print("check whether messaging port is listening.... (inbound)")
print("==================")

"""
COMMAND     PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
"""
stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61614 | grep LISTEN')
l_stdout = stdout.read().splitlines()

if len(l_stdout) > 0:
    for line in l_stdout:
        line = line.decode('utf-8')
        print(line)
    print("activemq port is listening... OK")
else:
    print("activemq port is NOT OK")


print("\n\n")
print("==================")
print("check whether messaging port outbound alive.... (outbound)")
print("==================")

"""
COMMAND     PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
"""
stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61614 | grep ESTABLISHED')
l_stdout = stdout.read().splitlines()

if len(l_stdout) > 0:
    for line in l_stdout:
        line = line.decode('utf-8')
        print(line)
    print("There are established activemq ports, status is OK")
else:
    print("There is no established activemq port")


ssh.close()
