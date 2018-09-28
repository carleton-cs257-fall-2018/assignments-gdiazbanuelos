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
    print("\n\'rtz\'s current matchmaking rating (MMR): \n" + str(player_data["solo_competitive_rank"]) +"\n\n")


def print_time():
    print("Accurate as of " + str(datetime.datetime.now()) + "\n")


def get_user_command():
    print("Here are the two operations available: ")
    print("teams : gets the top 3 Dota 2 teams based on team Elo")
    print("player : gets the solo elo of one the top Dota 2 platers in North America")
    user_command = input("--> ")
    return user_command


def main():
    function_to_run = get_user_command()
    if(function_to_run == "teams"):
        get_teams()
        print_time()
    elif(function_to_run == "player"):
        get_player(86745912)
        print_time()
    else:
        print("\nPlease only type in either 'teams' or 'player' as the argument!\n", file=sys.stderr)
        sys.exit()


if __name__ == '__main__':
    main()
