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

    print("\nTop Dota 2 teams according to Elo rating: ")
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
    print("\nPro player Arteezy\'s current matchmaking rating (MMR): \n" + str(player_data["solo_competitive_rank"]) +"\n\n")


def print_time():
    print("Accurate as of " + str(datetime.datetime.now()) + "\n")


def main():
    if(len(sys.argv) < 2 or len(sys.argv) >= 3):
        print("Usage error: api-test.py action", file=sys.stderr)
        print("Actions available are 'teams' or 'player'", file=sys.stderr)
        sys.exit()

    if(sys.argv[1] == "teams"):
        get_teams()
        print_time()
    elif(sys.argv[1] == "player"):
        get_player(86745912)
        print_time()
    else:
        print("Usage error: api-test.py action", file=sys.stderr)
        print("Please only type in either 'teams' or 'player' as the action argument!", file=sys.stderr)
        sys.exit()


if __name__ == '__main__':
    main()
