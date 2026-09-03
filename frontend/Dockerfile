FROM node:18-alpine
WORKDIR /app
COPY package.json .
# We use npm install but normally package-lock is preferred. Using standard npm install for simplicity.
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev"]
