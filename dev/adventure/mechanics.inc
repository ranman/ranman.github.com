/* * * * * * * * * * * * * * *
 * Adventure Game Mechanics  *
 * * * * * * * * * * * * * * */
:- dynamic alive/1.
    alive(you).
:- dynamic dead/1.
:- dynamic character_holding/1.

/* Movement commands */
n :- go(n).
s :- go(s).
e :- go(e).
w :- go(w).
u :- go(u).
d :- go(d).

go(Direction) :-
    location(you, Current), /* Where are we? */
    path(Current, Direction, Place), /* Can we get there? */
    access(Place), /* Locked? */
    retract(location(you, Current)),
    assert(location(you, Place)),
    look.

go(_) :-
    writeln('\033[31mYou can''t go that way.\033[0m').

look :-
    location(you, Place),
    write('\033[2J'), % clear screen
    write('\033[37m'), describe(Place), write('\033[0m'),
    writeln('Connected Places:'),
    list_rooms_at(Place),
    writeln('Objects:'),
    notice_objects_at(Place),
    writeln('Characters:'),
    notice_npc_at(Place),
    writeln('Items:'),
    notice_items_at(Place).

/* Loop over all the objects at Place */
notice_objects_at(Place) :-
    object_at(X, Place),
    tab(2), write('\033[35m'), write(X), write('\033[0m'), nl,
    fail.
notice_objects_at(_).

/* Loop over all the items at Place */
notice_items_at(Place) :-
    item_at(X, Place),
    tab(2), write('\033[35m'), write(X), write('\033[0m'), nl,
    fail.
notice_items_at(_).

/* Loop over all the chracters at Place */
notice_npc_at(Place) :-
    location(NPC, Place),
    tab(2), describe_npc(NPC),
    fail.
notice_npc_at(_).

/* Loop over all connected places */
list_rooms_at(Place) :-
    path(Place, Direction, X),
    tab(2), write('\033[32m'), write(Direction), write('\033[0m'),
    tab(2), write('\033[34m'), writeln(X), write('\033[0m'),
    fail.
list_rooms_at(_).

/* Pickup Item */
take(X) :-
    character_holding(X),
    write('\033[31mYou''re already holding '), write(X), writeln('.\033[0m').
take(X) :-
    location(you, Place),
    item_at(X, Place),
    retract(item_at(X, Place)),
    assert(character_holding(X)),
    write('Picked up: '), write(X), writeln('.').
take(_) :-
    writeln('\033[31mI don''t see that here.\033[0m').

/* Drop Item */
drop(X) :-
    character_holding(X),
    location(you, Place),
    retract(character_holding(X)),
    assert(item_at(X, Place)),
    write('Dropped item: '), write(X), write(' at '), writeln(Place).
drop(_) :-
    writeln('\033[31mYou aren''t holding it!\033[0m').
/* Inventory */
inventory :-
    character_holding(_), /* holding something */
    write('You''re holding: '),nl,
    list_possessions.
inventory :-
    writeln('\033[31mYou''re not holding anything.\033[0m').
list_possessions :-
    character_holding(X),
    tab(2),write(X),nl,
    fail.
list_possessions.
/* Write the instructions */
instructions :-
    nl,
    writeln('Enter commands using standard Prolog syntax.'),
    writeln('Available commands are:'),
    writeln('start.                 -- To start the game.'),
    writeln('n. s. e. w. u. d.      -- To go in that direction.'),
    writeln('take(Item).            -- To pick up an item.'),
    writeln('drop(Item).            -- To put down an item.'),
    writeln('use(Item/Object).      -- To manipulate an object or item'),
    writeln('look.                  -- To look around you again.'),
    writeln('talk(NPC).             -- To talk to an npc.'),
    writeln('instructions.          -- To see this dialog again.'),
    writeln('halt.                  -- To end this game and quit.'), nl.

/* End goal met? */
begin :- done.

/* Show prompt */
begin :-
    write('\033[34;5m >> \033[0m'), %blinking prompt
    write('\033[32m'), %make input green
    read(INPUT),
    write('\033[0m'),  %fix it back
    catch(call(INPUT),_,writeln('\033[31msomething went wrong\033[0m')),
    begin.

/* Win Goal */
done :-
    location(you, graduation),
    character_holding(diploma),
    writeln('You''ve got your diploma, YOU WIN!'),
    halt.
/* Did you fail? */
done :-
    dead(you),
    writeln('You lose!'),
    halt.