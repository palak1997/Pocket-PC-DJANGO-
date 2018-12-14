import tweepy

consumer_key	   =   'zG6WZkEhMeAl5GFVcLThtN1OJ'
consumer_secret	   =   '1a3VsIq3rUu0itz86FI7mCkFRwMyqwRvPzEmtresg69TpHRHBv'
access_token	   =   '853684306639675392-Xk6oib94GYIhU7SFa7i3E5sFcTmBz5a'
access_token_secret=   'gK4tPV3bk2EIvUeSeHKjXxoyefLrqUd0qAoykVJgILUPM'


def login_to_twitter(consumer_key, consumer_secret, access_token, access_token_secret):
    auth = tweepy.OAuthHandler(consumer_key,  consumer_secret)
    auth.set_access_token(access_token,access_token_secret)

    api = tweepy.API(auth)
    ret = {}
    ret['api'] = api
    ret['auth'] = auth
    return  api

class tweet_image():
    def fun_tweet_image(self,filename , message):
        #filename => path
        #message => message
        api = login_to_twitter(consumer_key, consumer_secret, access_token, access_token_secret )
        api.update_with_media(filename, status=message)



class post_tweets():
        def fun_post_tweet(self,message):
            #message =>  message
            api = login_to_twitter(consumer_key, consumer_secret, access_token, access_token_secret)
            ret = api.update_status(status=message)

# if __name__ == '__main__':
#     n = int(raw_input())
#     if(n == 1):
#         MSG = raw_input("Enter Tweet")

#         post_tweets(MSG)
#     else:
#         PATH = raw_input('Enter Path / filename')
#         MSG = raw_input("Enter Tweet")


#         tweet_image(PATH , MSG)
