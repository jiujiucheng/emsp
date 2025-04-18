#!/bin/sh
# 构建命令为 sh build.sh ARGS_DEV, 可传入ARGS_DEV作为用户变量 如 -Dmaven.test.skip=true

source ~/.bash_profile

ARGS_DEV="$@"

set -e

mvn clean package ${ARGS_DEV}
