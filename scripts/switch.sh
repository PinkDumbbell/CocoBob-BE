#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh


function switch_proxy() {
    IDLE_PORT=$(find_idle_port)
    CURRENT_ELASTIC_IP=$(find_current_elastic_ip)
    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://${CURRENT_ELASTIC_IP}:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    sudo sudo nginx -s reload
    echo "> sudo nginx -s reload"
}