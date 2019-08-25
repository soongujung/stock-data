FROM python:3.7.4-slim

# Set the working directory to /app
WORKDIR /toc_busan_mq_checker

# Copy the current directory contents into the container at /app
COPY ./original /toc_busan_mq_checker

# Install any needed packages specified in requirements.txt
RUN pip install --upgrade pip
RUN pip install virtualenv
RUN virtualenv env_mq_checker
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# RUN python /toc_busan_mq_checker/interval_webhook_monitoring.py

# Make port 80 available to the world outside this container
# EXPOSE 9998

# Define environment variable
ENV NAME toc_busan_mq_checker


# Run app.py when the container launches
# CMD ["python", "app.py"]