# YetAnotherDistroChooser back-end
It's not much, but it's honest work. As Distrowatch doesn't have an API, I had to use scrapper. If you want to do something similar, be respective to them, I actually was banned for 12 hours once for sending 1 request per second while downloading all the logos. That's why server greatly relies on caching and tries to solve n+1 problem by storing repos in its own db.

### Development
You will need to create a postgres db for testing, I won't describe it, but you can find more info [here](https://www.geeksforgeeks.org/postgresql/how-to-setup-a-postgresql-database-cluster/).

Also, its generally better to use `spring.profiles.active=test` and create a testing profile with all the datasource values, otherwise, you have to create an `.env` file in the project root. 