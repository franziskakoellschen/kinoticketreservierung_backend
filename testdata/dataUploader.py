import csv
import psycopg2

print("Database host (z.B. 'localhost'):")
url = input()
print("Database password:")
pwd = input()

conn = psycopg2.connect("host=" + url + " dbname=postgres user=postgres password=" + pwd)

cur = conn.cursor()

# cinema halls
with open('./database_tables/cinema_hall.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader) # Skip the header row.
    for row in reader:
        cur.execute(
        "INSERT INTO cinema_hall VALUES (%s, %s, %s)",
        row
    )

conn.commit()

# seats
with open('./database_tables/seats.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader) # Skip the header row.
    for row in reader:
        cur.execute(
        "INSERT INTO seats VALUES (%s, %s, %s, %s, %s)",
        row
    )

conn.commit()

# movies
with open('./database_tables/movie.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader) # Skip the header row.
    for row in reader:
        cur.execute(
        "INSERT INTO movies VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
        row
    )

# filmShows
with open('./database_tables/filmshow.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader) # Skip the header row.
    for row in reader:
        cur.execute(
        "INSERT INTO filmshow VALUES (%s, %s, %s, %s, %s)",
        row
    )

conn.commit()

# filmShow_seat
with open('./database_tables/filmshow_seat.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader) # Skip the header row.
    for row in reader:
        cur.execute(
        "INSERT INTO filmshow_seat VALUES (%s, %s, %s)",
        row
    )

conn.commit()
