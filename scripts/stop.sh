#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PROFILE=$(find_idle_profile)

CONTAINER_ID=$(docker container ls -f "name=${IDLE_PROFILE}" -q)

echo "> CONTAINER_ID ${CONTAINER_ID}"
echo "> CURRENT_PROFILE ${IDLE_PROFILE}"

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
#  echo "> docker stop ${IDLE_PROFILE}" > ~/deploy-log.log
  echo "sudo docker stop ${IDLE_PROFILE} && sudo docker rm ${IDLE_PROFILE}"
  sudo docker stop -f ${IDLE_PROFILE} && sudo docker rm -f ${IDLE_PROFILE} > ~/deploy-log.log
#  echo "> docker rm ${IDLE_PROFILE}" > ~/deploy-log.log
#  sudo docker rm ${IDLE_PROFILE} > ~/deploy-log.log
  sleep 5
fi