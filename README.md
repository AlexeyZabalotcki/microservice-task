# Currency converter developed on the basis of microservices

This application provides a currency conversion service based on a microservices architecture. 

It allows users to register, view exchange rates, and create currency conversion requests. 

The application can be run using Docker Compose.

## Run application:

To run the application, execute the following commands:

### `git clone <username>/microservice-task.git`

### `docker compose up -d --build`

Frontend runs on [http://localhost:3000](http://localhost:3000)

### `usage sample:`

1. Registration on http://localhost:3000
    
   `1.1 Enter your actual email`

    `1.2 Create a password`
2. You can see actual exchange rate for USD to BYN for today
3. Using the "Create Request" button, you can create a request for any possible currencies
    ```Sample:
    From : EUR
    To : BYN
    Amount : 12
    
    If you would like to subscribe to this currencies, tick the subscribe checkbox```

### Communicating with the Application via Postman:

### `Work with user-service:`

1. As Admin you can manipulate user's information:

    1.1. Get all users: GET `http://localhost:8000/users`

    1.2. Get a specific user by their user id: GET `http://localhost:8000/users/id/<id>`
    
    1.3. Get a specific user by their user email: GET `http://localhost:8000/users/email/<email>`
    
    1.4. Save new user: POST `http://localhost:8000/users`
    ```json
   {
    "email": "exam@gmail.com",
    "password": 123,
    "role": "ADMIN"
    }
   ```
   1.5. Update existing user: PUT `http://localhost:8000/users`
    ```json
    {
    "id": 3,
    "email": "example@gmail.com",
    "password": 1234,
    "role": "USER"
    }
    ```
   1.6. Delete user by their id: DELETE `http://localhost:8000/users/<id>`


2. As Admin/User you can work with currency information:

    2.1. Get all currencies requests for today: GET `http://localhost:8000/client/date/<YYYY-MM-DD>`
    
    2.2. Get all currencies requests by rate: GET `http://localhost:8000/client/rate/<rate>`

    2.3. Get all currencies requests by amount: GET `http://localhost:8000/client/between?from=1&to=5`

    2.4. Create new request to third-party server: POST `http://localhost:8000/client/new`
    ```json
    {
    "from": "USD",
    "to": "BYN",
    "amount": 10,
    "subscribe": true
    }
    ```

3. As Admin you can work with currency information:
    
    3.1. Get a specific currency by id: GET `http://localhost:8000/client/id/<id>`

    3.2. Get first currency request: GET `http://localhost:8000/client/first`

    3.3. Get last currency request: GET `http://localhost:8000/client/last`

    3.4. Delete currency request by their id: DELETE `http://localhost:8000/client/<id>`

### Diagram:

![Diagram.gif](Diagram.gif)