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

    def test_book_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.book, -1)

    def test_books(self):
        self.assertEqual(self.booksdatasource_checker.books(author_id=0), ['Connie Willis'])

    def test_books_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.books, author_id=-1)

    def test_books_non_int_id(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, author_id="egg")

    def test_books_non_int_start_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, start_year="egg")

    def test_books_non_int_end_year(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, end_year="egg")

    def test_books_in_between_years(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=1980, end_year=1990), ["Beloved", "Good Omens", "Love in the Time of Cholera"])

    def test_books_after_start_year(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=2010), ["All Clear", "Blackout"])

    def test_books_in_before_end_years(self):
        self.assertEqual(self.booksdatasource_checker.books(end_year=1900), ["Emma", "Jane Eyre"])

    def test_books_with_author_id(self):
        self.assertEqual(self.booksdatasource_checker.books(author_id=0), ["All Clear", "Blackout Jane"])

    def test_books_with_search_text(self):
        self.assertEqual(self.booksdatasource_checker.books(search_text="me"), ["Elmer Gantry", "Good Omens", "Love in the Time of Cholera"])

    def test_books_by_year(self):
        self.assertEqual(self.booksdatasource_checker.books(start_year=1989, sort_by='year'), ["Good Omens", "All Clear", "Blackout"])

    def test_books_non_string_search_text(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, search_text=1)

    def test_books_non_string_sort_by(self):
        self.assertRaises(TypeError, self.booksdatasource_checker.books, sort_by=1)

    def test_author(self):
        self.assertEqual(self.booksdatasource_checker.author(0), {'id': 0, 'last_name': 'Willis', 'first_name': 'Connie', 'birth_year': 1945, 'death_year': 'NULL'})

    def test_author_invalid_id(self):
        self.assertRaises(ValueError, self.booksdatasource_checker.author, -1)

    def test_authors(self):
        self.assertEqual(self.booksdatasource_checker.authors(book_id=0), ["All Clear"])

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
        self.assertEqual(self.booksdatasource_checker.books_for_author(0), ["All Clear"])

    def test_authors_for_book(self):
        self.assertEqual(self.booksdatasource_checker.authors_for_book(0), ["Connie Willis"])


if __name__ == '__main__':
    unittest.main()
