import csv
import psycopg2

print("Database host (z.B. 'localhost'):")
url = input()
print("Database password:")
pwd = input()

conn = psycopg2.connect("host=" + url + " dbname=postgres user=postgres password=" + pwd)

cur = conn.cursor()

data = {
    # number of '%s' reflects the number of columns in a table
    {"cinema_hall.csv", "INSERT INTO cinema_hall VALUES (%s, %s, %s)"},
    {"seats.csv", "INSERT INTO seats VALUES (%s, %s, %s, %s, %s)"},
    {"movie.csv", "INSERT INTO movies VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"},
    {"filmshow.csv", "INSERT INTO filmshow VALUES (%s, %s, %s, %s, %s)"},
    {"filmshow_seat.csv", "INSERT INTO filmshow_seat VALUES (%s, %s, %s)"}
}

for csvFile, sql in data:
    with open('./database_tables/' + csvFile, 'r') as f:
        reader = csv.reader(f)
        next(reader) # Skip the header row.
        for row in reader:
            cur.execute(sql, row)

conn.commit()
