FROM mysql:8
# 把数据库初始化数据文件复制到工作目录
COPY mall.sql /mall.sql
# 把要执行的shell文件放到/docker-entrypoint-initdb.d/目录下，容器会自动执行这个shell
COPY setup.sh /docker-entrypoint-initdb.d/setup.sh
# 给执行文件增加可执行权限
RUN chmod a+x /docker-entrypoint-initdb.d/setup.sh
