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
    def setUp(self):
        self.booksdatasource_checker = booksdatasource.BooksDataSource("books.csv", "authors.csv", "books_authors.csv")

    def tearDown(self):
        pass

    def test_book(self):
        self.assertEqual(self.booksdatasource_checker.book(0), {'id': 0, 'title': 'All Clear', 'publication_year': 2010})

    def test_books(self):
        self.assertEqual(self.booksdatasource_checker.books(author_id=0), ['Connie Willis'])

    def test_author(self):
        self.assertEqual(self.booksdatasource_checker.author(0), {'id': 0, 'last_name': 'Connie', 'first_name': 'Willis', 'birth_year': 1945, 'death_year': None})

    def test_authors(self):
        self.assertEqual(self.booksdatasource_checker.authors(book_id=0), ["All Clear"])

    def test_books_for_author(self):
        self.assertEqual(self.booksdatasource_checker.books_for_author(0), ["All Clear"])

    def test_authors_for_book(self):
        self.assertEqual(self.booksdatasource_checker.authors_for_book(0), ["Connie Willis"])


if __name__ == '__main__':
    unittest.main()
