SELECT name, COUNT(1) AS c, SUM(age) FROM table GROUP BY name WHERE age > 1 ORDER BY c DESC
