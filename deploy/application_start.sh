#!/bin/bash

GOOGLE_CLIENT_SECRET_KEY=$(aws ssm get-parameter --name "GOOGLE_CLIENT_SECRET_KEY" --query "Parameter.Value" --output text)
NAVER_MAP_CLIENT_SECRET_KEY=$(aws ssm get-parameter --name "NAVER_MAP_CLIENT_SECRET_KEY" --query "Parameter.Value" --output text)
NAVER_DEVELOP_SECRET_KEY=$(aws ssm get-parameter --name "NAVER_DEVELOP_SECRET_KEY" --query "Parameter.Value" --output text)
JWT_SECRET_KEY=$(aws ssm get-parameter --name "JWT_SECRET_KEY" --query "Parameter.Value" --output text)

ENCRYPTION_SECRET_KEY=$(aws ssm get-parameter --name "ENCRYPTION_SECRET_KEY" --query "Parameter.Value" --output text)
SALT=$(aws ssm get-parameter --name "SALT" --query "Parameter.Value" --output text)

DB_URL=$(aws ssm get-parameter --name "DB_URL" --query "Parameter.Value" --output text)
DB_USERNAME=$(aws ssm get-parameter --name "DB_USERNAME" --query "Parameter.Value" --output text)
DB_PASSWORD=$(aws ssm get-parameter --name "DB_PASSWORD" --query "Parameter.Value" --output text)

REDIS_HOST=$(aws ssm get-parameter --name "REDIS_HOST" --query "Parameter.Value" --output text)
CONTENT_SERVER_URL=$(aws ssm get-parameter --name "CONTENT_SERVER_URL" --query "Parameter.Value" --output text)

# 환경 변수 설정
export GOOGLE_CLIENT_SECRET_KEY=$GOOGLE_CLIENT_SECRET_KEY
export NAVER_MAP_CLIENT_SECRET_KEY=$NAVER_MAP_CLIENT_SECRET_KEY
export NAVER_DEVELOP_SECRET_KEY=$NAVER_DEVELOP_SECRET_KEY
export JWT_SECRET_KEY=$JWT_SECRET_KEY

export ENCRYPTION_SECRET_KEY=$ENCRYPTION_SECRET_KEY
export SALT=$SALT

export DB_URL=$DB_URL
export DB_USERNAME=$DB_USERNAME
export DB_PASSWORD=$DB_PASSWORD

export REDIS_HOST=$REDIS_HOST
export CONTENT_SERVER_URL=$CONTENT_SERVER_URL

export SPRING_PROFILES_ACTIVE=prod

HEALTH_STATUS_8080=$(curl -s http://localhost:8080/user/health | grep '{"status":true}')

if [ -z "$HEALTH_STATUS_8080" ]; then
    # 8080 상태가 0이라면, 해당 포트 사용 안하는 중
    NEW_PORT=8080
    OLD_PORT=8081
else
    NEW_PORT=8081
    OLD_PORT=8080
fi

echo "new port: $NEW_PORT, old port: $OLD_PORT"

export SERVER_PORT=$NEW_PORT
nohup java -jar /home/ec2-user/user-0.0.1-SNAPSHOT.jar &

# 헬스 체크 수행
echo "health check!!!"

sleep 50
HEALTH_STATUS=$(curl -s http://localhost:$NEW_PORT/user/health | grep '{"status":true}')
if [ -z "$HEALTH_STATUS" ]; then
  echo "health_status = $HEALTH_STATUS"
  echo "New application health check failed"
  exit 1
fi

echo "old port: $OLD_PORT remove"

OLD_PID=$(pgrep -f "java -jar /home/ec2-user/user-0.0.1-SNAPSHOT.jar --server.port=$OLD_PORT")
if [ -n "$OLD_PID" ]; then
    kill $OLD_PID
fi

HEALTH_STATUS=$(curl -s http://localhost:8080/user/health | grep '{"status":true}')
if [ -z "$HEALTH_STATUS" ]; then
        echo "port running: 8081"
        IDLE_PORT=8081
else
        echo "port running: 8080"
        IDLE_PORT=8080
fi

echo "switch port: $IDLE_PORT"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Nginx Reload"
sudo service nginx reload