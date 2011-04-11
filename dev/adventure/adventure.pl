/* "Graduation" -- a simple adventure game, by Randall Hunt. */
:- include('mechanics.inc'). /* Game Mechanics */
:- include('rooms.inc'). /* Room definitions */
:- include('stuff.inc'). /* Items and Objects */
:- include('characters.inc'). /* NPCs */

/* Start the game */
start :-
    write('\033[2J'), % clear screen
    instructions,
    writeln('Ready? (press enter)'),
    get_single_char(_),
    look,
    begin.