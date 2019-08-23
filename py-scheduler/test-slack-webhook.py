#-*- coding: utf-8 -*-

import getopt
import signal
import logging
import sys
from connections.toc_slack_info import busan_toc_slack
from slacker import Slacker
from requests.sessions import Session

import requests
import json

import os
import ssl
import urllib3


token = busan_toc_slack['token']
room_name = busan_toc_slack['name']
incoming_webhook_url = busan_toc_slack['incoming_webhook_url']
icon_emoji = busan_toc_slack['icon_emoji']

context = ssl._create_unverified_context()
os.environ['NLS_LANG'] = '.AL32UTF8'


def send_sms():
    slack = Slacker(token=token)
    obj = slack.chat.post_message(channel=room_name, text="asdf", username="sgjung")
    print(obj.successful, obj.__dict__['body']['channel'], obj.__dict__[
        'body']['ts'])


def send_request():
    http = urllib3.PoolManager()
    data = {"text": "urllib3 test message"}
    ret = http.request("POST", incoming_webhook_url,
                       body=json.dumps(data),
                       headers={'Content-Type': 'application/json'})
    # print('urllib3 result >>> ' + str(ret))


def send_request2():
    response = requests.post(
        incoming_webhook_url,
        data=json.dumps({"text": "hello, hi there~"}),
        headers={'Content-Type': 'application/json'}
    )


if __name__ == '__main__':
    # send_sms()
    send_request()
    # send_request2()
