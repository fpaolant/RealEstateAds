# Step 1: Costruzione del progetto Angular
FROM node:18-alpine AS build

# Imposta la directory di lavoro
WORKDIR /app

# Copia package.json e package-lock.json
COPY client/package*.json ./

# Installa le dipendenze
RUN npm install

# Copia il resto dei file del progetto
COPY client/ .

# Esegui il build Angular
RUN npm run build -- --configuration production

# Step 2: Servire i file statici con Nginx
FROM nginx:alpine

COPY client/nginx.conf /etc/nginx/conf.d/default.conf

CMD ["ls", "-la", "/usr/share/nginx/html;"]

CMD ["rm", "-rf", "/usr/share/nginx/html/*;"]

# Copia i file generati nella directory di Nginx
COPY --from=build /app/dist/real-estate-ads/browser /usr/share/nginx/html

CMD ["ls", "-la", "/usr/share/nginx/html;"]

# Espone la porta 80
EXPOSE 80

# Comando di avvio di Nginx
CMD ["nginx", "-g", "daemon off;"]
