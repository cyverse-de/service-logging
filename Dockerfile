FROM clojure
COPY . /usr/src/service-logging
COPY ./docker/profiles.clj /root/.lein/profiles.clj
WORKDIR /usr/src/service-logging
RUN lein deps
CMD ["lein", "test"]
