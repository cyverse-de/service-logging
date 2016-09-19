FROM clojure
COPY ./docker/profiles.clj /root/.lein/profiles.clj
WORKDIR /usr/src/service-logging

COPY project.clj /usr/src/service-logging/
RUN lein deps

COPY . /usr/src/service-logging
CMD ["lein", "test"]
