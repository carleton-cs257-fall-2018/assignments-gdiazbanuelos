import sys
import argparse
import json
import urllib.request
import datetime


def get_teams():
    teams_url = 'https://api.opendota.com/api/teams/'
    data_from_server = urllib.request.urlopen(teams_url).read()
    string_from_server = data_from_server.decode('utf-8')
    list_of_teams = json.loads(string_from_server)

    print("Top Dota 2 teams according to Elo rating: ")
    print("------------------------------------")
    print(list_of_teams[0]["name"])
    print("------------------------------------")
    print(list_of_teams[1]["name"])
    print("------------------------------------")
    print(list_of_teams[2]["name"])
    print("------------------------------------\n\n")


def get_player(player_id):
    player_id = 'https://api.opendota.com/api/players/86745912'
    data_from_server = urllib.request.urlopen(player_id).read()
    string_from_server = data_from_server.decode('utf-8')
    player_data = json.loads(string_from_server)
    print("\'rtz\'s current matchmaking rating (MMR): \n" + str(player_data["solo_competitive_rank"]))


def print_time():
    print("Accurate as of " + str(datetime.datetime.now()))


def main():
    get_teams()
    get_player(86745912)
    print_time()


if __name__ == '__main__':
    main()
