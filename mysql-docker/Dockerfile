FROM mysql:8

COPY mall.sql /mall.sql

COPY setup.sh /docker-entrypoint-initdb.d/setup.sh

RUN chmod a+x /docker-entrypoint-initdb.d/setup.sh
