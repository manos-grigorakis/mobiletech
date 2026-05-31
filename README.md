# MobileTech

A modern e-commerce platform for mobile devices, featuring product browsing, cart management and secure checkout with PayPal and Stripe payment integrations. Built with Vue 3, TypeScript, Pinia and Tailwind CSS for the frontend, alongside Spring Boot and MySQL for the backend. The project also uses MinIO for S3-compatible object storage.

**[Live Demo](https://mobiletech.manosgrigorakis.com)** \
**[Android Application](https://github.com/manos-grigorakis/mobiletech-android.git)**

## Features

- Product browsing with category filtering
- Cart management
- Secure checkout with 3 payment methods (Cash on Delivery, PayPal, Stripe)
- Webhook handling for real-time payment confirmation
- Authentication and JWT-based authorization
- Admin dashboard with analytics (revenue, orders, top products)
- Role-based access control (Admin, Manager, Demo)
- Responsive design

## Tech Stack

- **Frontend**: Vue 3, TypeScript, Tailwind CSS, Pinia
- **Backend**: Spring Boot, MySQL
- **Payments**: PayPal, Stripe
- **Infrastructure**: Docker, MinIO (S3-compatible object storage)

## Prerequisites

- Node.js 18+
- Java 21
- Maven
- Docker & Docker Compose
- (Optional) Stripe CLI
- (Optional) ngrok

## Deployment

- **Frontend:** Vercel
- **Backend**: Coolify (self-hosted)
- **Storage**: Cloudflare R2 (S3-compatible object storage), Cloudflare CDN

## Setup

1. Clone the repo

   ```bash
   git clone https://github.com/manos-grigorakis/mobiletech.git
   ```

2. Copy and configure environment variables

   ```bash
   cp frontend/.env.example frontend/.env
   cp backend/.env.example backend/.env
   ```

   > **Note:** Payment integrations (PayPal, Stripe) require sandbox API keys. PayPal also requires an HTTPS endpoint. Use [ngrok](https://ngrok.com) to expose your local server and update the PayPal Webhook URL accordingly.

3. Start infrastructure services

   ```bash
   docker compose -f backend/docker-compose.yaml up -d
   ```

4. Start backend server
   1. (Recommended) With IntelliJ IDEA \
      Open the `backend` directory in IntelliJ IDEA and the run configuration will automatically load.

   2. With Maven installed

      ```bash
      cd backend && mvn spring-boot:run
      ```

   3. Without Maven

      ```bash
      cd backend && ./mvnw spring-boot:run
      ```

5. Seed the database
   1. Init

      ```bash
      docker exec -i mobiletech-mysql-dev mysql -u<MYSQL_USER> -p<MYSQL_PASSWORD>  < backend/src/main/resources/db/init.sql
      ```

   2. Analytics Sample

      ```bash
      docker exec -i mobiletech-mysql-dev mysql -u<MYSQL_USER> -p<MYSQL_PASSWORD>  < backend/src/main/resources/db/analytics-sample.dev.sql
      ```

6. Install frontend dependencies

   ```bash
   cd frontend && npm install
   ```

7. Start frontend development server

   ```bash
   npm run dev
   ```

## Test Credentials

**Demo User (Read-Only Admin Access)**

- Email: `demo@mobiletech.manosgrigorakis.com`
- Password: `!Demo123@`

**PayPal Sandbox**

- Email: `john.doe.buyer@sandbox.com`
- Password: `kDPa3PoTXVOR0jZH`

**Stripe**

- Success Card: `4242 4242 4242 4242`
- Decline Card: `4000 0000 0000 0002`
- Expiry: Any future date
- CVC: Any 3 digits

## API

A Postman collection is available for testing the API endpoints. \
Import `docs/postman/MobileTech.postman_collection.json` into Postman.

## Screenshots

### Products Page

![Products Page](/docs/screenshots/products-page.webp)

### Cart Page

![Cart Page](/docs/screenshots/cart-page.webp)

### Shipping Form

![Shipping Form](/docs/screenshots/shipping-form.webp)

### Stripe Checkout

![Stripe Checkout](/docs/screenshots/stripe-checkout.webp)

### Successful Checkout

![Successful Checkout](/docs/gifs/successful-checkout.gif)

### Admin Dashboard

![Admin Dashboard](/docs/screenshots/admin-dashboard.webp)
