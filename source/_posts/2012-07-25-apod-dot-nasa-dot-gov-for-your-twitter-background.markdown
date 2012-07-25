---
layout: post
title: "apod.nasa.gov for your twitter background"
date: 2012-07-25 19:00
comments: true
categories: python, space
---

So I like space and I thought my twitter background should reflect that. 

Wouldn't it be cool if this:

{% img https://coderwall-assets-0.s3.amazonaws.com/uploads/picture/file/290/picture.png %}

Could be this:

{% img https://coderwall-assets-0.s3.amazonaws.com/uploads/picture/file/291/pic.png %}

requirements:
{% codeblock %}
PIL==1.1.7
requests==0.13.3
requests-oauth==0.4.1
{% endcodeblock %}

So you can probably just run (hopefully in a virtualenv):
{% codeblock %}
pip install PIL requests requests-oauth
{% endcodeblock %}

Next you'll want to use this script:
{% codeblock apod.py lang:python %}
import re
import urllib
import Image
import cStringIO
from oauth_hook import OAuthHook
import requests

# This is the APOD index page
apodbaseurl = 'http://apod.nasa.gov/apod/{}'
# This is how we look for the image on the page
regex = r'a href="(image.*)"'
# You can adjust this but twitter only allows 800k uploads
imgsize = 900, 900
# This our twitter API endpoint for changing the background
twitter_endpoint = 'http://api.twitter.com/1/account/update_profile_background_image.json'

# Create a twitter app: https://dev.twitter.com/apps/new
# After creation and clicking the generate access token button, click through
# to the oauth tab and use the info from there in the variables below.
OAuthHook.consumer_key = 'blarg'
OAuthHook.consumer_secret = 'blarg'
access_token = 'blarg'
access_token_secret = 'blarg'
# Setup the hook to call before we make a request
oauth_hook = OAuthHook(access_token, access_token_secret, header_auth=True)


def get_apod_image():
    # grab the mainpage
    apodpage = urllib.urlopen(apodbaseurl.format('astropix.html')).read()
    # find image url
    apodurl = re.search(regex, apodpage).group(1)
    # open the image file
    imgfile = urllib.urlopen(apodbaseurl.format(apodurl))
    # parse it into memory (cStringIO is faster than StringIO)
    imgstr = cStringIO.StringIO(imgfile.read())
    img = Image.open(imgstr)
    img.convert("RGB")
    # resize preserving aspect ratio
    img.thumbnail(imgsize, Image.ANTIALIAS)
    # save it in the smallest size possible
    img.save("apod.png", "PNG", optimize=True)


def update_twitter():
    client = requests.session(hooks={'pre_request': oauth_hook})
    image = open('apod.png', 'rb')
    response = client.post(twitter_endpoint, '', params={'tile': True},
                           files={'image': ('apod.png', image)})
    # lets print and return some info for troubleshooting
    print response.text
    return response

if __name__ == '__main__':
    get_apod_image()
    update_twitter()
{% endcodeblock %}

Now make a cronjob with something like:

{% codeblock %}
0 23 * * * ~/.virtualenvs/apod/bin/python ~/apod/apod.py
{% endcodeblock %}

AND BOOM! You're done.