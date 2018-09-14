import csv
import sys
parameters = []


def readInput():
    parameters.append(sys.argv[1])
    parameters.append(sys.argv[2])
    if len(sys.argv) == 4:
        if (sys.argv[3] == "forward"):
            parameters.append(False)
        elif (sys.argv[3] == "reverse"):
            parameters.append(True)
        else:
            sys.exit("Please type only 'forward' or 'reverse' as the third parameter!")
    else:
        parameters.append(False)


def getAction():
    if parameters[1] == "books":
        printBooks()
    elif parameters[1] == "authors":
        printAuthors()
    else:
        sys.exit("Please type in only 'books' or 'authors'!")


def printAuthors():
    print("yay we are in printAuthors!")


def printBooks():
    titles = []
    with open(parameters[0], newline='') as f:
        reader = csv.reader(f)
        for row in reader:
            titles.append(row[0])
    titles.sort(reverse=parameters[2])
    for t in titles:
        print(t)


def main():
    readInput()
    getAction()


if __name__ == '__main__':
    main()
