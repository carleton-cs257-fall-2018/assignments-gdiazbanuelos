import csv
import sys


def printLines():
    filename = sys.argv[1]
    action = sys.argv[2]
    with open(filename, newline='') as f:
        reader = csv.reader(f)
        for row in reader:
            print(row)


def main():
    printLines()


if __name__ == '__main__':
    main()
