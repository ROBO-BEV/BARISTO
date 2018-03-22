FROM node:latest

# Defines our working directory in container
WORKDIR /app

RUN apt-get upgrade -yq
RUN apt-get update -yq
RUN apt-get install -yq apt-utils
RUN apt-get install -yq apt-transport-https
# RUN apt-get install -yq ca-certificates
# RUN apt-get install -yq clang

RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add -
RUN echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list
RUN apt-get update -yq
RUN apt-get install -yq yarn

# This will copy all files in our root to the working  directory in the container
COPY . /app

RUN JOBS=MAX yarn && rm -rf /tmp/*

# compile typescript
RUN yarn build-src
