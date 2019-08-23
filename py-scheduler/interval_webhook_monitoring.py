from apscheduler.schedulers.background import BackgroundScheduler
from datetime import datetime
import time

# webhook
import requests
import json
import ssl
import urllib3
from connections.toc_slack_info import busan_toc_slack
import os

# ssh
import paramiko
from connections.connection_info import busan_toc_gw

token = busan_toc_slack['token']
room_name = busan_toc_slack['name']
incoming_webhook_url = busan_toc_slack['incoming_webhook_url']
icon_emoji = busan_toc_slack['icon_emoji']

context = ssl._create_unverified_context()
os.environ['NLS_LANG'] = '.AL32UTF8'

ssh = None
sch = BackgroundScheduler()


def send_request(interval_data):
    http = urllib3.PoolManager()
    ret = http.request("POST", incoming_webhook_url,
                       body=json.dumps(interval_data),
                       headers={'Content-Type': 'application/json'})
    # print('urllib3 result >>> ' + str(ret))


def send_request2():
    response = requests.post(
        incoming_webhook_url,
        data=json.dumps({"text": "hello, hi there~"}),
        headers={'Content-Type': 'application/json'}
    )


def interval_task():
    """
    입력 작업 종류
        1. mq process check
        2. port check (614)
        3. port check (616)
    """

    send_request({"text": "---------------------------"})
    send_request({"text": str(datetime.now().strftime("%Y/%m/%d %H:%M:%S, %A"))})
    l_result = check_mq_process()
    str_result = "".join(l_result)
    send_request({"text": str_result})

    l_result = check_614_port()
    str_result = "".join(l_result)
    send_request({"text": str_result})

    l_result = check_616_port()
    str_result = "".join(l_result)
    send_request({"text": str_result})
    send_request({"text": "---------------------------"})

    # send_request({"text": "interval data"})


# connect ssh
def connect_to_server():
    global ssh
    ssh = paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(hostname=busan_toc_gw['hostname'],
                username=busan_toc_gw['username'],
                password=busan_toc_gw['password'],
                port=busan_toc_gw['port'])

    print("==================")
    print("connection success ...")
    print("==================")


def check_mq_process():
    """
    프로세스 상태 확인
    :return:
    """
    print("\n\n")
    print("==================")
    print("check whether messaging process alive .... ")
    print("==================")

    """
    UID     PID     PPID    C   STIME   TTY     CMD
    root    1       0       0   2018    ?       /usr/local/java/bin/java Xms512M Xmx1G ...... xxx.jar start
    """
    l_result = ["\n *MQ 프로세스 (ps -ef result) >>>* \n"]

    stdin, stdout, stderr = ssh.exec_command('ps -ef | grep activemq')
    for line in stdout.read().splitlines():
        line = line.decode('utf-8')
        if line.find('ps -ef') >= 0 or line.find('grep') >= 0:
            continue
        print(line)
        l_result.append(line)

    l_result.append("\n *MQ 서비스 확인 (systemctl status active) >>> * \n")
    stdin, stdout, stderr = ssh.exec_command('systemctl status active')
    for line in stdout.read().splitlines():
        line = line.decode('utf-8')
        if line.find('ps -ef') >= 0 or line.find('grep') >= 0:
            continue
        print(line)
        l_result.append(line)

    return l_result


def check_614_port():
    """
    웹 연동 MQ 포트 확인
    :return:
    """
    print("\n\n")
    print("==================")
    print("check whether messaging port is listening.... (outbound)")
    print("==================")

    """
    COMMAND     PID   USER   FD   TYPE    DEVICE SIZE/OFF NODE NAME
    """
    l_result = ["\n *웹 연동 MQ 포트(614) 확인* \n"]
    
    stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61614')

    for line in stdout.read().splitlines():
        line = line.decode('utf-8')
        l_result.append(line)

    return l_result


def check_616_port():
    """
    내부 MQ 포트 확인
    :return:
    """
    print("\n\n")
    print("==================")
    print("check whether messaging port is listening.... (inbound)")
    print("==================")
    l_result = ["\n *내부 MQ 포트(616) 확인* \n"]

    stdin, stdout, stderr = ssh.exec_command('lsof -i -nP | grep 61616')

    for line in stdout.read().splitlines():
        line = line.decode('utf-8')
        l_result.append(line)

    return l_result


if __name__ == '__main__':

    connect_to_server()
    sch.add_job(interval_task, 'interval', minutes=30)
    sch.start()

    try:
        while True:
            time.sleep(0)

    except(KeyboardInterrupt, SystemExit):
        print("Exception occurred")
        sch.shutdown()
