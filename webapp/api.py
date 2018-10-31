"""
John Sherer
Gustavo Diaz
web_app_phase5
"""

import sys
import flask
import json
import psycopg2
from config import password
from config import database
from config import user
from flask import Flask, render_template
app = flask.Flask(__name__)


@app.route('/')
def greeting():
    return render_template('dotabasesite.html')


@app.route('/matches/<match_id>')
def get_match(match_id):
    cursor.execute('SELECT * FROM matches WHERE match_id = ' + str(match_id))
    match_dictionary = {}
    for row in cursor:
        match_dictionary = row
    return json.dumps(match_dictionary)


@app.route('/matches')
def get_all_matches():
    cursor.execute('SELECT * FROM matches')
    match_dictionaries = []
    for row in cursor:
        match_dictionaries.append(row)
    return json.dumps(match_dictionaries)


@app.route('/subset/<key>/<amount>')
def get_movies(key, amount):
    columns = ['id' 'start_time', 'duration', 'tower_status_radiant', 'tower_status_dire', 'barracks_status_dire', 'barracks_status_radiant', 'first_blood_time', 'game_mode', '=======', 'negative_votes', 'positive_votes']
    cursor.execute('SELECT * FROM matches')
    match_dictionaries = []
    result_dictionary = {"Greater": 0, "Less": 0}
    for row in cursor:
        match_dictionaries.append(row)
    columnIndex = columns.index(key) + 1
    if(columnIndex == -1):
        return "Did not recognize the provided key"
    for match in match_dictionaries:
        if(int(match[columnIndex]) > int(amount)):
            result_dictionary["Greater"] += 1
        else:
            result_dictionary["Less"] += 1
    return json.dumps(result_dictionary)


if __name__ == '__main__':
    connection = psycopg2.connect(database=database, user=user, password=password)
    cursor = connection.cursor()
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]))
        print('  Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
        exit()
    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)
