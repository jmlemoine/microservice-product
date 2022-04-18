FROM node:17.9.0-alpine as build
#RUN mkdir -p /app
WORKDIR usr/local/app
#COPY package.json /app
COPY ./ /usr/local/app/
RUN npm install
#COPY . /app
#RUN npm run build --prod
RUN npm run build

FROM nginx:1.20-alpine
EXPOSE 4200
#COPY --from=build /app/docs /app/node_modules
COPY --from=build /usr/local/app/dist/frontend-service /usr/share/nginx/html
