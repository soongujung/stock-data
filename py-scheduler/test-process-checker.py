from apscheduler.schedulers.background import BackgroundScheduler
from datetime import datetime
import time


def increment():
    global count
    count += 1


def print_task(**kwargs):
    increment()
    print(datetime.now())


sch = BackgroundScheduler()
count = 0

if __name__ == '__main__':

    sch.add_job(print_task, 'interval', seconds=1)
    sch.start()

    try:
        while True:
            time.sleep(0)
            if count >= 10:
                print("break")
                break

    except(KeyboardInterrupt, SystemExit):
        print("Exception Occured")
        sch.shutdown()
