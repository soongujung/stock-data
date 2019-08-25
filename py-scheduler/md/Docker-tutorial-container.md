# Docker 기본 사용법 (예제로 정리)

ssh 커맨드로 30분에 한번씩 서버내의 프로세스와 포트의 LISTEN/CLOSE_WAIT 상태를 체크하는 프로그램을 docker로 다른 컴퓨터에 배포해야 하는 과정을 정리한다.  

## Docker Image 생성/빌드하기

Docker Image 생성/빌드시  

- Docker 사용시 디렉터리 위치 구별
- Docker Image 생성, 간단 명령어 예제
- Docker Image 빌드 명령어

를 간략하게 정리한다.  



### Docker 사용시 디렉터리 위치 구별

샘플로 돌린 디렉터리가 다소 지저분하다. docker명령이 Dockerfile을 인식하는 디렉터리의 위치를 잘 모르고 있는 상태에서 디렉터리를 바꿔가며 테스트해서이다... 정리하자...  

```bash
$ cd D:/study/docker/tutorial/2_containers/toc_busan_docker/docker_app/toc_busan_mq_checker

# 이곳에 원래 소스들을 복사해준다.
$ mkdir original

# 여기에 Dockerfile 을 생성해둔다.
$ touch Dockerfile

$ cd ..

# 현재 디렉터리내의 toc_busan_mq_checker라는 디렉터리를 찾아서 
# 그 toc_busan_mq_checker 디렉터리 내의 Dockerfile 라는 이름으로 파일을 생성한다.
# Dockerfile 내에 들어가는 커맨드들의 내용은 아래에 기술해두었다. 
# Dockerfile 커맨드를 확인후 아래의 명령을 실행시키자.  
$ docker build --tag=img_toc_busan_mq_checker:v0.0.1 toc_busan_mq_checker
```



> Dockerfile은 목적지 디렉터리에 두어야 한다. 명령을 내리는 디렉터리가 아닌 목적지 디렉터리에 있어야 한다. 또는 docker build --tag=[원하는 이미지명]:[버전명] [Dockerfile이 위치한 디렉터리명] 에서 디렉터리명을 . 으로 주면 현재 디렉터리를 목적지로 설정할 수 있다. 초반에 이 것 때문에 조금 헤맸다.



### Docker Image 생성, 간단 명령어 예제

단순한 image 커맨드를 정리해봤다.  

```dockerfile
# docker hub내의 python 3.7.4-slim(python 재단에서 공식으로 지정한 이미지)를 가져온다. 
FROM python:3.7.4-slim

# Set the working directory to /app
WORKDIR /toc_busan_mq_checker

# Copy the current directory contents into the container at /app
COPY ./original /toc_busan_mq_checker

# Install any needed packages specified in requirements.txt
RUN pip install --upgrade pip
RUN pip install virtualenv
RUN pip install --trusted-host pypi.python.org -r requirements.txt

RUN virtualenv env_mq_checker

# Make port 80 available to the world outside this container
# EXPOSE 80

# Define environment variable
ENV NAME toc_busan_mq_checker


# Run app.py when the container launches
# CMD ["python", "app.py"]
```



### Docker Image 빌드 명령어

 Docker 명령어의 형식은

- docker build --tag=[원하는 이미지명]:[버전명] [Dockerfile이 위치한 디렉터리명]  

이다.  

```bash
$ docker build --tag=img_toc_busan_mq_checker:v0.0.1 toc_busan_mq_checker 
```













