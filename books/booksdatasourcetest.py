'''
    booksdatasourcetests.py
    Gustavo Diaz
    John Sherer
'''
import booksdatasource
import unittest

# {'id': 193, 'title': 'A Wild Sheep Chase', 'publication_year': 1982}

'''
{'id': 72, 'last_name': 'Austen', 'first_name': 'Jane',
         'birth_year': 1775, 'death_year': 1817}
'''


class BooksDataSourceTester(unittest.TestCase):

    maxDiff = None

    def setUp(self):
        self.booksdatasource_checker = booksdatasource.BooksDataSource("books.csv", "authors.csv", "books_authors.csv")

    def tearDown(self):
        pass

    def test_book(self):
        self.assertEqual(self.booksdatasource_checker.book(0), {'id': 0, 'title': 'All Clear', 'publication_year': 2010})

    def test_book_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.book, -1)

    def test_books(self):
        self.assertEqual(self.booksdatasource_checker.books()[0], {'id': 30, 'title': '1Q84', 'publication_year': 2009})

    def test_books_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.books, author_id=-1)

    def test_books_non_int_id(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, author_id="egg")

    def test_books_non_int_start_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, start_year="egg")

    def test_books_non_int_end_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, end_year="egg")

    def test_books_in_between_years(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=1980, end_year=1990), [
                        {'id': 31, 'title': 'A Wild Sheep Chase', 'publication_year': 1982},
                        {'id': 2, 'title': 'Beloved', 'publication_year': 1987},
                        {'id': 6, 'title': 'Good Omens', 'publication_year': 1990},
                        {'id': 9, 'title': 'Love in the Time of Cholera', 'publication_year': 1985},
                        {'id': 11, 'title': "Midnight's Children", 'publication_year': 1981},
                        {'id': 21, 'publication_year': 1986, 'title': 'Shards of Honor'},
                        {'id': 24, 'publication_year': 1988, 'title': 'The Satanic Verses'}])

    def test_books_after_start_year(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=2010), [{'id': 0, 'title': 'All Clear', 'publication_year': 2010}, {'id': 3, 'title': 'Blackout', 'publication_year': 2010}, {'id': 37, 'publication_year': 2015, 'title': 'The Fifth Season'}, {'id': 38, 'publication_year': 2015, 'title': 'The Obelisk Gate'},
            {'id': 35, 'publication_year': 2016, 'title': 'The Power'}, {'id': 39, 'publication_year': 2015, 'title': 'The Stone Sky'}])


    def test_books_in_before_end_years(self):
        self.assertEqual(self.booksdatasource_checker.books(end_year=1900), [{'id': 43, 'title': 'Bleak House', 'publication_year': 1852}, {'id': 5, 'title': 'Emma', 'publication_year': 1815},{'id': 44, 'title': 'Great Expectations', 'publication_year': 1860}, {'id': 7, 'title': 'Jane Eyre', 'publication_year': 1847},
            {'id': 41, 'publication_year': 1871, 'title': 'Middlemarch'},
            {'id': 13, 'publication_year': 1851, 'title': 'Moby Dick'},
            {'id': 16, 'publication_year': 1847, 'title': 'Omoo'},
            {'id': 18, 'publication_year': 1813, 'title': 'Pride and Prejudice'},
            {'id': 20, 'publication_year': 1813, 'title': 'Sense and Sensibility'},
            {'id': 42, 'publication_year': 1861, 'title': 'Silas Marner'},
            {'id': 25, 'publication_year': 1848, 'title': 'The Tenant of Wildfell Hall'},
            {'id': 40, 'publication_year': 1889, 'title': 'Three Men in a Boat (to Say Nothing of the Dog)'},
            {'id': 28, 'publication_year': 1853, 'title': 'Villette'},
            {'id': 29, 'publication_year': 1847, 'title': 'Wuthering Heights'}])

    def test_books_with_author_id(self):
        self.assertEqual(self.booksdatasource_checker.books(author_id=0), [
            {'id': 0, 'title': 'All Clear', 'publication_year': 2010},
            {'id': 3, 'title': 'Blackout', 'publication_year': 2010},
            {'id': 27, 'title': 'To Say Nothing of the Dog', 'publication_year': 1997}])

    def test_books_with_search_text(self):
        self.assertEqual(self.booksdatasource_checker.books(search_text="me"), [
            {'id': 4, 'title': 'Elmer Gantry', 'publication_year': 1927},
            {'id': 6, 'title': 'Good Omens', 'publication_year': 1990},
            {'id': 9, 'title': 'Love in the Time of Cholera', 'publication_year': 1985},
            {'id': 46, 'title': 'The Spy Who Came in From the Cold', 'publication_year': 1963},
            {'id': 26, 'publication_year': 1996, 'title': 'Thief of Time'}])

    def test_books_by_year(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=1989, sort_by='year'), [{'id': 35, 'title': 'The Power', 'publication_year': 2016},
              {'id': 37, 'publication_year': 2015, 'title': 'The Fifth Season'},
            {'id': 38, 'publication_year': 2015, 'title': 'The Obelisk Gate'},
            {'id': 39, 'publication_year': 2015, 'title': 'The Stone Sky'},
            {'id': 0, 'title': 'All Clear', 'publication_year': 2010},
            {'id': 3, 'title': 'Blackout', 'publication_year': 2010}, {'id': 30, 'publication_year': 2009, 'title': '1Q84'},
            {'id': 27, 'publication_year': 1997, 'title': 'To Say Nothing of the Dog'},
            {'id': 15, 'publication_year': 1996, 'title': 'Neverwhere'},
            {'id': 26, 'publication_year': 1996, 'title': 'Thief of Time'},
            {'id': 12, 'publication_year': 1994, 'title': 'Mirror Dance'},
            {'id': 32, 'publication_year': 1991, 'title': 'Hard-Boiled Wonderland and the End of the World'},
            {'id': 6, 'title': 'Good Omens', 'publication_year': 1990}])




    def test_books_non_string_search_text(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, search_text=1)

    def test_books_non_string_sort_by(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, sort_by=1)

    def test_author(self):
        self.assertEqual(self.booksdatasource_checker.author(0), {'id': 0, 'last_name': 'Willis', 'first_name': 'Connie', 'birth_year': 1945, 'death_year': 'NULL'})

    def test_author_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.author, -1)

    def test_authors(self):
        self.assertEqual(self.booksdatasource_checker.authors(book_id=0), [{'id': 0, 'last_name': 'Willis', 'first_name': 'Connie', 'birth_year': 1945, 'death_year': 'NULL'}])

    def test_authors_non_int_id(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.authors, book_id="egg")

    def test_authors_non_int_start_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.authors, start_year="egg")

    def test_authors_non_int_end_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.authors, end_year="egg")

    def test_authors_non_string_search_text(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.authors, search_text=1)

    def test_authors_non_string_sort_by(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.authors, sort_by=1)

    def test_books_for_author(self):
        self.assertEqual(self.booksdatasource_checker.books_for_author(0), [{'id': 0, 'title': 'All Clear', 'publication_year': 2010}, {'id': 3, 'title': 'Blackout', 'publication_year': 2010}, {'id': 27, 'title': 'To Say Nothing of the Dog', 'publication_year': 1997}])

    def test_authors_for_book(self):
        self.assertEqual(self.booksdatasource_checker.authors_for_book(0), [{'id': 0, 'last_name': 'Willis', 'first_name': 'Connie', 'birth_year': 1945, 'death_year': 'NULL'}])


if __name__ == '__main__':
    unittest.main()
