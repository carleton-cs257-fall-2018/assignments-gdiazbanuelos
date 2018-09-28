import sys
import argparse
import json
import urllib.request


def get_teams():
    teams_url = 'https://api.opendota.com/api/teams/'
    data_from_server = urllib.request.urlopen(teams_url).read()
    string_from_server = data_from_server.decode('utf-8')
    list_of_teams = json.loads(string_from_server)
    print(list_of_teams[0]["name"])
    print("------------------------------------")
    print(list_of_teams[1]["name"])
    print("------------------------------------")
    print(list_of_teams[2]["name"])


def get_player(player_id):
    player_id = 'https://api.opendota.com/api/players/{0}'
    player_id = player_id.format(player_id)
    data_from_server = urllib.request.urlopen(player_id).read()
    string_from_server = data_from_server.decode('utf-8')
    player_data = json.loads(string_from_server)
    print(player_data['leaderboard_rank'])



def pull_match_data(match_id):
    match_url = 'https://api.opendota.com/api/matches/{0}'
    match_url = match_url.format(match_id)
    print(match_url)



def main():
    get_teams()
    get_player(86745912)

if __name__ == '__main__':
    main()
