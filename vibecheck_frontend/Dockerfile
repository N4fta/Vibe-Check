FROM node:20.18

WORKDIR /app

COPY dist/ ./dist

RUN npm install -g serve

EXPOSE 3000

CMD ["serve", "-s", "dist"]