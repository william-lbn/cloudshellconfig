sudo docker run -d \
  --restart=unless-stopped \
  --name=kuboard \
  -p 36221:80/tcp \
  -p 10081:10081/tcp \
  -e KUBOARD_ENDPOINT="http://172.24.51.21:36221" \
  -e KUBOARD_AGENT_SERVER_TCP_PORT="10081" \
  -v /root/kuboard-data:/data \
  121.40.102.76:30080/pml_user_system/kuboard:v3

