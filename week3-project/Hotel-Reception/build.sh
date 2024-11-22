#git clone https://github.com/amang051/scala-task.git
#
#cd Hotel-Reception
cd play-hotel-service
docker build -t play-hotel-service:latest .

cd ..
cd akka-hotel-service
docker build -t akka-hotel-service:latest .

cd ..

docker network create kafka-network
