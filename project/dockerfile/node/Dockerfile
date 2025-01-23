# FROM node:lts
# COPY ./app.js /app.js 
# CMD ["node", "app.js"]

FROM node:lts
# /app 폴더에서 cmd 명령어가 진행됨 
# ※폴더가 없으면 생성해줌
WORKDIR /app
# 현재 폴더에 복사함
# ※WORKDIR 이 있으므로 /app에 복사됨
COPY ./package.json ./
RUN npm install