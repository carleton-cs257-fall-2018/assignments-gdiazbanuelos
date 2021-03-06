#!/usr/bin/env python3
'''
    booksdatasource.py
    Jeff Ondich, 18 September 2018
    Written by John Sherer and Gustavo Diaz

    For use in some assignments at the beginning of Carleton's
    CS 257 Software Design class, Fall 2018.
'''
import csv

authors = []

books = []

link = []


def get_book_author_ids(book):
    for i in range(0, len(link)):
        if(link[i]['book_id'] == book['id']):
            for j in range(0, len(authors)):
                if(link[i]['author_id'] == authors[j]['id']):
                    return (authors[j]['id'])


def get_author_book_ids(author):
        result = []
        for i in range(0, len(link)):
            if(link[i]['author_id'] == author['id']):
                for j in range(0, len(books)):
                    if(link[i]['book_id'] == books[j]['id']):
                        return (books[j]['id'])


class BooksDataSource:
    '''
    A BooksDataSource object provides access to data about books and authors.
    The particular form in which the books and authors are stored will
    depend on the context (i.e. on the particular assignment you're
    working on at the time).

    Most of this class's methods return Python lists, dictionaries, or
    strings representing books, authors, and related information.

    An author is represented as a dictionary with the keys
    'id', 'last_name', 'first_name', 'birth_year', and 'death_year'.
    For example, Jane Austen would be represented like this
    (assuming her database-internal ID number is 72):

        {'id': 72, 'last_name': 'Austen', 'first_name': 'Jane',
         'birth_year': 1775, 'death_year': 1817}

    For a living author, the death_year is represented in the author's
    Python dictionary as None.

        {'id': 77, 'last_name': 'Murakami', 'first_name': 'Haruki',
         'birth_year': 1949, 'death_year': None}

    Note that this is a simple-minded representation of a person in
    several ways. For example, how do you represent the birth year
    of Sophocles? What is the last name of Gabriel García Márquez?
    Should we refer to the author of "Tom Sawyer" as Samuel Clemens or
    Mark Twain? Are Voltaire and Molière first names or last names? etc.

    A book is represented as a dictionary with the keys 'id', 'title',
    and 'publication_year'. For example, "Pride and Prejudice"
    (assuming an ID of 132) would look like this:

        {'id': 193, 'title': 'A Wild Sheep Chase', 'publication_year': 1982}

    '''

    def __init__(self, books_filename, authors_filename, books_authors_link_filename):
        ''' Initializes this data source from the three specified  CSV files, whose
            CSV fields are:

                books: ID,title,publication-year
                  e.g. 6,Good Omens,1990
                       41,Middlemarch,1871


                authors: ID,last-name,first-name,birth-year,death-year
                  e.g. 5,Gaiman,Neil,1960,NULL
                       6,Pratchett,Terry,1948,2015
                       22,Eliot,George,1819,1880

                link between books and authors: book_id,author_id
                  e.g. 41,22
                       6,5
                       6,6

                  [that is, book 41 was written by author 22, while book 6
                    was written by both author 5 and author 6]

            Note that NULL is used to represent a non-existent (or rather, future and
            unknown) year in the cases of living authors.

            NOTE TO STUDENTS: I have not specified how you will store the books/authors
            data in a BooksDataSource object. That will be up to you, in Phase 3.
        '''
        with open(books_filename, newline='') as f:
            reader = csv.reader(f)
            for row in reader:
                newEntry = {'id': 'NULL', 'title': 'NULL', 'publication_year': 'NULL'}
                newEntry['id'] = int(row[0])
                newEntry['title'] = row[1]
                newEntry['publication_year'] = int(row[2])
                books.append(newEntry)

        with open(authors_filename, newline='') as f:
            reader = csv.reader(f)
            for row in reader:
                newEntry = {'id': 'NULL', 'last_name': 'NULL', 'first_name': 'NULL', 'birth_year': 'NULL', 'death_year': 'NULL'}
                newEntry['id'] = int(row[0])
                newEntry['last_name'] = row[1]
                newEntry['first_name'] = row[2]
                newEntry['birth_year'] = int(row[3])
                if(len(row) == 5 and row[4] != 'NULL'):
                    newEntry['death_year'] = int(row[4])
                authors.append(newEntry)

        with open(books_authors_link_filename, newline='') as f:
            reader = csv.reader(f)
            for row in reader:
                newEntry = {'book_id': 'NULL', 'author_id': 'NULL'}
                newEntry['book_id'] = int(row[0])
                newEntry['author_id'] = int(row[1])
                link.append(newEntry)

    def book(self, book_id):  # DOES NOT CHECK IF BOOK_ID EXISTS IN THE DATABASE. FIX??????
        if(book_id < 0):
            raise ValueError("The provided book id is not valid!")
        for i in range(0, len(books)):
            if(int(books[i]['id']) == book_id):
                return books[i]

        ''' Returns the book with the specified ID. (See the BooksDataSource comment
            for a description of how a book is represented.)

            Raises ValueError if book_id is not a valid book ID.
        '''
        return {}

    def books(self, *, author_id=None, search_text=None, start_year=None, end_year=None, sort_by='title'):
        ''' Returns a list of all the books in this data source matching all of
            the specified non-None criteria.

                author_id - only returns books by the specified author
                search_text - only returns books whose titles contain (case-insensitively) the search text
                start_year - only returns books published during or after this year
                end_year - only returns books published during or before this year

            Note that parameters with value None do not affect the list of books returned.
            Thus, for example, calling books() with no parameters will return JSON for
            a list of all the books in the data source.

            The list of books is sorted in an order depending on the sort_by parameter:

                'year' -- sorts by publication_year, breaking ties with (case-insenstive) title
                default -- sorts by (case-insensitive) title, breaking ties with publication_year

            See the BooksDataSource comment for a description of how a book is represented.

            QUESTION: Should Python interfaces specify TypeError?
            Raises TypeError if author_id, start_year, or end_year is non-None but not an integer.
            Raises TypeError if search_text or sort_by is non-None, but not a string.

            QUESTION: How about ValueError? And if so, for which parameters?
            Raises ValueError if author_id is non-None but is not a valid author ID.
        '''
        if(not (type(author_id) == type(None) or author_id >= 0)):
            raise ValueError("Invalid author ID!")

        if(type(sort_by) != type(None) and type(sort_by) != str):
            raise TypeError("Invalid sort_by parameter!")

        if(type(search_text) != type(None) and type(search_text) != str):
            raise TypeError("Invalid search_text parameter!")

        if(type(start_year) != type(None) and type(start_year) != int):
            raise TypeError("Invalid start_year parameter!")

        if(type(end_year) != type(None) and type(end_year) != int):
            raise TypeError("Invalid end_year parameter!")

        unsorted_list = []
        for i in range(0, len(books)):
            targetBook = books[i]
            if(not targetBook in unsorted_list):
                if(author_id is None or author_id == get_book_author_ids(targetBook)):
                    if(search_text is None or search_text in targetBook['title']):
                        if(start_year is None or start_year <= targetBook['publication_year']):
                            if(end_year is None or end_year >= targetBook['publication_year']):
                                unsorted_list.append(targetBook)
        if(len(unsorted_list) > 1):
            sorted_list = []
            search_criterion = 'title'
            if(sort_by == 'year'):
                search_criterion = 'publication_year'

            while(len(unsorted_list) != 0):
                first = unsorted_list[0]
                for i in range(1, len(unsorted_list)):
                    if(search_criterion == 'title'):
                        if(unsorted_list[i][search_criterion] < first[search_criterion]):
                            first = unsorted_list[i]
                    else:
                        if(unsorted_list[i][search_criterion] > first[search_criterion]):
                            first = unsorted_list[i]
                sorted_list.append(first)
                unsorted_list.remove(first)
            return sorted_list
        else:
            return unsorted_list

    def author(self, author_id):
        if(author_id < 0):
            raise ValueError("The provided author id is not valid!")
        for i in range(0, len(authors)):
            if(int(authors[i]['id']) == author_id):
                return authors[i]
        return {}

    def authors(self, *, book_id=None, search_text=None, start_year=None, end_year=None, sort_by='birth_year'):
        ''' Returns a list of all the authors in this data source matching all of the
            specified non-None criteria.

                book_id - only returns authors of the specified book
                search_text - only returns authors whose first or last names contain
                    (case-insensitively) the search text
                start_year - only returns authors who were alive during or after
                    the specified year
                end_year - only returns authors who were alive during or before
                    the specified year

            Note that parameters with value None do not affect the list of authors returned.
            Thus, for example, calling authors() with no parameters will return JSON for
            a list of all the authors in the data source.

            The list of authors is sorted in an order depending on the sort_by parameter:

                'birth_year' - sorts by birth_year, breaking ties with (case-insenstive) last_name,
                    then (case-insensitive) first_name
                any other value - sorts by (case-insensitive) last_name, breaking ties with
                    (case-insensitive) first_name, then birth_year

            See the BooksDataSource comment for a description of how an author is represented.
        '''
        if(type(book_id) != type(None) and type(book_id) != int):
            raise TypeError("The provided parameter is not valid!")
        if(type(search_text) != type(None) and type(search_text) != str):
            raise TypeError("The provided parameter is not valid stoopid!")
        if(type(start_year) != type(None) and type(start_year) != int):
            raise TypeError("The provided parameter is not valid!")
        if(type(end_year) != type(None) and type(end_year) != int):
            raise TypeError("The provided parameter is not valid!")
        if(type(sort_by) != type(None) and type(sort_by) != str):
            raise TypeError("The provided parameter is not valid!")

        unsorted_list = []
        for i in range(0, len(authors)):
            targetAuthor = authors[i]
            if(not targetAuthor in unsorted_list):
                if(book_id is None or book_id == get_author_book_ids(targetAuthor)):
                    if(search_text is None or (search_text in targetAuthor['first_name'] or search_text in targetAuthor['last_name'])):
                        if(start_year is None or start_year >= targetAuthor['birth_year']):
                            if(end_year is None or end_year <= targetAuthor['death_year']):
                                unsorted_list.append(targetAuthor)

        return unsorted_list  # GOTTA SORT THIS

    # Note for my students: The following two methods provide no new functionality beyond
    # what the books(...) and authors(...) methods already provide. But they do represent a
    # category of methods known as "convenience methods". That is, they provide very simple
    # interfaces for a couple very common operations.
    #
    # A question for you: do you think it's worth creating and then maintaining these
    # particular convenience methods? Is books_for_author(17) better than books(author_id=17)?

    def books_for_author(self, author_id):
        ''' Returns a list of all the books written by the author with the specified author ID.
            See the BooksDataSource comment for a description of how an book is represented. '''
        return self.books(author_id=author_id)

    def authors_for_book(self, book_id):
        ''' Returns a list of all the authors of the book with the specified book ID.
            See the BooksDataSource comment for a description of how an author is represented. '''
        return self.authors(book_id=book_id)
