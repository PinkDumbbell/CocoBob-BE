#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

IMAGE_URI=163378391403.dkr.ecr.ap-northeast-2.amazonaws.com/cocobob/be-api:latest

echo "> Delete Existing Image"
echo "docker rmi -f $(IMAGE_URI)"
docker rmi -f $(IMAGE_URI)

echo "> Pull Image From ECR"
echo "> docker pull $(IMAGE_URI)"

echo "> 새 어플리케이션 배포"

IDLE_PROFILE=$(find_idle_profile)

echo "> 새로운 어플리케이션을 profile=$IDLE_PROFILE 로 실행합니다."
docker run -it --name "$IDLE_PROFILE" -d -e active=$IDLE_PROFILE -p $IDLE_PORT:$IDLE_PORT spring