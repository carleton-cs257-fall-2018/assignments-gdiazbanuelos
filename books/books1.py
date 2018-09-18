# Gustavo Diaz Banuelos
# John Sherer
# CS 257

import csv
import sys
cmd_line_arguments_list = []


def readInput():
    if len(sys.argv) < 3:
        print("Please add to the command line a book source file and an action!", file=sys.stderr)
        print("Like this: python3 books1.py input-file action [sort-direction]", file=sys.stderr)
        sys.exit()
    cmd_line_arguments_list.append(sys.argv[1])
    cmd_line_arguments_list.append(sys.argv[2])
    if len(sys.argv) == 4:
        if (sys.argv[3] == "forward"):
            cmd_line_arguments_list.append(False)
        elif (sys.argv[3] == "reverse"):
            cmd_line_arguments_list.append(True)
        else:
            print("Please type only 'forward' or 'reverse' as the third parameter!", file=sys.stderr)
            sys.exit()
    else:
        cmd_line_arguments_list.append(False)


def getAction():
    if cmd_line_arguments_list[1] == "books":
        printBooks()
    elif cmd_line_arguments_list[1] == "authors":
        printAuthors()
    else:
        print("Please type in only 'books' or 'authors' as the action!", file=sys.stderr)
        sys.exit()


def splitAuthors(author, authorList):
    seperatedAuthors = []
    loopcounter = 0
    while(len(author) > 0 and loopcounter < 5):
        loopcounter += 1
        if(author.find(" and") == 0):
            author = author[(author.find("and") + 4):]

        seperatedAuthors.append(author[:author.find("(") - 1])
        author = author[(author.find(")") + 1):]

    if(loopcounter == 5):
        print("infinite loop!!!!???")
    for a in seperatedAuthors:
        authorList.append(a)

# Returns true if the first name preceds the other alphabetically


def compareTwoNames(firstName, secondName):
    firstLastName = firstName[(firstName.rfind(" ") + 1):]
    secondLastName = secondName[(secondName.rfind(" ") + 1):]
    return firstLastName < secondLastName


def sortAuthors(unsortedList):
    sortedList = []
    for i in range(0, len(unsortedList)):
        firstAlphabetical = "zzz"
        for j in range(0, len(unsortedList)):
            if(compareTwoNames(unsortedList[j], firstAlphabetical)):
                firstAlphabetical = unsortedList[j]
        sortedList.append(firstAlphabetical)
        print("Adding " + firstAlphabetical)
        unsortedList.remove(firstAlphabetical)
    return sortedList


def printAuthors():
    authors = []
    authorsSplicedList = []
    with open(cmd_line_arguments_list[0], newline='') as f:
        reader = csv.reader(f)
        for row in reader:
            authors.append(row[2])
    for a in authors:
        if(a.find("and") == -1):
            spliceIndex = a.find("(")
            a = a[:spliceIndex]
            authorsSplicedList.append(a)
        else:
            splitAuthors(a, authorsSplicedList)
    sortedAuthorsList = sortAuthors(authorsSplicedList)
    for a in sortedAuthorsList:
        print(a)


def printBooks():
    titles = []
    with open(cmd_line_arguments_list[0], newline='') as f:
        reader = csv.reader(f)
        for row in reader:
            titles.append(row[0])
    titles.sort(reverse=cmd_line_arguments_list[2])
    for t in titles:
        print(t)


def main():
    readInput()
    getAction()


if __name__ == '__main__':
    main()
