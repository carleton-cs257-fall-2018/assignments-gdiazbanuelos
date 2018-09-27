import sys
import argparse
import json
import urllib.request

'''
base_url = 'https://api.opendota.com/api/'
match_url = 'https://api.opendota.com/api/matches/{match_id}'
player_url =  'https://api.opendota.com/api/players/{account_id}'
'''

def get_teams():
    teams_url =  'https://api.opendota.com/api/teams/' 
    data_from_server = urllib.request.urlopen(teams_url).read()
    string_from_server = data_from_server.decode('utf-8')
    list_of_teams = json.loads(string_from_server)
    print(len(list_of_teams))


def pull_match_data(match_id):
    match_url = 'https://api.opendota.com/api/matches/{0}'
    match_url = match_url.format(match_id)
    print(match_url)

def main():
    # pull_match_data(sys.argv[1])
    get_teams()

if __name__ == '__main__':
    main()
