FROM nginx
RUN apt update && apt install -y nano && apt install -y curl
RUN apt install -y iputils-ping
WORKDIR /usr/share/nginx/html
COPY ./html .