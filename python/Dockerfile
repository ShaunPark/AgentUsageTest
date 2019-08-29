FROM python:alpine
MAINTAINER coolage "coolage73@gmail.com"
RUN pip install flask
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
EXPOSE 8080:8080/tcp
CMD ["main.py"]