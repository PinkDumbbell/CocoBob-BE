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
#  sudo rm -f ~/deploy-log.log
#  sudo touch ~/deploy-log.log
#  sudo chmod 777 ~/deploy-log.log
  sudo touch ~/1.log
  sudo touch ~/2.log
  echo "> docker stop" ${IDLE_PROFILE}
  sudo docker stop -f ${IDLE_PROFILE}
  echo "> docker rm" ${IDLE_PROFILE}
  sudo docker rm -f ${IDLE_PROFILE}
  sleep 5
fi