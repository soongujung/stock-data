from apscheduler.schedulers.background import BackgroundScheduler
from datetime import datetime
import time


def increment():
    """
        count increment
    :return:
    """
    global count
    count += 1


def interval_task():
    """
        interval 수행 동작
    :return:
    """
    # TODO 이 부분 데코레이터로 변경할 것
    increment()
    print(datetime.now())


sch = BackgroundScheduler()
count = 0

if __name__ == '__main__':

    sch.add_job(interval_task, 'interval', seconds=1)
    sch.start()

    try:
        while True:
            time.sleep(0)
            if count >= 10:
                print("break")
                sch.shutdown()
                break

    except(KeyboardInterrupt, SystemExit):
        print("Exception occurred")
        sch.shutdown()
