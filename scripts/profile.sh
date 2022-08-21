#!/usr/bin/env bash

function find_idle_profile()
{
    CURRENT_ELASTIC_IP=$(find_current_elastic_ip)
    RESPONSE_CODE=$(sudo curl -s -o /dev/null -w "%{http_code}" http://${CURRENT_ELASTIC_IP}/)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(sudo curl -s http://${CURRENT_ELASTIC_IP}/)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}
# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}
# 현재 Host IP에 따른 Public IP 사용
function find_current_elastic_ip()
{
    CURRENT_HOSTNAME="$(sudo hostname -i)"

    if [ ${CURRENT_HOSTNAME} = 172.31.48.26 ]
    then
      CURRENT_ELASTIC_IP="15.164.20.79"
    else
      CURRENT_ELASTIC_IP="15.164.63.237"
    fi
    echo ${CURRENT_ELASTIC_IP}
}
