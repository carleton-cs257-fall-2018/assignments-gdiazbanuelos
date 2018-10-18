'''
    api_tets.py
    Gustavo Diaz
    John Sherer
'''

import matches_api
import unittest

class BooksDataSourceTester(unittest.TestCase):
    def setUp(self):
        self.api_tester = matches_api.MatchesDataSource("match.csv")

    def tearDown(self):
        pass

    def test_get_match(self):
        self.assertEqual(self.api_tester.get_match(44569),
            {'start_time': 1447777244,
                'duration': 2304,
                'tower_status_radiant': 1926,
                'tower_status_dire': 0,
                'barracks_status_dire': 0,
                'barracks_status_radiant': 63,
                'first_blood_time': 63,
                'game_mode': 22,
                'radiant_win': False,
                'negative_votes': 0,
                'positive_votes': 0})

    def test_get_match_string_id(self):
        self.assertRaises(TypeError, self.api_tester.get_match('egg'))

    def test_get_match_negative_int_id(self):
        self.assertRaises(ValueError, self.api_tester.get_match(-7))

    def test_get_match_with_float_id(self):
        self.assertRaises(TypeError, self.api_tester.get_match(651.322))

    def test_get_match_out_of_bounds_id(self):
        self.assertRaises(ValueError, self.api_tester.get_match(5000000000000))

    def test_compare(self):
        self.assertEquals(self.api_tester.compare('duration', 4000), {'greater than':680,'less than':49320})

    def test_compare_invalid_key(self):
        self.assertRaises(ValueError, self.api_tester.compare, "radiant_win", 4)

    def test_compare_int_key(self):
        self.assertRaises(TypeError, self.api_tester.compare, 30, 4)

    def test_compare_invalid_amount(self):
        self.assertRaises(TypeError, self.api_tester.compare, "duration", 'egg')

    def test_wins(self):
        self.assertEquals(self.api_tester.wins(), {"Radiant": 25943, "Dire": 24057})


if __name__ == '__main__':
    unittest.main()
