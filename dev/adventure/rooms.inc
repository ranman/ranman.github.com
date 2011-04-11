/* * * * * * * * * * * * * * *
 * Adventure Game Room Defs  *
 * * * * * * * * * * * * * * */
:- dynamic clean/1.
:- dynamic describe/1.

describe(graduation) :-
    writeln('Welcome to graduation, are you ready?').

describe('Dorm Room') :-
    clean(you),
    clean('Dorm Room'),
    writeln('You''re clean, the room is clean, lets invite some people!').
describe('Dorm Room') :-
    clean(you),
    writeln('You''re Clean but the room is still dirty!').
describe('Dorm Room') :-
    clean('Dorm Room'),
    writeln('Well the room is clean but you still smell!').
describe('Dorm Room') :-
    writeln('This room is nasty and so are you!\n Try taking a shower, or using that broom!').

describe('The Quad') :-
    writeln('This is The Quad, there are lots of places to go from here.').

describe('Social Life') :-
    writeln('Welcome to your social life, there is nothing for you to do here.').

describe('Girlfriend''s Room') :-
    writeln('Only the bravest of adventurers can conquer this room!').

describe('Classes') :-
    writeln('All of your classes are here').
describe('CS150/151') :-
    writeln('CS 150 and 151').
describe('CS363') :-
    writeln('Software Engineering').
describe('Capstone') :-
    writeln('Take the smartness and go to graduation!').


:- dynamic path/3.
path('Dorm Room', s, 'The Quad').

path('The Quad', n, 'Dorm Room').
path('The Quad', e, graduation).
path('The Quad', w, 'Social Life').
path('The Quad', s, 'Classes').

path('Social Life', e, 'The Quad').
path('Social Life', n, 'Girlfriend''s Room').
path('Girlfriend''s Room', s, 'Social Life').
path(graduation, w, 'The Quad').

path('Classes', n, 'The Quad').
path('Classes', d, 'CS150/151').
path('CS150/151', u, 'Classes').
path('CS150/151', d, 'CS363').
path('CS363', u, 'CS150/151').
path('CS363', d, 'Capstone').
path('Capstone', u, 'CS363').

:- dynamic access/1.
    access('Dorm Room').
    access('The Quad').
    access('Classes').
    access('CS150/151').
    access('CS363').
    access('Capstone').
    access('Social Life') :- writeln('\033[32mYou''ll have to take a shower to get there.\033[0m'), fail.
    access('Girlfriend''s Room') :- writeln('\033[32mYou''ll need to woo her first.\033[0m'), fail.
    access(graduation) :-
        item_at(friend, 'Dorm Room'),
        location(girlfriend, 'Girlfriend''s Room'),
        clean(you), clean('Dorm Room'),
        character_holding(smartness).