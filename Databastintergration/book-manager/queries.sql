
-- 1. Every book, newest first
-- Expected: 10 rows with the original seed data
SELECT * FROM books
ORDER BY year DESC;

-- 2. Books by Andy Weir, oldest first
-- Expected: 3 rows
SELECT * FROM books
WHERE author = 'Andy Weir'
ORDER BY year ASC;

-- 3. Unread books, A-Z by title
-- Expected: 3 rows before updates
SELECT * FROM books
WHERE is_read = 0
ORDER BY title ASC;

-- 4. Titles containing 'the', any case
-- Expected: 3 rows
SELECT * FROM books
WHERE LOWER(title) LIKE '%the%';

-- 5. Number of books by author, most first
-- Expected: 8 author rows
SELECT author, COUNT(*) AS total
FROM books
GROUP BY author
ORDER BY total DESC, author ASC;

-- 6. Average rating of rated books
-- Expected: 1 result row
SELECT AVG(rating) AS average_rating
FROM books
WHERE rating > 0;

-- 7. Mark Sapiens as read with a rating of 4
-- Expected: 1 row updated
UPDATE books
SET is_read = 1, rating = 4
WHERE title = 'Sapiens';

-- 8. Check which books would be deleted
-- Expected: 1 row before deletion
SELECT * FROM books
WHERE rating = 0
  AND is_read = 0
  AND year < 2000;

-- 8. Delete unread, unrated books published before 2000
-- Expected: 1 row deleted
DELETE FROM books
WHERE rating = 0
  AND is_read = 0
  AND year < 2000;
