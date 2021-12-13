import csv
import psycopg2

def getConnection():
    print("Database host (z.B. 'localhost'):")
    url = input()
    print("Database password:")
    pwd = input()

    conn = psycopg2.connect("host=" + url + " dbname=postgres user=postgres password=" + pwd)

    return conn

def uploadCSVs(conn):
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

    print("Success")
    
def cleanTables(conn):
    cur = conn.cursor()
    tables = {
        "cinema_hall",
        "seats",
        "movies",
        "filmshow",
        "filmshow_seat"
    }

    for table in tables:
        cur.execute("DELETE CASCADE from %s", table)
    
    conn.commit()
    
    print("Success")


if __name__ == "__main__":
    conn = getConnection()
    
    while (True):
        print("1: Clean Tables")
        print("2: Upload CSVs")
        print("3: Exit")
        
        selection = input()
        
        if selection == 1:
            cleanTables(conn)
        
        if selection == 2:
            uploadCSVs(conn)
            
        if selection == 3:
            exit()
