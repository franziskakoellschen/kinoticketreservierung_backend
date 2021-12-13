import csv
import psycopg2

def getConnection():

    print("1 - prod space")
    print("2 - dev space")
    print("3 - custom")

    selection = input()

    host = ""
    database = ""
    user = ""
    pwd = ""

    if selection == "1":
        host = "ec2-63-33-14-215.eu-west-1.compute.amazonaws.com"
        database = "deb1hdv5c2raku"
        user = "kzzxpuxgvekjkp"
        pwd = "30ebd9a2f009b4f89ced5c39d85639db6efd44a0378d172fce95c97a58cebec9"

    if selection == "2":
        host = "ec2-34-243-180-8.eu-west-1.compute.amazonaws.com"
        database = "d5iqk6nl9urqqb"
        user = "gucosqewklsdkh"
        pwd = "eb29e2d78d1da0fb670cfc921e1684364c781fde54fbf00687289626167c5e76"

    if selection == "3":
        print("Database host (z.B. 'localhost'):")
        host = input()
        print("Database (z.B. 'postgres'):")
        database = input()
        print("User (z.B. 'postgres'):")
        user = input()
        print("Password:")
        pwd = input()

    conn = psycopg2.connect("host=" + host + " dbname=" + database + " user=" + user + " password=" + pwd)

    return conn

def uploadCSVs(conn):
    cur = conn.cursor()

    # number of '%s' reflects the number of columns in a table
    data = [
        ["cinema_hall.csv", "INSERT INTO cinema_hall VALUES (%s, %s, %s)"],
        ["seats.csv", "INSERT INTO seats VALUES (%s, %s, %s, %s, %s)"],
        ["movie.csv", "INSERT INTO movies VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"],
        ["filmshow.csv", "INSERT INTO filmshow VALUES (%s, %s, %s, %s, %s)"],
        ["filmshow_seat.csv", "INSERT INTO filmshow_seat VALUES (%s, %s, %s)"]
    ]

    for csvFile, sql in data:
        filename = "./database_tables/" + csvFile
        with open(filename, 'r') as f:
            reader = csv.reader(f)
            next(reader) # Skip the header row.
            for row in reader:
                cur.execute(sql, row)

    conn.commit()

    print("Success")
    
def cleanTables(conn):
    cur = conn.cursor()
    tables = [
        "filmshow_seat",
        "filmshow",
        "movies",
        "seats",
        "cinema_hall",
    ]

    for table in tables:
        cur.execute("DELETE from " + table)

    conn.commit()
    
    print("Success")


if __name__ == "__main__":

    conn = getConnection()

    while (True):
        print("1: Clean Tables")
        print("2: Upload CSVs")
        print("3: Exit")

        selection = input()

        if selection == "1":
            cleanTables(conn)

        if selection == "2":
            uploadCSVs(conn)

        if selection == "3":
            exit()
