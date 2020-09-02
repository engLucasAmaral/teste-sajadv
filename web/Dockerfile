FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install 
COPY . .

RUN npm run build

FROM nginx:1.18.0-alpine as  production-stage

COPY --from=build-stage /app/build /var/www/gestao
COPY nginx.conf  /etc/nginx/conf.d/default.conf
ENV TZ=America/Sao_Paulo
CMD ["nginx", "-g", "daemon off;"]