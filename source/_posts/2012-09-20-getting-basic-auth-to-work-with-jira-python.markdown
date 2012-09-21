---
layout: post
title: "Getting basic_auth to work with jira-python"
date: 2012-09-20 15:38
comments: true
categories: python, 10gen
---
![Dark Blue Tiger tirumala septentrionis by kadavoor][titleimage]

At 10gen we use JIRA as our main bug tracker. We use a few different custom python scripts to generate charts that track everything from customer engagement to the number of server tickets linked to support tickets.

Unfortunately, before JIRA's REST interface was available, the majority of this stuff was done with the [suds][suds] package and a few hand written calls to urllib (all of this was written well before the time of the brilliant [requests][requests] package). Now we attack these problems with a more elegant weapon for a more civilized age. So, in walks [jira-python][jira-python] a tiny wrapper over JIRA's REST API written by [Ben Speakmon][Ben Speakmon]


[titleimage]: //upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Dark_Blue_Tiger_tirumala_septentrionis_by_kadavoor.JPG/512px-Dark_Blue_Tiger_tirumala_septentrionis_by_kadavoor.JPG "By Jkadavoor (Own work) [CC-BY-SA-3.0 (http://creativecommons.org/licenses/by-sa/3.0) or GFDL (http://www.gnu.org/copyleft/fdl.html)], via Wikimedia Commons"
[suds]: http://pypi.python.org/pypi/suds
[requests]: http://docs.python-requests.org/en/latest/index.html
[jira-python]: http://jira-python.readthedocs.org/en/latest/index.html
[Ben Speakmon]: https://bitbucket.org/bspeakmon/jira-python