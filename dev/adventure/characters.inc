/* * * * * * * * * * * * * * *
 * Adventure Game Characters *
 * * * * * * * * * * * * * * */
:- dynamic location/2.
location(you, 'Dorm Room').
location(provost, graduation).
location(advisor, 'Classes').
location(friends, 'Social Life').
location(girlfriend, 'Social Life').

:- dynamic npc_holding/2.
    npc_holding(provost, diploma).

:- dynamic describe_npc/1.
    describe_npc(you) :-
        writeln('You are here').
    describe_npc(friends) :-
        writeln('friends, you should talk(friends) to them.').
    describe_npc(girlfriend) :-
        writeln('girlfriend, try talk(girlfriend), to talk to her.').
    describe_npc(provost) :-
        writeln('The provost is here with your diploma!').
    describe_npc(advisor) :-
        writeln('The advisor is here with some advice!').
:- dynamic talk/1.
    talk(provost) :-
        location(you, graduation),
        writeln('Here take a diploma.'),
        retract(npc_holding(provost,diploma)),
        assert(character_holding(diploma)).

    talk(friends) :-
        friends_quiz,
        writeln('Right! You''ve got friends now!'),
        assert(character_holding(friend)).
    talk(friends) :-
        writeln('You didn''t make friends!'),
        assert(dead(you)).

    talk(girlfriend) :- 
        character_holding(small_key),
        writeln('\033[4mOh thank you! That''s my key!\033[0m'),
        use(small_key),
        assert(location(girlfriend, 'Girlfriend''s Room')),
        retract(location(girlfriend, 'Social Life')).

    talk(girlfriend) :- 
        character_holding(flowers),
        item_at(friend, 'Dorm Room'),
        writeln('You give the girl the flowers'),
        retract(character_holding(flowers)),
        writeln('\033[4mOh thank you!\033[0m'),
        writeln('\033[32mCongrats you''ve got a girlfriend now!\033[0m'),
        writeln('\033[4mOh no I''ve lost my key. I must have left it in \033[34mThe Quad.\033[0m'),
        assert(describe_npc(girlfriend) :- writeln('Your girlfriend.')),
        assert(object_at(locked_door, 'Social Life')),
        assert(item_at(small_key, 'The Quad')).
    talk(girlfriend) :-
        writeln('You should get flowers to talk to me!').

friends_quiz :-
    writeln('\033[4mHey man what''s up?\033[0m'),
    writeln('1) Not much.'),
    writeln('2) Not much, how about you'),
    writeln('3) Go away.'),
    writeln('4) RAWWWWWR.'),
    writeln('How do you reply? (Format: 1.,2.,3.,4.)'),
    write('\033[34;5m>>>\033[0m'),
    read(INPUT),
    INPUT == 2.
    