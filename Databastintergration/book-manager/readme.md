

This is a Java program that keeps track of books using an SQLite database. You can add books, search for them, update them, and keep track of which ones you've read. The books are saved even after closing the program.


- View all books
- Search by title or author
- See unread books
- Add new books
- Mark books as read and give them a rating
- Edit or delete books
- See book counts by author and the average rating



Open a terminal in the book-manager folder and run:

```powershell
mvn compile
mvn exec:java "-Dexec.mainClass=edu.cwi.swdv110.books.App"
```

The program creates the database and loads the sample books if the database is empty.



Run:

```powershell
mvn test
```

All 13 tests passed when I ran them.



SQL injection is when someone enters SQL code as input to try to change what a database query does.

For example, someone could enter `' OR '1'='1` when searching for a book. If the program put that text directly into a query using string concatenation, the SQL could look like this:

```sql
SELECT * FROM books
WHERE title LIKE '%' OR '1'='1%'
   OR author LIKE '%' OR '1'='1%';
```

The extra `OR` condition could cause the search to return books it wasn't supposed to. Using a `PreparedStatement` with `?` keeps the user's input separate from the SQL command, so it is treated as text instead of SQL code.



I used try-with-resources so the database connections and statements get closed automatically when they're finished.

If connections aren't closed, the program could leave resources open or cause database locking problems. It also makes the code easier to manage because I don't have to close everything manually.



One benefit is that `App.java` doesn't need to worry about how the database works. It just calls methods from `BookDAO`.

Another benefit is that if I need to change the database code later, I can make those changes in one place. It would also make it easier to add a different interface, like JavaFX, without rewriting the database methods.



These are the expected results using the original 10 sample books, with the queries run in order.

| Query | Expected result |
|---|---|
| 1. All books, newest first | 10 rows |
| 2. Books by Andy Weir | 3 rows |
| 3. Unread books | 3 rows |
| 4. Titles containing "the" | 3 rows |
| 5. Books per author | 8 rows |
| 6. Average rating | 1 row |
| 7. Mark Sapiens as read | 1 row updated |
| 8. Find and delete old unread books | 1 row found and deleted |

I used `SELECT`, `WHERE`, `ORDER BY`, `LIKE`, `GROUP BY`, `COUNT`, and `AVG` for the queries. I also used `UPDATE` and `DELETE` to change and remove records.



- `App.java` - Menu and user input
- `Book.java` - Book information
- `BookDAO.java` - Database methods
- `Database.java` - Database connection and setup
- `schema.sql` - Creates the table
- `seed.sql` - Starting books
- `queries.sql` - SQL queries
- `BookDAOTest.java` - Tests for the database methods
